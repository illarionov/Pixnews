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
import com.squareup.kotlinpoet.FileSpec
import com.squareup.kotlinpoet.KModifier.INTERNAL
import com.squareup.kotlinpoet.PropertySpec
import com.squareup.kotlinpoet.TypeSpec
import com.squareup.kotlinpoet.buildCodeBlock
import java.io.File

internal class FirebaseOptionsGenerator(
    private val options: LocalFirebaseOptions,
    private val codeGenDir: File,
    private val outputObjectClassName: ClassName = ClassName("ru.pixnews.firebase", "GeneratedFirebaseOptions"),
    private val propertyName: String = "firebaseOptions",
) {
    fun generate() {
        val outputFileName = outputObjectClassName.simpleName
        val packageName = outputObjectClassName.packageName

        val firebaseOptionsProperty = PropertySpec.builder(
            name = propertyName,
            type = firebaseOptionsClassName,
            INTERNAL,
        )
            .initializer(
                buildCodeBlock {
                    addStatement("%T()", firebaseOptionsBuilderClassName)
                    firebaseBuilderMethods.forEach { (statement, valueFactory) ->
                        valueFactory(options)?.let {
                            addStatement(".$statement(%S)", it)
                        }
                    }
                    addStatement(".build()")
                },
            )
            .build()

        val objectSpec = TypeSpec.objectBuilder(outputObjectClassName)
            .addModifiers(INTERNAL)
            .addProperty(firebaseOptionsProperty)
            .build()

        val fileContent = FileSpec
            .builder(packageName, outputFileName)
            .addType(objectSpec)
            .build()
        fileContent.writeTo(codeGenDir)
    }

    private companion object {
        const val DUMMY_APPLICATION_ID = "DUMMY_APPLICATION_ID"
        const val DUMMY_API_KEY = "DUMMY_API_KEY"
        val firebaseOptionsClassName = ClassName(
            "com.google.firebase",
            "FirebaseOptions",
        )
        val firebaseOptionsBuilderClassName = ClassName(
            "com.google.firebase",
            "FirebaseOptions",
            "Builder",
        )
        val firebaseBuilderMethods: List<Pair<String, (LocalFirebaseOptions) -> String?>> = listOf(
            "setProjectId" to LocalFirebaseOptions::projectId,
            "setApiKey" to { options -> options.apiKey ?: DUMMY_API_KEY },
            "setApplicationId" to { options -> options.applicationId ?: DUMMY_APPLICATION_ID },
            "setDatabaseUrl" to LocalFirebaseOptions::databaseUrl,
            "setGaTrackingId" to LocalFirebaseOptions::gaTrackingId,
            "setGcmSenderId" to LocalFirebaseOptions::gcmSenderId,
            "setStorageBucket" to LocalFirebaseOptions::storageBucket,
        )
    }
}
