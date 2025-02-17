/*
 * Copyright (c) 2023, the Pixnews project authors and contributors. Please see the AUTHORS file for details.
 * Use of this source code is governed by the Apache 2.0 license that can be found in the LICENSE file.
 */

package ru.pixnews.feature.calendar.ui.header

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.WindowInsetsSides
import androidx.compose.foundation.layout.exclude
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.only
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.sizeIn
import androidx.compose.foundation.layout.systemGestures
import androidx.compose.foundation.layout.union
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.LocalContentColor
import androidx.compose.material3.LocalMinimumInteractiveComponentSize
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
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.semantics.LiveRegionMode
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.semantics.isTraversalGroup
import androidx.compose.ui.semantics.liveRegion
import androidx.compose.ui.semantics.role
import androidx.compose.ui.semantics.selected
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.semantics.testTag
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
import ru.pixnews.feature.calendar.PreviewFixtures
import ru.pixnews.feature.calendar.model.GamesOnDay
import ru.pixnews.feature.calendar.test.constants.CalendarTestTag
import ru.pixnews.feature.calendar.util.DateLocalization
import ru.pixnews.feature.calendar.util.getWeekDays
import ru.pixnews.foundation.ui.theme.PixnewsTheme
import ru.pixnews.foundation.ui.theme.md_theme_palette_neutral_variant_90
import ru.pixnews.foundation.ui.theme.md_theme_palette_tertiary70
import ru.pixnews.library.compose.utils.defaultLocale
import ru.pixnews.foundation.ui.design.R as uiDesignR

@Composable
internal fun DateSelectionHeader(
    activeDate: State<LocalDate>,
    games: ImmutableMap<LocalDate, GamesOnDay>,
    onYearMonthSelectionClick: () -> Unit,
    onDaySelectionClick: (LocalDate) -> Unit,
    modifier: Modifier = Modifier,
) {
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

@Composable
internal fun WeekDaysRow(
    activeDate: State<LocalDate>,
    onDayClick: (LocalDate) -> Unit,
    games: ImmutableMap<LocalDate, GamesOnDay>,
    modifier: Modifier = Modifier,
) {
    Row(
        modifier = modifier
            .windowInsetsPadding(
                WindowInsets.systemGestures
                    .only(WindowInsetsSides.Horizontal)
                    .union(WindowInsets(left = 16.dp, right = 16.dp)),
            )
            .semantics {
                isTraversalGroup = true
                testTag = CalendarTestTag.HEADER_WEEK_DAYS_ROW
            }
            .fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
    ) {
        val days = getWeekDays(
            activeDate.value,
            DateLocalization.getFirstDayOfWeek(defaultLocale()),
        )
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
                LocalMinimumInteractiveComponentSize provides 42.dp,
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
    val weekdayNames = DateLocalization.getShortWeekDays(defaultLocale())
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
            .windowInsetsPadding(
                WindowInsets.systemGestures
                    .only(WindowInsetsSides.Horizontal)
                    .exclude(WindowInsets(left = 16.dp, right = 12.dp))
                    .union(WindowInsets(right = 4.dp)),
            )
            .fillMaxWidth(),
    ) {
        YearMonthPickerButton(
            onClick = onClick,
            modifier = Modifier
                .testTag(CalendarTestTag.HEADER_YEAR_MONTH_PICKER_BUTTON),
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
        contentPadding = PaddingValues(
            start = 16.dp,
            end = 12.dp,
            top = 8.dp,
            bottom = 8.dp,
        ),
        elevation = null,
        border = null,
    ) {
        content()
        Spacer(Modifier.size(ButtonDefaults.IconSpacing))
        Icon(
            Icons.Filled.ArrowDropDown,
            contentDescription = stringResource(uiDesignR.string.yeas_selection_show_date_picker_content_description),
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
    val dateString = DateLocalization.formatCalendarDateForContentDescription(date, defaultLocale())
    val skeletonResourceId = if (gamesOnDay?.hasFollowedGames == true) {
        uiDesignR.string.weekdays_row_has_tracked_games_content_description
    } else {
        uiDesignR.string.weekdays_row_no_tracked_games_content_description
    }

    return stringResource(id = skeletonResourceId, dateString)
}

@Composable
internal fun formatYearMonthPickerTitle(date: LocalDate): String {
    return DateLocalization.formatYearMonthSelectionTitle(date, defaultLocale())
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
