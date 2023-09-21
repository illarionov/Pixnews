/*
 * Copyright (c) 2023, the Pixnews project authors and contributors. Please see the AUTHORS file for details.
 * Use of this source code is governed by the Apache 2.0 license that can be found in the LICENSE file.
 */

package ru.pixnews.feature.calendar.datasource.igdb.dsl

import io.kotest.matchers.shouldBe
import org.junit.jupiter.api.Test
import ru.pixnews.feature.calendar.datasource.igdb.field.scheme.AgeRatingField
import ru.pixnews.feature.calendar.datasource.igdb.field.scheme.GameField
import ru.pixnews.feature.calendar.datasource.igdb.field.scheme.IgdbField

class IgdbRequestFieldTest {
    @Test
    fun `getIgdbName() should return correct path on field with no parent`() {
        val field = IgdbRequestField(IgdbField.ALL, null)

        field.igdbFullName shouldBe "*"
    }

    @Test
    fun `getIgdbName() should return correct path on field with parents`() {
        val gameField = IgdbRequestField(GameField.ID)
        val ageRatingField = IgdbRequestField(
            GameField.AGE_RATINGS,
            gameField,
        )
        val contentDescription = IgdbRequestField(
            AgeRatingField.CONTENT_DESCRIPTIONS,
            ageRatingField,
        )

        contentDescription.igdbFullName shouldBe "id.age_ratings.content_descriptions"
    }
}
