/*
 * Copyright (c) 2023, the Pixnews project authors and contributors. Please see the AUTHORS file for details.
 * Use of this source code is governed by the Apache 2.0 license that can be found in the LICENSE file.
 */

package ru.pixnews.feature.calendar.datasource.igdb.fixtures.company

import at.released.igdbclient.model.Company
import at.released.igdbclient.model.CompanyLogo
import at.released.igdbclient.model.CompanyWebsite
import at.released.igdbclient.model.DateFormatChangeDateCategoryEnum.TBD
import at.released.igdbclient.model.DateFormatChangeDateCategoryEnum.YYYY
import at.released.igdbclient.model.WebsiteCategoryEnum.WEBSITE_OFFICIAL
import ru.pixnews.feature.calendar.datasource.igdb.fixtures.IgdbCompanyFixtures
import java.time.Instant

internal val IgdbCompanyFixtures.wushuStudios: Company
    get() = Company(
        id = 47197,
        name = "Wushu Studios",
        change_date_category = TBD,
        country = 826,
        created_at = Instant.ofEpochSecond(1_683_732_321),
        description = "Founded in 2017 by a group of industry veterans, " +
                "Wushu are an experienced and professional team of creative " +
                "and technical developers based in the heart of Liverpool’s " +
                "digital creative hub, The Baltic Triangle.",
        logo = CompanyLogo(
            id = 8725,
            height = 128,
            width = 472,
            image_id = "cl6qd",
            url = "//images.igdb.com/igdb/image/upload/t_thumb/cl6qd.jpg",
            checksum = "25ad2d64-8837-67b9-be51-12ae753273e0",
        ),
        slug = "wushu-studios",
        start_date = Instant.ofEpochSecond(1_514_678_400),
        start_date_category = YYYY,
        updated_at = Instant.ofEpochSecond(1_687_783_972),
        url = "https://www.igdb.com/companies/wushu-studios",
        websites = listOf(
            CompanyWebsite(
                id = 7890,
                category = WEBSITE_OFFICIAL,
                trusted = false,
                url = "https://wushustudios.com/",
                checksum = "126eddc0-e57e-8906-5791-a30fb3c86b55",
            ),
        ),
        checksum = "8c56bc1d-02b7-34a2-5135-d80a4f524523",
    )
