/*
 * Copyright (c) 2023, the Pixnews project authors and contributors. Please see the AUTHORS file for details.
 * Use of this source code is governed by the Apache 2.0 license that can be found in the LICENSE file.
 */
package ru.pixnews.foundation.ui.assets.icons.contenrating

import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import ru.pixnews.foundation.ui.assets.icons.util.IconsPreview

public object ContentRatingIcons {
    public object Esrb {
        public val everyone: ImageVector
            get() = EsrbEveryone
        public val earlyChildhood: ImageVector
            get() = EsrbAdultsOnly18
        public val everyone10plus: ImageVector
            get() = EsrbEveryone10
        public val teen: ImageVector
            get() = EsrbTeen
        public val mature17plus: ImageVector
            get() = EsrbMature17
        public val adultsOnly18plus: ImageVector
            get() = EsrbAdultsOnly18
        public val ratingPending: ImageVector
            get() = EsrbRatingPending
    }

    public object Pegi {
        public val pegi3: ImageVector
            get() = Pegi3
        public val pegi4: ImageVector
            get() = Pegi4
        public val pegi7: ImageVector
            get() = Pegi7
        public val pegi12: ImageVector
            get() = Pegi12
        public val pegi16: ImageVector
            get() = Pegi16
        public val pegi18: ImageVector
            get() = Pegi18
    }
}

@Preview(
    showBackground = true,
    backgroundColor = 0xffffff,
)
@Composable
private fun ContentRatingEsrbIconsPreview() = IconsPreview(
    icons = listOf(
        ContentRatingIcons.Esrb.everyone,
        ContentRatingIcons.Esrb.earlyChildhood,
        ContentRatingIcons.Esrb.everyone10plus,
        ContentRatingIcons.Esrb.teen,
        ContentRatingIcons.Esrb.mature17plus,
        ContentRatingIcons.Esrb.adultsOnly18plus,
        ContentRatingIcons.Esrb.ratingPending,
    ),
    iconSize = 144.dp,
)

@Preview(
    showBackground = true,
    backgroundColor = 0xffffff,
)
@Composable
private fun ContentRatingPegiIconsPreview() = IconsPreview(
    icons = listOf(
        ContentRatingIcons.Pegi.pegi3,
        ContentRatingIcons.Pegi.pegi4,
        ContentRatingIcons.Pegi.pegi7,
        ContentRatingIcons.Pegi.pegi12,
        ContentRatingIcons.Pegi.pegi16,
        ContentRatingIcons.Pegi.pegi18,
    ),
    iconSize = 144.dp,
)
