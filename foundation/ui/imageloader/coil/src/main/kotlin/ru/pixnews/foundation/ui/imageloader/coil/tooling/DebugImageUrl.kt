/*
 * Copyright 2023 Alexey Illarionov
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package ru.pixnews.foundation.ui.imageloader.coil.tooling

import android.graphics.drawable.Drawable
import androidx.compose.runtime.Immutable
import ru.pixnews.domain.model.util.ImageUrl
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
