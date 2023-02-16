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

import ru.pixnews.PixnewsExtension
import ru.pixnews.versionCatalog

/**
 * Convention plugin that configures anvil with kapt
 */
plugins {
    kotlin("kapt")
    id("com.squareup.anvil")
}

dependencies {
    add("api", versionCatalog.findLibrary("dagger").orElseThrow())
    add("kapt", versionCatalog.findLibrary("dagger.compiler").orElseThrow())
}

kapt {
    javacOptions {
        option("-Adagger.fastInit=enabled")
        option("-Adagger.strictMultibindingValidation=enabled")
        option("-Adagger.experimentalDaggerErrorMessages=enabled")
    }
}

afterEvaluate {
    if (extensions.findByType<PixnewsExtension>()?.managedDevices?.get() == true) {
        dependencies.add("kaptAndroidTest", versionCatalog.findLibrary("dagger.compiler").orElseThrow())
    }
}
