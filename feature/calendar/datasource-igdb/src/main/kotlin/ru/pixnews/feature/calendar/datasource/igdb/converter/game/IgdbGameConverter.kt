/*
 * Copyright (c) 2023, the Pixnews project authors and contributors. Please see the AUTHORS file for details.
 * Use of this source code is governed by the Apache 2.0 license that can be found in the LICENSE file.
 */

@file:Suppress("TooManyFunctions")

package ru.pixnews.feature.calendar.datasource.igdb.converter.game

import kotlinx.collections.immutable.persistentListOf
import ru.pixnews.domain.model.game.Game
import ru.pixnews.domain.model.util.Ref
import ru.pixnews.feature.calendar.datasource.igdb.converter.util.errorFieldNotRequested
import ru.pixnews.feature.calendar.datasource.igdb.converter.util.requireFieldInitialized
import ru.pixnews.feature.calendar.datasource.igdb.model.id.IgdbGameId
import ru.pixnews.feature.calendar.datasource.igdb.model.igdbDataSource
import ru.pixnews.igdbclient.model.Game as IgdbGame

internal fun IgdbGame.toGameRef(): Ref<Game> = when {
    name.isNotEmpty() -> Ref.FullObject(this.toGame())
    id != 0L -> Ref.Id(IgdbGameId(id))
    else -> errorFieldNotRequested("game.id")
}

internal fun IgdbGame.toGame(): Game {
    requireFieldInitialized("game.name", this.name)
    return Game(
        id = IgdbGameIdConverter.convert(this),
        name = IgdbGameNameConverter.convert(this),
        summary = IgdbGameSummaryConverter.convert(this),
        description = IgdbGameDescriptionConverter.convert(this),
        videoUrls = IgdbGameVideoUrlsConverter.convert(this),
        screenshots = IgdbGameScreenshotsConverter.convert(this),
        developer = IgdbGameDeveloperConverter.convert(this),
        publisher = IgdbGamePublisherConverter.convert(this),
        releaseDate = IgdbGameReleaseDateConverter.convert(this),
        releaseStatus = IgdbGameReleaseStatusConverter.convert(this),
        genres = IgdbGameGenresConverter.convert(this),
        tags = IgdbGameTagsConverter.convert(this),
        ratings = IgdbGameRatingsConverter.convert(this),
        links = IgdbGameLinksConverter.convert(this),
        category = IgdbGameCategoryConverter.convert(this),
        parentGame = IgdbGameParentGameConverter.convert(this),
        series = IgdbGameSeriesConverter.convert(this),
        platforms = IgdbGamePlatformsConverter.convert(this),
        ageRanking = IgdbGameAgeRatingConverter.convert(this),
        localizations = IgdbGameLocalizationsConverter.convert(this),
        gameMode = IgdbGameGameModeConverter.convert(this),
        playerPerspectives = IgdbGamePlayerPerspectivesConverter.convert(this),
        systemRequirements = null,
        dataSources = persistentListOf(igdbDataSource),
    )
}
