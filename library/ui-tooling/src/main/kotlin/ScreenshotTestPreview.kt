/*
 * Copyright (c) 2024, the Pixnews project authors and contributors. Please see the AUTHORS file for details.
 * Use of this source code is governed by the Apache 2.0 license that can be found in the LICENSE file.
 */

package ru.pixnews.library.ui.tooling

import androidx.compose.ui.tooling.preview.Preview

/**
 * Compose [Preview] annotation with a locale independent of the local host system locale
 */
@Preview(
    locale = "en",
)
@Retention(AnnotationRetention.BINARY)
@Suppress("PreviewAnnotationNaming")
@Target(AnnotationTarget.FUNCTION, AnnotationTarget.CLASS)
public annotation class ScreenshotTestPreview
