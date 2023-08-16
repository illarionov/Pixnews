/*
 * Copyright (c) 2023, the Pixnews project authors and contributors. Please see the AUTHORS file for details.
 * Use of this source code is governed by the Apache 2.0 license that can be found in the LICENSE file.
 */
package ru.pixnews.domain.model.game

public sealed class PlayerPerspective(
    public open val name: String,
) {
    public object Auditory : PlayerPerspective("Auditory")
    public object Isometric : PlayerPerspective("Bird view / Isometric")
    public object FirstPerson : PlayerPerspective("First person")
    public object ThirdPerson : PlayerPerspective("Third person")
    public object SideView : PlayerPerspective("Side view")
    public object Text : PlayerPerspective("Text")
    public object Vr : PlayerPerspective("VR (Virtual Reality)")

    public data class Other(override val name: String) : PlayerPerspective(name)
}
