@file:Suppress("HEADER_MISSING_IN_NON_SINGLE_CLASS_FILE")

package ru.pixnews

import org.gradle.api.Project
import org.gradle.api.file.Directory

internal val Project.configRootDir: Directory
    get() {
        return rootProject.layout.projectDirectory.dir("config")
    }
