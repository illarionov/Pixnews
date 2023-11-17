/*
 * Copyright (c) 2023, the Pixnews project authors and contributors. Please see the AUTHORS file for details.
 * Use of this source code is governed by the Apache 2.0 license that can be found in the LICENSE file.
 */

package ru.pixnews.foundation.database.entity.embedded

import androidx.room.Entity
import kotlinx.datetime.Instant
import okio.ByteString
import ru.pixnews.domain.model.datasource.DataSourceType

/**
 * Additional information on the data source
 *
 * @param type ID of the Data source
 * @param externalId ID of the entity in the remote data source
 * @param updatedAt value of the "updated in" field of an entity in the remote data source
 * @param metadata additional serialized parameters
 */
@Entity
public data class DataSourceMetadataEntity(
    val type: DataSourceType,
    val externalId: String?,
    val updatedAt: Instant,
    val metadata: ByteString? = null,
)
