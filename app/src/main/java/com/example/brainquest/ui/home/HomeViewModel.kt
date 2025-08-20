package com.example.brainquest.ui.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.brainquest.data.model.Quiz
import com.example.brainquest.data.repository.AuthRepository
import com.example.brainquest.data.repository.QuizRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

// Data class para representar o estado da tela
data class HomeState(
    val isLoading: Boolean = true,
    val quizzes: List<Quiz> = emptyList(),
    val errorMessage: String? = null
)

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val authRepository: AuthRepository,
    private val quizRepository: QuizRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow(HomeState())
    val uiState = _uiState.asStateFlow()

    // O bloco init é executado assim que o ViewModel é criado
    init {
        fetchQuizzes()
    }

    private fun fetchQuizzes() {
        viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true) }
            val result = quizRepository.getQuizzes()
            result.onSuccess { quizzes ->
                _uiState.update { it.copy(isLoading = false, quizzes = quizzes) }
            }
            result.onFailure { error ->
                _uiState.update { it.copy(isLoading = false, errorMessage = error.message) }
            }
        }
    }

    fun onLogoutClicked() {
        authRepository.logout()
    }
}