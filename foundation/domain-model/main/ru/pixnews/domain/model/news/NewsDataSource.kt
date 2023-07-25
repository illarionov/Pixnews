/*
 * Copyright (c) 2023, the Pixnews project authors and contributors. Please see the AUTHORS file for details.
 * Use of this source code is governed by the Apache 2.0 license that can be found in the LICENSE file.
 */
package ru.pixnews.domain.model.news

import ru.pixnews.domain.model.util.ImageUrl
import ru.pixnews.domain.model.util.RichText
import ru.pixnews.domain.model.util.Url

public class NewsDataSource(
    public val name: String,
    public val url: Url?,
    public val iconUrl: ImageUrl?,
    public val attributionText: RichText,
)
