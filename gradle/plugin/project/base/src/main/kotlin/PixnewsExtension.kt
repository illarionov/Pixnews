/*
 * Copyright (c) 2023-2024, the Pixnews project authors and contributors. Please see the AUTHORS file for details.
 * Use of this source code is governed by the Apache 2.0 license that can be found in the LICENSE file.
 */

package ru.pixnews.gradle.project.base

import org.gradle.api.Project
import org.gradle.api.file.RegularFileProperty
import org.gradle.api.model.ObjectFactory
import org.gradle.api.provider.Property
import org.gradle.kotlin.dsl.create
import org.gradle.kotlin.dsl.property
import ru.pixnews.gradle.project.base.UnitTestEngine.JUNIT5
import javax.inject.Inject

public open class PixnewsExtension
@Inject
constructor(
    objects: ObjectFactory,
) {
    val configFile: RegularFileProperty = objects.fileProperty()
        .convention(objects.directoryProperty().file("config/pixnews.properties"))
    val unitTestEngine: Property<UnitTestEngine> = objects.property<UnitTestEngine>().convention(JUNIT5)
}

public fun Project.createPixnewsExtension(): PixnewsExtension {
    return extensions.create<PixnewsExtension>("pixnews").apply {
        this.configFile.convention(
            provider {
                project.layout.settingsDirectory.file("config/pixnews.properties")
            },
        )
    }
}
