package ru.pixnews.feature.calendar.data.sync.policy

import io.kotest.matchers.shouldBe
import kotlinx.datetime.Instant
import kotlinx.datetime.Instant.Companion
import org.junit.jupiter.api.DynamicTest
import org.junit.jupiter.api.DynamicTest.dynamicTest
import org.junit.jupiter.api.TestFactory
import kotlin.time.Duration.Companion.seconds

class SyncPolicyEvaluatorTest {
    @TestFactory
    fun `isSyncRequired should return correct value`(): Iterable<DynamicTest> = listOf(
        SyncRequiredTestCase(
            currentTime = "2024-01-26T14:40:09Z",
            expectedResult = SyncRequiredResult.NotRequired("Next sync not earlier than 2024-01-26T14:40:10Z"),
        ),
        SyncRequiredTestCase(
            currentTime = "2024-01-26T14:40:09Z",
            forceSync = true,
            expectedResult = SyncRequiredResult.Required(isForced = true, "Forced"),
        ),
        SyncRequiredTestCase(
            currentTime = "2024-01-26T14:40:10Z",
            expectedResult = SyncRequiredResult.Required(isForced = false, ""),
        ),
    ).map { testCase ->
        dynamicTest(
            "isSyncRequired(${testCase.syncPolicy.minPeriod}, " +
                    "${testCase.lastSyncTime}), " +
                    "${testCase.currentTime}, " +
                    "${testCase.forceSync}" +
                    ") should return ${testCase.expectedResult}",
        ) {
            val result = testCase.syncPolicy.isSyncRequired(
                lastSyncTime = Instant.parse(testCase.lastSyncTime),
                currentTime = Companion.parse(testCase.currentTime),
                forceSync = testCase.forceSync,
            )

            result shouldBe testCase.expectedResult
        }
    }

    private data class SyncRequiredTestCase(
        val syncPolicy: SyncPolicy = SyncPolicy(minPeriod = 10.seconds),
        val lastSyncTime: String = "2024-01-26T14:40:00Z",
        val currentTime: String = "2024-01-26T14:40:10Z",
        val forceSync: Boolean = false,
        val expectedResult: SyncRequiredResult,
    )
}
