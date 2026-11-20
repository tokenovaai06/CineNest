# CineNest

CineNest is an Android application for movie lovers. It shows a list of movies with descriptions and ratings. Users can add movies to favorites, mark them as watched and add their own movies.

## Group Members

| Name              | Student ID  |
|-------------------|-------------|
| Tolkynov Dastan   | 240107050   |
| Tokenova Aidana   | 230107125   |

## Main Features

- Movie list with title, year, genre and rating
- Movie details screen with full description
- Add a movie to favorites from the list or from the details screen
- Mark a movie as watched
- Add your own movie
- Navigation between screens with a movie ID argument and back navigation

## Architecture

The project follows MVVM:

- `model` - the `Movie` data class
- `data` - `MovieRepository` with the sample movie catalog
- `viewmodel` - `MovieViewModel` keeps the UI state in a `StateFlow` and holds the logic
- `ui/screens` - Compose screens that only show state and send events
- `ui/components` - reusable composables: `MovieCard`, `RatingBadge`, `FavoriteButton`, `CineNestTopBar`
- `ui/navigation` - routes and `NavHost`

## Tech Stack

- Kotlin
- Jetpack Compose
- Material Design 3
- MVVM architecture (ViewModel + StateFlow)
- Navigation Compose