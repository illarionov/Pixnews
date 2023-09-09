/*
 * Copyright (c) 2023, the Pixnews project authors and contributors. Please see the AUTHORS file for details.
 * Use of this source code is governed by the Apache 2.0 license that can be found in the LICENSE file.
 */
package ru.pixnews.feature.calendar.datasource.igdb.converter

import ru.pixnews.domain.model.url.ExternalLinkType
import ru.pixnews.domain.model.url.ExternalLinkType.APP_STORE
import ru.pixnews.domain.model.url.ExternalLinkType.DISCORD
import ru.pixnews.domain.model.url.ExternalLinkType.EPICGAMES_STORE
import ru.pixnews.domain.model.url.ExternalLinkType.FACEBOOK
import ru.pixnews.domain.model.url.ExternalLinkType.GOG
import ru.pixnews.domain.model.url.ExternalLinkType.GOOGLE_PLAY
import ru.pixnews.domain.model.url.ExternalLinkType.INSTAGRAM
import ru.pixnews.domain.model.url.ExternalLinkType.ITCH_IO
import ru.pixnews.domain.model.url.ExternalLinkType.OFFICIAL
import ru.pixnews.domain.model.url.ExternalLinkType.OTHER
import ru.pixnews.domain.model.url.ExternalLinkType.REDDIT
import ru.pixnews.domain.model.url.ExternalLinkType.STEAM
import ru.pixnews.domain.model.url.ExternalLinkType.TWITCH
import ru.pixnews.domain.model.url.ExternalLinkType.TWITTER
import ru.pixnews.domain.model.url.ExternalLinkType.WIKIPEDIA
import ru.pixnews.domain.model.url.ExternalLinkType.YOUTUBE
import ru.pixnews.igdbclient.model.WebsiteCategoryEnum
import ru.pixnews.igdbclient.model.WebsiteCategoryEnum.WEBSITE_ANDROID
import ru.pixnews.igdbclient.model.WebsiteCategoryEnum.WEBSITE_CATEGORY_NULL
import ru.pixnews.igdbclient.model.WebsiteCategoryEnum.WEBSITE_DISCORD
import ru.pixnews.igdbclient.model.WebsiteCategoryEnum.WEBSITE_EPICGAMES
import ru.pixnews.igdbclient.model.WebsiteCategoryEnum.WEBSITE_FACEBOOK
import ru.pixnews.igdbclient.model.WebsiteCategoryEnum.WEBSITE_GOG
import ru.pixnews.igdbclient.model.WebsiteCategoryEnum.WEBSITE_INSTAGRAM
import ru.pixnews.igdbclient.model.WebsiteCategoryEnum.WEBSITE_IPAD
import ru.pixnews.igdbclient.model.WebsiteCategoryEnum.WEBSITE_IPHONE
import ru.pixnews.igdbclient.model.WebsiteCategoryEnum.WEBSITE_ITCH
import ru.pixnews.igdbclient.model.WebsiteCategoryEnum.WEBSITE_OFFICIAL
import ru.pixnews.igdbclient.model.WebsiteCategoryEnum.WEBSITE_REDDIT
import ru.pixnews.igdbclient.model.WebsiteCategoryEnum.WEBSITE_STEAM
import ru.pixnews.igdbclient.model.WebsiteCategoryEnum.WEBSITE_TWITCH
import ru.pixnews.igdbclient.model.WebsiteCategoryEnum.WEBSITE_TWITTER
import ru.pixnews.igdbclient.model.WebsiteCategoryEnum.WEBSITE_WIKIA
import ru.pixnews.igdbclient.model.WebsiteCategoryEnum.WEBSITE_WIKIPEDIA
import ru.pixnews.igdbclient.model.WebsiteCategoryEnum.WEBSITE_YOUTUBE

@Suppress("CyclomaticComplexMethod")
internal fun WebsiteCategoryEnum.toExternalLinkType(): ExternalLinkType? = when (this) {
    WEBSITE_CATEGORY_NULL -> null
    WEBSITE_OFFICIAL -> OFFICIAL
    WEBSITE_WIKIA -> OTHER
    WEBSITE_WIKIPEDIA -> WIKIPEDIA
    WEBSITE_FACEBOOK -> FACEBOOK
    WEBSITE_TWITTER -> TWITTER
    WEBSITE_TWITCH -> TWITCH
    WEBSITE_INSTAGRAM -> INSTAGRAM
    WEBSITE_YOUTUBE -> YOUTUBE
    WEBSITE_IPHONE -> APP_STORE
    WEBSITE_IPAD -> APP_STORE
    WEBSITE_ANDROID -> GOOGLE_PLAY
    WEBSITE_STEAM -> STEAM
    WEBSITE_REDDIT -> REDDIT
    WEBSITE_ITCH -> ITCH_IO
    WEBSITE_EPICGAMES -> EPICGAMES_STORE
    WEBSITE_GOG -> GOG
    WEBSITE_DISCORD -> DISCORD
}
