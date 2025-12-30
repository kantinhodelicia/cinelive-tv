#!/bin/bash

# CineLive Android TV - Build Script
# This script builds and installs the APK on a connected Android TV device

set -e

echo "🎬 CineLive Android TV Build Script"
echo "===================================="

cd "$(dirname "$0")/android"

# Check if gradlew exists
if [ ! -f "./gradlew" ]; then
    echo "⚠️  Gradle wrapper not found. Creating..."
    gradle wrapper
fi

# Make gradlew executable
chmod +x ./gradlew

# Build debug APK
echo ""
echo "📦 Building debug APK..."
./gradlew assembleDebug

APK_PATH="app/build/outputs/apk/debug/app-debug.apk"

if [ -f "$APK_PATH" ]; then
    echo "✅ Build successful!"
    echo "📍 APK location: $APK_PATH"
    
    # Check if device is connected
    if command -v adb &> /dev/null; then
        DEVICES=$(adb devices | grep -v "List" | grep "device$" | wc -l)
        
        if [ "$DEVICES" -gt 0 ]; then
            echo ""
            echo "📱 Android TV device detected. Installing..."
            adb install -r "$APK_PATH"
            echo "✅ Installation complete!"
            echo ""
            echo "🚀 Launching CineLive..."
            adb shell am start -n com.cinelive.tv/.MainActivity
        else
            echo ""
            echo "⚠️  No Android TV device connected."
            echo "Connect your device and run: adb install -r $APK_PATH"
        fi
    else
        echo ""
        echo "⚠️  ADB not found. Install Android SDK Platform Tools."
    fi
else
    echo "❌ Build failed. Check errors above."
    exit 1
fi
