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
package ru.pixnews.feature.calendar.element

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
import ru.pixnews.domain.model.game.GameId
import ru.pixnews.domain.model.game.game.slimeRancher2
import ru.pixnews.foundation.ui.design.GameIdKey

internal class GameFeedElement(
    private val composeTestRule: AndroidComposeTestRule<*, *>,
) {
    val majorReleases = MajorReleasesElement(composeTestRule)

    fun root(): SemanticsNodeInteraction {
        return composeTestRule.onNode(rootMatcher)
    }

    fun dateSubheader(
        title: String = firstCalendarTitle,
    ): SemanticsNodeInteraction {
        return composeTestRule.onNode(dateSubheaderMatcher(title))
    }

    fun gameCard(
        id: GameId = firstGameId,
    ): SemanticsNodeInteraction {
        return composeTestRule.onNode(gameCardMatcher(id))
    }

    fun scrollToDateSubheader(
        title: String = firstCalendarTitle,
    ) {
        root().performScrollToNode(dateSubheaderMatcher(title))
    }

    fun scrollToGameCard(
        gameId: GameId = firstGameId,
    ) {
        root().performScrollToNode(gameCardMatcher(gameId))
    }

    internal class MajorReleasesElement(
        private val composeTestRule: AndroidComposeTestRule<*, *>,
    ) {
        fun root(): SemanticsNodeInteraction {
            return composeTestRule.onNode(rootMatcher)
        }

        fun title(): SemanticsNodeInteraction {
            return composeTestRule.onNode(titleMatcher)
        }

        fun gameCard(gameId: GameId): SemanticsNodeInteraction {
            return composeTestRule.onNode(
                gameId.matcher.and(hasAnyAncestor(rootMatcher)),
            )
        }

        fun gameCards(): SemanticsNodeInteractionCollection {
            return composeTestRule.onAllNodes(
                SemanticsMatcher.keyIsDefined(GameIdKey)
                    .and(hasAnyAncestor(rootMatcher)),
            )
        }

        fun waitTitle() {
            composeTestRule.waitUntilExactlyOneExists(titleMatcher, timeoutMillis = 5_000)
        }

        companion object {
            val rootMatcher = hasTestTag("calendar:content:major_releases_carousel")
            val titleMatcher = isHeading().and(hasAnyAncestor(rootMatcher))
        }
    }

    companion object {
        @Suppress("CONSTANT_UPPERCASE")
        const val firstCalendarTitle = "1 January 2024"
        val firstGameId = GameFixtures.slimeRancher2.id
        val rootMatcher = hasTestTag("calendar:content:lazy_list")

        fun dateSubheaderMatcher(title: String? = null): SemanticsMatcher {
            val matcher = isHeading().and(hasTestTag("calendar:content:game_subheader"))
            return if (title != null) {
                hasText(title).and(matcher)
            } else {
                matcher
            }
        }

        fun gameCardMatcher(gameId: GameId) = gameId.matcher
            .and(hasAnyAncestor(MajorReleasesElement.rootMatcher).not())
            .and(hasAnyAncestor(rootMatcher))
    }
}

private val GameId.matcher
    get() = SemanticsMatcher.expectValue(GameIdKey, this.toString())
