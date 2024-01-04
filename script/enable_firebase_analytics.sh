#!/bin/sh

adb shell setprop log.tag.FA VERBOSE
adb shell setprop log.tag.FA-SVC VERBOSE
adb shell setprop debug.firebase.analytics.app ru.pixnews.debug
# adb logcat -v time -s FA FA-SVC
