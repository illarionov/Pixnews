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

public class IgdbFieldsDslGeneratorFactory : SchemaHandler.Factory {
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

private class IgdbFieldsDslGenerator : SchemaHandler() {
    override fun handle(extend: Extend, field: Field, context: Context): Path? {
        return null
    }

    override fun handle(service: Service, context: Context): List<Path> {
        return listOf()
    }

    override fun handle(type: Type, context: Context): Path? {
        if ((type is EnumType)
            || (type.name in EXCLUDED_MESSAGES)
            || (type.type.simpleName.endsWith("Result"))
        ) return null

        return writeFieldsDslFileFile(
            type.type,
            FieldClassGenerator(type).invoke(),
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
        val path = outDirectory / toPath(protoType).joinToString(separator = "/")
        fileSystem.createDirectories(path.parent!!)
        fileSystem.write(path) { writeUtf8(fileContent) }
        return path
    }

    /** Returns a path like `igdb/field/GameFields.kt`. */
    private fun toPath(protoType: ProtoType): List<String> {
        val result = protoType.toString().split(".").toMutableList()
        result[result.lastIndex] += "Fields.kt"
        return result
    }

    private companion object {
        val EXCLUDED_MESSAGES: Set<String> = setOf(
            "Count",
            "MultiQueryResult",
            "MultiQueryResultArray"
        )
    }
}
