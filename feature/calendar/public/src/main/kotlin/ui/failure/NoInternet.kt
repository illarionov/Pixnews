/*
 * Copyright (c) 2023, the Pixnews project authors and contributors. Please see the AUTHORS file for details.
 * Use of this source code is governed by the Apache 2.0 license that can be found in the LICENSE file.
 */

package ru.pixnews.feature.calendar.ui.failure

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.widthIn
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import at.released.debuglayout.DebugLayout.Companion.debugLayout
import at.released.debuglayout.ruler.DebugRulerVerticalZeroPoint
import at.released.debuglayout.ruler.DebugRulerVerticalZeroPoint.Alignment.CENTER
import ru.pixnews.feature.calendar.R
import ru.pixnews.foundation.ui.design.R.string
import ru.pixnews.foundation.ui.theme.PixnewsTheme

@Composable
internal fun NoInternet(
    modifier: Modifier = Modifier,
    paddingValues: PaddingValues = PaddingValues(),
) {
    Box(
        contentAlignment = Alignment.Center,
        modifier = modifier.fillMaxSize(),
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center,
            modifier = Modifier
                .padding(paddingValues)
                .semantics(mergeDescendants = true) {},
        ) {
            Icon(
                imageVector = baselineSignalWifi0BarVector,
                tint = MaterialTheme.colorScheme.primary,
                contentDescription = null,
            )
            Text(
                modifier = Modifier.padding(top = 32.dp),
                text = stringResource(string.title_error),
                style = MaterialTheme.typography.titleMedium,
            )

            Text(
                modifier = Modifier.widthIn(max = 500.dp),
                text = stringResource(R.string.title_error_no_internet_connection),
                style = MaterialTheme.typography.bodyMedium,
                maxLines = 5,
            )
        }
    }
}

@Preview
@Composable
private fun FailureNoInternetPreview() {
    PixnewsTheme(useDynamicColor = false) {
        Surface(
            color = MaterialTheme.colorScheme.background,
            modifier = Modifier
                .fillMaxSize()
                .debugLayout {
                    grid(size = 8.dp)
                    verticalRuler(
                        zeroOffset = DebugRulerVerticalZeroPoint(CENTER),
                    )
                },
        ) {
            NoInternet()
        }
    }
}
