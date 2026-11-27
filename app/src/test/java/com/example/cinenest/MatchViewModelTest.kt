package com.example.cinenest

import com.example.cinenest.model.Movie
import com.example.cinenest.viewmodel.MatchPhase
import com.example.cinenest.viewmodel.MatchViewModel
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Test

class MatchViewModelTest {

    private val movies = (1..6).map { id ->
        Movie(
            id = id,
            title = "Movie $id",
            description = "Description $id",
            rating = 7.0,
            year = 2020,
            genre = "Drama"
        )
    }

    private fun MatchViewModel.swipeWholeDeck(likes: (Movie) -> Boolean) {
        val phase = uiState.value.phase
        while (uiState.value.phase == phase) {
            val movie = uiState.value.currentMovie ?: return
            if (likes(movie)) like() else skip()
        }
    }

    @Test
    fun newGame_startsWithIntro() {
        val viewModel = MatchViewModel()

        assertEquals(MatchPhase.INTRO, viewModel.uiState.value.phase)
    }

    @Test
    fun start_withEmptyList_staysInIntro() {
        val viewModel = MatchViewModel()

        viewModel.start(emptyList())

        assertEquals(MatchPhase.INTRO, viewModel.uiState.value.phase)
    }

    @Test
    fun start_buildsDeckOfRequestedSize() {
        val viewModel = MatchViewModel()

        viewModel.start(movies, deckSize = 4)

        assertEquals(MatchPhase.PLAYER_ONE, viewModel.uiState.value.phase)
        assertEquals(4, viewModel.uiState.value.deck.size)
    }

    @Test
    fun playerOneFinishesDeck_goesToHandoff() {
        val viewModel = MatchViewModel()
        viewModel.start(movies, deckSize = movies.size)

        viewModel.swipeWholeDeck { true }

        assertEquals(MatchPhase.HANDOFF, viewModel.uiState.value.phase)
    }

    @Test
    fun matches_areMoviesLikedByBothPlayers() {
        val viewModel = MatchViewModel()
        viewModel.start(movies, deckSize = movies.size)

        viewModel.swipeWholeDeck { it.id <= 4 }
        viewModel.playerReady()
        viewModel.swipeWholeDeck { it.id % 2 == 0 }

        val state = viewModel.uiState.value
        assertEquals(MatchPhase.RESULTS, state.phase)
        assertEquals(setOf(2, 4), state.matches.map { it.id }.toSet())
    }

    @Test
    fun noCommonLikes_givesNoMatches() {
        val viewModel = MatchViewModel()
        viewModel.start(movies, deckSize = movies.size)

        viewModel.swipeWholeDeck { it.id <= 3 }
        viewModel.playerReady()
        viewModel.swipeWholeDeck { it.id > 3 }

        assertTrue(viewModel.uiState.value.matches.isEmpty())
    }

    @Test
    fun playAgain_resetsGame() {
        val viewModel = MatchViewModel()
        viewModel.start(movies, deckSize = movies.size)
        viewModel.like()

        viewModel.playAgain()

        val state = viewModel.uiState.value
        assertEquals(MatchPhase.INTRO, state.phase)
        assertTrue(state.deck.isEmpty())
        assertTrue(state.playerOneLikes.isEmpty())
    }
}
