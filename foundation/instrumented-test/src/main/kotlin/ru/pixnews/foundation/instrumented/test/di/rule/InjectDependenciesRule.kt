/*
 * Copyright (c) 2023, the Pixnews project authors and contributors. Please see the AUTHORS file for details.
 * Use of this source code is governed by the Apache 2.0 license that can be found in the LICENSE file.
 */
package ru.pixnews.foundation.instrumented.test.di.rule

import org.junit.rules.TestRule
import org.junit.runner.Description
import org.junit.runners.model.Statement
import ru.pixnews.di.root.component.PixnewsRootComponentHolder
import ru.pixnews.foundation.instrumented.test.di.InstrumentedTestInjectorHolder

public class InjectDependenciesRule(
    private val instance: Any,
) : TestRule {
    override fun apply(base: Statement, description: Description): Statement {
        injectDependencies()
        return base
    }

    private fun injectDependencies() {
        (PixnewsRootComponentHolder.appComponent as InstrumentedTestInjectorHolder)
            .instrumentedTestInjector
            .inject(instance)
    }
}
