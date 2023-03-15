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
package ru.pixnews.features.calendar.ui.header

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.WindowInsetsSides
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.only
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.sizeIn
import androidx.compose.foundation.layout.tappableElement
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.LocalContentColor
import androidx.compose.material3.LocalMinimumInteractiveComponentEnforcement
import androidx.compose.material3.LocalTextStyle
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.semantics.LiveRegionMode
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.semantics.liveRegion
import androidx.compose.ui.semantics.role
import androidx.compose.ui.semantics.selected
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.semantics.text
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import kotlinx.collections.immutable.ImmutableMap
import kotlinx.datetime.Clock.System
import kotlinx.datetime.LocalDate
import kotlinx.datetime.TimeZone
import kotlinx.datetime.todayIn
import ru.pixnews.features.calendar.PreviewFixtures
import ru.pixnews.features.calendar.R
import ru.pixnews.features.calendar.model.GamesOnDay
import ru.pixnews.features.calendar.model.LocalCalendarModel
import ru.pixnews.features.calendar.util.DateFormatter
import ru.pixnews.features.calendar.util.getWeekDays
import ru.pixnews.foundation.ui.theme.PixnewsTheme
import ru.pixnews.foundation.ui.theme.md_theme_palette_neutral_variant_90
import ru.pixnews.foundation.ui.theme.md_theme_palette_tertiary70
import ru.pixnews.libraries.compose.utils.defaultLocale

@Composable
internal fun DateSelectionHeader(
    activeDate: State<LocalDate>,
    games: ImmutableMap<LocalDate, GamesOnDay>,
    onYearMonthSelectionClick: () -> Unit,
    onDaySelectionClick: (LocalDate) -> Unit,
    modifier: Modifier = Modifier,
) {
    CompositionLocalProvider(LocalCalendarModel provides PreviewFixtures.DummyCalendarModel) {
        Column(
            modifier = modifier
                .fillMaxWidth(),
        ) {
            YearMonthPicker(
                activeDate = activeDate,
                onClick = onYearMonthSelectionClick,
            )
            WeekDaysRow(
                activeDate = activeDate,
                onDayClick = onDaySelectionClick,
                games = games,
            )
        }
    }
}

@Composable
internal fun WeekDaysRow(
    activeDate: State<LocalDate>,
    onDayClick: (LocalDate) -> Unit,
    games: ImmutableMap<LocalDate, GamesOnDay>,
    modifier: Modifier = Modifier,
) {
    Row(
        modifier = modifier
            .windowInsetsPadding(WindowInsets.tappableElement.only(WindowInsetsSides.Horizontal))
            .fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceEvenly,
    ) {
        val days = getWeekDays(activeDate.value, LocalCalendarModel.current.firstDayOfWeek)
        for (day in days) {
            val isActive = day == activeDate.value
            val contentDescription = AnnotatedString(
                formatDayContentDescription(
                    date = day,
                    gamesOnDay = games[day],
                ),
            )
            val contentColor = if (isActive) {
                MaterialTheme.colorScheme.primary
            } else {
                MaterialTheme.colorScheme.onSurface
            }
            val textStyle = if (isActive) {
                MaterialTheme.typography.labelMedium.copy(fontWeight = FontWeight.Bold)
            } else {
                MaterialTheme.typography.labelMedium
            }
            CompositionLocalProvider(
                LocalTextStyle provides textStyle,
                LocalMinimumInteractiveComponentEnforcement provides false,
            ) {
                Surface(
                    contentColor = contentColor,
                    selected = isActive,
                    onClick = { onDayClick(day) },
                    modifier = Modifier
                        .semantics(mergeDescendants = true) {
                            text = contentDescription
                            role = Role.Button
                            selected = isActive
                        },
                    shape = RectangleShape,
                    border = null,
                ) {
                    WeeksDay(day, isActive, games)
                }
            }
        }
    }
}

@Composable
internal fun WeeksDay(
    day: LocalDate,
    isActive: Boolean,
    games: ImmutableMap<LocalDate, GamesOnDay>,
    modifier: Modifier = Modifier,
) {
    val weekdayNames = LocalCalendarModel.current.weekdayShortNames
    Column(
        modifier = modifier
            .sizeIn(minWidth = 32.dp, minHeight = 42.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Top,
    ) {
        Text(
            maxLines = 1,
            text = weekdayNames[day.dayOfWeek]
                ?: error("No translation for ${day.dayOfWeek}"),
        )
        Text(
            color = if (isActive) {
                LocalContentColor.current
            } else {
                MaterialTheme.colorScheme.onSurfaceVariant
            },
            maxLines = 1,
            text = day.dayOfMonth.toString(),
        )
        val indicatorColor = getFollowedGamesIndicatorColor(games[day])
        Canvas(
            modifier = Modifier
                .size(8.dp),
        ) {
            @Suppress("FLOAT_IN_ACCURATE_CALCULATIONS")
            drawCircle(
                radius = 3.dp.toPx() / 2f,
                color = indicatorColor,
            )
        }
    }
}

@Composable
internal fun YearMonthPicker(
    activeDate: State<LocalDate>,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Box(
        modifier = modifier
            .windowInsetsPadding(WindowInsets.tappableElement.only(WindowInsetsSides.Horizontal))
            .fillMaxWidth()
            .height(56.dp),
    ) {
        YearMonthPickerButton(
            onClick = onClick,
            modifier = Modifier
                .widthIn(min = 156.dp),
        ) {
            val title = formatYearMonthPickerTitle(activeDate.value)
            Text(
                text = title,
                style = MaterialTheme.typography.labelLarge,
                modifier = Modifier
                    // Make the screen reader read out updates to the menu button text as the user
                    // scrolls to change the displayed month.
                    .semantics {
                        liveRegion = LiveRegionMode.Polite
                    },
            )
        }
    }
}

@Composable
internal fun YearMonthPickerButton(
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    content: @Composable () -> Unit,
) {
    TextButton(
        onClick = onClick,
        modifier = modifier,
        shape = CircleShape,
        colors = ButtonDefaults.textButtonColors(
            contentColor = MaterialTheme.colorScheme.onSurface,
        ),
        elevation = null,
        border = null,
    ) {
        content()
        Spacer(Modifier.size(ButtonDefaults.IconSpacing))
        Icon(
            Icons.Filled.ArrowDropDown,
            contentDescription = stringResource(R.string.yeas_selection_show_date_picker_content_description),
        )
    }
}

@Composable
private fun getFollowedGamesIndicatorColor(gamesOnDay: GamesOnDay?): Color {
    val color = if (gamesOnDay?.hasFollowedGames == true) {
        md_theme_palette_tertiary70
    } else if (gamesOnDay?.hasReleases == true) {
        md_theme_palette_neutral_variant_90
    } else {
        Color.Transparent
    }
    return color
}

@Composable
private fun formatDayContentDescription(
    date: LocalDate,
    gamesOnDay: GamesOnDay?,
): String {
    val dateString = DateFormatter.formatCalendarDateForContentDescription(date, defaultLocale())
    val skeletonResourceId = if (gamesOnDay?.hasFollowedGames == true) {
        R.string.weekdays_row_has_tracked_games_content_description
    } else {
        R.string.weekdays_row_no_tracked_games_content_description
    }

    return stringResource(id = skeletonResourceId, dateString)
}

@Composable
internal fun formatYearMonthPickerTitle(date: LocalDate): String {
    return DateFormatter.formatYearMonthSelectionTitle(date, defaultLocale())
}

@Preview
@Composable
private fun DateSelectionPreview() {
    PixnewsTheme(
        useDynamicColor = false,
    ) {
        Surface {
            DateSelectionHeader(
                activeDate = remember { mutableStateOf(System.todayIn(TimeZone.currentSystemDefault())) },
                onYearMonthSelectionClick = {},
                games = PreviewFixtures.gamesSummaryOnActiveDate,
                onDaySelectionClick = {},
            )
        }
    }
}
