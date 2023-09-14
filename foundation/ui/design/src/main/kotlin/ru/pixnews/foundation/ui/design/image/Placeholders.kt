/*
 * Copyright (c) 2023, the Pixnews project authors and contributors. Please see the AUTHORS file for details.
 * Use of this source code is governed by the Apache 2.0 license that can be found in the LICENSE file.
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
