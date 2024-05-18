/*
 * Copyright (c) 2023, the Pixnews project authors and contributors. Please see the AUTHORS file for details.
 * Use of this source code is governed by the Apache 2.0 license that can be found in the LICENSE file.
 */

package ru.pixnews.foundation.database.converter

import androidx.room.TypeConverter
import ru.pixnews.domain.model.url.ExternalLinkType
import ru.pixnews.domain.model.url.ExternalLinkType.APP_STORE
import ru.pixnews.domain.model.url.ExternalLinkType.BATTLE_NET
import ru.pixnews.domain.model.url.ExternalLinkType.DISCORD
import ru.pixnews.domain.model.url.ExternalLinkType.EPICGAMES_STORE
import ru.pixnews.domain.model.url.ExternalLinkType.FACEBOOK
import ru.pixnews.domain.model.url.ExternalLinkType.GOG
import ru.pixnews.domain.model.url.ExternalLinkType.GOOGLE_PLAY
import ru.pixnews.domain.model.url.ExternalLinkType.HUMBLE
import ru.pixnews.domain.model.url.ExternalLinkType.INSTAGRAM
import ru.pixnews.domain.model.url.ExternalLinkType.ITCH_IO
import ru.pixnews.domain.model.url.ExternalLinkType.METACRITIC
import ru.pixnews.domain.model.url.ExternalLinkType.NINTENDO
import ru.pixnews.domain.model.url.ExternalLinkType.OFFICIAL
import ru.pixnews.domain.model.url.ExternalLinkType.ORIGIN
import ru.pixnews.domain.model.url.ExternalLinkType.OTHER
import ru.pixnews.domain.model.url.ExternalLinkType.PLAYSTATION
import ru.pixnews.domain.model.url.ExternalLinkType.REDDIT
import ru.pixnews.domain.model.url.ExternalLinkType.SOUNDCLOUD
import ru.pixnews.domain.model.url.ExternalLinkType.STEAM
import ru.pixnews.domain.model.url.ExternalLinkType.TWITCH
import ru.pixnews.domain.model.url.ExternalLinkType.TWITTER
import ru.pixnews.domain.model.url.ExternalLinkType.VK
import ru.pixnews.domain.model.url.ExternalLinkType.WIKIPEDIA
import ru.pixnews.domain.model.url.ExternalLinkType.XBOX_COM
import ru.pixnews.domain.model.url.ExternalLinkType.YOUTUBE
import ru.pixnews.foundation.database.util.errorUnknownValue

@Suppress("MagicNumber", "CyclomaticComplexMethod")
internal object ExternalLinkTypeConverter {
    @TypeConverter
    fun fromExternalLinkType(value: ExternalLinkType?): Int? = when (value) {
        OFFICIAL -> 1
        APP_STORE -> 2
        BATTLE_NET -> 3
        DISCORD -> 4
        EPICGAMES_STORE -> 5
        FACEBOOK -> 6
        GOG -> 7
        GOOGLE_PLAY -> 8
        HUMBLE -> 9
        INSTAGRAM -> 10
        ITCH_IO -> 11
        METACRITIC -> 12
        NINTENDO -> 13
        ORIGIN -> 14
        PLAYSTATION -> 15
        REDDIT -> 16
        SOUNDCLOUD -> 17
        STEAM -> 18
        TWITCH -> 19
        TWITTER -> 20
        VK -> 21
        XBOX_COM -> 22
        YOUTUBE -> 23
        WIKIPEDIA -> 24
        OTHER -> 25
        null -> null
    }

    @TypeConverter
    fun toExternalLinkType(value: Int?): ExternalLinkType? = when (value) {
        1 -> OFFICIAL
        2 -> APP_STORE
        3 -> BATTLE_NET
        4 -> DISCORD
        5 -> EPICGAMES_STORE
        6 -> FACEBOOK
        7 -> GOG
        8 -> GOOGLE_PLAY
        9 -> HUMBLE
        10 -> INSTAGRAM
        11 -> ITCH_IO
        12 -> METACRITIC
        13 -> NINTENDO
        14 -> ORIGIN
        15 -> PLAYSTATION
        16 -> REDDIT
        17 -> SOUNDCLOUD
        18 -> STEAM
        19 -> TWITCH
        20 -> TWITTER
        21 -> VK
        22 -> XBOX_COM
        23 -> YOUTUBE
        24 -> WIKIPEDIA
        25 -> OTHER
        null -> null
        else -> errorUnknownValue(value, ExternalLinkType::class)
    }
}
