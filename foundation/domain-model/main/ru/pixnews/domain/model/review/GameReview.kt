/*
 * Copyright (c) 2023, the Pixnews project authors and contributors. Please see the AUTHORS file for details.
 * Use of this source code is governed by the Apache 2.0 license that can be found in the LICENSE file.
 */
package ru.pixnews.domain.model.review

import kotlinx.datetime.Instant
import ru.pixnews.domain.model.id.GameId
import ru.pixnews.domain.model.locale.LanguageCode
import ru.pixnews.domain.model.review.UserUtilityMark.UNSET
import ru.pixnews.domain.model.util.RichText

public data class GameReview(
    val id: GameReviewId,
    val gameId: GameId,

    val author: ReviewAuthor,
    val date: Instant,
    val language: LanguageCode,
    val rating: ReviewerRating,
    val text: RichText,
    val utility: Int = 0,
    val utilityMark: UserUtilityMark = UNSET,
)
