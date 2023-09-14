/*
 * Copyright (c) 2023, the Pixnews project authors and contributors. Please see the AUTHORS file for details.
 * Use of this source code is governed by the Apache 2.0 license that can be found in the LICENSE file.
 */
package ru.pixnews.domain.model.game

import ru.pixnews.domain.model.id.PlayerPerspectiveId
import ru.pixnews.domain.model.util.HasId

public sealed class PlayerPerspective(
    public open val name: String,
    public override val id: PlayerPerspectiveId = PlayerPerspectiveId(name),
) : HasId<PlayerPerspectiveId> {
    public data object Auditory : PlayerPerspective("Auditory")
    public data object Isometric : PlayerPerspective("Bird view / Isometric")
    public data object FirstPerson : PlayerPerspective("First person")
    public data object ThirdPerson : PlayerPerspective("Third person")
    public data object SideView : PlayerPerspective("Side view")
    public data object Text : PlayerPerspective("Text")
    public data object Vr : PlayerPerspective("VR (Virtual Reality)")

    public data class Other(
        override val name: String,
        public override val id: PlayerPerspectiveId = PlayerPerspectiveId(name),
    ) : PlayerPerspective(name)
}
