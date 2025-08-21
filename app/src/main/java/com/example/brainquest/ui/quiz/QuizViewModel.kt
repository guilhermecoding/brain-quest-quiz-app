package com.example.brainquest.ui.quiz

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.brainquest.data.model.Question
import com.example.brainquest.data.repository.QuizRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

data class QuizState(
    val isLoading: Boolean = true,
    val quizTitle: String = "",
    val questions: List<Question> = emptyList(),
    val currentQuestionIndex: Int = 0,
    val selectedAnswerIndex: Int? = null,
    val userAnswers: Map<Int, Int> = emptyMap(), // Map<Índice da Pergunta, Índice da Resposta>
    val quizFinished: Boolean = false,
    val finalScore: Int = 0,
    val errorMessage: String? = null
) {
    val currentQuestion: Question?
        get() = questions.getOrNull(currentQuestionIndex)
    val totalQuestions: Int
        get() = questions.size
}

@HiltViewModel
class QuizViewModel @Inject constructor(
    private val quizRepository: QuizRepository,
    savedStateHandle: SavedStateHandle // Para receber o ID do quiz da navegação
) : ViewModel() {

    private val _uiState = MutableStateFlow(QuizState())
    val uiState = _uiState.asStateFlow()

    init {
        // Pega o quizId passado pela navegação e busca os dados
        val quizId: String? = savedStateHandle.get("quizId")
        if (quizId != null) {
            fetchQuiz(quizId)
        } else {
            _uiState.update { it.copy(isLoading = false, errorMessage = "ID do Quiz não encontrado.") }
        }
    }

    private fun fetchQuiz(quizId: String) {
        viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true) }
            val result = quizRepository.getQuizById(quizId)
            result.onSuccess { quiz ->
                _uiState.update {
                    it.copy(
                        isLoading = false,
                        quizTitle = quiz.title,
                        questions = quiz.questions
                    )
                }
            }
            result.onFailure { error ->
                _uiState.update { it.copy(isLoading = false, errorMessage = error.message) }
            }
        }
    }

    fun onAnswerSelected(answerIndex: Int) {
        val newAnswers = _uiState.value.userAnswers.toMutableMap()
        newAnswers[_uiState.value.currentQuestionIndex] = answerIndex
        _uiState.update {
            it.copy(
                selectedAnswerIndex = answerIndex,
                userAnswers = newAnswers
            )
        }
    }

    fun onNextClicked() {
        // Se for a última pergunta, finalize o quiz
        if (_uiState.value.currentQuestionIndex == _uiState.value.totalQuestions - 1) {
            finishQuiz()
        } else {
            // Caso contrário, avance para a próxima
            _uiState.update {
                it.copy(
                    currentQuestionIndex = it.currentQuestionIndex + 1,
                    selectedAnswerIndex = null // Reseta a seleção para a nova pergunta
                )
            }
        }
    }

    private fun finishQuiz() {
        var correctAnswers = 0
        val questions = _uiState.value.questions
        val userAnswers = _uiState.value.userAnswers

        for (i in questions.indices) {
            val correctAnswerIndex = questions[i].resposta_correta
            val userAnswerIndex = userAnswers[i]
            if (correctAnswerIndex == userAnswerIndex) {
                correctAnswers++
            }
        }

        // Atualiza o estado para sinalizar que o quiz acabou e guarda a pontuação
        _uiState.update { it.copy(quizFinished = true, finalScore = correctAnswers) }
    }

    fun onPreviousClicked() {
        if (_uiState.value.currentQuestionIndex > 0) {
            _uiState.update {
                it.copy(
                    currentQuestionIndex = it.currentQuestionIndex - 1,
                    selectedAnswerIndex = null
                )
            }
        }
    }
}