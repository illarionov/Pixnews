/*
 * Copyright (c) 2023, the Pixnews project authors and contributors. Please see the AUTHORS file for details.
 * Use of this source code is governed by the Apache 2.0 license that can be found in the LICENSE file.
 */

package ru.pixnews.foundation.ui.imageloader.coil.tooling

import android.graphics.drawable.Drawable
import androidx.compose.runtime.Immutable
import ru.pixnews.domain.model.url.ImageUrl
import kotlin.time.Duration
import kotlin.time.Duration.Companion.ZERO

@Immutable
public class DebugImageUrl(
    internal val originalUrl: ImageUrl,
    public val loadingDelay: Duration = ZERO,
    public val overriddenResult: OverrideResult = OverrideResult.Original,
) : ImageUrl by originalUrl

@Immutable
public sealed class OverrideResult {
    public object Original : OverrideResult()

    public class Success(
        public val drawable: Drawable? = null,
    ) : OverrideResult()

    public class Error(
        public val throwable: Throwable? = null,
    ) : OverrideResult()
}

public fun ImageUrl.withDebug(
    loadingDelay: Duration = ZERO,
    overrideResult: OverrideResult = OverrideResult.Original,
): DebugImageUrl = DebugImageUrl(this, loadingDelay, overrideResult)
