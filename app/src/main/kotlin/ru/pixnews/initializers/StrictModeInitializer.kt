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
package ru.pixnews.initializers

import android.os.Build
import android.os.StrictMode
import android.os.StrictMode.ThreadPolicy.Builder
import android.os.StrictMode.VmPolicy
import androidx.fragment.app.strictmode.FragmentStrictMode
import androidx.fragment.app.strictmode.FragmentStrictMode.Policy
import co.touchlab.kermit.Logger
import ru.pixnews.foundation.initializers.Initializer
import java.util.concurrent.Executors
import javax.inject.Inject

@Suppress("MagicNumber")
class StrictModeInitializer @Inject constructor(logger: Logger) : Initializer {
    private val logger = logger.withTag("StrictModeInitializer")

    override fun init() {
        logger.v { "Setting up StrictMode" }
        val threadExecutor = Executors.newSingleThreadExecutor()
        StrictMode.setThreadPolicy(
            Builder()
                .detectAll()
                .also { builder ->
                    if (Build.VERSION.SDK_INT >= 28) {
                        builder.penaltyListener(threadExecutor) { violation ->
                            logger.e(violation) { "ThreadPolicy violation" }
                        }
                    } else {
                        builder.penaltyLog()
                    }
                }
                .build(),
        )
        StrictMode.setVmPolicy(
            VmPolicy.Builder()
                .detectAll()
                .also { builder ->
                    if (Build.VERSION.SDK_INT >= 28) {
                        builder.penaltyListener(threadExecutor) { violation ->
                            logger.e(violation) { "VmPolicy violation" }
                        }
                    } else {
                        builder.penaltyLog()
                    }
                }
                .build(),
        )

        FragmentStrictMode.defaultPolicy = Policy.Builder()
            .detectFragmentReuse()
            .detectFragmentTagUsage()
            .detectRetainInstanceUsage()
            .detectSetUserVisibleHint()
            .detectTargetFragmentUsage()
            .detectWrongFragmentContainer()
            .detectWrongNestedHierarchy()
            .penaltyListener { violation ->
                logger.e(violation) { "FragmentStrictMode violation" }
            }
            .build()
    }
}
