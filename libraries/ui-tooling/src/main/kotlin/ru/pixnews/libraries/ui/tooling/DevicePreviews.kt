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
@file:JvmName("DevicePreviewConstants")
@file:Suppress("CONSTANT_UPPERCASE")

package ru.pixnews.libraries.ui.tooling

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
    showSystemUi = true,
    showBackground = true,
)
@Preview(
    name = "Phone w393dp 20:9",
    group = "phones",
    device = "spec:shape=Normal,width=393,height=873,unit=dp,dpi=480",
    showSystemUi = true,
    showBackground = true,
)
@Preview(
    name = "Phone w412dp 19.5:9 (Reference)",
    group = "phones",
    device = "spec:shape=Normal,width=412,height=892,unit=dp,dpi=480",
    showSystemUi = true,
    showBackground = true,
)
@Retention(AnnotationRetention.BINARY)
@Target(AnnotationTarget.FUNCTION, AnnotationTarget.CLASS)
public annotation class PhonePreviews

@Preview(
    name = "phone w360dp",
    group = "devices",
    device = "spec:shape=Normal,width=360,height=640,unit=dp,dpi=480",
    showSystemUi = true,
    showBackground = true,
)
@Preview(
    name = "foldable w673dp",
    group = "devices",
    device = "spec:shape=Normal,width=673,height=841,unit=dp,dpi=480",
    showSystemUi = true,
    showBackground = true,
)
@Preview(
    name = "tablet w1280dp",
    group = "devices",
    device = "spec:shape=Normal,width=1280,height=800,unit=dp,dpi=480",
    showSystemUi = true,
    showBackground = true,
)
@Retention(AnnotationRetention.BINARY)
@Target(AnnotationTarget.FUNCTION, AnnotationTarget.CLASS)
public annotation class DevicePreviews

@Preview(
    name = "Portrait Phone",
    group = "shapes",
    device = "spec:width=412dp,height=892dp,dpi=480",
    uiMode = UI_MODE_TYPE_NORMAL,
)
@Preview(
    name = "Landscape Phone",
    group = "shapes",
    device = "spec:width=412dp,height=892dp,dpi=480,orientation=landscape",
    uiMode = UI_MODE_TYPE_NORMAL,
)
@Retention(AnnotationRetention.BINARY)
@Target(AnnotationTarget.FUNCTION, AnnotationTarget.CLASS)
public annotation class DeviceShapePreviews

@Preview(
    name = "dark theme",
    group = "themes",
    uiMode = UI_MODE_NIGHT_YES,
)
@Preview(
    name = "light theme",
    group = "themes",
    uiMode = UI_MODE_NIGHT_NO,
)
@DevicePreviews
public annotation class ThemePreviews

@DevicePreviews
@PhonePreviews
@ThemePreviews
@DeviceShapePreviews
public annotation class CompletePreviews
