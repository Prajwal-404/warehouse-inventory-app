# 📦 Warehouse Inventory App

A simple inventory management application developed during CS-360 at SNHU. The purpose of this application is to maintain quantity on stock and provide users with quick on-the-go access to current inventory, with the ability to add or remove inventory items from a local database.

---

## 📖 Overview

The app features a login screen with authentication, and a main page that displays a list of inventory items with names and quantities. Core requirements included user login creation, quick quantity adjustment, and adding or removing inventory items — all backed by a local Room database with no internet connection required.

---

## ✨ Features

- 🔐 **User Authentication** — Login and account creation with SHA-256 password hashing
- 📋 **Inventory List** — RecyclerView displaying all warehouse items with name and quantity
- ➕ **CRUD Operations** — Add, edit, and delete inventory items in real time
- 📲 **SMS Notifications** — Optional runtime SMS permission to alert on low stock
- 📵 **Minimal Permissions** — Only SMS and POST_NOTIFICATIONS; no camera, audio, or internet
- 📱 **Tablet Compatible** — `telephony required` set to false in the manifest
- 🏗️ **MVVM Architecture** — ViewModel, LiveData, and Repository pattern
- 🗄️ **Room Database** — Persistent local storage with separate DAOs for users and inventory
- ✅ **Test Suite** — 16 passing unit and instrumented tests

---

## ✅ Requirements

- Android Studio Hedgehog or later
- Java 11
- Android SDK minimum API 26 (Android 8.0 Oreo)
- Physical device or emulator running API 26+

> **Why API 26?** Notification channels required by this app were introduced in Android 8.0. API 33 additionally requires `POST_NOTIFICATIONS` runtime permission, which is declared in the manifest.

---

## 🚀 Installation

1. Clone the repository:
   ```bash
   git clone https://github.com/DPHarmon/CS360_Mobile_Architecture_Project.git
   ```
2. Open the project in **Android Studio**
3. Wait for **Gradle sync** to complete
4. Connect a device or start an emulator (API 26+)
5. Click **Run ▶️** or press `Shift + F10`

---

## 🛠️ Usage

1. Launch the app — you will land on the **Login Screen**
2. Create a new account or log in with existing credentials
3. Grant SMS permissions on the **SMS Permission Screen** (optional — can be denied)
4. Use the **Inventory Screen** to:
   - View all current stock items and quantities
   - Tap the **+** button to add a new item
   - Tap an item to edit its name or quantity
   - Swipe or use the delete button to remove an item

---

## 📦 Technologies

| Layer | Technology |
|---|---|
| Language | Java |
| UI | XML Layouts, RecyclerView, Material Design |
| Architecture | MVVM (ViewModel + LiveData + Repository) |
| Database | Room (SQLite) |
| Security | SHA-256 password hashing (`PasswordUtils`) |
| Permissions | Runtime SMS + POST_NOTIFICATIONS |
| Testing | JUnit, Espresso, AndroidX Test |
| Build System | Gradle |

---

## 🗂️ Repository Structure

```
CS360_Mobile_Architecture_Project/
│
├── .idea/                          # Android Studio project configuration (do not edit)
│   ├── .gitignore
│   ├── AndroidProjectSystem.xml
│   ├── androidTestResultsUserPreferences.xml
│   ├── compiler.xml
│   ├── deploymentTargetSelector.xml
│   ├── deviceManager.xml
│   ├── gradle.xml
│   ├── misc.xml
│   └── runConfigurations.xml
│
├── app/                            # Main application module
│   └── src/
│       ├── androidTest/            # Instrumented tests (run on device/emulator)
│       │   └── java/com/zybooks/cs360_warehouse_inventory_app/
│       │
│       ├── main/                   # Production source code
│       │   ├── java/com/zybooks/cs360_warehouse_inventory_app/
│       │   │   ├── AppDatabase.java          # Room database singleton — registers DAOs and entities
│       │   │   ├── InventoryActivity.java    # Main screen — hosts the inventory RecyclerView
│       │   │   ├── InventoryAdapter.java     # RecyclerView adapter — binds inventory items to views
│       │   │   ├── InventoryDao.java         # Data Access Object — SQL queries for inventory items
│       │   │   ├── InventoryItem.java        # Entity — defines the inventory_items table schema
│       │   │   ├── InventoryRepository.java  # Repository — abstracts DB access from the ViewModel
│       │   │   ├── InventoryViewModel.java   # ViewModel — exposes LiveData to InventoryActivity
│       │   │   ├── LoginActivity.java        # Login/register screen — entry point of the app
│       │   │   ├── PasswordUtils.java        # SHA-256 hashing utility for secure password storage
│       │   │   ├── SmsPermissionActivity.java# Handles runtime SMS permission request flow
│       │   │   ├── SmsUtils.java             # Utility for sending SMS low-stock alerts
│       │   │   ├── User.java                 # Entity — defines the users table schema
│       │   │   └── UserDao.java              # Data Access Object — SQL queries for user accounts
│       │   │
│       │   └── res/                # Resources
│       │       ├── drawable/           # Vector drawables and background assets
│       │       ├── layout/             # XML layout files for each Activity and list item
│       │       ├── mipmap-anydpi/      # Adaptive launcher icons
│       │       ├── mipmap-hdpi/        # Launcher icon — hdpi
│       │       ├── mipmap-mdpi/        # Launcher icon — mdpi
│       │       ├── mipmap-xhdpi/       # Launcher icon — xhdpi
│       │       ├── mipmap-xxhdpi/      # Launcher icon — xxhdpi
│       │       ├── mipmap-xxxhdpi/     # Launcher icon — xxxhdpi
│       │       ├── values/             # Colors, strings, and default themes
│       │       ├── values-night/       # Dark mode theme overrides
│       │       └── xml/                # Backup rules and data extraction config
│       │           ├── backup_rules.xml
│       │           └── data_extraction_rules.xml
│       │
│       │   └── AndroidManifest.xml   # App permissions, activities, and entry point declaration
│       │
│       └── test/                   # Unit tests (run on local JVM)
│           └── java/com/zybooks/cs360_warehouse_inventory_app/
│
├── gradle/                         # Gradle wrapper files
├── .gitignore                      # Files and folders excluded from version control
├── README.md                       # This file
├── build.gradle                    # Project-level Gradle build configuration
├── gradle.properties               # Gradle performance and project-wide properties
├── gradlew                         # Unix Gradle wrapper script
├── gradlew.bat                     # Windows Gradle wrapper script
└── settings.gradle                 # Declares the app module to Gradle
```

---

## 🔗 Architecture Flow

```
┌─────────────────────────────────────────────────────┐
│                    UI Layer                         │
│  LoginActivity → InventoryActivity                  │
│                  SmsPermissionActivity              │
└──────────────────────┬──────────────────────────────┘
                       │ observes LiveData
┌──────────────────────▼──────────────────────────────┐
│                 ViewModel Layer                     │
│              InventoryViewModel                     │
└──────────────────────┬──────────────────────────────┘
                       │ calls
┌──────────────────────▼──────────────────────────────┐
│               Repository Layer                      │
│             InventoryRepository                     │
└──────────────────────┬──────────────────────────────┘
                       │ queries
┌──────────────────────▼──────────────────────────────┐
│                  Database Layer                     │
│   AppDatabase (Room)                                │
│   ├── InventoryDao  →  InventoryItem (Entity)       │
│   └── UserDao       →  User (Entity)                │
└─────────────────────────────────────────────────────┘
```

---

## ✅ Running Tests

```bash
# Unit tests (local JVM)
./gradlew test

# Instrumented tests (requires connected device or emulator)
./gradlew connectedAndroidTest
```
---

## 🚀 App Launch Plan (Project 3)

A hypothetical Google Play Store launch plan was written as the final assignment for CS-360:

- **App Name Candidates** — *StockTracker* or *The Vault*. "Stock" was favored for dual-meaning discoverability alongside stock market apps on the Play Store.
- **UI Improvements** — Darker color scheme (smoke gray + gold trim) proposed to reduce eye strain under warehouse fluorescent lighting.
- **Icon** — A safe/vault icon concept to reinforce the security and storage theme.
- **Play Store SMS Policy** — SMS must be declared a core function in the store listing or Google restricts it to POST_NOTIFICATIONS only.
- **Monetization** — One-time purchase, no ads, optional free trial:

| Tier 1 Standard | Tier 2 Small Business | Tier 3 Mid-Large Business |
|---|---|---|
| $14.99 — 1 Device | $49.99 — 5 Devices | $149.99 — 25 Devices |

---

