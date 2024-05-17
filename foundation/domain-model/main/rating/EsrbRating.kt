/*
 * Copyright (c) 2023, the Pixnews project authors and contributors. Please see the AUTHORS file for details.
 * Use of this source code is governed by the Apache 2.0 license that can be found in the LICENSE file.
 */

package ru.pixnews.domain.model.rating

public enum class EsrbRating {
    EVERYONE,
    EVERYONE_10PLUS,
    TEEN,
    MATURE_17PLUS,
    ADULTS_ONLY_18PLUS,
    RATING_PENDING,
    RATING_PENDING_LIKELY_MATURE_17PLUS,
}
