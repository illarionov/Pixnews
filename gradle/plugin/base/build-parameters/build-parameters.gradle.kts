/*
 * Copyright (c) 2024, the Pixnews project authors and contributors. Please see the AUTHORS file for details.
 * Use of this source code is governed by the Apache 2.0 license that can be found in the LICENSE file.
 */

plugins {
    id("org.gradlex.build-parameters")
}

group = "ru.pixnews.gradle.base"

buildParameters {
    pluginId("ru.pixnews.gradle.base.build-parameters")
    enableValidation.set(false)
    bool("ci") {
        defaultValue.set(false)
        fromEnvironment()
    }
    bool("releasing") {
        defaultValue.set(false)
        description.set("Going to build the final release version.")
        fromEnvironment()
    }
    string("config") {
        fromEnvironment()
        description.set("Path to the .properties file with all configurations and API keys")
        defaultValue.set("config/pixnews.properties")
    }

    group("signing") {
        bool("sign_with_debug_keys") {
            description.set("Sign release build type with debug keys")
            defaultValue.set(false)
        }
        string("release_keystore_properties_file") {
            fromEnvironment()
            description.set("Path to the .properties file with signing information for the release build type")
            defaultValue.set("config/release_keystore.properties")
        }
    }

    group("compose") {
        bool("enable_compose_compiler_metrics") {
            description.set("Enable Compose compiler metrics")
            defaultValue.set(true)
        }
        bool("enable_compose_compiler_reports") {
            description.set("Enable Compose compiler reports")
            defaultValue.set(true)
        }
    }
    group("testing") {
        bool("verbose") {
            description.set("Display test output in the console")
            defaultValue.set(false)
        }
    }
}
