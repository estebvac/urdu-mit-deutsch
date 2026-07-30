# Urdu mit Deutsch

<p align="center">
  <strong>Offline Urdu A0 learning for German-speaking learners</strong><br>
  Zehn strukturierte Lektionen mit Urdu-Schrift, deutscher Erklärung und optionaler Umschrift.
</p>

<p align="center">
  <a href="https://github.com/estebvac/urdu-mit-deutsch/actions/workflows/android.yml">
    <img alt="Android 16 APK workflow" src="https://github.com/estebvac/urdu-mit-deutsch/actions/workflows/android.yml/badge.svg?branch=main">
  </a>
  <img alt="Android 14+" src="https://img.shields.io/badge/Android-14%2B-3DDC84?logo=android&logoColor=white">
  <img alt="Kotlin" src="https://img.shields.io/badge/Kotlin-Jetpack%20Compose-7F52FF?logo=kotlin&logoColor=white">
  <img alt="Offline" src="https://img.shields.io/badge/Mode-Offline-blue">
</p>

## Download

<p align="center">
  <a href="https://github.com/estebvac/urdu-mit-deutsch/releases/latest/download/UrduMitDeutsch-v0.2.0.apk">
    <img alt="Download APK" src="https://img.shields.io/badge/Download-UrduMitDeutsch--v0.2.0.apk-3DDC84?style=for-the-badge&logo=android&logoColor=white">
  </a>
</p>

- **APK:** [UrduMitDeutsch-v0.2.0.apk](https://github.com/estebvac/urdu-mit-deutsch/releases/latest/download/UrduMitDeutsch-v0.2.0.apk)
- **SHA-256:** [UrduMitDeutsch-v0.2.0.apk.sha256](https://github.com/estebvac/urdu-mit-deutsch/releases/latest/download/UrduMitDeutsch-v0.2.0.apk.sha256)
- Requires **Android 14 / API 34 or newer**.
- The current artifact is development-signed. Uninstall an older build first if Android reports a signing-certificate conflict.

On Android, allow installation from the browser or file manager used to download the APK, then open the downloaded file.

## Screenshot

<p align="center">
  <img src="https://github.com/estebvac/urdu-mit-deutsch/releases/latest/download/android16-launch.png" width="360" alt="Urdu mit Deutsch running on Android 16">
</p>

The screenshot is produced automatically by the Android 16 emulator smoke test after the APK is installed and cold-launched.

## Features

- Ten A0 lessons covering script, greetings, personal information, family, food, restaurants, travel, directions, numbers, weather, shopping and hobbies.
- Urdu text with correct right-to-left presentation.
- Optional Latin transliteration.
- German grammar explanations and translations.
- Dialogues, vocabulary cards and mini quizzes.
- Offline vocabulary review.
- Local progress, quiz scores and display preferences.
- Optional Urdu text-to-speech when the device provides a compatible voice.
- No login, ads, analytics, backend or network permission.

## Android compatibility

| Setting | Value |
|---|---:|
| Package | `com.estebvac.urdudeutsch` |
| Version | `0.2.0` (`versionCode 2`) |
| Minimum SDK | Android 14 / API 34 |
| Target SDK | Android 16 / API 36 |
| Compile SDK | API 36 |
| Java toolchain | Java 17 |

The CI runner uses the closest available Pixel hardware profile. The validated run used a Pixel 7 Pro profile with the Android 16/API 36 system image; the resulting APK is also suitable for a Pixel 9 Pro running Android 16.

## Test status

Every pull request and push to `main` runs the complete workflow below:

| Validation | Status |
|---|:---:|
| JUnit course-content tests | ✅ |
| Gradle debug APK build | ✅ |
| Package-name verification | ✅ |
| `minSdk 34` verification | ✅ |
| `targetSdk 36` verification | ✅ |
| Install on Android 16 emulator | ✅ |
| Cold launch of `MainActivity` | ✅ |
| Process remains alive | ✅ |
| Activity remains in foreground | ✅ |
| Screenshot capture | ✅ |
| Fatal-crash scan in Logcat | ✅ |

The workflow uploads temporary diagnostic artifacts for each run and publishes the tested APK, checksum and launch screenshot to the GitHub Release only after all smoke-test checks pass.

## Build locally

Open the project in Android Studio with Android SDK 36 installed, or build from the command line:

```bash
gradle --no-daemon testDebugUnitTest assembleDebug
```

The APK is written to:

```text
app/build/outputs/apk/debug/app-debug.apk
```

## Project structure

```text
app/
├── src/main/java/com/estebvac/urdudeutsch/
│   ├── CourseCatalog.kt
│   ├── CourseModels.kt
│   ├── LessonsOneToFive.kt
│   ├── LessonsSixToTen.kt
│   ├── LearningViewModel.kt
│   ├── ProgressRepository.kt
│   ├── MainActivity.kt
│   └── Compose screens and theme
├── src/main/res/
└── src/test/
```

## Privacy

All course content and learner progress remain on the device. The application does not request internet access and does not send usage data anywhere.
