/*
 * Copyright (c) 2023, the Pixnews project authors and contributors. Please see the AUTHORS file for details.
 * Use of this source code is governed by the Apache 2.0 license that can be found in the LICENSE file.
 */

import ru.pixnews.gradle.base.versionCatalog

plugins {
    id("com.google.protobuf")
}

dependencies {
    add("implementation", versionCatalog.findLibrary("protobuf.kotlinlite").orElseThrow())
}

protobuf {
    protoc {
        artifact = versionCatalog.findLibrary("protobuf.protoc").orElseThrow().get().toString()
    }

    generateProtoTasks {
        all().forEach { task ->
            task.builtins {
                create("java") {
                    option("lite")
                }
                create("kotlin") {
                    option("lite")
                }
            }
        }
    }
}
