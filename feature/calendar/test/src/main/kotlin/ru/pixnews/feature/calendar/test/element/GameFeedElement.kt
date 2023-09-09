/*
 * Copyright (c) 2023, the Pixnews project authors and contributors. Please see the AUTHORS file for details.
 * Use of this source code is governed by the Apache 2.0 license that can be found in the LICENSE file.
 */
package ru.pixnews.feature.calendar.test.element

import androidx.compose.ui.test.ExperimentalTestApi
import androidx.compose.ui.test.SemanticsMatcher
import androidx.compose.ui.test.SemanticsNodeInteraction
import androidx.compose.ui.test.SemanticsNodeInteractionCollection
import androidx.compose.ui.test.hasAnyAncestor
import androidx.compose.ui.test.hasTestTag
import androidx.compose.ui.test.hasText
import androidx.compose.ui.test.isHeading
import androidx.compose.ui.test.junit4.AndroidComposeTestRule
import androidx.compose.ui.test.performScrollToNode
import ru.pixnews.domain.model.game.GameFixtures
import ru.pixnews.domain.model.game.game.slimeRancher2
import ru.pixnews.domain.model.id.GameId
import ru.pixnews.feature.calendar.test.constants.CalendarTestTag
import ru.pixnews.foundation.ui.design.GameIdKey

public class GameFeedElement(
    private val composeTestRule: AndroidComposeTestRule<*, *>,
) {
    public val majorReleases: MajorReleasesElement = MajorReleasesElement(composeTestRule)

    public fun root(): SemanticsNodeInteraction {
        return composeTestRule.onNode(rootMatcher)
    }

    public fun dateSubheader(
        title: String = firstCalendarTitle,
    ): SemanticsNodeInteraction {
        return composeTestRule.onNode(dateSubheaderMatcher(title))
    }

    public fun gameCard(
        id: GameId = firstGameId,
    ): SemanticsNodeInteraction {
        return composeTestRule.onNode(gameCardMatcher(id))
    }

    public fun scrollToDateSubheader(
        title: String = firstCalendarTitle,
    ) {
        root().performScrollToNode(dateSubheaderMatcher(title))
    }

    public fun scrollToGameCard(
        gameId: GameId = firstGameId,
    ) {
        root().performScrollToNode(gameCardMatcher(gameId))
    }

    public class MajorReleasesElement(
        private val composeTestRule: AndroidComposeTestRule<*, *>,
    ) {
        public fun root(): SemanticsNodeInteraction {
            return composeTestRule.onNode(rootMatcher)
        }

        public fun title(): SemanticsNodeInteraction {
            return composeTestRule.onNode(titleMatcher)
        }

        public fun gameCard(gameId: GameId): SemanticsNodeInteraction {
            return composeTestRule.onNode(
                gameId.matcher.and(hasAnyAncestor(rootMatcher)),
            )
        }

        public fun gameCards(): SemanticsNodeInteractionCollection {
            return composeTestRule.onAllNodes(
                SemanticsMatcher.keyIsDefined(GameIdKey)
                    .and(hasAnyAncestor(rootMatcher)),
            )
        }

        @OptIn(ExperimentalTestApi::class)
        public fun waitTitle() {
            composeTestRule.waitUntilExactlyOneExists(titleMatcher, timeoutMillis = 5_000)
        }

        public companion object {
            public val rootMatcher: SemanticsMatcher = hasTestTag(CalendarTestTag.CONTENT_GAME_SUBHEADER)
            public val titleMatcher: SemanticsMatcher = isHeading().and(hasAnyAncestor(rootMatcher))
        }
    }

    public companion object {
        @Suppress("CONSTANT_UPPERCASE")
        public const val firstCalendarTitle: String = "1 January 2024"
        public val firstGameId: GameId = GameFixtures.slimeRancher2.id
        public val rootMatcher: SemanticsMatcher = hasTestTag(CalendarTestTag.CONTENT_LAZY_LIST)

        public fun dateSubheaderMatcher(title: String? = null): SemanticsMatcher {
            val matcher = isHeading().and(hasTestTag(CalendarTestTag.CONTENT_GAME_SUBHEADER))
            return if (title != null) {
                hasText(title).and(matcher)
            } else {
                matcher
            }
        }

        public fun gameCardMatcher(gameId: GameId): SemanticsMatcher = gameId.matcher
            .and(hasAnyAncestor(MajorReleasesElement.rootMatcher).not())
            .and(hasAnyAncestor(rootMatcher))
    }
}

private val GameId.matcher
    get() = SemanticsMatcher.expectValue(GameIdKey, this.toString())
