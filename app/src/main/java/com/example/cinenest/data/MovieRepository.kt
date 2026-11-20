package com.example.cinenest.data

import com.example.cinenest.model.Movie

object MovieRepository {

    val movies = listOf(

        Movie(
            id = 1,
            title = "Interstellar",
            description = "A team of astronauts travels through space to find a new home for humanity.",
            rating = 8.7,
            year = 2014,
            genre = "Sci-Fi, Drama, Adventure"
        ),

        Movie(
            id = 2,
            title = "Mutiny",
            description = "A bodyguard searches for the people responsible for his employer's murder.",
            rating = 7.2,
            year = 2025,
            genre = "Action, Crime"
        ),
        Movie(
            id = 3,
            title = "QARA: Witness",
            description = "A student witnesses a crime through a security camera and faces a dangerous choice.",
            rating = 6.2,
            year = 2026,
            genre = "Thriller"
        ),
        Movie(
            id = 4,
            title = "Kung Fu Panda 3",
            description = "Po meets his panda family and faces a powerful new enemy.",
            rating = 7.3,
            year = 2016,
            genre = "Animation, Fantasy"
        ),
        Movie(
            id = 5,
            title = "Alien",
            description = "A spaceship crew encounters a dangerous alien creature.",
            rating = 8.1,
            year = 1979,
            genre = "Horror, Sci-Fi, Thriller"
        ),
        Movie(
            id = 6,
            title = "Avatar",
            description = "A former marine discovers the world of Pandora and its people.",
            rating = 8.0,
            year = 2009,
            genre = "Sci-Fi, Action, Adventure"
        ),
        Movie(
            id = 7,
            title = "La La Land",
            description = "An actress and a musician fall in love while following their dreams.",
            rating = 8.0,
            year = 2016,
            genre = "Musical, Romance"
        ),
        Movie(
            id = 8,
            title = "Now You See Me",
            description = "A team of illusionists performs spectacular shows and mysterious robberies.",
            rating = 7.8,
            year = 2013,
            genre = "Thriller, Crime"
        ),
        Movie(
            id = 9,
            title = "Gladiator",
            description = "A Roman general becomes a gladiator and fights for justice.",
            rating = 8.6,
            year = 2000,
            genre = "History, Action, Drama"
        ),
        Movie(
            id = 10,
            title = "Spider-Man: Brand New Day",
            description = "Peter Parker returns as Spider-Man and faces new challenges.",
            rating = 7.9,
            year = 2026,
            genre = "Action, Superhero"
        )
    )
}
