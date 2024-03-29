/*
 * Copyright (c) 2023, the Pixnews project authors and contributors. Please see the AUTHORS file for details.
 * Use of this source code is governed by the Apache 2.0 license that can be found in the LICENSE file.
 */

@file:Suppress("FILE_NAME_MATCH_CLASS")

package ru.pixnews.foundation.ui.design.card

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.ZeroCornerSize
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedCard
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.semantics.clearAndSetSemantics
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.semantics.text
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.tooling.preview.datasource.CollectionPreviewParameterProvider
import androidx.compose.ui.unit.dp
import kotlinx.collections.immutable.ImmutableSet
import ru.pixnews.domain.model.game.GameFixtures
import ru.pixnews.domain.model.game.GamePlatform
import ru.pixnews.domain.model.game.game.beyondGoodEvil2
import ru.pixnews.domain.model.game.game.gta6
import ru.pixnews.domain.model.game.game.halfLife3
import ru.pixnews.domain.model.game.game.hytale
import ru.pixnews.domain.model.game.game.project007
import ru.pixnews.domain.model.game.game.sims5
import ru.pixnews.domain.model.game.game.slimeRancher2
import ru.pixnews.domain.model.game.game.smalland
import ru.pixnews.domain.model.game.game.starWarsEclipse
import ru.pixnews.domain.model.game.game.theLostWild
import ru.pixnews.domain.model.id.GameId
import ru.pixnews.foundation.ui.assets.icons.image.ImagePlaceholders
import ru.pixnews.foundation.ui.design.gameId
import ru.pixnews.foundation.ui.design.icon.PixnewsGameCardFavouriteIcon
import ru.pixnews.foundation.ui.design.image.NetworkImage
import ru.pixnews.foundation.ui.design.image.errorLoadingImageSmallPainter
import ru.pixnews.foundation.ui.design.image.noImageSmallPainter
import ru.pixnews.foundation.ui.design.test.constants.card.PixnewsGameCardGridSmallTestTags
import ru.pixnews.foundation.ui.design.util.composeColor
import ru.pixnews.foundation.ui.design.util.contentDescription
import ru.pixnews.foundation.ui.design.util.uniqueIcons
import ru.pixnews.foundation.ui.theme.PixnewsTheme
import ru.pixnews.foundation.ui.theme.md_theme_palette_neutral_variant_40

@Suppress("FLOAT_IN_ACCURATE_CALCULATIONS")
private const val GAME_CARD_ASPECT_RATIO = 3f / 4f

/**
 * Game card: cover, platforms, title, favourite icon for grids
 *
 * @param game
 * @param onClick
 * @param modifier
 */
@Composable
public fun PixnewsGameCardGridSmall(
    game: PixnewsGameCardGridSmallUiModel,
    onClick: (GameId) -> Unit,
    modifier: Modifier = Modifier,
) {
    val borderColor = MaterialTheme.colorScheme.outlineVariant
    OutlinedCard(
        modifier = modifier
            .width(136.dp)
            .wrapContentHeight()
            .semantics {
                gameId = game.gameId.toString()
            },
        shape = MaterialTheme.shapes.medium,
        onClick = { onClick(game.gameId) },
        border = remember(borderColor) { BorderStroke(1.dp, borderColor) },
        colors = CardDefaults.outlinedCardColors(
            contentColor = MaterialTheme.colorScheme.onSurfaceVariant,
        ),
    ) {
        NetworkImage(
            modifier = Modifier
                .fillMaxWidth()
                .aspectRatio(GAME_CARD_ASPECT_RATIO)
                .testTag(PixnewsGameCardGridSmallTestTags.IMAGE),
            model = game.cover,
            fallback = ImagePlaceholders.noImageSmallPainter(),
            error = ImagePlaceholders.errorLoadingImageSmallPainter(),
            contentDescription = game.title,
            contentScale = ContentScale.Crop,
            placeholderColor = game.cover?.prevailingColor?.composeColor() ?: Color.Unspecified,
            placeholderShape = MaterialTheme.shapes.small.copy(
                bottomStart = ZeroCornerSize,
                bottomEnd = ZeroCornerSize,
            ),
        )
        Box(
            modifier = Modifier.fillMaxWidth(),
        ) {
            PixnewsGameCardFavouriteIcon(
                isFavourite = game.favourite,
                modifier = Modifier
                    .align(Alignment.TopEnd)
                    .padding(top = 4.dp, end = 4.dp)
                    .size(16.dp)
                    .testTag(PixnewsGameCardGridSmallTestTags.FAVOURITE_ICON),
            )
            Column(
                modifier = Modifier
                    .padding(
                        top = 6.dp,
                        start = 8.dp,
                        end = 8.dp,
                        bottom = 8.dp,
                    ),
                verticalArrangement = Arrangement.spacedBy(4.dp),
            ) {
                PlatformsRowSmall(
                    modifier = Modifier
                        .height(8.dp)
                        .testTag(PixnewsGameCardGridSmallTestTags.PLATFORMS_ROW),
                    platforms = game.platforms,
                )
                Text(
                    modifier = Modifier.fillMaxWidth(),
                    style = MaterialTheme.typography.labelMedium,
                    minLines = 2,
                    maxLines = 2,
                    overflow = TextOverflow.Ellipsis,
                    text = game.title,
                )
            }
        }
    }
}

@Composable
internal fun PlatformsRowSmall(
    platforms: ImmutableSet<GamePlatform>,
    modifier: Modifier = Modifier,
) {
    val contentDescription = AnnotatedString(platforms.contentDescription())
    Row(
        modifier = modifier
            .clearAndSetSemantics {
                text = contentDescription
            },
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(4.dp),
    ) {
        val uniqueIcons = remember(platforms, platforms::uniqueIcons)
        for (icon in uniqueIcons) {
            Image(
                modifier = Modifier
                    .size(8.dp),
                imageVector = icon,
                contentDescription = null,
                colorFilter = ColorFilter.tint(md_theme_palette_neutral_variant_40),
            )
        }
    }
}

private class GameCardGridPreviewProvider : CollectionPreviewParameterProvider<PixnewsGameCardGridSmallUiModel>(
    listOf(
        GameFixtures.beyondGoodEvil2 to true,
        GameFixtures.gta6 to false,
        GameFixtures.hytale to true,
        GameFixtures.halfLife3 to false,
        GameFixtures.project007 to true,
        GameFixtures.sims5 to false,
        GameFixtures.slimeRancher2 to true,
        GameFixtures.smalland to false,
        GameFixtures.starWarsEclipse to true,
        GameFixtures.theLostWild to false,
    ).map { (game, favourite) ->
        game.toGameCardGridMajorReleasesUiItem(favourite)
    },
)

@Preview
@Composable
private fun CalendarGameListCardPreview(
    @PreviewParameter(GameCardGridPreviewProvider::class) gameModel: PixnewsGameCardGridSmallUiModel,
) {
    PixnewsTheme(useDynamicColor = false) {
        PixnewsGameCardGridSmall(
            game = gameModel,
            onClick = {},
        )
    }
}
