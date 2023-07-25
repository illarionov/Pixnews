/*
 * Copyright (c) 2023, the Pixnews project authors and contributors. Please see the AUTHORS file for details.
 * Use of this source code is governed by the Apache 2.0 license that can be found in the LICENSE file.
 */
package ru.pixnews.domain.model.game

import io.kotest.assertions.throwables.shouldThrow
import io.kotest.common.runBlocking
import io.kotest.matchers.floats.shouldBeExactly
import io.kotest.matchers.shouldBe
import io.kotest.property.Arb
import io.kotest.property.arbitrary.filter
import io.kotest.property.arbitrary.float
import io.kotest.property.checkAll
import org.junit.jupiter.api.Test
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.ValueSource
import java.util.Locale

class AverageRatingTest {
    @Test
    fun `invoke(int) should creates correct value`() {
        for (i in 1..10) {
            val rating = AverageRating(i)
            rating.value shouldBeExactly i.toFloat()
        }
    }

    @ParameterizedTest
    @ValueSource(ints = [-1, 0, 11])
    fun `invoke(int) should fail on incorrect value`(rating: Int) {
        shouldThrow<IllegalArgumentException> {
            AverageRating(rating)
        }
    }

    @ParameterizedTest
    @ValueSource(floats = [9.70500f, 4.727499f])
    fun `invoke(float) should creates correct value parametrized test`(value: Float) {
        val rating = AverageRating(value)
        String.format(Locale.ROOT, "%.2f", rating.value) shouldBe
                String.format(Locale.ROOT, "%.2f", value)
    }

    @Test
    fun `invoke(float) should creates correct value`(): Unit = runBlocking {
        Arb.float(1F..10F).filter(Float::isFinite).checkAll { value ->
            val rating = AverageRating(value)
            String.format(Locale.ROOT, "%.2f", rating.value) shouldBe
                    String.format(Locale.ROOT, "%.2f", value)
        }
    }

    @ParameterizedTest
    @ValueSource(
        floats = [
            -1f,
            0f,
            0.9f,
            0.99f,
            0.999f,
            0.9999f,
            10.001f,
            10.01f,
            10.01f,
            10.1f,
            11f,
        ],
    )
    fun `invoke(float) should fails on incorrect test value`(rating: Float) {
        shouldThrow<IllegalArgumentException> {
            AverageRating(rating)
        }
    }
}
