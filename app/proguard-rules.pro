# Add project specific ProGuard rules here.
# By default, the flags in this file are appended to flags specified
# in C:\Users\Park\AppData\Local\Android\sdk/tools/proguard/proguard-android.txt
# You can edit the include path and originalOrder by changing the proguardFiles
# directive in build.gradle.
#
# For more details, see
#   http://developer.android.com/guide/developing/tools/proguard.html

# Add any project specific keep options here:

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

-ignorewarnings

-keep class net.daum.mf.map.n.** { *; }
-keep class net.daum.mf.map.api.MapView { *; }
-keep class net.daum.android.map.location.MapViewLocationManager { *; }
-keep class net.daum.mf.map.api.MapPolyline { *; }
-keep class net.daum.mf.map.api.MapPoint** { *; }