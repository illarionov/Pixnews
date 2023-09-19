/*
 * Copyright (c) 2023, the Pixnews project authors and contributors. Please see the AUTHORS file for details.
 * Use of this source code is governed by the Apache 2.0 license that can be found in the LICENSE file.
 */

package ru.pixnews.gradle.protobuf.igdb

import com.squareup.wire.schema.EnumType
import com.squareup.wire.schema.Extend
import com.squareup.wire.schema.Field
import com.squareup.wire.schema.ProtoType
import com.squareup.wire.schema.SchemaHandler
import com.squareup.wire.schema.Service
import com.squareup.wire.schema.Type
import okio.Path
import ru.pixnews.gradle.protobuf.igdb.FieldClassGenerator.Companion.getFieldsClassPath

public class IgdbFieldsDslGeneratorFactory : SchemaHandler.Factory {
    @Suppress("WRONG_OVERLOADING_FUNCTION_ARGUMENTS")
    @Deprecated("Deprecated in parent")
    override fun create(): SchemaHandler = error("Should not be called")

    override fun create(
        includes: List<String>,
        excludes: List<String>,
        exclusive: Boolean,
        outDirectory: String,
        options: Map<String, String>,
    ): SchemaHandler = IgdbFieldsDslGenerator()
}

private class IgdbFieldsDslGenerator(
    val fieldClassGenerator: (Type, Context) -> String = { type, _ -> FieldClassGenerator(type).invoke() },
) : SchemaHandler() {
    override fun handle(extend: Extend, field: Field, context: Context): Path? = null

    override fun handle(service: Service, context: Context): List<Path> = listOf()

    override fun handle(type: Type, context: Context): Path? {
        if ((type is EnumType) ||
            (type.name in EXCLUDED_MESSAGES) ||
            (type.type.simpleName.endsWith("Result"))
        ) {
            return null
        }

        return writeFieldsDslFileFile(
            type.type,
            fieldClassGenerator(type, context),
            context,
        )
    }

    private fun writeFieldsDslFileFile(
        protoType: ProtoType,
        fileContent: String,
        context: Context,
    ): Path {
        val outDirectory = context.outDirectory
        val fileSystem = context.fileSystem
        val path = outDirectory / getFieldsClassPath(protoType).joinToString(separator = "/")
        fileSystem.createDirectories(path.parent!!)
        fileSystem.write(path) { writeUtf8(fileContent) }
        return path
    }

    internal companion object {
        val EXCLUDED_MESSAGES: Set<String> = setOf(
            "Count",
            "MultiQueryResult",
            "MultiQueryResultArray",
        )
    }
}
