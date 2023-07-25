/*
 * Copyright (c) 2023, the Pixnews project authors and contributors. Please see the AUTHORS file for details.
 * Use of this source code is governed by the Apache 2.0 license that can be found in the LICENSE file.
 */
package ru.pixnews.foundation.ui.assets.icons.navigation

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Immutable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import ru.pixnews.foundation.ui.assets.icons.navigation.NavigationIcons.Calendar
import ru.pixnews.foundation.ui.assets.icons.navigation.NavigationIcons.Collections
import ru.pixnews.foundation.ui.assets.icons.navigation.NavigationIcons.Profile

public object NavigationIcons {
    public object Calendar : IconPair {
        public override val unfilled: ImageVector
            get() = CalendarMonthRoundedUnfilled400w0g24dp
        public override val filled: ImageVector
            get() = CalendarMonthRoundedFilled400w0g24dp
    }

    public object Collections : IconPair {
        public override val unfilled: ImageVector
            get() = CollectionsBookmarkRoundedUnfilled400w0g24dp
        public override val filled: ImageVector
            get() = CollectionsBookmarkRoundedFilled400w0g24dp
    }

    public object Profile : IconPair {
        public override val unfilled: ImageVector
            get() = PersonRoundedUnfilled400w0g24dp
        public override val filled: ImageVector
            get() = PersonRoundedFilled400w0g24dp
    }

    @Immutable
    public interface IconPair {
        public val unfilled: ImageVector
        public val filled: ImageVector
    }
}

@Preview(
    showBackground = true,
    backgroundColor = 0xffffff,
)
@Composable
private fun NavigationIconsPreview() {
    LazyVerticalGrid(
        columns = GridCells.Fixed(2),
        contentPadding = PaddingValues(vertical = 24.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp),
        horizontalArrangement = Arrangement.spacedBy(12.dp),
    ) {
        items(
            items = listOf(
                Calendar.unfilled,
                Calendar.filled,
                Collections.unfilled,
                Collections.filled,
                Profile.unfilled,
                Profile.filled,
            ),
        ) { icon ->
            Image(
                imageVector = icon,
                contentDescription = null,
                modifier = Modifier.aspectRatio(1f),
            )
        }
    }
}
