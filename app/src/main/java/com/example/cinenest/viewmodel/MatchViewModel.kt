package com.example.cinenest.viewmodel

import androidx.lifecycle.ViewModel
import com.example.cinenest.model.Movie
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

enum class MatchPhase { INTRO, PLAYER_ONE, HANDOFF, PLAYER_TWO, RESULTS }

data class MatchUiState(
    val phase: MatchPhase = MatchPhase.INTRO,
    val deck: List<Movie> = emptyList(),
    val currentIndex: Int = 0,
    val playerOneLikes: Set<Int> = emptySet(),
    val playerTwoLikes: Set<Int> = emptySet()
) {
    val currentMovie: Movie?
        get() = deck.getOrNull(currentIndex)

    val currentPlayerName: String
        get() = if (phase == MatchPhase.PLAYER_TWO) "Player 2" else "Player 1"

    val matches: List<Movie>
        get() = deck.filter { it.id in playerOneLikes && it.id in playerTwoLikes }
}

class MatchViewModel : ViewModel() {

    private val _uiState = MutableStateFlow(MatchUiState())
    val uiState: StateFlow<MatchUiState> = _uiState.asStateFlow()

    fun start(movies: List<Movie>, deckSize: Int = 10) {
        if (movies.isEmpty()) return
        _uiState.value = MatchUiState(
            phase = MatchPhase.PLAYER_ONE,
            deck = movies.shuffled().take(deckSize)
        )
    }

    fun like() = answer(liked = true)

    fun skip() = answer(liked = false)

    private fun answer(liked: Boolean) {
        _uiState.update { state ->
            val movie = state.currentMovie ?: return@update state
            val isPlayerOne = state.phase == MatchPhase.PLAYER_ONE
            val isPlayerTwo = state.phase == MatchPhase.PLAYER_TWO
            if (!isPlayerOne && !isPlayerTwo) return@update state

            val playerOneLikes = if (isPlayerOne && liked) state.playerOneLikes + movie.id else state.playerOneLikes
            val playerTwoLikes = if (isPlayerTwo && liked) state.playerTwoLikes + movie.id else state.playerTwoLikes
            val nextIndex = state.currentIndex + 1
            val deckFinished = nextIndex >= state.deck.size

            val nextPhase = when {
                !deckFinished -> state.phase
                isPlayerOne -> MatchPhase.HANDOFF
                else -> MatchPhase.RESULTS
            }

            state.copy(
                phase = nextPhase,
                currentIndex = if (deckFinished) 0 else nextIndex,
                playerOneLikes = playerOneLikes,
                playerTwoLikes = playerTwoLikes
            )
        }
    }

    fun playerReady() {
        _uiState.update { state ->
            if (state.phase == MatchPhase.HANDOFF) {
                state.copy(phase = MatchPhase.PLAYER_TWO, currentIndex = 0)
            } else {
                state
            }
        }
    }

    fun playAgain() {
        _uiState.value = MatchUiState()
    }
}
