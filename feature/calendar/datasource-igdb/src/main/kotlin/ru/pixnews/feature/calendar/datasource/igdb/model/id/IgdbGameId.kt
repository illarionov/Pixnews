/*
 * Copyright (c) 2023, the Pixnews project authors and contributors. Please see the AUTHORS file for details.
 * Use of this source code is governed by the Apache 2.0 license that can be found in the LICENSE file.
 */

package ru.pixnews.feature.calendar.datasource.igdb.model.id

import ru.pixnews.domain.model.id.GameId

@JvmInline
internal value class IgdbGameId(
    val id: Long,
) : GameId
