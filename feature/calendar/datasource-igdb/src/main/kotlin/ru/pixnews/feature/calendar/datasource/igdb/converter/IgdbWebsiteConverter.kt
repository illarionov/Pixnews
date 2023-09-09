/*
 * Copyright (c) 2023, the Pixnews project authors and contributors. Please see the AUTHORS file for details.
 * Use of this source code is governed by the Apache 2.0 license that can be found in the LICENSE file.
 */
package ru.pixnews.feature.calendar.datasource.igdb.converter

import ru.pixnews.domain.model.links.ExternalLink
import ru.pixnews.domain.model.util.Url
import ru.pixnews.igdbclient.model.Website as IgdbWebsite

internal fun IgdbWebsite.toExternalLink(): ExternalLink = ExternalLink(
    type = this.category.toExternalLinkType() ?: errorFieldNotRequested("website.category"),
    url = Url(requireFieldInitialized("website.url", url)),
)
