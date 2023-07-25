/*
 * Copyright (c) 2023, the Pixnews project authors and contributors. Please see the AUTHORS file for details.
 * Use of this source code is governed by the Apache 2.0 license that can be found in the LICENSE file.
 */
package ru.pixnews.gradle.config.firebase

import io.kotest.matchers.shouldBe
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.MethodSource
import java.util.Properties

class FirebaseConfigReaderTest {
    @ParameterizedTest
    @MethodSource("configReaderTestSource")
    fun `Config reader should read properties`(source: ConfigReaderTest) {
        val properties = Properties().apply { putAll(source.properties) }
        val reader = FirebaseConfigReader(
            properties = properties,
            applicationId = source.applicationId,
        )

        val parsedOptions = reader.read()

        parsedOptions shouldBe source.expectedResult
    }

    data class ConfigReaderTest(
        val properties: Map<String, String>,
        val applicationId: String? = null,
        val expectedResult: LocalFirebaseOptions,
    ) {
        override fun toString(): String {
            return "[applicationId=$applicationId]"
        }
    }

    companion object {
        private val allDefaultKeysMap = mapOf(
            "firebase_project_id" to "test-app",
            "firebase_google_api_key" to "AIzaAaA1_aaaaaaaaaaaaaaaaaaaaaaaaaaaaaa",
            "firebase_google_app_id" to "1:102030456789:android:112233445566778899aabb",
            "firebase_database_url" to "test-app-url",
            "firebase_ga_tracking_id" to "test-tracking-id",
            "firebase_gcm_default_sender_id" to "123456789012",
            "firebase_storage_bucket" to "test-app.appspot.com",
        )
        private val benchmarkAppKeysMap = mapOf(
            "firebase_test_app_benchmark_project_id" to "test-app-benchmark",
            "firebase_test_app_benchmark_google_api_key" to "BIzaAaA1_aaaaaaaaaaaaaaaaaaaaaaaaaaaaaa",
            "firebase_test_app_benchmark_google_app_id" to "2:102030456789:android:112233445566778899aabb",
            "firebase_test_app_benchmark_database_url" to "benchmark-test-app-url",
            "firebase_test_app_benchmark_ga_tracking_id" to "benchmark-test-tracking-id",
            "firebase_test_app_benchmark_gcm_default_sender_id" to "234567890123",
            "firebase_test_app_benchmark_storage_bucket" to "benchmark-test-app.appspot.com",
        )
        private val releaseAppKeysMap = mapOf(
            "firebase_test_app_release_project_id" to "test-app-release",
            "firebase_test_app_release_google_api_key" to "CIzaAaA1_aaaaaaaaaaaaaaaaaaaaaaaaaaaaaa",
            "firebase_test_app_release_google_app_id" to "3:102030456789:android:112233445566778899aabb",
            "firebase_test_app_release_database_url" to "release-test-app-url",
            "firebase_test_app_release_ga_tracking_id" to "release-test-tracking-id",
            "firebase_test_app_release_gcm_default_sender_id" to "345678901234",
            "firebase_test_app_release_storage_bucket" to "release-test-app.appspot.com",
        )

        @JvmStatic
        fun configReaderTestSource(): List<ConfigReaderTest> = listOf(
            ConfigReaderTest(
                properties = mapOf(),
                applicationId = null,
                expectedResult = LocalFirebaseOptions.empty,
            ),
            ConfigReaderTest(
                properties = mapOf(),
                applicationId = "test.app",
                expectedResult = LocalFirebaseOptions.empty,
            ),
            ConfigReaderTest(
                properties = allDefaultKeysMap + benchmarkAppKeysMap + releaseAppKeysMap,
                applicationId = null,
                expectedResult = LocalFirebaseOptions(
                    projectId = "test-app",
                    apiKey = "AIzaAaA1_aaaaaaaaaaaaaaaaaaaaaaaaaaaaaa",
                    applicationId = "1:102030456789:android:112233445566778899aabb",
                    databaseUrl = "test-app-url",
                    gaTrackingId = "test-tracking-id",
                    gcmSenderId = "123456789012",
                    storageBucket = "test-app.appspot.com",
                ),
            ),
            ConfigReaderTest(
                properties = allDefaultKeysMap + benchmarkAppKeysMap + releaseAppKeysMap,
                applicationId = "test.app.default",
                expectedResult = LocalFirebaseOptions(
                    projectId = "test-app",
                    apiKey = "AIzaAaA1_aaaaaaaaaaaaaaaaaaaaaaaaaaaaaa",
                    applicationId = "1:102030456789:android:112233445566778899aabb",
                    databaseUrl = "test-app-url",
                    gaTrackingId = "test-tracking-id",
                    gcmSenderId = "123456789012",
                    storageBucket = "test-app.appspot.com",
                ),
            ),
            ConfigReaderTest(
                properties = allDefaultKeysMap + benchmarkAppKeysMap + releaseAppKeysMap,
                applicationId = "test.app.benchmark",
                expectedResult = LocalFirebaseOptions(
                    projectId = "test-app-benchmark",
                    apiKey = "BIzaAaA1_aaaaaaaaaaaaaaaaaaaaaaaaaaaaaa",
                    applicationId = "2:102030456789:android:112233445566778899aabb",
                    databaseUrl = "benchmark-test-app-url",
                    gaTrackingId = "benchmark-test-tracking-id",
                    gcmSenderId = "234567890123",
                    storageBucket = "benchmark-test-app.appspot.com",
                ),
            ),
        )
    }
}
