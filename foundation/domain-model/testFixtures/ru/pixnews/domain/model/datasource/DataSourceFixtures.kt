/*
 * Copyright (c) 2023, the Pixnews project authors and contributors. Please see the AUTHORS file for details.
 * Use of this source code is governed by the Apache 2.0 license that can be found in the LICENSE file.
 */
package ru.pixnews.domain.model.datasource

import ru.pixnews.domain.model.locale.LanguageCode
import ru.pixnews.domain.model.locale.Localized
import ru.pixnews.domain.model.util.RichText

public object DataSourceFixtures

public val DataSourceFixtures.igdb: DataSource
    get() = DataSource(
        source = DataSourceType.Igdb,
        attributionText = Localized(
            value = RichText("https://www.igdb.com/"),
            language = LanguageCode.ENGLISH,
        ),
    )
