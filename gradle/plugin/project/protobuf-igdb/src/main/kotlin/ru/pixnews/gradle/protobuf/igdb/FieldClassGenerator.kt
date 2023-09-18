/*
 * Copyright (c) 2023, the Pixnews project authors and contributors. Please see the AUTHORS file for details.
 * Use of this source code is governed by the Apache 2.0 license that can be found in the LICENSE file.
 */

package ru.pixnews.gradle.protobuf.igdb

import com.squareup.kotlinpoet.ClassName
import com.squareup.kotlinpoet.FileSpec
import com.squareup.kotlinpoet.FunSpec
import com.squareup.kotlinpoet.KModifier.INTERNAL
import com.squareup.kotlinpoet.KModifier.PRIVATE
import com.squareup.kotlinpoet.KModifier.PUBLIC
import com.squareup.kotlinpoet.ParameterSpec
import com.squareup.kotlinpoet.ParameterizedTypeName.Companion.parameterizedBy
import com.squareup.kotlinpoet.PropertySpec
import com.squareup.kotlinpoet.STAR
import com.squareup.kotlinpoet.STRING
import com.squareup.kotlinpoet.TypeSpec
import com.squareup.wire.schema.EnclosingType
import com.squareup.wire.schema.Field
import com.squareup.wire.schema.MessageType
import com.squareup.wire.schema.Type

internal class FieldClassGenerator(
    private val type: Type,
) : () -> String {
    private val outputFieldsClassName: ClassName = ClassName(PACKAGE_NAME, type.name + "Fields")
    private val outputFileName = outputFieldsClassName.simpleName
    private val igdbclientModelClass = ClassName(IGDBCLIENT_MODEL_PACKAGE_NAME, type.name)
    private val igdbclientModelClassCompanion = ClassName(IGDBCLIENT_MODEL_PACKAGE_NAME, type.name, "Companion")
    private val fieldsReturnType = IGDB_REQUEST_FIELD_CLASS.parameterizedBy(igdbclientModelClass)

    private val parentConstructorParameter = ParameterSpec.builder(
        "parentIgdbField",
        IGDB_REQUEST_FIELD_CLASS.parameterizedBy(STAR).copy(nullable = true)
    )
            .defaultValue("null")
            .build()

    /***
     * ```
     * private fun named(igdbFieldName: String): IgdbRequestField<Game> = IgdbRequestField(...
     * ```
     */
    private val namedFunction: FunSpec = run {
        val igdbFieldNameParameter = ParameterSpec.builder("igdbFieldName", STRING).build()
        FunSpec.builder("named")
            .addModifiers(PRIVATE)
            .returns(fieldsReturnType)
            .addParameter(igdbFieldNameParameter)
            .addStatement(
                """return %T(
            %N,
            %T::class,
            %N,
        )""".trimIndent(),
                IGDB_REQUEST_FIELD_CLASS,
                igdbFieldNameParameter,
                igdbclientModelClass,
                parentConstructorParameter
            )
            .build()
    }

    override fun invoke(): String = FileSpec
        .builder(PACKAGE_NAME, outputFileName)
        .addFunction(generateClassCompanionFieldMethod())
        .addType(generateFieldsClass())
        .build()
        .toString()

    /**
     * ```
     * public fun Game.Companion.field(): GameFields = GameFields()
     * ```
     */
    private fun generateClassCompanionFieldMethod(): FunSpec = FunSpec.builder("field")
        .receiver(igdbclientModelClassCompanion)
        .addModifiers(PUBLIC)
        .returns(outputFieldsClassName)
        .addStatement("return %T()", outputFieldsClassName)
        .build()

    /**
     * ```
     * public class GameFields...
     * ```
     */
    private fun generateFieldsClass(): TypeSpec {

        val primaryConstructor = FunSpec.constructorBuilder()
            .addModifiers(INTERNAL)
            .addParameter(parentConstructorParameter)
            .build()

        val classBuilder = TypeSpec.classBuilder(outputFieldsClassName)
            .addModifiers(PUBLIC)
            .addAnnotation(IGDB_FIELD_DSL_CLASS)
            .primaryConstructor(primaryConstructor)
            .superclass(IGDB_REQUEST_FIELDS_BASE_CLASS.parameterizedBy(igdbclientModelClass))
            .addSuperclassConstructorParameter("%N", parentConstructorParameter)

        when (type) {
            is MessageType -> classBuilder.addDeclaredFields(type)
            is EnclosingType -> Unit //TODO
            else -> Unit
        }

        classBuilder.addFunction(namedFunction)
        return classBuilder.build()
    }

    /**
     * ```
     * public val id: IgdbRequestField<Game> get() = named("id")
     * ```
     */
    private fun TypeSpec.Builder.addDeclaredFields(messageType: MessageType) {
        val properties = messageType.declaredFields
            .map { field ->
                PropertySpec.builder(field.name, fieldsReturnType, PUBLIC)
                    .getter(generateGetterForField(field))
                    .build()
            }
        this.addProperties(properties)
    }

    private fun generateGetterForField(field: Field): FunSpec {
        return FunSpec.getterBuilder()
            .addStatement("return %N(%S)", namedFunction, field.name)
            .build()
    }

    private companion object {
        const val PACKAGE_NAME = "ru.pixnews.feature.calendar.datasource.igdb.field"
        const val IGDBCLIENT_MODEL_PACKAGE_NAME = "ru.pixnews.igdbclient.model"
        val IGDB_FIELD_DSL_CLASS = ClassName("ru.pixnews.feature.calendar.datasource.igdb.dsl", "IgdbFieldDsl")
        val IGDB_REQUEST_FIELD_CLASS = ClassName("ru.pixnews.feature.calendar.datasource.igdb.dsl", "IgdbRequestField")
        val IGDB_REQUEST_FIELDS_BASE_CLASS = ClassName(
            "ru.pixnews.feature.calendar.datasource.igdb.field",
            "IgdbRequestFields",
        )
    }
}


