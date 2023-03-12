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
package ru.pixnews.foundation.ui.design.card

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement.spacedBy
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.ZeroCornerSize
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedCard
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.semantics.clearAndSetSemantics
import androidx.compose.ui.semantics.text
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.request.ImageRequest
import coil.size.Scale.FILL
import kotlinx.collections.immutable.ImmutableSet
import ru.pixnews.domain.model.game.GameFixtures
import ru.pixnews.domain.model.game.GamePlatform
import ru.pixnews.domain.model.game.game.halfLife3
import ru.pixnews.foundation.ui.design.icon.PixnewsGameCardFavouriteIcon
import ru.pixnews.foundation.ui.design.util.contentDescription
import ru.pixnews.foundation.ui.design.util.uniqueIcons
import ru.pixnews.foundation.ui.imageloader.coil.compose.AsyncImage
import ru.pixnews.foundation.ui.theme.PixnewsTheme
import ru.pixnews.foundation.ui.theme.md_theme_palette_neutral_variant_40

@Suppress("FLOAT_IN_ACCURATE_CALCULATIONS")
private const val GAME_CARD_IMAGE_ASPECT_RATIO = 3f / 4f

@Composable
public fun PixnewsGameCard(
    game: PixnewsGameCardUiModel,
    onClick: () -> Unit,
    onFavouriteClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    val borderColor = MaterialTheme.colorScheme.outlineVariant
    OutlinedCard(
        modifier = modifier
            .fillMaxWidth()
            .wrapContentHeight(),
        shape = MaterialTheme.shapes.medium,
        onClick = onClick,
        border = remember(borderColor) { BorderStroke(1.dp, borderColor) },
        colors = CardDefaults.outlinedCardColors(
            contentColor = MaterialTheme.colorScheme.onSurfaceVariant,
        ),
    ) {
        AsyncImage(
            modifier = Modifier
                .fillMaxWidth()
                .aspectRatio(GAME_CARD_IMAGE_ASPECT_RATIO)
                .clip(
                    MaterialTheme.shapes.medium.copy(
                        bottomStart = ZeroCornerSize,
                        bottomEnd = ZeroCornerSize,
                    ),
                ),
            model = ImageRequest.Builder(LocalContext.current)
                .data(game.cover?.getUrl())
                .scale(FILL)
                .crossfade(true)
                .build(),
            contentDescription = null,
            contentScale = ContentScale.Crop,
        )
        Box(
            modifier = Modifier.fillMaxWidth(),
        ) {
            IconButton(
                modifier = Modifier
                    .align(Alignment.TopEnd)
                    .size(48.dp),
                onClick = onFavouriteClick,
            ) {
                PixnewsGameCardFavouriteIcon(
                    isFavourite = game.favourite,
                )
            }
            Column(
                modifier = Modifier
                    .padding(16.dp)
                    .fillMaxWidth(),
                verticalArrangement = spacedBy(24.dp),
            ) {
                Headline(
                    title = game.title,
                    genres = game.genres,
                    platforms = game.platforms,
                )
                Text(
                    text = game.description,
                    maxLines = 5,
                    style = MaterialTheme.typography.bodyMedium,
                    overflow = TextOverflow.Ellipsis,
                )
            }
        }
    }
}

@Composable
internal fun Headline(
    title: String,
    genres: String,
    platforms: ImmutableSet<GamePlatform>,
    modifier: Modifier = Modifier,
) {
    Column(
        modifier = modifier,
        verticalArrangement = spacedBy(1.dp),
    ) {
        Text(
            modifier = Modifier
                .padding(end = 18.dp),
            style = MaterialTheme.typography.bodyLarge,
            minLines = 1,
            maxLines = 2,
            overflow = TextOverflow.Clip,
            text = title,
        )
        Text(
            style = MaterialTheme.typography.bodySmall,
            minLines = 1,
            maxLines = 2,
            overflow = TextOverflow.Clip,
            text = genres,
        )
        PlatformsRow(platforms = platforms)
    }
}

@Composable
private fun PlatformsRow(
    platforms: ImmutableSet<GamePlatform>,
    modifier: Modifier = Modifier,
) {
    val contentDescription = AnnotatedString(platforms.contentDescription())
    Row(
        modifier = modifier
            .height(24.dp)
            .clearAndSetSemantics {
                text = contentDescription
            },
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = spacedBy(4.dp),
    ) {
        val uniqueIcons = platforms.uniqueIcons()
        for (icon in uniqueIcons) {
            Image(
                modifier = Modifier.size(12.dp),
                imageVector = icon,
                contentDescription = null,
                colorFilter = ColorFilter.tint(md_theme_palette_neutral_variant_40),
            )
        }
    }
}

@Preview
@Composable
private fun CalendarGameListCardPreview() {
    PixnewsTheme(useDynamicColor = false) {
        Surface(
            modifier = Modifier.fillMaxSize(),
        ) {
            PixnewsGameCard(
                game = GameFixtures.halfLife3.toGameCardItem(),
                onClick = {},
                onFavouriteClick = {},
            )
        }
    }
}
