# KMM Android App

Kotlin Multiplatform Mobile (KMM) project with Android support — built entirely on Windows. Fetches and displays users from JSONPlaceholder API.

## Structure

```
├── shared/                     # KMM shared module
│   └── src/
│       ├── commonMain/kotlin/com/hitha/shared/
│       │   ├── model/User.kt           # Data models (User, Address, Geo, Company)
│       │   ├── api/UsersApi.kt         # Ktor HTTP client
│       │   ├── repository/UsersRepository.kt  # Result-wrapped data access
│       │   ├── di/SharedModule.kt      # Koin DI module
│       │   ├── Platform.kt             # expect/actual platform abstraction
│       │   └── Greeting.kt
│       └── androidMain/kotlin/com/hitha/shared/
│           └── Platform.android.kt     # Android actual implementation
├── androidApp/                 # Android application
│   └── src/main/java/com/hitha/android/
│       ├── KmmApp.kt                   # Application class (Koin init)
│       ├── MainActivity.kt             # Entry point with NavHost
│       ├── navigation/
│       │   ├── Routes.kt               # Route definitions
│       │   └── AppNavGraph.kt          # Compose Navigation graph
│       ├── ui/
│       │   ├── UsersScreen.kt          # User list with cards
│       │   └── UserDetailsScreen.kt    # User detail with scaffold
│       └── di/
│           └── AppModule.kt            # Koin Android initialization
└── gradle/
    └── libs.versions.toml              # Version catalog
```

## Stack

| Layer | Technology |
|-------|-----------|
| **UI** | Jetpack Compose + Material 3 + Material Icons |
| **Navigation** | Compose Navigation (`navigation-compose:2.8.0`) |
| **Networking** | Ktor 2.3.12 (Content Negotiation, Kotlinx JSON, Logging) |
| **Serialization** | kotlinx-serialization 1.7.3 |
| **DI** | Koin 3.5.6 (core + android + androidx-compose) |
| **Coroutines** | kotlinx-coroutines 1.8.1 |
| **Language** | Kotlin 2.0.21 Multiplatform |
| **Build** | Gradle 8.9 + Version Catalogs + AGP 8.5.2 |
| **API** | [JSONPlaceholder](https://jsonplaceholder.typicode.com/users) |

## Features

- **User List** — fetches users via Ktor, displays in `LazyColumn` with Material 3 cards (name, username, email, phone, website, company)
- **User Details** — tap a card to navigate to a full detail screen with back navigation, showing address with geo coordinates and company info
- **Dependency Injection** — Koin modules in both shared and Android layers
- **Error handling** — loading spinners and error messages for both screens
- **Platform abstraction** — `expect`/`actual` pattern for platform-specific code

## Setup

1. Open in Android Studio (or IntelliJ IDEA with Android plugin)
2. Sync Gradle
3. Run `androidApp` on an emulator or device (API 24+)

## Requirements

- Android Studio Hedgehog+ or IntelliJ IDEA 2024+
- JDK 17
- Android SDK 35

> **Note:** iOS target is not included since this project is built on Windows. To add iOS support, open on a Mac and add `iosX64()`, `iosArm64()`, `iosSimulatorArm64()` targets in `shared/build.gradle.kts`.
