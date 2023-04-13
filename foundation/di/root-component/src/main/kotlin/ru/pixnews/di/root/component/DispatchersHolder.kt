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
package ru.pixnews.di.root.component

import ru.pixnews.foundation.coroutines.ComputationCoroutineDispatcherProvider
import ru.pixnews.foundation.coroutines.IoCoroutineDispatcherProvider
import ru.pixnews.foundation.coroutines.MainCoroutineDispatcherProvider

public interface DispatchersHolder {
    public val computationDispatcherProvider: ComputationCoroutineDispatcherProvider
    public val mainDispatcherProvider: MainCoroutineDispatcherProvider
    public val ioDispatcherProvider: IoCoroutineDispatcherProvider
}
