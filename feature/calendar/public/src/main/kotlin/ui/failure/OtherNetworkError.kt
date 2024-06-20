/*
 * Copyright (c) 2023, the Pixnews project authors and contributors. Please see the AUTHORS file for details.
 * Use of this source code is governed by the Apache 2.0 license that can be found in the LICENSE file.
 */

package ru.pixnews.feature.calendar.ui.failure

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.widthIn
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.FilledTonalButton
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
import ru.pixnews.feature.calendar.R
import ru.pixnews.foundation.ui.assets.icons.PixnewsIcons
import ru.pixnews.foundation.ui.design.R.string
import ru.pixnews.foundation.ui.theme.PixnewsTheme

@Composable
internal fun OtherNetworkError(
    onClickRefresh: () -> Unit,
    modifier: Modifier = Modifier,
    refreshActive: () -> Boolean = { false },
    paddingValues: PaddingValues = PaddingValues(),
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(32.dp, Alignment.CenterVertically),
        modifier = modifier
            .fillMaxSize()
            .widthIn(max = 500.dp)
            .padding(paddingValues),
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.semantics(mergeDescendants = true) {},
        ) {
            Icon(
                imageVector = PixnewsIcons.content.ErrorOutline,
                tint = MaterialTheme.colorScheme.primary,
                contentDescription = null,
                modifier = Modifier
                    .padding(bottom = 32.dp)
                    .size(100.dp),
            )
            Text(
                text = stringResource(string.title_error),
                style = MaterialTheme.typography.titleMedium,
            )
            Text(
                text = stringResource(R.string.title_error_can_not_load_release_calendar),
                style = MaterialTheme.typography.bodyMedium,
                maxLines = 5,
            )
        }

        FilledTonalButton(
            contentPadding = ButtonDefaults.ButtonWithIconContentPadding,
            onClick = onClickRefresh,
            enabled = !refreshActive(),
        ) {
            Icon(
                imageVector = PixnewsIcons.content.Refresh,
                contentDescription = stringResource(R.string.refresh_release_calendar_content_description),
                modifier = Modifier.size(ButtonDefaults.IconSize),
            )
            Spacer(Modifier.size(ButtonDefaults.IconSpacing))
            Text(stringResource(string.button_refresh))
        }
    }
}

@Preview
@Composable
private fun FailureOtherErrorPreview() {
    PixnewsTheme(useDynamicColor = false) {
        Surface(
            modifier = Modifier.fillMaxSize(),
        ) {
            OtherNetworkError(
                refreshActive = { true },
                onClickRefresh = { },
            )
        }
    }
}
