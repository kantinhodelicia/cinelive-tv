# Keep WebView classes
-keepclassmembers class * extends android.webkit.WebView {
   public *;
}

# Keep JavaScript interface
-keepattributes JavascriptInterface
-keepclassmembers class * {
    @android.webkit.JavascriptInterface <methods>;
}

# Keep Kotlin metadata
-keep class kotlin.Metadata { *; }

# AndroidX
-keep class androidx.** { *; }
-dontwarn androidx.**
