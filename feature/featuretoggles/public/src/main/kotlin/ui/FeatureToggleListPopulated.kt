/*
 * Copyright (c) 2023, the Pixnews project authors and contributors. Please see the AUTHORS file for details.
 * Use of this source code is governed by the Apache 2.0 license that can be found in the LICENSE file.
 */

package ru.pixnews.feature.featuretoggles.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.ListItemDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.unit.dp
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.ImmutableSet
import ru.pixnews.feature.featuretoggles.FeatureTogglePreviewFixtures
import ru.pixnews.feature.featuretoggles.FeatureTogglePreviewParameterProvider
import ru.pixnews.feature.featuretoggles.R.string
import ru.pixnews.feature.featuretoggles.model.FeatureToggleUiModel
import ru.pixnews.feature.featuretoggles.model.VariantUiModel
import ru.pixnews.foundation.featuretoggles.ExperimentKey
import ru.pixnews.foundation.ui.assets.icons.action.ActionIcons
import ru.pixnews.foundation.ui.theme.PixnewsTheme

@Composable
internal fun FeatureToggleListPopulated(
    toggles: ImmutableList<FeatureToggleUiModel>,
    modifier: Modifier = Modifier,
    paddingValues: PaddingValues = PaddingValues(),
    onClickResetExperimentOverride: (ExperimentKey) -> Unit = {},
    onSelectExperimentVariant: (ExperimentKey, VariantUiModel) -> Unit = { _, _ -> },
) {
    Surface(
        modifier = modifier.fillMaxSize(),
    ) {
        LazyColumn(
            modifier = Modifier,
            verticalArrangement = Arrangement.Top,
            horizontalAlignment = Alignment.Start,
            contentPadding = paddingValues,
        ) {
            for (toggle in toggles) {
                item(key = toggle.key.stringValue, contentType = toggle.type) {
                    ToggleListItem(
                        toggle = toggle,
                        onClickResetExperimentOverride = { onClickResetExperimentOverride(toggle.key) },
                        onSelectExperimentVariant = { variant ->
                            onSelectExperimentVariant(
                                toggle.key,
                                variant,
                            )
                        },
                    )
                }
            }
        }
    }
}

@Composable
internal fun ToggleListItem(
    toggle: FeatureToggleUiModel,
    modifier: Modifier = Modifier,
    onClickResetExperimentOverride: () -> Unit = {},
    onSelectExperimentVariant: (VariantUiModel) -> Unit = {},
) {
    Surface(
        modifier = modifier,
        shape = ListItemDefaults.shape,
        color = ListItemDefaults.containerColor,
        contentColor = MaterialTheme.colorScheme.onSurfaceVariant,
        tonalElevation = ListItemDefaults.Elevation,
        shadowElevation = ListItemDefaults.Elevation,
    ) {
        Column(
            modifier = Modifier
                .heightIn(min = 146.dp)
                .padding(horizontal = 16.dp, vertical = 12.dp),
        ) {
            Text(
                text = toggle.key.stringValue,
                style = MaterialTheme.typography.labelMedium,
                color = MaterialTheme.colorScheme.onSurfaceVariant,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
                modifier = Modifier.heightIn(min = 16.dp),
            )
            Text(
                text = toggle.name,
                style = MaterialTheme.typography.bodyLarge,
                color = MaterialTheme.colorScheme.onSurface,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
                modifier = Modifier.heightIn(min = 20.dp),
            )
            Text(
                text = toggle.description,
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onSurfaceVariant,
                maxLines = 3,
                overflow = TextOverflow.Ellipsis,
                modifier = Modifier.heightIn(min = 24.dp),
            )
            GroupSelectionRow(
                toggle = toggle,
                onSelectExperimentVariant = onSelectExperimentVariant,
                onClickResetExperimentOverride = onClickResetExperimentOverride,
            )
        }
    }
}

@Composable
private fun GroupSelectionRow(
    toggle: FeatureToggleUiModel,
    modifier: Modifier = Modifier,
    onSelectExperimentVariant: (VariantUiModel) -> Unit = {},
    onClickResetExperimentOverride: () -> Unit = {},
) {
    Row(modifier = modifier) {
        Box(
            modifier = Modifier
                .heightIn(min = 64.dp)
                .weight(1f),
            contentAlignment = Alignment.CenterStart,
        ) {
            GroupSelectionDropdown(
                modifier = Modifier
                    .width(200.dp),
                selectedKey = toggle.activeVariant.stringValue,
                variants = toggle.variants,
                onSelectVariant = onSelectExperimentVariant,
            )
        }
        if (toggle.isOverridden) {
            TextButton(
                modifier = Modifier
                    .heightIn(min = 64.dp)
                    .align(Alignment.CenterVertically),
                onClick = onClickResetExperimentOverride,
            ) {
                Icon(
                    imageVector = ActionIcons.Cancel,
                    contentDescription = stringResource(string.content_description_reset_override),
                    modifier = Modifier
                        .size(ButtonDefaults.IconSize),
                )
                Spacer(Modifier.size(ButtonDefaults.IconSpacing))
                Text(
                    text = stringResource(string.button_reset_override),
                )
            }
        }
    }
}

@Composable
internal fun GroupSelectionDropdown(
    selectedKey: String,
    variants: ImmutableSet<VariantUiModel>,
    modifier: Modifier = Modifier,
    defaultExpanded: Boolean = false,
    onSelectVariant: (VariantUiModel) -> Unit = {},
) {
    var expanded: Boolean by remember { mutableStateOf(defaultExpanded) }
    ExposedDropdownMenuBox(
        modifier = modifier,
        expanded = expanded,
        onExpandedChange = { expanded = it },
    ) {
        OutlinedTextField(
            modifier = Modifier.menuAnchor(),
            readOnly = true,
            value = selectedKey,
            onValueChange = {},
            trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = expanded) },
            colors = ExposedDropdownMenuDefaults.outlinedTextFieldColors(),
        )
        ExposedDropdownMenu(
            expanded = expanded,
            onDismissRequest = { expanded = false },
        ) {
            VariantDropdownMenu(
                variants,
                onSelectVariant = {
                    expanded = false
                    onSelectVariant(it)
                },
            )
        }
    }
}

@Composable
private fun ColumnScope.VariantDropdownMenu(
    variants: ImmutableSet<VariantUiModel>,
    onSelectVariant: (VariantUiModel) -> Unit = {},
) {
    for (variant in variants) {
        DropdownMenuItem(
            text = {
                Column(
                    modifier = Modifier,
                    verticalArrangement = Arrangement.Center,
                ) {
                    Text(
                        text = variant.key.stringValue,
                        style = MaterialTheme.typography.bodyLarge,
                        color = MaterialTheme.colorScheme.onSurface,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis,
                    )
                    Text(
                        text = variant.description,
                        style = MaterialTheme.typography.bodyMedium,
                        color = MaterialTheme.colorScheme.onSurfaceVariant,
                        maxLines = 3,
                        overflow = TextOverflow.Ellipsis,
                    )
                }
            },
            onClick = { onSelectVariant(variant) },
            contentPadding = PaddingValues(
                horizontal = 16.dp,
                vertical = 8.dp,
            ),
        )
    }
}

@Preview
@Composable
private fun FeatureTogglesComposablePreviewPopulated(
    @PreviewParameter(FeatureTogglePreviewParameterProvider::class) toggles: ImmutableList<FeatureToggleUiModel>,
) {
    PixnewsTheme {
        FeatureToggleListPopulated(toggles)
    }
}

@Preview
@Composable
private fun PreviewFeatureTogglesDropdownMenu() {
    val variants = FeatureTogglePreviewFixtures.homeScreenGameCardModel.variants
    PixnewsTheme {
        Card(
            colors = CardDefaults.elevatedCardColors(),
            elevation = CardDefaults.elevatedCardElevation(),
        ) {
            Column {
                VariantDropdownMenu(variants)
            }
        }
    }
}
