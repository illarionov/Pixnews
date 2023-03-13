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
package ru.pixnews.domain.model.util

import androidx.annotation.ColorInt
import androidx.annotation.IntRange

/**
 * ARGB color
 */
@JvmInline
public value class Color(private val value: ULong) {
    init {
        require(value <= 0xffff_ffff_U || value == 0xffff_ffff_ffff_ffff_U)
    }

    @ColorInt
    public fun argbValue(): Int {
        check(this != Unspecified)
        return value.toInt()
    }

    public companion object {
        public val Unspecified: Color = Color(0xffff_ffff_ffff_ffff_U)
    }
}

public fun Color(
    @ColorInt argbValue: Int,
): Color = Color(argbValue.toULong())

@Suppress("MagicNumber")
public fun Color(
    @IntRange(from = 0, to = 0xFF)
    red: UInt,
    @IntRange(from = 0, to = 0xFF)
    green: UInt,
    @IntRange(from = 0, to = 0xFF)
    blue: UInt,
    @IntRange(from = 0, to = 0xFF)
    alpha: UInt = 0xFFU,
): Color {
    val color = ((alpha and 0xFFU) shl 24) or
            ((red and 0xFFU) shl 16) or
            ((green and 0xFFU) shl 8) or
            (blue and 0xFFU)
    return Color(color.toULong())
}
