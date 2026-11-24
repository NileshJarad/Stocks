# Stocks

A sample Android app that loads a stock holdings portfolio and shows the current value, total
investment, today's profit & loss and overall profit & loss, both as a summary and per holding.

The project demonstrates a modern Android setup: **Jetpack Compose**, **MVVM**,
**Clean Architecture**, **Hilt** dependency injection and a multi-module Gradle build.

---

## Features

- Portfolio summary: current value, total investment, today's P&L, overall P&L
- Per-holding list: symbol, company name, quantity, LTP and P&L
- Explicit UI states: loading, empty, success and error with retry
- One-off snackbar events for recoverable errors
- Offline mock data source so the app runs without any backend
- Locale-aware INR currency formatting
- Unit tests for domain logic, DTO mapping and the ViewModel, plus Compose UI tests

---

## Architecture

The app follows **Clean Architecture** with a **unidirectional data flow (UDF)** and the
**MVVM** presentation pattern.

```
        UI (Compose)
             |
             v
   ViewModel (StateFlow / events)
             |
             v
     Domain use cases
             |
             v
  Repository interface  <--- owned by :domain
             ^
             |
  RepositoryImpl (DTO -> domain, Result)
             |
             v
        Retrofit API
```

Dependency rule: presentation and data depend on domain; domain depends on nothing Android.
The repository **interface** lives in `:domain`, and its **implementation** lives in `:data`.

### Data flow

1. `HoldingsViewModel` calls `GetHoldingsUseCase`.
2. The use case calls the `StocksRepository` abstraction.
3. `StocksRepositoryImpl` performs the network call via `StockApi` on the IO dispatcher, maps
   DTOs to domain models and wraps the outcome in `Result`.
4. The ViewModel computes the portfolio summary with `CalculateHoldingsSummaryUseCase`, maps
   everything to an immutable `HoldingsUiState` and exposes it as a `StateFlow`.
5. `HoldingsScreen` observes the state with `collectAsStateWithLifecycle()` and renders the UI.
   One-off messages (e.g. snackbars) flow through a buffered `Channel` as `HoldingsEvent`.

### Presentation state

```kotlin
sealed interface HoldingsUiState {
    data object Loading : HoldingsUiState
    data object Empty : HoldingsUiState
    data class Error(val message: String) : HoldingsUiState
    data class Success(
        val summary: HoldingsSummaryUi,
        val holdings: List<HoldingUi>,
    ) : HoldingsUiState
}
```

---

## Modules

| Module | Type | Responsibility |
| --- | --- | --- |
| `:app` | Android application | Application entry point, single-activity Compose host, Hilt bootstrap |
| `:core:common` | Kotlin/JVM library | Dispatcher qualifiers and money formatting utilities |
| `:core:designsystem` | Android library | Material 3 theme, colors and typography |
| `:domain` | Kotlin/JVM library | Domain models, repository contracts and use cases |
| `:data` | Android library | Retrofit API, DTOs, mappers, repository implementation, network DI |
| `:feature:holdings` | Android library | Holdings screen, ViewModel, UI state and feature DI |

```
Stocks/
├── app/                     # Application + MainActivity (Compose)
├── core/
│   ├── common/              # Qualifiers, money formatters
│   └── designsystem/        # Material 3 theme
├── domain/                  # Models, repository interface, use cases (pure Kotlin)
├── data/                    # Retrofit, DTOs, mappers, repository impl, assets
├── feature/
│   └── holdings/            # Holdings ViewModel + Compose UI
├── gradle/
│   └── libs.versions.toml   # Version catalog
├── build.gradle
└── settings.gradle
```

---

## Tech stack

| Area | Technology |
| --- | --- |
| Language | Kotlin 2.0.21 |
| Build | Gradle 8.9, Android Gradle Plugin 8.7.2, Groovy DSL + version catalog |
| UI | Jetpack Compose (BOM 2024.10.01), Material 3 |
| Architecture | MVVM + Clean Architecture, single activity, unidirectional data flow |
| State | Kotlin Coroutines 1.9.0, Flow / StateFlow, lifecycle-aware collection |
| DI | Hilt 2.52 with KSP |
| Networking | Retrofit 2.11.0, OkHttp 4.12.0, kotlinx.serialization 1.7.3 |
| Testing | JUnit4, Truth, Turbine, kotlinx-coroutines-test, Compose UI Test |
| SDK | compileSdk/targetSdk 35, minSdk 24 |
| JDK | 17 |

---

## Domain calculations

Per holding (`Stock`):

```
investedValue  = avgPrice * quantity
currentValue   = lastPrice * quantity
pnl            = currentValue - investedValue
todaysPnl      = (lastPrice - previousClose) * quantity
```

Portfolio (`HoldingsSummary`) is the sum of each holding's values:

```
totalInvestment = Σ investedValue
currentValue    = Σ currentValue
todaysPnl       = Σ todaysPnl
overallPnl      = currentValue - totalInvestment
```

> Note: today's P&L is `(ltp - close) * quantity`, i.e. positive when the price rises above the
> previous close. The original implementation had this sign inverted; it is covered by a
> regression test.

---

## Dependency injection

Hilt is used throughout:

- `StocksApplication` is annotated with `@HiltAndroidApp`.
- `MainActivity` is annotated with `@AndroidEntryPoint`.
- Hilt modules:
  - `NetworkModule` provides `Json`, `OkHttpClient`, `Retrofit` and `StockApi`.
  - `DataModule` binds `StocksRepositoryImpl` to `StocksRepository`.
  - `CoroutinesModule` provides the qualified `@IoDispatcher`.
- `HoldingsViewModel` is a `@HiltViewModel` and is obtained with `hiltViewModel()`.

---

## Configuration

The base URL, API token and local-mock toggle are exposed through `BuildConfig` and can be
overridden without touching source code. Add them to `local.properties` (git-ignored) or pass
them as Gradle properties.

`local.properties`:

```properties
BASE_URL=https://your-api.example.com/
STOCKS_TOKEN=your-token
USE_LOCAL_MOCK=false
```

Or from the command line:

```bash
./gradlew assembleDebug -PBASE_URL=https://your-api.example.com/ -PSTOCKS_TOKEN=your-token -PUSE_LOCAL_MOCK=false
```

| Property | Default | Description |
| --- | --- | --- |
| `BASE_URL` | `https://run.mocky.io/` | Retrofit base URL (used only when the mock is disabled) |
| `STOCKS_TOKEN` | legacy token | Path parameter for `GET v3/{token}` |
| `USE_LOCAL_MOCK` | `true` | Serves the bundled `holdings.json` through an OkHttp interceptor |

The API contract is `GET {BASE_URL}v3/{token}` returning a response with a `data` array of stock
objects (`avg_price`, `close`, `company_name`, `ltp`, `quantity`, `symbol`).

### About the mock data

The original `run.mocky.io` endpoint is no longer usable: the host now returns **404** behind a
**self-signed certificate**, which surfaces in the app as a connection error. To keep the project
runnable, a static response is bundled at `data/src/main/assets/holdings.json` and served by
`MockHoldingsInterceptor` when `USE_LOCAL_MOCK` is enabled (the default). Disable it and set
`BASE_URL`/`STOCKS_TOKEN` to point at a real backend.

---

## Getting started

Requirements:

- Android Studio (with JDK 17)
- Android SDK 35

Clone the repository and build:

```bash
git clone <repository-url>
cd Stocks
./gradlew assembleDebug
```

Run the debug APK:

```bash
./gradlew installDebug
```

---

## Testing

```bash
./gradlew test                 # JVM + Android unit tests
./gradlew lint                 # Android lint
./gradlew connectedDebugAndroidTest   # requires a running emulator/device
```

Test coverage includes:

- `CalculateHoldingsSummaryUseCaseTest` - summary math and today's P&L sign
- `StockMapperTest` - DTO to domain mapping
- `HoldingsViewModelTest` - loading, success, empty, error and retry flows (fake repository)
- `HoldingsScreenTest` - Compose UI states

---

## Key design decisions

- **Single source of truth**: the ViewModel exposes one immutable `HoldingsUiState` through a
  `StateFlow`; no `LiveData`/`ObservableField` mixing.
- **Framework-light domain**: `:domain` is a plain Kotlin module and only depends on
  `javax.inject` for constructor injection.
- **No loading in the data layer**: the repository returns `Result<T>`; the ViewModel owns the
  loading/empty/error presentation state.
- **Config-change safe**: holdings are loaded once in `ViewModel.init`, so rotation does not
  trigger a second fetch or duplicate collection.
- **Typed configuration**: no hardcoded base URL or token in source; everything comes from
  `BuildConfig`.
- **Debug-only logging**: the OkHttp body logging interceptor is only added in debug builds.
