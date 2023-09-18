/*
 * Copyright (c) 2023, the Pixnews project authors and contributors. Please see the AUTHORS file for details.
 * Use of this source code is governed by the Apache 2.0 license that can be found in the LICENSE file.
 */
import com.squareup.wire.gradle.WireExtension
import ru.pixnews.gradle.protobuf.igdb.IgdbFieldsDslGeneratorFactory

plugins {
    id("com.squareup.wire")
}

extensions.configure<WireExtension>("wire") {
    custom {
        schemaHandlerFactoryClass = IgdbFieldsDslGeneratorFactory::class.java.canonicalName
        exclusive = true
        out = layout.buildDirectory.dir("igdb-fields").get().toString()
    }
}
