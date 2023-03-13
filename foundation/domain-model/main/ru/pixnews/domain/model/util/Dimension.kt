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

import androidx.annotation.Px

/**
 * Represents either the width or height of a [Size].
 */
public sealed class Dimension {
    /**
     * Represents an undefined pixel value.
     */
    public object Undefined : Dimension() {
        override fun toString(): String = "Dimension.Undefined"
    }

    public class Pixels(@Px public val px: UInt) : Dimension() {
        init {
            require(px > 0U) { "px must be > 0." }
        }

        override fun equals(other: Any?): Boolean {
            if (this === other) {
                return true
            }
            return other is Pixels && px == other.px
        }

        override fun hashCode(): Int = px.hashCode()

        override fun toString(): String = px.toString()
    }

    public companion object {
        /** Create a [Dimension.Pixels] value with [px] number of pixels. */
        public operator fun invoke(@Px px: UInt): Pixels = Dimension.Pixels(px)
    }
}

/**
 * If this is a [Dimension.Pixels] value, return its pixel value.
 * Else, invoke and return the value from [block].
 */
public inline fun Dimension.pxOrElse(block: () -> UInt): UInt {
    return if (this is Dimension.Pixels) px else block()
}
