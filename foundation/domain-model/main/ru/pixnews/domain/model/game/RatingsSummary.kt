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
package ru.pixnews.domain.model.game

import java.util.Locale
import kotlin.math.roundToInt

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
            return AverageRating((value * 100F).roundToInt().toUInt())
        }
    }
}
