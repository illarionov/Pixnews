/*
 * Copyright (c) 2023-2024, the Pixnews project authors and contributors. Please see the AUTHORS file for details.
 * Use of this source code is governed by the Apache 2.0 license that can be found in the LICENSE file.
 */

@file:Suppress("PackageDirectoryMismatch")

package ru.pixnews.gradle.project.config.igdb

import com.squareup.kotlinpoet.ClassName
import com.tschuchort.compiletesting.JvmCompilationResult
import com.tschuchort.compiletesting.KotlinCompilation
import com.tschuchort.compiletesting.KotlinCompilation.ExitCode.OK
import com.tschuchort.compiletesting.SourceFile
import io.kotest.matchers.shouldBe
import org.jetbrains.kotlin.compiler.plugin.ExperimentalCompilerApi
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.io.TempDir
import java.io.File

@OptIn(ExperimentalCompilerApi::class)
class IgdbOptionsGeneratorTest {
    @TempDir
    var codeGenDir: File? = null

    @Test
    fun `Generated config should compile`() {
        val localIgdbOptions = LocalIgdbOptions(
            igdbBaseUrl = "https://example.com/v4/",
            igdbApiKey = "test api key",
            igdbTwitchClientId = "test twitch client id",
            igdbTwitchClientSecret = "test twitch client secret",
            igdbTwitchToken = "test twitch token",
        )

        val compilationResult = compileFirebaseOptions(
            localIgdbOptions,
            codeGenDir!!,
        )
        compilationResult.exitCode shouldBe OK
    }

    private fun compileFirebaseOptions(
        options: LocalIgdbOptions,
        codeGenDir: File,
    ): JvmCompilationResult {
        IgdbOptionsGenerator(
            options = options,
            codeGenDir = codeGenDir,
            outputObjectClassName = ClassName("com.test", "GeneratedIgdbOptions"),
        ).generate()

        val igdbClientConfigInterfaceStub = SourceFile.kotlin(
            "AppConfig.kt",
            """
                    package ru.pixnews.foundation.appconfig

                    public interface IgdbClientConfig {
                        public val baseUrl: String get() = "https://api.igdb.com/v4/"
                        public val apiKey: String get() = ""
                        public val twitchAuth: TwitchAuth? get() = null
                        public interface TwitchAuth {
                            public val clientId: String get() = ""
                            public val clientSecret: String get() = ""
                            public val token: String? get() = null
                        }
                    }
            """,
        )

        val generatedSource = SourceFile.fromPath(
            File(codeGenDir, "com/test/GeneratedIgdbOptions.kt"),
        )

        return KotlinCompilation().apply {
            sources = listOf(igdbClientConfigInterfaceStub, generatedSource)
            inheritClassPath = false
            messageOutputStream = System.out
        }.compile()
    }
}
