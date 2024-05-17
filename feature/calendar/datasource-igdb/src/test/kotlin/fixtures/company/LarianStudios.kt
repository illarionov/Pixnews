/*
 * Copyright (c) 2023, the Pixnews project authors and contributors. Please see the AUTHORS file for details.
 * Use of this source code is governed by the Apache 2.0 license that can be found in the LICENSE file.
 */

package ru.pixnews.feature.calendar.datasource.igdb.fixtures.company

import ru.pixnews.feature.calendar.datasource.igdb.fixtures.IgdbCompanyFixtures
import ru.pixnews.igdbclient.model.CompanyLogo
import ru.pixnews.igdbclient.model.CompanyWebsite
import ru.pixnews.igdbclient.model.DateFormatChangeDateCategoryEnum.TBD
import ru.pixnews.igdbclient.model.DateFormatChangeDateCategoryEnum.YYYY
import ru.pixnews.igdbclient.model.WebsiteCategoryEnum.WEBSITE_OFFICIAL
import java.time.Instant
import ru.pixnews.igdbclient.model.Company as IgdbCompany
import ru.pixnews.igdbclient.model.Game as IgdbGame

internal val IgdbCompanyFixtures.larianStudios: IgdbCompany
    get() = IgdbCompany(
        id = 510,
        name = "Larian Studios",
        change_date_category = TBD,
        country = 56,
        created_at = Instant.ofEpochSecond(1_317_748_788),
        description = "Larian Studios is a game developer studio located in Gent, " +
                "Belgium. Proud to be independent for 19 years, they create games " +
                "for multiple genres and platforms.\\n" +
                "They have a passion for role playing games; our Divinity series " +
                "includes 5 games spanning over 13 years in multiple genres. " +
                "Their crown jewel, Divinity: Original Sin, was a Kickstarter " +
                "success story of 2014.",
        developed = listOf(
            671L,
            2905,
            5082,
            6358,
            9781,
            11800,
            13178,
            14177,
            14198,
            78459,
            78635,
            103337,
            116642,
            119171,
            144597,
        ).map { IgdbGame(id = it) },
        logo = CompanyLogo(
            id = 484,
            height = 2686,
            width = 1734,
            image_id = "emebqrowinirmlk4r69n",
            url = "//images.igdb.com/igdb/image/upload/t_thumb/emebqrowinirmlk4r69n.jpg",
            checksum = "92df79a3-8295-a133-de92-068464b618d6",
        ),
        published = listOf(
            2905L,
            5082,
            6358,
            11800,
            14198,
            18459,
            103337,
            119171,
            144597,
        ).map { IgdbGame(id = it) },
        slug = "larian-studios",
        start_date = Instant.ofEpochSecond(851_990_400),
        start_date_category = YYYY,
        updated_at = Instant.ofEpochSecond(1_583_699_093),
        url = "https://www.igdb.com/companies/larian-studios",
        websites = listOf(
            CompanyWebsite(
                id = 485,
                category = WEBSITE_OFFICIAL,
                trusted = false,
                url = "http://larian.com/",
                checksum = "51b9a9d6-4542-e4bf-5600-b9f381054ebb",
            ),
        ),
        checksum = "050d0733-216b-73f7-a3cc-7f756c98229f",
    )
