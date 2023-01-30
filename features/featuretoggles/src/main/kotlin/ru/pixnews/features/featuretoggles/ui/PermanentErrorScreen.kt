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
package ru.pixnews.features.featuretoggles.ui

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
import ru.pixnews.features.featuretoggles.R.string
import ru.pixnews.features.featuretoggles.model.PermanentErrorMessage
import ru.pixnews.foundation.ui.theme.PixnewsTheme
import ru.pixnews.libraries.compose.utils.fillMinHeight
import ru.pixnews.libraries.ui.tooling.CompletePreviews

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
