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
package ru.pixnews.foundation.di.workmanager

/**
 * Annotate a CoroutineWorker class with this to automatically contribute it to the WorkManagerScope multibinding.
 * Equivalent to the following declaration in an application module:
 *```
 * @ContributesMultibinding(scope = WorkManagerScope::class)
 * @AssistedFactory
 * public interface TestWorkerFactory : CoroutineWorkerFactory {
 *     override fun create(@ApplicationContext context: Context, workerParameters: WorkerParameters): TestWorker
 * }
 *```
 * The generated code created via the anvil-codegen module.
 */
public annotation class ContributesCoroutineWorker