/*
 * Copyright (c) 2023, the Pixnews project authors and contributors. Please see the AUTHORS file for details.
 * Use of this source code is governed by the Apache 2.0 license that can be found in the LICENSE file.
 */
package ru.pixnews.domain.model.util

import ru.pixnews.domain.model.id.ExternalId
import ru.pixnews.domain.model.util.Ref.FullObject

/**
 * Object or reference to object
 */
public sealed interface Ref<out T : HasId<I>, out I : ExternalId> {
    /**
     * Fully loaded object
     */
    @JvmInline
    public value class FullObject<out T : HasId<I>, out I : ExternalId>(
        public val value: T,
    ) : Ref<T, I>

    /**
     * A reference that can be used to download the full object
     */
    @JvmInline
    public value class Id<I : ExternalId>(
        public val id: I,
    ) : Ref<Nothing, I>
}

public fun <T : HasId<*>> Ref<T, *>.getObjectOrThrow(): T =
    (this as? FullObject)?.value ?: error("Ref `$this` not loaded")
