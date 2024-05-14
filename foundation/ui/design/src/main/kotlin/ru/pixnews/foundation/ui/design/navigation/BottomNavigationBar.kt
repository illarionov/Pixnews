/*
 * Copyright (c) 2023, the Pixnews project authors and contributors. Please see the AUTHORS file for details.
 * Use of this source code is governed by the Apache 2.0 license that can be found in the LICENSE file.
 */

package ru.pixnews.foundation.ui.design.navigation

import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.RowScope
import androidx.compose.material.ripple.RippleAlpha
import androidx.compose.material3.LocalRippleConfiguration
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemColors
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.RippleConfiguration
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.NonRestartableComposable
import androidx.compose.runtime.ReadOnlyComposable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color

@Composable
public fun PixnewsBottomNavigationBar(
    modifier: Modifier = Modifier,
    content: @Composable RowScope.() -> Unit,
) {
    CompositionLocalProvider(LocalRippleConfiguration provides BottomNavigationBarDefaults.rippleConfiguration()) {
        NavigationBar(
            modifier = modifier,
            content = content,
        )
    }
}

@Composable
@NonRestartableComposable
public fun RowScope.PixnewsBottomNavigationBarItem(
    selected: Boolean,
    onClick: () -> Unit,
    icon: @Composable () -> Unit,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    label: @Composable (() -> Unit)? = null,
    alwaysShowLabel: Boolean = true,
    colors: NavigationBarItemColors = NavigationBarItemDefaults.colors(
        indicatorColor = BottomNavigationBarDefaults.indicatorColor(),
        selectedIconColor = BottomNavigationBarDefaults.selectedIconColor(),
    ),
    interactionSource: MutableInteractionSource = remember { MutableInteractionSource() },
) {
    NavigationBarItem(
        selected = selected,
        onClick = onClick,
        icon = icon,
        modifier = modifier,
        enabled = enabled,
        label = label,
        alwaysShowLabel = alwaysShowLabel,
        colors = colors,
        interactionSource = interactionSource,
    )
}

public object BottomNavigationBarDefaults {
    private val rippleAlpha = RippleAlpha(
        draggedAlpha = 0.16f,
        focusedAlpha = 0.12f,
        hoveredAlpha = 0.08f,
        pressedAlpha = 0.12f,
    )

    @Composable
    @ReadOnlyComposable
    public fun indicatorColor(): Color = MaterialTheme.colorScheme.secondaryContainer

    @Composable
    @ReadOnlyComposable
    public fun selectedIconColor(): Color = MaterialTheme.colorScheme.onSecondaryContainer

    @Composable
    @ReadOnlyComposable
    public fun rippleConfiguration(): RippleConfiguration = RippleConfiguration(
        color = MaterialTheme.colorScheme.secondary,
        rippleAlpha = rippleAlpha,
    )
}
