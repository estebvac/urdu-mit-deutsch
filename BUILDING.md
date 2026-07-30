# Building the real APK

This project is built with the standard Android toolchain. The GitHub Actions workflow:

1. installs Java 17, Gradle 8.13, and Android SDK 36;
2. runs unit tests;
3. builds the Jetpack Compose APK;
4. installs and launches it on an Android 16 emulator using an available Pixel hardware profile;
5. fails if startup does not return `Status: ok`, the activity is not active, or logcat contains a fatal crash;
6. uploads the APK and Android 16 launch evidence.

Local build:

```bash
gradle testDebugUnitTest assembleDebug
```

APK output:

```text
app/build/outputs/apk/debug/app-debug.apk
```
