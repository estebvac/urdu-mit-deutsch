# Android project guidance

- Minimum SDK 34, target/compile SDK 36.
- Kotlin, Gradle Kotlin DSL, and Jetpack Compose.
- Keep UI state in ViewModels and expose immutable StateFlow.
- Follow unidirectional data flow and hoist composable state.
- Keep domain models free of Android dependencies.
- Keep the app offline-first and avoid adding permissions without a concrete need.
- Preserve proper RTL rendering for Urdu while keeping German navigation LTR.
- All interactive controls must expose useful semantics and a minimum 48 dp touch target.
- Add unit tests for course content and business logic.
