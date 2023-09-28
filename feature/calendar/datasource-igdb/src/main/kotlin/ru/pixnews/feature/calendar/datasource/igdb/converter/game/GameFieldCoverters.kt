/*
 * Copyright (c) 2023, the Pixnews project authors and contributors. Please see the AUTHORS file for details.
 * Use of this source code is governed by the Apache 2.0 license that can be found in the LICENSE file.
 */

package ru.pixnews.feature.calendar.datasource.igdb.converter.game

import ru.pixnews.domain.model.game.GameField
import ru.pixnews.domain.model.game.GameField.AgeRanking
import ru.pixnews.domain.model.game.GameField.Category
import ru.pixnews.domain.model.game.GameField.Description
import ru.pixnews.domain.model.game.GameField.Developer
import ru.pixnews.domain.model.game.GameField.GameMode
import ru.pixnews.domain.model.game.GameField.Genres
import ru.pixnews.domain.model.game.GameField.Id
import ru.pixnews.domain.model.game.GameField.Links
import ru.pixnews.domain.model.game.GameField.Localizations
import ru.pixnews.domain.model.game.GameField.Name
import ru.pixnews.domain.model.game.GameField.ParentGame
import ru.pixnews.domain.model.game.GameField.Platforms
import ru.pixnews.domain.model.game.GameField.PlayerPerspective
import ru.pixnews.domain.model.game.GameField.Publisher
import ru.pixnews.domain.model.game.GameField.Ratings
import ru.pixnews.domain.model.game.GameField.ReleaseDate
import ru.pixnews.domain.model.game.GameField.ReleaseStatus
import ru.pixnews.domain.model.game.GameField.Screenshots
import ru.pixnews.domain.model.game.GameField.Series
import ru.pixnews.domain.model.game.GameField.Summary
import ru.pixnews.domain.model.game.GameField.SystemRequirements
import ru.pixnews.domain.model.game.GameField.Tags
import ru.pixnews.domain.model.game.GameField.VideoUrls

internal val GameField.igdbFieldConverter: IgdbGameFieldConverter<*>
    get() = when (this) {
        Id -> IgdbGameIdConverter
        Name -> IgdbGameNameConverter
        Description -> IgdbGameDescriptionConverter
        Summary -> IgdbGameSummaryConverter
        VideoUrls -> IgdbGameVideoUrlsConverter
        Screenshots -> IgdbGameScreenshotsConverter
        Developer -> IgdbGameDeveloperConverter
        Publisher -> IgdbGamePublisherConverter
        ReleaseDate -> IgdbGameReleaseDateConverter
        ReleaseStatus -> IgdbGameReleaseStatusConverter
        Genres -> IgdbGameGenresConverter
        Tags -> IgdbGameTagsConverter
        Ratings -> IgdbGameRatingsConverter
        Links -> IgdbGameLinksConverter
        Category -> IgdbGameCategoryConverter
        is ParentGame -> IgdbGameParentGameConverter
        is Series -> IgdbGameSeriesConverter
        is Platforms -> IgdbGamePlatformsConverter
        AgeRanking -> IgdbGameAgeRatingConverter
        Localizations -> IgdbGameLocalizationsConverter
        is GameMode -> IgdbGameGameModeConverter
        is PlayerPerspective -> IgdbGamePlayerPerspectivesConverter
        SystemRequirements -> error("Not supported")
    }
