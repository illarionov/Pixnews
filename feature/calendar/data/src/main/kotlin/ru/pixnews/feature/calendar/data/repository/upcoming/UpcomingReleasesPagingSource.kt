/*
 * Copyright (c) 2024, the Pixnews project authors and contributors. Please see the AUTHORS file for details.
 * Use of this source code is governed by the Apache 2.0 license that can be found in the LICENSE file.
 */

package ru.pixnews.feature.calendar.data.repository.upcoming

import androidx.paging.PagingSource
import ru.pixnews.domain.model.game.Game

public abstract class UpcomingReleasesPagingSource : PagingSource<IgdbPagingSourceKey, Game>()
