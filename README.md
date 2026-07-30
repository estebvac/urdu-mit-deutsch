# Urdu mit Deutsch

Offline-first Android A0 learning app for Urdu speakers learning through German explanations.

## Features
- 10 structured A0 lessons
- Correct RTL Urdu rendering with optional Latin transliteration
- Dialogues, grammar, vocabulary and mini quizzes
- Offline vocabulary review
- Local progress and settings
- Optional device Urdu Text-to-Speech
- No login, ads, analytics or network permission

## Android compatibility
- `minSdk = 34` (Android 14)
- `compileSdk = 36`
- `targetSdk = 36` (Android 16)
- Designed for Android 14 (API 34) and newer

## Build
```bash
gradle testDebugUnitTest assembleDebug
```
APK output: `app/build/outputs/apk/debug/app-debug.apk`

## Architecture
Single-module first draft with pure Kotlin course models, repository-backed local progress, immutable StateFlow UI state, and stateless Compose components. The included `Agent.md` and selected Android agent skills document the intended evolution into a modular production app.
