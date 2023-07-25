/*
 * Copyright (c) 2023, the Pixnews project authors and contributors. Please see the AUTHORS file for details.
 * Use of this source code is governed by the Apache 2.0 license that can be found in the LICENSE file.
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
class DebugStrictModeInitializer @Inject constructor(logger: Logger) : Initializer {
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
