# CineLive Android TV App

Professional Android TV application for CineLive streaming platform.

## 📱 Project Structure

```
cinelive-tv/
└── android/
    ├── app/
    │   ├── src/main/
    │   │   ├── AndroidManifest.xml
    │   │   ├── java/com/cinelive/MainActivity.kt
    │   │   └── res/
    │   │       ├── values/
    │   │       │   ├── strings.xml
    │   │       │   └── styles.xml
    │   │       └── drawable-xhdpi/
    │   │           └── tv_banner.png (320x180)
    │   └── build.gradle
    ├── build.gradle
    ├── settings.gradle
    └── gradle.properties
```

## 🚀 Build Instructions

### Prerequisites
- Android Studio Hedgehog or later
- JDK 17+
- Android SDK 34
- Android TV Emulator or physical device

### Build Debug APK
```bash
cd android
./gradlew assembleDebug
```

Output: `app/build/outputs/apk/debug/app-debug.apk`

### Build Release APK
```bash
./gradlew assembleRelease
```

### Install on Device
```bash
adb install app/build/outputs/apk/debug/app-debug.apk
```

## 📺 Features

- ✅ **Leanback Launcher**: Appears in Android TV home screen
- ✅ **D-Pad Navigation**: Full remote control support
- ✅ **WebView Integration**: Loads CineLive web app
- ✅ **Hardware Acceleration**: Smooth video playback
- ✅ **Immersive Mode**: Fullscreen experience
- ✅ **Keyboard Event Injection**: D-Pad → Arrow keys

## 🎮 Remote Control Mapping

| TV Remote | Web Event |
|-----------|-----------|
| D-Pad Up | Arrow Up |
| D-Pad Down | Arrow Down |
| D-Pad Left | Arrow Left |
| D-Pad Right | Arrow Right |
| Center/OK | Enter |
| Back | Browser back |

## 🔧 Configuration

Edit `MainActivity.kt` to change the CineLive URL:
```kotlin
private val cineliveUrl = "https://cinelive.djuntemon.com"
```

## 📦 Dependencies

- AndroidX Core KTX 1.12.0
- AndroidX AppCompat 1.6.1
- AndroidX Leanback 1.0.0
- Material Components 1.11.0

## 🎨 Assets Required

Create a TV banner (320x180 PNG) and place it at:
```
app/src/main/res/drawable-xhdpi/tv_banner.png
```

## 🐛 Troubleshooting

### WebView not loading
- Check internet connection
- Verify `INTERNET` permission in manifest
- Enable `usesCleartextTraffic` for HTTP URLs

### D-Pad not working
- Ensure `android.hardware.gamepad` feature is declared
- Test on actual TV device (emulator may have issues)

## 📄 License

Proprietary - CineLive Platform
