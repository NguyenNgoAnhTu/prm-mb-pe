# Meal Browser App

A complete Android application for browsing meals using TheMealDB API. Built with modern Android development practices using Jetpack Compose, Room, and Retrofit.

## Features

- **Browse Meals**: View all available meals from TheMealDB API
- **Search**: Search meals by name or category
- **Filter by Category**: Filter meals by their categories
- **Meal Details**: View detailed information about each meal including:
  - Meal name and image
  - Category and area/cuisine
  - Complete list of ingredients with measurements
  - Step-by-step cooking instructions
  - Tags
  - YouTube video link (if available)
- **Favorites**: Save your favorite meals for quick access
  - Add/remove favorites
  - Browse favorite meals
  - Search and filter within favorites
- **Offline Support**: Meals are cached locally using Room database
- **Material 3 Design**: Modern UI with Material Design 3 components

## Technology Stack

### Architecture
- **MVVM Architecture**: Model-View-ViewModel pattern
- **Repository Pattern**: Single source of truth for data
- **Clean Architecture**: Separation of concerns with data, domain, and presentation layers

### Libraries & Technologies
- **Jetpack Compose**: Modern declarative UI
- **Material 3**: Latest Material Design components
- **Navigation Compose**: Type-safe navigation between screens
- **Room Database**: Local database for offline caching
- **Retrofit**: REST API client for network calls
- **Gson**: JSON serialization/deserialization
- **Coroutines & Flow**: Asynchronous programming and reactive streams
- **Coil**: Image loading library
- **ViewModel**: Lifecycle-aware state management
- **KSP**: Kotlin Symbol Processing for Room

## Project Structure

```
app/src/main/java/com/example/se1720_nguyenngoanhtu_se181559/
├── data/
│   ├── api/
│   │   ├── MealApiService.kt        # Retrofit API service
│   │   └── MealApiModels.kt         # API response models
│   ├── database/
│   │   ├── MealDatabase.kt          # Room database
│   │   ├── MealDao.kt               # Database access object
│   │   ├── MealEntity.kt            # Database entity for meals
│   │   └── FavoriteEntity.kt        # Database entity for favorites
│   ├── model/
│   │   └── Meal.kt                  # Domain model
│   └── repository/
│       └── MealRepository.kt        # Repository implementation
├── viewmodel/
│   ├── MealListViewModel.kt         # ViewModel for meal list
│   ├── MealDetailViewModel.kt       # ViewModel for meal details
│   ├── FavoritesViewModel.kt        # ViewModel for favorites
│   └── ViewModelFactory.kt          # Factory for creating ViewModels
├── ui/
│   ├── screens/
│   │   ├── MealListScreen.kt        # Main meal browsing screen
│   │   ├── MealDetailScreen.kt      # Meal details screen
│   │   └── FavoritesScreen.kt       # Favorites screen
│   └── theme/
│       ├── Color.kt
│       ├── Theme.kt
│       └── Type.kt
├── navigation/
│   └── NavGraph.kt                  # Navigation graph
└── MainActivity.kt                   # Main activity
```

## API Endpoints Used

The app uses TheMealDB API (https://www.themealdb.com/api/json/v1/1/):

- `search.php?s=` - Get all meals
- `lookup.php?i={id}` - Get meal by ID
- `filter.php?c={category}` - Filter meals by category
- `categories.php` - Get all categories

## Setup & Installation

1. **Clone the repository**
   ```bash
   git clone <repository-url>
   cd SE1720NguyenNgoAnhTuSE181559
   ```

2. **Open in Android Studio**
   - Open Android Studio
   - Select "Open an existing project"
   - Navigate to the project directory

3. **Sync Gradle**
   - The project will automatically sync Gradle dependencies
   - Wait for the sync to complete

4. **Run the app**
   - Connect an Android device or start an emulator
   - Click the "Run" button in Android Studio
   - Select your device and click OK

## Requirements

- **Min SDK**: 28 (Android 9.0)
- **Target SDK**: 35 (Android 15)
- **Compile SDK**: 35
- **Android Gradle Plugin**: 8.9.0
- **Kotlin**: 2.0.21
- **Java**: 11

## Build

To build the project from command line:

```bash
# Debug build
./gradlew assembleDebug

# Release build
./gradlew assembleRelease

# Run tests
./gradlew test

# Full build with tests
./gradlew build
```

## Key Features Implementation

### 1. Offline-First Architecture
The app uses Room database to cache meals locally, providing a seamless offline experience. When you browse meals:
- First, cached data is displayed from the local database
- Simultaneously, fresh data is fetched from the API
- The database is updated with new data
- UI automatically updates when data changes

### 2. Reactive UI with Flow
All data operations use Kotlin Flow for reactive updates:
- UI observes data changes
- Automatic updates when data changes
- No manual refresh needed

### 3. Search & Filter
- Real-time search as you type
- Category filtering with chips
- Combines search and category filters

### 4. Favorites Management
- Toggle favorite status with a single tap
- Favorites are persisted in the database
- Separate screen for browsing favorites
- Same search and filter capabilities

## Permissions

The app requires the following permissions:
- `INTERNET` - To fetch data from TheMealDB API
- `ACCESS_NETWORK_STATE` - To check network connectivity

## Future Enhancements

Potential improvements for future versions:
- [ ] Add more sorting options (alphabetical, date added)
- [ ] Implement sharing meals with others
- [ ] Add shopping list feature for ingredients
- [ ] Support for multiple languages
- [ ] Dark mode preference
- [ ] Add animations and transitions
- [ ] Implement pull-to-refresh
- [ ] Add meal ratings and reviews
- [ ] Support for dietary restrictions filtering
- [ ] Meal planning calendar

## Screenshots

### Main Screen
- Grid/List view of meals
- Search bar and category filters
- Favorite button on each meal card

### Meal Detail Screen
- Large meal image
- Ingredients with measurements
- Step-by-step instructions
- YouTube link to cooking video

### Favorites Screen
- All your saved meals
- Same search and filter options
- Quick access to favorite meals

## Credits

- **API**: [TheMealDB](https://www.themealdb.com/) - Free meal database and API
- **Images**: Meal images from TheMealDB
- **Icons**: Material Design Icons

## License

This project is created for educational purposes.

## Author

Nguyen Ngo Anh Tu (SE181559)
SE1720 - Mobile Application Development
