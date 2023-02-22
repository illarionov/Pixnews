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
package ru.pixnews.foundation.ui.design.assets.icons.contenrating

import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import ru.pixnews.foundation.ui.design.assets.icons.util.IconsPreview

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
