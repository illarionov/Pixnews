/*
 * Copyright (c) 2023, the Pixnews project authors and contributors. Please see the AUTHORS file for details.
 * Use of this source code is governed by the Apache 2.0 license that can be found in the LICENSE file.
 */

package ru.pixnews.foundation.database

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import ru.pixnews.foundation.database.converter.ByteStringConverter
import ru.pixnews.foundation.database.converter.ColorConverter
import ru.pixnews.foundation.database.converter.CountryCodeConverter
import ru.pixnews.foundation.database.converter.EsrbRatingConverter
import ru.pixnews.foundation.database.converter.ExternalDataSourceTypeConverter
import ru.pixnews.foundation.database.converter.ExternalLinkTypeConverter
import ru.pixnews.foundation.database.converter.GameCompanyEntityTypeConverter
import ru.pixnews.foundation.database.converter.GameLocalizationEntityTypeConverter
import ru.pixnews.foundation.database.converter.GameReleaseCategoryTypeConverter
import ru.pixnews.foundation.database.converter.GameReleaseDateEntityTypeConverter
import ru.pixnews.foundation.database.converter.GameReleaseStatusTypeConverter
import ru.pixnews.foundation.database.converter.GameTextInfoEntityTypeConverter
import ru.pixnews.foundation.database.converter.InstantConverter
import ru.pixnews.foundation.database.converter.LanguageCodeConverter
import ru.pixnews.foundation.database.converter.PegiRatingConverter
import ru.pixnews.foundation.database.converter.VotesDistributionConverter
import ru.pixnews.foundation.database.dao.GameDao
import ru.pixnews.foundation.database.entity.GameCompanyEntity
import ru.pixnews.foundation.database.entity.GameGameModeEntity
import ru.pixnews.foundation.database.entity.GameGameSeriesEntity
import ru.pixnews.foundation.database.entity.GameGenreEntity
import ru.pixnews.foundation.database.entity.GamePlatformEntity
import ru.pixnews.foundation.database.entity.GamePlayerPerspectiveEntity
import ru.pixnews.foundation.database.entity.company.CompanyDescriptionEntity
import ru.pixnews.foundation.database.entity.company.CompanyEntity
import ru.pixnews.foundation.database.entity.company.CompanyExternalLinkEntity
import ru.pixnews.foundation.database.entity.company.CompanyScreenshotEntity
import ru.pixnews.foundation.database.entity.game.ExternalGameIdEntity
import ru.pixnews.foundation.database.entity.game.FavoriteGameEntity
import ru.pixnews.foundation.database.entity.game.GameEntity
import ru.pixnews.foundation.database.entity.game.GameExternalLinkEntity
import ru.pixnews.foundation.database.entity.game.GameLocalizationEntity
import ru.pixnews.foundation.database.entity.game.GameNameEntity
import ru.pixnews.foundation.database.entity.game.GameRatingSummaryEntity
import ru.pixnews.foundation.database.entity.game.GameScreenshotEntity
import ru.pixnews.foundation.database.entity.game.GameSystemRequirementsEntity
import ru.pixnews.foundation.database.entity.game.GameTagEntity
import ru.pixnews.foundation.database.entity.game.GameTextEntity
import ru.pixnews.foundation.database.entity.game.GameVideoEntity
import ru.pixnews.foundation.database.entity.genre.GenreEntity
import ru.pixnews.foundation.database.entity.genre.GenreNameEntity
import ru.pixnews.foundation.database.entity.mode.GameModeEntity
import ru.pixnews.foundation.database.entity.mode.GameModeNameEntity
import ru.pixnews.foundation.database.entity.perspective.PlayerPerspectiveEntity
import ru.pixnews.foundation.database.entity.perspective.PlayerPerspectiveNameEntity
import ru.pixnews.foundation.database.entity.platform.PlatformEntity
import ru.pixnews.foundation.database.entity.platform.PlatformNameEntity
import ru.pixnews.foundation.database.entity.series.GameSeriesEntity
import ru.pixnews.foundation.database.entity.series.GameSeriesNameEntity

@Database(
    entities = [
        CompanyDescriptionEntity::class,
        CompanyEntity::class,
        CompanyExternalLinkEntity::class,
        CompanyScreenshotEntity::class,
        ExternalGameIdEntity::class,
        FavoriteGameEntity::class,
        GameCompanyEntity::class,
        GameEntity::class,
        GameExternalLinkEntity::class,
        GameGameModeEntity::class,
        GameGameSeriesEntity::class,
        GameGenreEntity::class,
        GameLocalizationEntity::class,
        GameModeEntity::class,
        GameModeNameEntity::class,
        GameNameEntity::class,
        GamePlatformEntity::class,
        GamePlayerPerspectiveEntity::class,
        GameRatingSummaryEntity::class,
        GameScreenshotEntity::class,
        GameSeriesEntity::class,
        GameSeriesNameEntity::class,
        GameSystemRequirementsEntity::class,
        GameTagEntity::class,
        GameTextEntity::class,
        GameVideoEntity::class,
        GenreEntity::class,
        GenreNameEntity::class,
        PlatformEntity::class,
        PlatformNameEntity::class,
        PlayerPerspectiveEntity::class,
        PlayerPerspectiveNameEntity::class,
    ],
    version = 1,
)
@TypeConverters(
    ByteStringConverter::class,
    ColorConverter::class,
    CountryCodeConverter::class,
    EsrbRatingConverter::class,
    ExternalDataSourceTypeConverter::class,
    ExternalLinkTypeConverter::class,
    GameCompanyEntityTypeConverter::class,
    GameLocalizationEntityTypeConverter::class,
    GameReleaseCategoryTypeConverter::class,
    GameReleaseDateEntityTypeConverter::class,
    GameReleaseStatusTypeConverter::class,
    GameTextInfoEntityTypeConverter::class,
    InstantConverter::class,
    LanguageCodeConverter::class,
    PegiRatingConverter::class,
    VotesDistributionConverter::class,
)
public abstract class PixnewsDatabase : RoomDatabase() {
    public abstract fun gameDao(): GameDao
    public companion object
}
