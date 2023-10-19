/*
 * Copyright (c) 2023, the Pixnews project authors and contributors. Please see the AUTHORS file for details.
 * Use of this source code is governed by the Apache 2.0 license that can be found in the LICENSE file.
 */

package ru.pixnews.test.assumption

import org.junit.rules.ExternalResource
import org.junit.runner.Description
import org.junit.runners.model.Statement
import ru.pixnews.di.root.component.PixnewsRootComponentHolder
import ru.pixnews.inject.MockResourcesHolder
import ru.pixnews.test.app.mock.mockokhttp.NetworkBehavior

class NetworkAssumptions : ExternalResource() {
    private lateinit var networkBehavior: NetworkBehavior

    override fun apply(base: Statement?, description: Description?): Statement {
        networkBehavior = (PixnewsRootComponentHolder.appComponent as MockResourcesHolder).networkBehavior
        return super.apply(base, description)
    }

    override fun before() {
        super.before()
        networkBehavior.reset()
    }

    override fun after() {
        super.after()
        networkBehavior.reset()
    }

    fun assumeNetworkConnected() {
        networkBehavior.networkConnected = true
    }

    fun assumeNetworkDisconnected() {
        networkBehavior.networkConnected = false
    }
}
