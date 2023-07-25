/*
 * Copyright (c) 2023, the Pixnews project authors and contributors. Please see the AUTHORS file for details.
 * Use of this source code is governed by the Apache 2.0 license that can be found in the LICENSE file.
 */
package ru.pixnews.foundation.ui.design.overlay

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.requiredSize
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import ru.pixnews.foundation.ui.theme.PixnewsTheme

@Composable
public fun PixnewsLoadingOverlay(
    modifier: Modifier = Modifier,
) {
    CircularProgressIndicator(
        modifier = modifier
            .fillMaxSize()
            .requiredSize(64.dp),
    )
}

@Preview(showBackground = true)
@Composable
private fun LoadingOverlayPreview() {
    PixnewsTheme {
        PixnewsLoadingOverlay()
    }
}
