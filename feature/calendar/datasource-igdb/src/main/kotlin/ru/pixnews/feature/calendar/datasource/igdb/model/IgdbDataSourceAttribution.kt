/*
 * Copyright (c) 2023, the Pixnews project authors and contributors. Please see the AUTHORS file for details.
 * Use of this source code is governed by the Apache 2.0 license that can be found in the LICENSE file.
 */
package ru.pixnews.feature.calendar.datasource.igdb.model

import ru.pixnews.domain.model.datasource.DataSource
import ru.pixnews.domain.model.datasource.DataSourceType
import ru.pixnews.domain.model.locale.LanguageCode
import ru.pixnews.domain.model.locale.Localized
import ru.pixnews.domain.model.util.RichText

internal val igdbDataSource: DataSource = DataSource(
    source = DataSourceType.Igdb,
    attributionText = Localized(
        RichText(
            """Information is provided by IGDB.com — Not an IGDB Official Product""".trimIndent(),
        ),
        LanguageCode.ENGLISH,
    ),
)
