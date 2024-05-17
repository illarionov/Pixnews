/*
 * Copyright (c) 2023, the Pixnews project authors and contributors. Please see the AUTHORS file for details.
 * Use of this source code is governed by the Apache 2.0 license that can be found in the LICENSE file.
 */

package ru.pixnews.foundation.database.converter

import androidx.room.TypeConverter
import ru.pixnews.domain.model.game.VotesDistribution
import ru.pixnews.foundation.database.model.proto.VotesDistributionDb

internal class VotesDistributionConverter {
    @TypeConverter
    fun fromVotesDistribution(value: VotesDistribution?): ByteArray? = value?.serialize()

    @TypeConverter
    fun toVotesDistribution(bytes: ByteArray?): VotesDistribution? = bytes?.let(::deserialize)

    private fun VotesDistribution.serialize(): ByteArray = VotesDistributionDb(
        p1to2 = this.p1to2.toLong(),
        p3to4 = this.p3to4.toLong(),
        p5to6 = this.p5to6.toLong(),
        p7to8 = this.p7to8.toLong(),
        p9to10 = this.p9to10.toLong(),
    ).encode()

    private fun deserialize(bytes: ByteArray): VotesDistribution = VotesDistributionDb.ADAPTER.decode(bytes).let {
        VotesDistribution(
            p1to2 = it.p1to2.toULong(),
            p3to4 = it.p3to4.toULong(),
            p5to6 = it.p5to6.toULong(),
            p7to8 = it.p7to8.toULong(),
            p9to10 = it.p9to10.toULong(),
        )
    }
}
