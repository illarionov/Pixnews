/*
 * Copyright (c) 2023, the Pixnews project authors and contributors. Please see the AUTHORS file for details.
 * Use of this source code is governed by the Apache 2.0 license that can be found in the LICENSE file.
 */

package ru.pixnews.gradle.config.igdb

import com.squareup.kotlinpoet.ClassName
import com.squareup.kotlinpoet.FileSpec
import com.squareup.kotlinpoet.KModifier.INTERNAL
import com.squareup.kotlinpoet.KModifier.OVERRIDE
import com.squareup.kotlinpoet.PropertySpec
import com.squareup.kotlinpoet.STRING
import com.squareup.kotlinpoet.TypeSpec
import java.io.File

class IgdbOptionsGenerator(
    private val options: LocalIgdbOptions,
    private val codeGenDir: File,
    private val outputObjectClassName: ClassName = ClassName("ru.pixnews.config", "GeneratedIgdbClientConfig"),
) {
    fun generate() {
        val outputFileName = outputObjectClassName.simpleName
        val packageName = outputObjectClassName.packageName

        val objectSpec = TypeSpec.objectBuilder(outputObjectClassName)
            .addModifiers(INTERNAL)
            .addSuperinterface(igdbConfigInterfaceName)

        objectSpec.apply {
            options.igdbBaseUrl?.let { igdbBaseUrl ->
                addProperty(overrideStringValPropertySpec("baseUrl", igdbBaseUrl))
            }
            options.igdbApiKey?.let { apiKey ->
                addProperty(overrideStringValPropertySpec("apiKey", apiKey))
            }
            if (options.isTwitchAuthActive()) {
                addProperty(
                    PropertySpec.builder("twitchAuth", twitchAuthInterfaceName, OVERRIDE)
                        .initializer("%L", createAnonymousTwitchAuthObject())
                        .build(),
                )
            }
        }

        val fileContent = FileSpec
            .builder(packageName, outputFileName)
            .addType(objectSpec.build())
            .build()
        fileContent.writeTo(codeGenDir)
    }

    private fun createAnonymousTwitchAuthObject(): TypeSpec {
        return TypeSpec.anonymousClassBuilder().apply {
            addSuperinterface(twitchAuthInterfaceName)
            twitchTokenObjectProperties.forEach { (valueProvider, propertyName) ->
                valueProvider(options)?.let { propertyValue ->
                    addProperty(overrideStringValPropertySpec(propertyName, propertyValue))
                }
            }
        }.build()
    }

    private companion object {
        val igdbConfigInterfaceName = ClassName(
            "ru.pixnews.foundation.appconfig",
            "IgdbClientConfig",
        )
        val twitchAuthInterfaceName = ClassName(
            "ru.pixnews.foundation.appconfig.IgdbClientConfig",
            "TwitchAuth",
        )
        val twitchTokenObjectProperties = listOf(
            LocalIgdbOptions::igdbTwitchClientId to "clientId",
            LocalIgdbOptions::igdbTwitchClientSecret to "clientSecret",
            LocalIgdbOptions::igdbTwitchToken to "token",
        )

        fun overrideStringValPropertySpec(name: String, value: String): PropertySpec {
            return PropertySpec.builder(name, STRING, OVERRIDE)
                .initializer("%P", value)
                .build()
        }

        private fun LocalIgdbOptions.isTwitchAuthActive(): Boolean = twitchTokenObjectProperties.any {
            it.first.get(this) != null
        }
    }
}
