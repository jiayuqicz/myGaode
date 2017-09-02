# Add project specific ProGuard rules here.
# By default, the flags in this file are appended to flags specified
# in D:\AndroidSDK/tools/proguard/proguard-android.txt
# You can edit the include path and order by changing the proguardFiles
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
-keep class com.amap.api.maps.**{*;}
-keep class com.autonavi.**{*;}
-keep class com.amap.api.trace.**{*;}

-dontwarn com.amap.api.location.AMapLocationClient
-dontwarn com.amap.api.location.AMapLocationClientOption
-dontwarn com.amap.api.location.AMapLocation
-dontwarn com.amap.api.location.AMapLocationListener
-dontwarn com.amap.api.location.AMapLocationClientOption$AMapLocationMode
-dontwarn com.amap.api.location.AMapLocationClientOption$AMapLocationProtocol

-keep class com.amap.api.services.**{*;}
