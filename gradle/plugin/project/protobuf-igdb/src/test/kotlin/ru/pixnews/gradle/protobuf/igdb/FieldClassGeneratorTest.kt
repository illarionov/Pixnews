/*
 * Copyright (c) 2023, the Pixnews project authors and contributors. Please see the AUTHORS file for details.
 * Use of this source code is governed by the Apache 2.0 license that can be found in the LICENSE file.
 */

package ru.pixnews.gradle.protobuf.igdb

import com.squareup.wire.Syntax.PROTO_3
import com.squareup.wire.schema.Field
import com.squareup.wire.schema.Field.Label
import com.squareup.wire.schema.Location
import com.squareup.wire.schema.MessageType
import com.squareup.wire.schema.Options
import com.squareup.wire.schema.Options.Companion.FIELD_OPTIONS
import com.squareup.wire.schema.Options.Companion.FILE_OPTIONS
import com.squareup.wire.schema.ProtoType
import com.squareup.wire.schema.Type
import com.tschuchort.compiletesting.JvmCompilationResult
import com.tschuchort.compiletesting.KotlinCompilation
import com.tschuchort.compiletesting.KotlinCompilation.ExitCode.OK
import com.tschuchort.compiletesting.SourceFile
import io.kotest.matchers.shouldBe
import org.jetbrains.kotlin.compiler.plugin.ExperimentalCompilerApi
import org.junit.jupiter.api.Test
import kotlin.reflect.KMutableProperty
import kotlin.reflect.full.declaredMemberProperties
import kotlin.reflect.jvm.isAccessible

@OptIn(ExperimentalCompilerApi::class)
class FieldClassGeneratorTest {
    @Test
    fun `generated field class should compile`() {
        val gameType = DEFAULT_MESSAGE_TYPE.copy(
            type = ProtoType.get("ru.pixnews.igdbclient.model.Game"),
            name = "Game",
            declaredFields = listOf(
                DEFAULT_FIELD.copy(
                    name = "id",
                    tag = 1,
                    elementType = "uint64",
                ).forceSetType(ProtoType.UINT64),
                DEFAULT_FIELD.copy(
                    name = "parent",
                    tag = 1,
                    elementType = "AgeRating",
                    label = Label.REPEATED,
                ).forceSetType(ProtoType.get("ru.pixnews.igdbclient.model.AgeRating")),
            ),
        )

        val ageRatintType = DEFAULT_MESSAGE_TYPE.copy(
            type = ProtoType.get("ru.pixnews.igdbclient.model.AgeRating"),
            name = "AgeRating",
            declaredFields = listOf(
                DEFAULT_FIELD.copy(
                    name = "id",
                    namespaces = listOf("ru.pixnews.igdbclient.model", "AgeRating"),
                    tag = 1,
                    elementType = "uint64",
                ).forceSetType(ProtoType.UINT64),
            ),
        )

        val compilationResult = compileFieldsClass(gameType, ageRatintType)
        compilationResult.exitCode shouldBe OK
    }

    private fun compileFieldsClass(vararg types: Type): JvmCompilationResult {
        val generatedSources = types
            .map { type ->
                val generatedSourceText = FieldClassGenerator(type).invoke()

                SourceFile.kotlin(
                    generatedSourceText.filePath.last(),
                    generatedSourceText.content,
                )
            }

        return KotlinCompilation().apply {
            sources = listOf(
                IGDB_FIELD_DSL_STUB,
                IGDB_REQUEST_FIELD_STUB,
                IGDB_GAME_MODEL_STUB,
                IGDB_AGE_RATING_MODEL_STUB,
                IGDB_REQUEST_FIELDS_STUB,
            ) + generatedSources
            inheritClassPath = false
            messageOutputStream = System.out
        }.compile()
    }

    private companion object {
        val DEFAULT_MESSAGE_TYPE = MessageType(
            type = ProtoType.get("ru.pixnews.igdbclient.model.Game"),
            location = Location.get("/datasource-igdb/src/main/proto/igdbapi.proto:469:1"),
            documentation = "Test documentation",
            name = "Game",
            declaredFields = listOf(),
            extensionFields = mutableListOf(),
            oneOfs = listOf(),
            nestedTypes = listOf(),
            nestedExtendList = listOf(),
            extensionsList = listOf(),
            reserveds = listOf(),
            options = Options(FILE_OPTIONS, listOf()),
            syntax = PROTO_3,
        )
        val DEFAULT_FIELD = Field(
            namespaces = listOf(
                "ru.pixnews.igdbclient.model",
                "Game",
            ),
            location = Location.get("/datasource-igdb/src/main/proto/igdbapi.proto:470:5"),
            label = null,
            name = "id",
            documentation = "",
            tag = 1,
            default = null,
            elementType = "uint64",
            options = Options(FIELD_OPTIONS, listOf()),
            isExtension = false,
            isOneOf = false,
            declaredJsonName = null,
        )
        val IGDB_FIELD_DSL_STUB = SourceFile.kotlin(
            "IgdbFieldDsl.kt",
            """
                package ru.pixnews.feature.calendar.datasource.igdb.dsl

                @DslMarker
                @Target(AnnotationTarget.CLASS, AnnotationTarget.TYPEALIAS, AnnotationTarget.TYPE, AnnotationTarget.FUNCTION)
                public annotation class IgdbFieldDsl
            """.trimIndent(),
        )
        val IGDB_REQUEST_FIELD_STUB = SourceFile.kotlin(
            "IgdbRequestField.kt",
            """
                package ru.pixnews.feature.calendar.datasource.igdb.dsl

                import kotlin.reflect.KClass

                public data class IgdbRequestField<O : Any> internal constructor(
                    public val igdbFieldName: String,
                    public val fieldClass: KClass<O>,
                    public val parent: IgdbRequestField<*>? = null,
                ) {
                    public val igdbName: String
                        get() = if (parent == null) {
                            this.igdbFieldName
                        } else {
                            parent.igdbName + "." + this.igdbFieldName
                        }

                    override fun toString(): String = igdbName
                }
            """.trimIndent(),
        )
        val IGDB_GAME_MODEL_STUB = SourceFile.kotlin(
            "Game.kt",
            """
                package ru.pixnews.igdbclient.model
                public class Game {
                    public companion object
                }
            """.trimIndent(),
        )
        val IGDB_AGE_RATING_MODEL_STUB = SourceFile.kotlin(
            "AgeRating.kt",
            """
                package ru.pixnews.igdbclient.model
                public class AgeRating {
                    public companion object
                }
            """.trimIndent(),
        )
        val IGDB_REQUEST_FIELDS_STUB = SourceFile.kotlin(
            "IgdbRequestFields.kt",
            """
                package ru.pixnews.feature.calendar.datasource.igdb.field

                import ru.pixnews.feature.calendar.datasource.igdb.dsl.IgdbFieldDsl
                import ru.pixnews.feature.calendar.datasource.igdb.dsl.IgdbRequestField

                @IgdbFieldDsl
                public sealed class IgdbRequestFields<out T : Any>(
                    protected val parentIgdbField: IgdbRequestField<*>? = null,
                ) {
                    public val all: IgdbRequestField<out T> get() = IgdbRequestField("*", Nothing::class, parentIgdbField)
                }
            """.trimIndent(),
        )
        private val fieldTypeSetter = Field::class.declaredMemberProperties
            .filterIsInstance<KMutableProperty<ProtoType?>>()
            .first { it.name == "type" }
            .setter.apply {
                this.isAccessible = true
            }

        private fun Field.forceSetType(type: ProtoType?): Field {
            fieldTypeSetter.call(this, type)
            return this
        }
    }
}
