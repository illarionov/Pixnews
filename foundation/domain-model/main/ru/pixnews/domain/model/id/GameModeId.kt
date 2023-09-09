/*
 * Copyright (c) 2023, the Pixnews project authors and contributors. Please see the AUTHORS file for details.
 * Use of this source code is governed by the Apache 2.0 license that can be found in the LICENSE file.
 */
package ru.pixnews.domain.model.id

public interface GameModeId : ExternalId {
    public companion object {
        public operator fun invoke(id: String): GameId = DefaultGameModeId(id)
    }
}

@JvmInline
public value class DefaultGameModeId(
    public val id: String,
) : GameId
