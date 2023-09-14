/*
 * Copyright (c) 2023, the Pixnews project authors and contributors. Please see the AUTHORS file for details.
 * Use of this source code is governed by the Apache 2.0 license that can be found in the LICENSE file.
 */
package ru.pixnews.domain.model.company

import kotlinx.collections.immutable.ImmutableList
import ru.pixnews.domain.model.company.CompanyStatus.UNKNOWN
import ru.pixnews.domain.model.datasource.DataSource
import ru.pixnews.domain.model.id.CompanyId
import ru.pixnews.domain.model.locale.CountryCode
import ru.pixnews.domain.model.locale.Localized
import ru.pixnews.domain.model.url.ExternalLink
import ru.pixnews.domain.model.url.ExternalLinkType.OFFICIAL
import ru.pixnews.domain.model.url.ImageUrl
import ru.pixnews.domain.model.url.Url
import ru.pixnews.domain.model.util.ApproximateDate
import ru.pixnews.domain.model.util.HasId
import ru.pixnews.domain.model.util.Ref
import ru.pixnews.domain.model.util.RichText

public data class Company(
    override val id: CompanyId,
    val name: String,
    val description: Localized<RichText>,
    val avatar: ImageUrl?,
    val foundingDate: ApproximateDate?,
    val status: CompanyStatus = UNKNOWN,
    val country: CountryCode?,
    val parentCompany: Ref<Company>?,

    val dataSources: ImmutableList<DataSource>,
    val links: ImmutableList<ExternalLink>,
) : HasId<CompanyId> {
    val url: Url? = links.firstOrNull { it.type == OFFICIAL }?.url
}
