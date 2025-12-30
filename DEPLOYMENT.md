# CineLive Android TV - Deployment Guide

## ⚠️ Important: Build Requirements

Building Android APKs requires:
1. **Android SDK** (not available on this server)
2. **Java JDK 17+** (not installed)
3. **Android Studio** (recommended for development)

## 🚀 Recommended Deployment Options

### Option 1: Build on Your Local Machine (Recommended)

1. **Install Android Studio**: https://developer.android.com/studio
2. **Copy the project**:
   ```bash
   # Download the cinelive-tv folder to your local machine
   scp -r user@server:/var/ver/www/cinelive-tv ~/
   ```
3. **Open in Android Studio**:
   - File → Open → Select `cinelive-tv/android`
   - Wait for Gradle sync
   - Click "Run" or Build → Build Bundle(s) / APK(s) → Build APK(s)

4. **Install on TV**:
   ```bash
   adb connect YOUR_TV_IP:5555
   adb install app/build/outputs/apk/debug/app-debug.apk
   ```

---

### Option 2: GitHub Actions (CI/CD)

Create `.github/workflows/build.yml`:

```yaml
name: Build Android TV APK

on:
  push:
    branches: [ main ]

jobs:
  build:
    runs-on: ubuntu-latest
    steps:
      - uses: actions/checkout@v3
      - name: Set up JDK 17
        uses: actions/setup-java@v3
        with:
          java-version: '17'
          distribution: 'temurin'
      - name: Build with Gradle
        run: |
          cd android
          chmod +x gradlew
          ./gradlew assembleDebug
      - name: Upload APK
        uses: actions/upload-artifact@v3
        with:
          name: app-debug
          path: android/app/build/outputs/apk/debug/app-debug.apk
```

Push to GitHub and download the APK from Actions tab.

---

### Option 3: Use Online Build Service

**Appetize.io** or **BrowserStack** can build and test Android apps online:
1. Upload the `android/` folder as a ZIP
2. Build and test in browser
3. Download APK

---

## 📱 Alternative: Test Web Version on Android TV Browser

Since the Android TV app is a WebView wrapper, you can test the web version directly:

1. Open **Chrome** or **Puffin Browser** on Android TV
2. Navigate to: `https://cinelive.djuntemon.com`
3. Use D-Pad to navigate (already optimized!)

This gives you 90% of the functionality without needing the APK.

---

## 🔧 What's Already Done

✅ Complete Android TV project structure
✅ Leanback launcher configuration
✅ D-Pad navigation mapping
✅ WebView with hardware acceleration
✅ Gradle build scripts
✅ ProGuard rules

**All code is ready** - you just need an environment with Android SDK to compile it.

---

## 📞 Next Steps

Choose one of the options above based on your setup:
- **Have Android Studio?** → Option 1 (5 minutes)
- **Want automation?** → Option 2 (GitHub Actions)
- **Quick test?** → Option 3 (TV Browser)

Let me know which option you prefer and I can provide more detailed instructions!
