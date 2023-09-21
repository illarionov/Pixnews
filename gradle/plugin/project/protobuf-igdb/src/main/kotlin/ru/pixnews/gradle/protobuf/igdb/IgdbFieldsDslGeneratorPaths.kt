/*
 * Copyright (c) 2023, the Pixnews project authors and contributors. Please see the AUTHORS file for details.
 * Use of this source code is governed by the Apache 2.0 license that can be found in the LICENSE file.
 */

package ru.pixnews.gradle.protobuf.igdb

import com.squareup.kotlinpoet.ClassName

internal object IgdbFieldsDslGeneratorPaths {
    const val FEATURE_FLAG_WITH_BACKING_INSTANCE = false
    const val PACKAGE_NAME = "ru.pixnews.feature.calendar.datasource.igdb.field"
    const val SCHEME_PACKAGE_NAME = "ru.pixnews.feature.calendar.datasource.igdb.field.scheme"
    const val IGDBCLIENT_MODEL_PACKAGE_NAME = "ru.pixnews.igdbclient.model"
    val IGDB_FIELD_DSL_CLASS = ClassName("ru.pixnews.feature.calendar.datasource.igdb.dsl", "IgdbFieldDsl")
    val IGDB_REQUEST_FIELD_CLASS = ClassName("ru.pixnews.feature.calendar.datasource.igdb.dsl", "IgdbRequestField")
    val IGDB_FIELD_INTERFACE = ClassName("ru.pixnews.feature.calendar.datasource.igdb.field.scheme", "IgdbField")
    val IGDB_REQUEST_FIELDS_BASE_CLASS = ClassName(
        "ru.pixnews.feature.calendar.datasource.igdb.field",
        "IgdbRequestFields",
    )
    val EXCLUDED_MESSAGES: Set<String> = setOf(
        "Count",
        "MultiQueryResult",
        "MultiQueryResultArray",
    )
}
