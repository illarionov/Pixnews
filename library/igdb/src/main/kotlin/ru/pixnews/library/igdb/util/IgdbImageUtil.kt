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
package ru.pixnews.library.igdb.util

import ru.pixnews.library.igdb.dsl.IgdbClientConfig.Companion.IGDB_IMAGE_URL
import ru.pixnews.library.igdb.model.Artwork
import ru.pixnews.library.igdb.model.CharacterMugShot
import ru.pixnews.library.igdb.model.CompanyLogo
import ru.pixnews.library.igdb.model.Cover
import ru.pixnews.library.igdb.model.GameEngineLogo
import ru.pixnews.library.igdb.model.IgdbImageFormat
import ru.pixnews.library.igdb.model.IgdbImageFormat.WEBP
import ru.pixnews.library.igdb.model.IgdbImageSize
import ru.pixnews.library.igdb.model.IgdbImageSize.H1080P
import ru.pixnews.library.igdb.model.PlatformLogo
import ru.pixnews.library.igdb.model.Screenshot

@Suppress("AVOID_USING_UTILITY_CLASS")
public fun igdbImageUrl(
    imageId: String,
    imageSize: IgdbImageSize = H1080P,
    size2x: Boolean = true,
    imageFormat: IgdbImageFormat = WEBP,
): String {
    val encodedImageId = imageId.filter(Char::isLetterOrDigit)
    require(encodedImageId.isNotEmpty()) { "imageId should not be empty" }

    return buildString {
        append(IGDB_IMAGE_URL)
        append(imageSize.igdbId)
        if (size2x) {
            append("_2x")
        }
        append('/')
        append(encodedImageId)
        append('.')
        append(imageFormat.igdbId)
    }
}

public fun Artwork.imageUrl(
    imageSize: IgdbImageSize = H1080P,
    size2x: Boolean = true,
    imageFormat: IgdbImageFormat = WEBP,
): String = igdbImageUrl(this.image_id, imageSize, size2x, imageFormat)

public fun CharacterMugShot.imageUrl(
    imageSize: IgdbImageSize = H1080P,
    size2x: Boolean = true,
    imageFormat: IgdbImageFormat = WEBP,
): String = igdbImageUrl(this.image_id, imageSize, size2x, imageFormat)

public fun CompanyLogo.imageUrl(
    imageSize: IgdbImageSize = H1080P,
    size2x: Boolean = true,
    imageFormat: IgdbImageFormat = WEBP,
): String = igdbImageUrl(this.image_id, imageSize, size2x, imageFormat)

public fun Cover.imageUrl(
    imageSize: IgdbImageSize = H1080P,
    size2x: Boolean = true,
    imageFormat: IgdbImageFormat = WEBP,
): String = igdbImageUrl(this.image_id, imageSize, size2x, imageFormat)

public fun GameEngineLogo.imageUrl(
    imageSize: IgdbImageSize = H1080P,
    size2x: Boolean = true,
    imageFormat: IgdbImageFormat = WEBP,
): String = igdbImageUrl(this.image_id, imageSize, size2x, imageFormat)

public fun PlatformLogo.imageUrl(
    imageSize: IgdbImageSize = H1080P,
    size2x: Boolean = true,
    imageFormat: IgdbImageFormat = WEBP,
): String = igdbImageUrl(this.image_id, imageSize, size2x, imageFormat)

public fun Screenshot.imageUrl(
    imageSize: IgdbImageSize = H1080P,
    size2x: Boolean = true,
    imageFormat: IgdbImageFormat = WEBP,
): String = igdbImageUrl(this.image_id, imageSize, size2x, imageFormat)
