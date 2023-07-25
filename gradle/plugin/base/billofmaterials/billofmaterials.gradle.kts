/*
 * Copyright (c) 2023, the Pixnews project authors and contributors. Please see the AUTHORS file for details.
 * Use of this source code is governed by the Apache 2.0 license that can be found in the LICENSE file.
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
