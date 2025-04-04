/*
 * Copyright (c) 2023, the Pixnews project authors and contributors. Please see the AUTHORS file for details.
 * Use of this source code is governed by the Apache 2.0 license that can be found in the LICENSE file.
 */

package ru.pixnews.feature.profile

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import ru.pixnews.feature.profile.test.constants.ProfileTestTags

@Composable
internal fun ProfileScreen(
    modifier: Modifier = Modifier,
) {
    Box(
        modifier = modifier
            .testTag(ProfileTestTags.SUCCESS_CONTENT)
            .fillMaxSize(),
    ) {
        // TODO("Not yet implemented")
    }
}
