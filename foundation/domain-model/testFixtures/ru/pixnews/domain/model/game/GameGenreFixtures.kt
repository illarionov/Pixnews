/*
 * Copyright (c) 2023, the Pixnews project authors and contributors. Please see the AUTHORS file for details.
 * Use of this source code is governed by the Apache 2.0 license that can be found in the LICENSE file.
 */
package ru.pixnews.domain.model.game

public object GameGenreFixtures

public val GameGenreFixtures.adventure: GameGenre get() = GameGenre("Adventure")
public val GameGenreFixtures.indie: GameGenre get() = GameGenre("Indie")
public val GameGenreFixtures.rpg: GameGenre get() = GameGenre("RPG")
public val GameGenreFixtures.shooter: GameGenre get() = GameGenre("Shooter")
public val GameGenreFixtures.simulator: GameGenre get() = GameGenre("Simulator")
