# Presence Starter Repo (Android + Firebase Functions)

This is a minimal, compile-first starter for the **Presence Meetups** app: Android (Jetpack Compose) + Firebase Cloud Functions + docs.
It’s designed for **solo dev** momentum—get the app running quickly, then layer Firebase and features as you go.

## What’s inside
```
presence-starter-repo/
├─ android/                # Android app (Kotlin + Compose)
│  └─ app/
│     ├─ build.gradle.kts  # Compose, Material3, Navigation (Firebase commented out)
│     └─ src/main/         # Minimal UI + mock data
├─ firebase/               # Cloud Functions (TypeScript) + rules + emulator config
│  ├─ functions/
│  ├─ firestore.rules
│  ├─ firestore.indexes.json
│  └─ firebase.json
└─ docs/                   # Architecture + Roadmap stubs
```

## Quickstart (Android only, no backend)
1. Open **Android Studio** → **Open** → select `android/` folder.
2. Let it sync. Press **Run**. You should see a simple feed with fake places.
3. Edit UI in `MainActivity.kt` and `ui/` to confirm fast iteration.

## Add Firebase later (staging project)
1. Create a Firebase project (staging). Add Android app with package: `com.example.presence` (or your own).
2. Download `google-services.json` and put it in `android/app/src/google-services.json`.
3. In `android/app/build.gradle.kts` uncomment the Firebase dependencies, and add the Google Services plugin:
   - In `build.gradle.kts (project)`: add `id("com.google.gms.google-services") version "4.4.2" apply false`
   - In `app/build.gradle.kts`: add `id("com.google.gms.google-services")` under `plugins {}`.
4. Run the app; sign-in wiring can be added later.
5. For Functions, install Node 18+, then in `firebase/functions` run:
   ```bash
   npm i
   npm run serve    # runs emulators (if set in firebase.json) and functions
   ```

## Notes
- This skeleton avoids version churn by keeping deps modest. Upgrade libraries in Android Studio when prompted.
- Firebase pieces are **commented** so the app compiles without any keys.
- The Functions folder includes a **placeholder** aggregator and ETA service with TODOs.
