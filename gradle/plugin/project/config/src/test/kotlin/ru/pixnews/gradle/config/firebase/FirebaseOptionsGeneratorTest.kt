/*
 * Copyright 2023 Alexey Illarionov
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package ru.pixnews.gradle.config.firebase

import com.squareup.kotlinpoet.ClassName
import com.tschuchort.compiletesting.KotlinCompilation
import com.tschuchort.compiletesting.KotlinCompilation.ExitCode.OK
import com.tschuchort.compiletesting.SourceFile
import io.kotest.matchers.shouldBe
import org.jetbrains.kotlin.compiler.plugin.ExperimentalCompilerApi
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.io.TempDir
import java.io.File

@OptIn(ExperimentalCompilerApi::class)
class FirebaseOptionsGeneratorTest {
    @TempDir
    var codeGenDir: File? = null

    @Test
    fun `Generated config should compile`() {
        val localFirebaseOptions = LocalFirebaseOptions(
            projectId = "PROJECT_ID",
            apiKey = "API_KEY",
            applicationId = "APPLICATION_ID",
            databaseUrl = "DATABASE_TRACKING_URL",
            gaTrackingId = "GA_TRACKING_ID",
            gcmSenderId = "GCM_SENDER_ID",
            storageBucket = "STORAGE_BUCKET",
        )
        val compilationResult = compileFirebaseOptions(
            localFirebaseOptions,
            codeGenDir!!,
        )
        compilationResult.exitCode shouldBe OK
    }

    private fun compileFirebaseOptions(
        options: LocalFirebaseOptions,
        codeGenDir: File,
    ): KotlinCompilation.Result {
        FirebaseOptionsGenerator(
            options = options,
            codeGenDir = codeGenDir,
            outputObjectClassName = ClassName("com.test", "GeneratedFirebaseOptions"),
            propertyName = "firebaseOptions",
        ).generate()

        val firebaseOptionsStub = SourceFile.fromPath(
            File(
                javaClass.classLoader.getResource("FirebaseOptions.java")?.file
                    ?: error("No StubFirebaseOptions.java"),
            ),
        )

        val generatedSource = SourceFile.fromPath(
            File(codeGenDir, "com/test/GeneratedFirebaseOptions.kt"),
        )

        return KotlinCompilation().apply {
            sources = listOf(firebaseOptionsStub, generatedSource)
            inheritClassPath = false
            messageOutputStream = System.out
        }.compile()
    }
}
