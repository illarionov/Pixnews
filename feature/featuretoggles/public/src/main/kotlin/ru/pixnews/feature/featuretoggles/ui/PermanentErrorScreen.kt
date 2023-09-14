/*
 * Copyright (c) 2023, the Pixnews project authors and contributors. Please see the AUTHORS file for details.
 * Use of this source code is governed by the Apache 2.0 license that can be found in the LICENSE file.
 */

package ru.pixnews.feature.featuretoggles.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.widthIn
import androidx.compose.material.icons.Icons.Outlined
import androidx.compose.material.icons.outlined.Warning
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import ru.pixnews.feature.featuretoggles.R.string
import ru.pixnews.feature.featuretoggles.model.PermanentErrorMessage
import ru.pixnews.foundation.ui.theme.PixnewsTheme
import ru.pixnews.library.compose.utils.fillMinHeight
import ru.pixnews.library.ui.tooling.CompletePreviews

@Composable
@Suppress("MagicNumber")
internal fun PermanentErrorScreen(
    message: PermanentErrorMessage,
    modifier: Modifier = Modifier,
    paddingValues: PaddingValues = PaddingValues(),
) {
    Surface(
        modifier = modifier
            .padding(paddingValues),
    ) {
        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.TopCenter,
        ) {
            Column(
                modifier = Modifier
                    .fillMinHeight(0.5f),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally,
            ) {
                Icon(
                    imageVector = Outlined.Warning,
                    contentDescription = null,
                    tint = MaterialTheme.colorScheme.error.copy(alpha = 0.8f),
                    modifier = Modifier
                        .padding(top = 24.dp, bottom = 4.dp)
                        .size(72.dp),
                )
                Text(
                    text = stringResource(string.title_error),
                    style = MaterialTheme.typography.titleLarge,
                )

                Text(
                    modifier = Modifier
                        .padding(horizontal = 24.dp, vertical = 8.dp)
                        .widthIn(max = 500.dp),
                    text = message.localizedMessage,
                    color = MaterialTheme.colorScheme.error,
                    textAlign = TextAlign.Center,
                    maxLines = 5,
                )
            }
        }
    }
}

@CompletePreviews
@Composable
@Suppress("MagicNumber")
private fun FeatureTogglesComposablePreviewError() {
    val message = PermanentErrorMessage(RuntimeException("Long long long longtext string".repeat(20)))
    PixnewsTheme {
        PermanentErrorScreen(message)
    }
}
