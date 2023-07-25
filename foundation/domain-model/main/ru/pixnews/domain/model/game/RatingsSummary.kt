/*
 * Copyright (c) 2023, the Pixnews project authors and contributors. Please see the AUTHORS file for details.
 * Use of this source code is governed by the Apache 2.0 license that can be found in the LICENSE file.
 */
package ru.pixnews.domain.model.game

import java.math.BigDecimal
import java.math.RoundingMode.HALF_UP
import java.util.Locale

public data class RatingsSummary(
    val gameId: GameId,
    val numberOfVotes: ULong,
    val averageRating: AverageRating?,
    val distribution: VotesDistribution?,
)

public data class VotesDistribution(
    val p1to2: ULong,
    val p3to4: ULong,
    val p5to6: ULong,
    val p7to8: ULong,
    val p9to10: ULong,
)

@JvmInline
@Suppress("MagicNumber", "FLOAT_IN_ACCURATE_CALCULATIONS")
public value class AverageRating private constructor(
    private val ratingX100: UInt,
) {
    public val value: Float
        get() = ratingX100.toFloat() / 100.0F
    init {
        require(ratingX100 in 100U..1000U)
    }

    override fun toString(): String {
        return String.format(Locale.ROOT, "AverageRating(%.2f)", value)
    }

    public companion object {
        public operator fun invoke(value: Int): AverageRating = AverageRating((value * 100).toUInt())

        public operator fun invoke(value: Float): AverageRating {
            require(value in 1F..10F)
            val intValue = BigDecimal.valueOf(value.toDouble() * 100F)
                .setScale(0, HALF_UP)
                .toInt()
                .toUInt()
            return AverageRating(intValue)
        }
    }
}
