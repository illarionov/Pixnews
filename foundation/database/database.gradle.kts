/*
 * Copyright (c) 2023, the Pixnews project authors and contributors. Please see the AUTHORS file for details.
 * Use of this source code is governed by the Apache 2.0 license that can be found in the LICENSE file.
 */
plugins {
    id("ru.pixnews.gradle.android.library")
    id("ru.pixnews.gradle.android.room")
    id("ru.pixnews.gradle.di.anvil-factories")
    id("ru.pixnews.gradle.protobuf-wire")
    alias(libs.plugins.prefiller)
}

pixnews {
    compose.set(false)
}

android {
    namespace = "ru.pixnews.foundation.database"
    buildTypes {
        create("instrumentedTests") {
            initWith(getByName("debug"))
        }
    }

    sourceSets {
        named("instrumentedTests") {
            assets.srcDir(layout.projectDirectory.dir("schemas"))
        }
    }
}

@Suppress("COMMENTED_OUT_CODE")
/*
prefiller {
    database("pixnews") {
        classname = "ru.pixnews.foundation.database.PixnewsDatabase"
        scripts.from(file("src/main/sql/setup.sql"))
    }
}
*/

dependencies {
    api(projects.foundation.coroutines)
    api(projects.foundation.di.base)
    api(projects.foundation.di.rootComponent)
    api(projects.foundation.domainModel)
    api(libs.inject)
    api(libs.okio)
    implementation(projects.library.kotlinDatetimeUtils)

    testImplementation(projects.library.test)
    testImplementation(testFixtures(projects.library.kotlinDatetimeUtils))
    testImplementation(libs.junit.jupiter.params)
}
