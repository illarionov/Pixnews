/*
 * Copyright (c) 2023, the Pixnews project authors and contributors. Please see the AUTHORS file for details.
 * Use of this source code is governed by the Apache 2.0 license that can be found in the LICENSE file.
 */

package ru.pixnews.foundation.database.model

import ru.pixnews.domain.model.id.GameId

@JvmInline
public value class PixnewsDatabaseGameId(
    private val id: Long,
) : GameId {
    override fun toString(): String = "pixnews-$id"
}
