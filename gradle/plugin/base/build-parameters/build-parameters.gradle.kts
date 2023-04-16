/*
 * Copyright 2023 Alexey Illarionov
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
plugins {
    id("org.gradlex.build-parameters") version "1.4.3"
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
            defaultValue.set("config/signing/release.properties")
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
