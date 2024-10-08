/*
 * Copyright (c) 2023, the Pixnews project authors and contributors. Please see the AUTHORS file for details.
 * Use of this source code is governed by the Apache 2.0 license that can be found in the LICENSE file.
 */

@file:JvmName("DevicePreviewConstants")
@file:Suppress("CONSTANT_UPPERCASE")

package ru.pixnews.library.ui.tooling

import android.content.res.Configuration.UI_MODE_NIGHT_NO
import android.content.res.Configuration.UI_MODE_NIGHT_YES
import android.content.res.Configuration.UI_MODE_TYPE_NORMAL
import androidx.compose.ui.tooling.preview.Preview

/**
 * Huawei P40
 *
 * 6.1″, 19.5:9 ratio, 422 ppi density.
 *
 * 360x780dp
 */
public const val DEVICE_HUAWEI_P40: String = "spec:shape=Normal,width=360,height=780,unit=dp,dpi=422"

/**
 * Xiaomi Mi 10
 *
 * 6.67″, 19.5:9 ratio, 386 ppi density.
 *
 * 393x851dp
 */
public const val DEVICE_XIAOMI_MI10: String = "spec:shape=Normal,width=393,height=851,unit=dp,dpi=386"

/**
 * Samsung Galaxy S10 Lite
 *
 * 6.7″, 20:9 ratio, 394 ppi density.
 *
 * 412x394dp
 */
public const val DEVICE_SAMSUNG_S10_LITE: String = "spec:shape=Normal,width=412,height=914,unit=dp,dpi=394"

@Preview(
    name = "Phone w360dp 16:9",
    group = "phones",
    device = "spec:shape=Normal,width=360,height=640,unit=dp,dpi=480",
    locale = "en",
    showSystemUi = true,
    showBackground = true,
)
@Preview(
    name = "Phone w393dp 20:9",
    group = "phones",
    device = "spec:shape=Normal,width=393,height=873,unit=dp,dpi=480",
    locale = "en",
    showSystemUi = true,
    showBackground = true,
)
@Preview(
    name = "Phone w412dp 19.5:9 (Reference)",
    group = "phones",
    device = "spec:shape=Normal,width=412,height=892,unit=dp,dpi=480",
    locale = "en",
    showSystemUi = true,
    showBackground = true,
)
@Retention(AnnotationRetention.BINARY)
@Target(AnnotationTarget.FUNCTION, AnnotationTarget.CLASS)
public annotation class PreviewPhones

@Preview(
    name = "phone w360dp",
    group = "devices",
    device = "spec:shape=Normal,width=360,height=640,unit=dp,dpi=480",
    locale = "en",
    showSystemUi = true,
    showBackground = true,
)
@Preview(
    name = "foldable w673dp",
    group = "devices",
    device = "spec:shape=Normal,width=673,height=841,unit=dp,dpi=480",
    locale = "en",
    showSystemUi = true,
    showBackground = true,
)
@Preview(
    name = "tablet w1280dp",
    group = "devices",
    device = "spec:shape=Normal,width=1280,height=800,unit=dp,dpi=480",
    locale = "en",
    showSystemUi = true,
    showBackground = true,
)
@Retention(AnnotationRetention.BINARY)
@Target(AnnotationTarget.FUNCTION, AnnotationTarget.CLASS)
public annotation class PreviewDevices

@Preview(
    name = "Portrait Phone",
    group = "shapes",
    locale = "en",
    device = "spec:width=412dp,height=892dp,dpi=480",
    uiMode = UI_MODE_TYPE_NORMAL,
)
@Preview(
    name = "Landscape Phone",
    group = "shapes",
    locale = "en",
    device = "spec:width=412dp,height=892dp,dpi=480,orientation=landscape",
    uiMode = UI_MODE_TYPE_NORMAL,
)
@Retention(AnnotationRetention.BINARY)
@Target(AnnotationTarget.FUNCTION, AnnotationTarget.CLASS)
public annotation class PreviewDeviceShapes

@Preview(
    name = "dark theme",
    group = "themes",
    locale = "en",
    uiMode = UI_MODE_NIGHT_YES,
)
@Preview(
    name = "light theme",
    group = "themes",
    locale = "en",
    uiMode = UI_MODE_NIGHT_NO,
)
@PreviewDevices
public annotation class PreviewThemes

@PreviewDevices
@PreviewPhones
@PreviewThemes
@PreviewDeviceShapes
public annotation class PreviewComplete
