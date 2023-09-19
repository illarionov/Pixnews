package ru.pixnews.feature.calendar.datasource.igdb.dsl

import io.kotest.matchers.shouldBe
import org.junit.jupiter.api.Test
import ru.pixnews.igdbclient.model.AgeRating
import ru.pixnews.igdbclient.model.AgeRatingContentDescription
import ru.pixnews.igdbclient.model.Game

class IgdbRequestFieldTest {
    @Test
    fun `getIgdbName() should return correct path on field with no parent`() {
        val field = IgdbRequestField("*", Nothing::class)

        field.igdbName shouldBe "*"
    }

    @Test
    fun `getIgdbName() should return correct path on field with parents`() {
        val gameField = IgdbRequestField("id", Game::class)
        val ageRatingField = IgdbRequestField(
            "age_rating",
            AgeRating::class,
            gameField,
        )
        val contentDescription = IgdbRequestField(
            "content_descriptions",
            AgeRatingContentDescription::class,
            ageRatingField,
        )

        contentDescription.igdbName shouldBe "id.age_rating.content_descriptions"
    }
}
