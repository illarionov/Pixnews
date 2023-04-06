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
package ru.pixnews.foundation.ui.design.image

import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.painter.Painter
import ru.pixnews.foundation.ui.assets.icons.image.ImagePlaceholders
import ru.pixnews.foundation.ui.theme.md_theme_palette_neutral_variant_95
import ru.pixnews.library.compose.utils.painter.rememberCenteredInParentVectorPainter

@Composable
public fun ImagePlaceholders.noImageLargePainter(): Painter {
    return rememberCenteredInParentVectorPainter(
        image = noImageLarge,
        backgroundColor = md_theme_palette_neutral_variant_95,
    )
}

@Composable
public fun ImagePlaceholders.errorLoadingImageLargePainter(): Painter {
    return rememberCenteredInParentVectorPainter(
        image = errorLoadingImageLarge,
        backgroundColor = md_theme_palette_neutral_variant_95,
    )
}

@Composable
public fun ImagePlaceholders.noImageSmallPainter(): Painter {
    return rememberCenteredInParentVectorPainter(
        image = noImageSmall,
        backgroundColor = md_theme_palette_neutral_variant_95,
    )
}

@Composable
public fun ImagePlaceholders.errorLoadingImageSmallPainter(): Painter {
    return rememberCenteredInParentVectorPainter(
        image = errorLoadingImageSmall,
        backgroundColor = md_theme_palette_neutral_variant_95,
    )
}
