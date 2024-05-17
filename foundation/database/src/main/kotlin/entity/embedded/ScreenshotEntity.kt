/*
 * Copyright (c) 2023, the Pixnews project authors and contributors. Please see the AUTHORS file for details.
 * Use of this source code is governed by the Apache 2.0 license that can be found in the LICENSE file.
 */

package ru.pixnews.foundation.database.entity.embedded

import androidx.room.Embedded
import androidx.room.Entity
import kotlinx.datetime.Instant
import ru.pixnews.foundation.database.model.ColorWrapper

@Entity
public data class ScreenshotEntity(
    val releasedAt: Instant,
    val url: String,
    val width: Long = 0,
    val height: Long = 0,
    val prevailColor: ColorWrapper? = null,
    @Embedded(prefix = "dataSource_")
    val dataSource: DataSourceMetadataEntity,
)
