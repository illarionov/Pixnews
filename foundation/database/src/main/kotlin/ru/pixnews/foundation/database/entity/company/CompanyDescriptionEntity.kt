/*
 * Copyright (c) 2023, the Pixnews project authors and contributors. Please see the AUTHORS file for details.
 * Use of this source code is governed by the Apache 2.0 license that can be found in the LICENSE file.
 */

package ru.pixnews.foundation.database.entity.company

import androidx.room.ColumnInfo
import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey
import ru.pixnews.foundation.database.entity.embedded.DataSourceMetadataEntity
import ru.pixnews.foundation.database.model.LanguageCodeWrapper
import ru.pixnews.library.internationalization.language.LanguageCode

/**
 * Localized company description
 *
 * @param id Auto-generated ID in the database
 * @param companyId ID of the company in [CompanyEntity] table
 * @param language ISO 639-3 language code of the description
 * @param text Localized description
 * @param dataSource Additional information on the data source used to obtain this information
 */
@Entity(
    tableName = "companyDescription",
    foreignKeys = [
        ForeignKey(
            entity = CompanyEntity::class,
            parentColumns = ["id"],
            childColumns = ["companyId"],
            onDelete = ForeignKey.CASCADE,
            onUpdate = ForeignKey.CASCADE,
            deferred = true,
        ),
    ],
    indices = [
        Index("companyId"),
    ],
)
public data class CompanyDescriptionEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    val companyId: Long,

    val language: LanguageCodeWrapper = LanguageCodeWrapper(LanguageCode.ENGLISH),

    @ColumnInfo(collate = ColumnInfo.UNICODE)
    val text: String,

    @Embedded(prefix = "dataSource_")
    val dataSource: DataSourceMetadataEntity,
)
