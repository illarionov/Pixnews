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

/**
 * Disk read/write violations on [androidx.test.runner.MonitoringInstrumentation.specifyDexMakerCacheProperty]
 */
@SuppressLint("NewApi")
val instrumentationDexMakerViolation: IgnoredViolation = IgnoredViolation(
    "androidx.test.runner.MonitoringInstrumentation#specifyDexMakerCacheProperty Disk Violation",
) {
    (this is DiskReadViolation || this is DiskWriteViolation) && this.stackTrace.any {
        it.fileName == "MonitoringInstrumentation.java" && it.methodName == "specifyDexMakerCacheProperty"
    }
}

@SuppressLint("NewApi")
internal val typefaceFullFlipFontViolation: IgnoredViolation = IgnoredViolation(
    "Typeface.java#{get/set}FullFlipFont Disk Read Violation",
) {
    this is DiskReadViolation && this.stackTrace.any {
        it.fileName == "Typeface.java" && (it.methodName == "getFullFlipFont" || it.methodName == "setFlipFonts")
    }
}

@SuppressLint("NewApi")
internal val fontRequestViolation: IgnoredViolation = IgnoredViolation(
    "FontRequestWorker Disk Read Violation",
) {
    this is DiskReadViolation && this.stackTrace.any {
        it.className == "androidx.core.provider.FontRequestWorker" && it.fileName == "FontRequestWorker.java"
    }
}

@SuppressLint("NewApi")
internal val profileSizeOfAppViolation: IgnoredViolation = IgnoredViolation(
    "ActivityThread.java#getProfileSizeOfApp Disk Read Violation",
) {
    this is DiskReadViolation && this.stackTrace.any {
        it.fileName == "ActivityThread.java" && it.methodName == "getProfileSizeOfApp"
    }
}

@SuppressLint("NewApi")
internal val untaggedSocketViolation: IgnoredViolation = IgnoredViolation(
    "Untagged Socket Violation",
) {
    this is UntaggedSocketViolation
}

// Class instance limit occasionally triggered in instrumented tests for unknown reasons
@SuppressLint("NewApi")
internal val instanceCountViolation: IgnoredViolation = IgnoredViolation(
    "Instance Count Violation",
) {
    this is InstanceCountViolation
}

/**
 * Difficult to diagnose violation in GMS
 */
@SuppressLint("NewApi")
internal val gmsDiskReadViolation: IgnoredViolation = IgnoredViolation(
    ":com.google.android.gms Disk Read Violation",
) {
    (this is DiskReadViolation) && this.stackTrace.any {
        it.fileName?.contains(":com.google.android.gms") ?: false
    }
}
