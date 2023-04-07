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
package ru.pixnews.feature.featuretoggles.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
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
    onResetExperimentOverrideClicked: (ExperimentKey) -> Unit = {},
    onExperimentVariantSelected: (ExperimentKey, VariantUiModel) -> Unit = { _, _ -> },
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
                        onResetExperimentOverrideClicked = { onResetExperimentOverrideClicked(toggle.key) },
                        onExperimentVariantSelected = { variant ->
                            onExperimentVariantSelected(
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
    onResetExperimentOverrideClicked: () -> Unit = {},
    onExperimentVariantSelected: (VariantUiModel) -> Unit = {},
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
                onExperimentVariantSelected = onExperimentVariantSelected,
                onResetExperimentOverrideClicked = onResetExperimentOverrideClicked,
            )
        }
    }
}

@Composable
private fun GroupSelectionRow(
    toggle: FeatureToggleUiModel,
    modifier: Modifier = Modifier,
    onExperimentVariantSelected: (VariantUiModel) -> Unit = {},
    onResetExperimentOverrideClicked: () -> Unit = {},
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
                onVariantSelected = onExperimentVariantSelected,
            )
        }
        if (toggle.isOverridden) {
            TextButton(
                modifier = Modifier
                    .heightIn(min = 64.dp)
                    .align(Alignment.CenterVertically),
                onClick = onResetExperimentOverrideClicked,
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
    onVariantSelected: (VariantUiModel) -> Unit,
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
                onVariantSelected = {
                    expanded = false
                    onVariantSelected(it)
                },
            )
        }
    }
}

@Composable
private fun VariantDropdownMenu(
    variants: ImmutableSet<VariantUiModel>,
    onVariantSelected: (VariantUiModel) -> Unit = {},
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
            onClick = { onVariantSelected(variant) },
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
private fun FeatureTogglesDropdownMenu() {
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
