/*
 * Copyright (c) 2023, the Pixnews project authors and contributors. Please see the AUTHORS file for details.
 * Use of this source code is governed by the Apache 2.0 license that can be found in the LICENSE file.
 */
package ru.pixnews.feature.root

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.semantics.testTagsAsResourceId
import ru.pixnews.foundation.appconfig.AppConfig
import ru.pixnews.foundation.ui.theme.PixnewsTheme

@Suppress("ModifierMissing")
@Composable
public fun PixnewsRootContent(
    appConfig: AppConfig,
) {
    PixnewsTheme(
        appConfig = appConfig,
        useDynamicColor = false,
    ) {
        PixnewsApp(
            modifier = Modifier
                .semantics {
                    testTagsAsResourceId = appConfig.buildType != "release"
                },
        )
    }
}
