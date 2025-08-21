package com.example.brainquest.ui.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.brainquest.data.model.Quiz
import com.example.brainquest.data.model.QuizResult
import com.example.brainquest.data.model.User
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
    val currentUser: User? = null,
    val progressMap: Map<String, Int> = emptyMap(),
    val quizHistory: List<QuizResult> = emptyList(),
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
        fetchCurrentUserProfile()
        fetchUserProgress()
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

    // ✅ 3. Nova função para buscar os dados do usuário
    private fun fetchCurrentUserProfile() {
        viewModelScope.launch {
            val result = authRepository.getCurrentUserProfile()
            result.onSuccess { user ->
                _uiState.update { it.copy(currentUser = user) }
            }
            result.onFailure { error ->
                _uiState.update { it.copy(errorMessage = error.message) }
            }
        }
    }

    private fun fetchUserProgress() {
        viewModelScope.launch {
            val result = quizRepository.getQuizHistory()
            result.onSuccess { historyList ->
                val progress = historyList.groupBy { it.category }
                    .mapValues { it.value.size }
                _uiState.update { it.copy(
                    progressMap = progress,
                    quizHistory = historyList
                )}
            }
        }
    }

    fun onLogoutClicked() {
        authRepository.logout()
    }
}