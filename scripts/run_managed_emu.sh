#!/bin/sh

# Runs Gradle managed emulator
ANDROID_AVD_HOME=~/.android/avd/gradle-managed ${ANDROID_HOME}/emulator/emulator @dev30_aosp_atd_x86_Pixel_2 -no-window -no-audio -gpu swiftshader_indirect -read-only -no-boot-anim -id :foundation:ui-theme:pixel2api30DebugAndroidTest
