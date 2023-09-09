/*
 * Copyright (c) 2023, the Pixnews project authors and contributors. Please see the AUTHORS file for details.
 * Use of this source code is governed by the Apache 2.0 license that can be found in the LICENSE file.
 */
package ru.pixnews.feature.calendar.datasource.igdb.fixtures.agerating

import ru.pixnews.feature.calendar.datasource.igdb.fixtures.IgdbAgeRatingFixtures
import ru.pixnews.igdbclient.model.AgeRating
import ru.pixnews.igdbclient.model.AgeRatingCategoryEnum.ACB
import ru.pixnews.igdbclient.model.AgeRatingCategoryEnum.CERO
import ru.pixnews.igdbclient.model.AgeRatingCategoryEnum.CLASS_IND
import ru.pixnews.igdbclient.model.AgeRatingCategoryEnum.ESRB
import ru.pixnews.igdbclient.model.AgeRatingCategoryEnum.GRAC
import ru.pixnews.igdbclient.model.AgeRatingCategoryEnum.PEGI
import ru.pixnews.igdbclient.model.AgeRatingCategoryEnum.USK
import ru.pixnews.igdbclient.model.AgeRatingContentDescription
import ru.pixnews.igdbclient.model.AgeRatingRatingEnum.ACB_M
import ru.pixnews.igdbclient.model.AgeRatingRatingEnum.CERO_C
import ru.pixnews.igdbclient.model.AgeRatingRatingEnum.CLASS_IND_FOURTEEN
import ru.pixnews.igdbclient.model.AgeRatingRatingEnum.GRAC_EIGHTEEN
import ru.pixnews.igdbclient.model.AgeRatingRatingEnum.M
import ru.pixnews.igdbclient.model.AgeRatingRatingEnum.SIXTEEN
import ru.pixnews.igdbclient.model.AgeRatingRatingEnum.TWELVE
import ru.pixnews.igdbclient.model.AgeRatingRatingEnum.USK_16

internal val IgdbAgeRatingFixtures.esrbM
    get() = AgeRating(
        id = 150089,
        category = ESRB,
        content_descriptions = listOf(139710L, 139711L, 139712L).map {
            AgeRatingContentDescription(id = it)
        },
        rating = M,
        checksum = "1e332a1c-213c-c0ca-5204-598e0403a651",
    )
internal val IgdbAgeRatingFixtures.pegiTwelve
    get() = AgeRating(
        id = 24604,
        category = PEGI,
        content_descriptions = listOf(23767L, 23768L).map {
            AgeRatingContentDescription(id = it)
        },
        rating = TWELVE,
        checksum = "95080a63-427c-495e-016e-bd22e577625b",
    )
internal val IgdbAgeRatingFixtures.pegiSixteen
    get() = AgeRating(
        id = 150090,
        category = PEGI,
        content_descriptions = listOf(139713L, 139714L).map {
            AgeRatingContentDescription(id = it)
        },
        rating = SIXTEEN,
        checksum = "95080a63-427c-495e-016e-bd22e577625b",
    )
internal val IgdbAgeRatingFixtures.ceroC
    get() = AgeRating(
        id = 150262,
        category = CERO,
        content_descriptions = listOf(139865L, 139866L).map {
            AgeRatingContentDescription(id = it)
        },
        rating = CERO_C,
        checksum = "f3fc3197-cf52-52e4-5967-31cf3b62890c",
    )
internal val IgdbAgeRatingFixtures.usk16
    get() = AgeRating(
        id = 150264,
        category = USK,
        rating = USK_16,
        checksum = "bdbdcb21-aea1-c63f-d412-6af0899bc181",
    )
internal val IgdbAgeRatingFixtures.gracEighteen
    get() = AgeRating(
        id = 150263,
        category = GRAC,
        content_descriptions = listOf(139867L, 139868L, 139869L).map {
            AgeRatingContentDescription(id = it)
        },
        rating = GRAC_EIGHTEEN,
        checksum = "4f960ab7-751c-7459-7d47-72f80266c8f0",
    )
internal val IgdbAgeRatingFixtures.acbM
    get() = AgeRating(
        id = 150265,
        category = ACB,
        rating = ACB_M,
        checksum = "e7044322-4181-045b-b176-d35ed3087696",
    )
internal val IgdbAgeRatingFixtures.classIndFourteen
    get() = AgeRating(
        id = 150546,
        category = CLASS_IND,
        content_descriptions = listOf(140004L, 140005L, 140006L).map {
            AgeRatingContentDescription(id = it)
        },
        rating = CLASS_IND_FOURTEEN,
        checksum = "e0c0ffd1-0034-b4d8-1a43-2187b8720c3a",
    )
