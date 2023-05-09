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
package ru.pixnews.inject.initializer

import co.touchlab.kermit.Logger
import ru.pixnews.foundation.initializers.AsyncInitializer
import ru.pixnews.foundation.initializers.inject.ContributesInitializer
import ru.pixnews.initializers.FirebaseInitializer

@ContributesInitializer(replaces = [FirebaseInitializer::class])
class TestFirebaseInitializer(
    val logger: Logger,
) : AsyncInitializer {
    override fun init() {
        logger.i("Skipping Firebase initialization")
    }
}
