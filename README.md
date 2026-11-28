# CineNest

CineNest is an Android app that helps two people choose a movie for the evening. It loads popular movies from TMDB, keeps a personal list of favorite and watched movies on the phone, and has Movie Match: both players swipe through the same deck of movies, and the app shows the ones you both liked.

## Group Members

| Name              | Student ID  |
|-------------------|-------------|
| Tolkynov Dastan   | 240107050   |
| Tokenova Aidana   | 230107125   |

## Main Features

- Popular movies from the TMDB API with posters, year, genre and rating
- Loading indicator, error message with retry and pull to refresh
- Movie details screen with poster and full description
- Favorites and watched marks saved on the phone with Room
- My list tab with favorite movies
- Add your own movie with input validation
- Movie Match: a pass-the-phone swiping game for two players with a list of matches
- Bottom navigation: Home, Match, My list

## Architecture

The project follows MVVM with a repository:

- `data/remote` - Retrofit service and TMDB response models
- `data/local` - Room database, entity and DAO
- `data/MovieRepository` - loads movies from TMDB into Room; the screens read only from Room
- `data/AppContainer` - creates Retrofit, Room and the repository once (manual dependency injection)
- `viewmodel` - `MovieViewModel` for movies, loading and errors; `MatchViewModel` for the game logic
- `ui/screens`, `ui/components` - stateless Compose screens and reusable components
- `ui/navigation` - routes, `NavHost` and bottom tabs

## How to Run

1. Get a free API key at https://www.themoviedb.org/settings/api
2. Add this line to `local.properties` in the project root: `TMDB_API_KEY=your_key_here`
3. Sync Gradle and run the app. `local.properties` is not committed, so the key stays private.

## Tests

`MatchViewModelTest` checks the Movie Match logic: building the deck, player turns, handoff, matches and reset.

## Tech Stack

- Kotlin, Jetpack Compose, Material Design 3
- MVVM, ViewModel, StateFlow, Kotlin Coroutines and Flow
- Navigation Compose
- Retrofit and kotlinx.serialization
- Room
- Coil

This product uses the TMDB API but is not endorsed or certified by TMDB.
