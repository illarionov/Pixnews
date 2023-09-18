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
import com.squareup.wire.schema.Type
import okio.Path

public class IgdbFieldsDslGeneratorFactory : SchemaHandler.Factory {
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

    override fun handle(service: com.squareup.wire.schema.Service, context: Context): List<Path> {
        return listOf()
    }

    override fun handle(type: Type, context: Context): Path? {
        if ((type is EnumType)
            || (type.type.simpleName == "Count")
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
        context: SchemaHandler.Context,
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
}
