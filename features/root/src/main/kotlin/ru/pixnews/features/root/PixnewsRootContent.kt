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
package ru.pixnews.features.root

import androidx.compose.material3.windowsizeclass.WindowSizeClass
import androidx.compose.runtime.Composable
import ru.pixnews.foundation.appconfig.AppConfig
import ru.pixnews.foundation.ui.theme.PixnewsTheme

@Composable
public fun PixnewsRootContent(
    appConfig: AppConfig,
    windowSizeClass: WindowSizeClass,
) {
    PixnewsTheme(
        appConfig = appConfig,
        useDynamicColor = false,
    ) {
        PixnewsApp(windowSizeClass = windowSizeClass)
    }
}
