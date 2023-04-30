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

import kotlin.jvm.optionals.getOrNull

plugins {
    `java-platform`
}

group = "ru.pixnews.gradle.base"

javaPlatform {
    allowDependencies()
}

private val versionCatalog: VersionCatalog = kotlin.runCatching {
    extensions.getByType<VersionCatalogsExtension>().named("libs")
}.getOrElse { throw IllegalStateException("Can not find libs version catalog", it) }

private val bomsBundle: Map<ModuleIdentifier, MinimalExternalModuleDependency> = versionCatalog.findBundle("boms")
    .getOrNull()
    ?.get()
    ?.associateBy(MinimalExternalModuleDependency::getModule)
    ?: error("No `boms` version bundle in version catalog")

dependencies {
    bomsBundle.values.forEach {
        add("api", platform(it))
    }

    constraints {
        versionCatalog.libraryAliases
            .map { alias -> versionCatalog.findLibrary(alias).get().get() }
            .filter { dependency -> !bomsBundle.containsKey(dependency.module) }
            .filter { dependency -> dependency.version != null }
            .forEach { dependency -> add("api", dependency) }
    }
}
