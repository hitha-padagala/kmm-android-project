# KMM Android App

Kotlin Multiplatform Mobile (KMM) project with Android support — built entirely on Windows.

## Structure

| Module | Description |
|--------|-------------|
| `shared/` | Kotlin Multiplatform shared module (common + Android) |
| `androidApp/` | Android application with Jetpack Compose UI |

## Stack

- **Kotlin** 2.0.21 (Multiplatform)
- **Jetpack Compose** with Material 3
- **Gradle** 8.9 + Version Catalogs
- **Android** SDK 35 (min 24)

## Setup

1. Open in Android Studio (or IntelliJ IDEA)
2. Sync Gradle
3. Run `androidApp` on an emulator or device

## Requirements

- Android Studio Hedgehog+ or IntelliJ IDEA 2024+
- JDK 17
- Android SDK 35

> **Note:** iOS target is not included since this project is built on Windows. To add iOS support, open on a Mac and add `iosX64()`, `iosArm64()`, `iosSimulatorArm64()` targets in `shared/build.gradle.kts`.
