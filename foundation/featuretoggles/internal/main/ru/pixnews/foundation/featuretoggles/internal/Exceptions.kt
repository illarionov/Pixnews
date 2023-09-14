/*
 * Copyright (c) 2023, the Pixnews project authors and contributors. Please see the AUTHORS file for details.
 * Use of this source code is governed by the Apache 2.0 license that can be found in the LICENSE file.
 */

package ru.pixnews.foundation.featuretoggles.internal

import ru.pixnews.foundation.featuretoggles.FeatureToggleException

public class DuplicateExperimentException(message: String) : FeatureToggleException(message)

public class NoDataSourceException(message: String) : FeatureToggleException(message)
