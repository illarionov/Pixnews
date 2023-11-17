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
import ru.pixnews.domain.model.company.CompanyStatus
import ru.pixnews.domain.model.company.CompanyStatus.UNKNOWN
import ru.pixnews.foundation.database.entity.embedded.DataSourceMetadataEntity
import ru.pixnews.foundation.database.entity.embedded.GameReleaseDateEntity
import ru.pixnews.foundation.database.model.CountryCodeWrapper

/**
 * Game company.
 *
 * Represents [Company] in the database.
 *
 * @param id Auto-generated Company ID in the database
 * @param name Canonical name of the company
 * @param foundingDate Foundation date, if known
 * @param status Status of the company (at the time of last update)
 * @param countryCode Original country of the company
 * @param parentCompany
 * @param dataSource Additional information on the data source used to obtain this information
 */
@Entity(
    tableName = "company",
    foreignKeys = [
        ForeignKey(
            entity = CompanyEntity::class,
            parentColumns = ["id"],
            childColumns = ["parentCompany"],
            onDelete = ForeignKey.SET_NULL,
            onUpdate = ForeignKey.CASCADE,
            deferred = true,
        ),
    ],
    indices = [
        Index("parentCompany"),
        Index("dataSource_type", "dataSource_externalId", unique = true),
    ],
)
public data class CompanyEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    @ColumnInfo(collate = ColumnInfo.NOCASE)
    val name: String,

    @Embedded(prefix = "foundingDate_")
    val foundingDate: GameReleaseDateEntity?,
    val status: CompanyStatus = UNKNOWN,
    val countryCode: CountryCodeWrapper? = null,
    val parentCompany: Long? = null,
    @Embedded(prefix = "dataSource_") // TODO: 1:M?
    val dataSource: DataSourceMetadataEntity,
)
