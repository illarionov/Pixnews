/*
 * Copyright (c) 2024, the Pixnews project authors and contributors. Please see the AUTHORS file for details.
 * Use of this source code is governed by the Apache 2.0 license that can be found in the LICENSE file.
 */

package ru.pixnews.util.strictmode

import android.annotation.SuppressLint
import android.os.strictmode.DiskReadViolation
import android.os.strictmode.DiskWriteViolation
import android.os.strictmode.InstanceCountViolation
import android.os.strictmode.UntaggedSocketViolation
import android.os.strictmode.Violation

/**
 * Disk read/write violations on [androidx.test.runner.MonitoringInstrumentation.specifyDexMakerCacheProperty]
 */
@SuppressLint("NewApi")
internal val isInstrumentationDexMakerViolation: Violation.() -> Boolean = {
    (this is DiskReadViolation || this is DiskWriteViolation) && this.stackTrace.any {
        it.fileName == "MonitoringInstrumentation.java" && it.methodName == "specifyDexMakerCacheProperty"
    }
}

@SuppressLint("NewApi")
internal val isTypefaceFullFlipFontViolation: Violation.() -> Boolean = {
    this is DiskReadViolation && this.stackTrace.any {
        it.fileName == "Typeface.java" && (it.methodName == "getFullFlipFont" || it.methodName == "setFlipFonts")
    }
}

@SuppressLint("NewApi")
internal val isProfileSizeOfAppViolation: Violation.() -> Boolean = {
    this is DiskReadViolation && this.stackTrace.any {
        it.fileName == "ActivityThread.java" && it.methodName == "getProfileSizeOfApp"
    }
}

@SuppressLint("NewApi")
internal val isUntaggedSocketViolation: Violation.() -> Boolean = {
    this is UntaggedSocketViolation
}

// Class instance limit occasionally triggered in instrumented tests for unknown reasons
@SuppressLint("NewApi")
internal val isInstanceCountViolation: Violation.() -> Boolean = {
    this is InstanceCountViolation
}
