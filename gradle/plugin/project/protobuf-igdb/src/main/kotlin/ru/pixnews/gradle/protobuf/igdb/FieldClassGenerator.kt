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
import com.squareup.kotlinpoet.TypeName
import com.squareup.kotlinpoet.TypeSpec
import com.squareup.wire.schema.EnclosingType
import com.squareup.wire.schema.Field
import com.squareup.wire.schema.MessageType
import com.squareup.wire.schema.ProtoType
import com.squareup.wire.schema.Type
import java.util.Locale

internal class FieldClassGenerator(
    private val type: Type,
) : () -> String {
    private val outputFieldsClassName: ClassName = outputFieldsClassName(type.name)
    private val outputFileName = outputFieldsClassName.simpleName
    private val igdbclientModel = ClassName(IGDBCLIENT_MODEL_PACKAGE_NAME, type.name)
    private val igdbclientModelCompanion = ClassName(IGDBCLIENT_MODEL_PACKAGE_NAME, type.name, "Companion")
    private val fieldsReturnType = IGDB_REQUEST_FIELD_CLASS.parameterizedBy(igdbclientModel)

    /**
     * ```
     * private val _gameFieldsInstance = IgdbGameFields()
     * ```
     */
    private val backingInstance: PropertySpec = PropertySpec.builder(
        "_${outputFieldsClassName.simpleName.replaceFirstChar { it.lowercase(Locale.ROOT) }}Instance",
        outputFieldsClassName,
    )
        .addModifiers(PRIVATE)
        .initializer("%T()", outputFieldsClassName)
        .build()

    /**
     * ```
     * public val Game.Companion.field: IgdbGameFields get() = _gameFieldsInstance
     * ```
     */
    private val classCompanionFieldFactory: PropertySpec = PropertySpec.builder("field", outputFieldsClassName)
        .receiver(igdbclientModelCompanion)
        .addModifiers(PUBLIC)
        .getter(
            FunSpec.getterBuilder()
                .addStatement("return %N", backingInstance)
                .build(),
        )
        .build()
    private val parentConstructorParameter = ParameterSpec.builder(
        "parentIgdbField",
        IGDB_REQUEST_FIELD_CLASS.parameterizedBy(STAR).copy(nullable = true),
    )
        .defaultValue("null")
        .build()

    /***
     * ```
     * private fun named(igdbFieldName: String): IgdbRequestField<Game> = IgdbRequestField(...
     * ```
     */
    private val namedFunc: FunSpec = run {
        val igdbFieldNameParameter = ParameterSpec.builder("igdbFieldName", STRING).build()
        FunSpec.builder("named")
            .addModifiers(PRIVATE)
            .returns(fieldsReturnType)
            .addParameter(igdbFieldNameParameter)
            .addStatement(
                "return %T(%N, %T::class, %N)",
                IGDB_REQUEST_FIELD_CLASS,
                igdbFieldNameParameter,
                igdbclientModel,
                parentConstructorParameter,
            )
            .build()
    }

    override fun invoke(): String = FileSpec
        .builder(PACKAGE_NAME, outputFileName)
        .addProperty(backingInstance)
        .addProperty(classCompanionFieldFactory)
        .addType(generateFieldsClass())
        .build()
        .toString()

    /**
     * ```
     * public class GameFields internal constructor()...
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
            .superclass(IGDB_REQUEST_FIELDS_BASE_CLASS.parameterizedBy(igdbclientModel))
            .addSuperclassConstructorParameter("%N", parentConstructorParameter)

        when (type) {
            is MessageType -> classBuilder.addProperties(type.declaredFields.map(::generateProperty))
            is EnclosingType -> Unit
            else -> Unit
        }

        classBuilder.addFunction(namedFunc)
        return classBuilder.build()
    }

    /**
     * ```
     * public val id: IgdbRequestField<Game> get() = named("id")
     * public val age_ratings: AgeRatingFields<Game> get() = AgeRatingFields(named("age_ratings"))
     * ```
     */
    private fun generateProperty(field: Field): PropertySpec {
        val returnType: TypeName
        val getter: FunSpec
        if (field.isIgdbObjectModel()) {
            val fieldFieldsClass = outputFieldsClassName(field.type?.simpleName ?: error("field.type not set"))
            returnType = fieldFieldsClass
            getter = FunSpec.getterBuilder()
                .addStatement("return %T(%N(%S))", fieldFieldsClass, namedFunc, field.name)
                .build()
        } else {
            returnType = fieldsReturnType
            getter = FunSpec.getterBuilder()
                .addStatement("return %N(%S)", namedFunc, field.name)
                .build()
        }

        return PropertySpec.builder(field.name, returnType, PUBLIC)
            .getter(getter)
            .build()
    }

    internal companion object {
        const val PACKAGE_NAME = "ru.pixnews.feature.calendar.datasource.igdb.field"
        const val IGDBCLIENT_MODEL_PACKAGE_NAME = "ru.pixnews.igdbclient.model"
        val IGDB_FIELD_DSL_CLASS = ClassName("ru.pixnews.feature.calendar.datasource.igdb.dsl", "IgdbFieldDsl")
        val IGDB_REQUEST_FIELD_CLASS = ClassName("ru.pixnews.feature.calendar.datasource.igdb.dsl", "IgdbRequestField")
        val IGDB_REQUEST_FIELDS_BASE_CLASS = ClassName(
            "ru.pixnews.feature.calendar.datasource.igdb.field",
            "IgdbRequestFields",
        )

        /** Returns a path like `igdb/field/GameFields.kt`. */
        fun getFieldsClassPath(protoType: ProtoType): List<String> {
            val result = protoType.toString().split(".").toMutableList()
            result[result.lastIndex] += "Fields.kt"
            return result
        }

        private fun outputFieldsClassName(typeName: String): ClassName = ClassName(PACKAGE_NAME, typeName + "Fields")

        private fun Field.isIgdbObjectModel(): Boolean = this.type?.let { type ->
            when {
                type.toString().endsWith("Enum") -> false
                type.enclosingTypeOrPackage == IGDBCLIENT_MODEL_PACKAGE_NAME -> true
                else -> false
            }
        } ?: false
    }
}
