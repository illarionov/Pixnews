/*
 * Copyright (c) 2023, the Pixnews project authors and contributors. Please see the AUTHORS file for details.
 * Use of this source code is governed by the Apache 2.0 license that can be found in the LICENSE file.
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
