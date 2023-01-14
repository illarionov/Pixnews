# Add project specific ProGuard rules here.
# You can control the set of applied configuration files using the
# proguardFiles setting in build.gradle.kts.
#
# For more details, see
#   http://developer.android.com/guide/developing/tools/proguard.html

# If your project uses WebView with JS, uncomment the following
# and specify the fully qualified class name to the JavaScript interface
# class:
#-keepclassmembers class fqcn.of.javascript.interface.for.webview {
#   public *;
#}

# Uncomment this to preserve the line number information for
# debugging stack traces.
#-keepattributes SourceFile,LineNumberTable

# If you keep the line number information, uncomment this to
# hide the original source file name.
#-renamesourcefileattribute SourceFile

#-assumenosideeffects class android.util.Log {
#  static int d(...);
#  static int v(...);
#  static int i(...);
##  static int w(...);
##  static int e(...);
#  static int println(...);
#  static boolean isLoggable(...);
#}

-dontobfuscate
-optimizationpasses 20

-assumenosideeffects class co.touchlab.kermit.Logger$Companion {
  public void a(...);
  public void v(...);
  public void d(...);
  public void i(...);
  public void w(...);
  public void e(...);
}

-assumenosideeffects class co.touchlab.kermit.Logger {
  public void a(...);
  public void v(...);
  public void d(...);
  public void i(...);
}

-dontwarn com.google.android.material.shadow.ShadowDrawableWrapper
-dontwarn kotlinx.coroutines.internal.ClassValueCtorCache
