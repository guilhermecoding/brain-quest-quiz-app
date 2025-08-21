package com.example.brainquest.ui.quiz

import android.os.CountDownTimer
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.brainquest.data.local.PrefsManager
import com.example.brainquest.data.model.Question
import com.example.brainquest.data.model.QuizResult
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
    val errorMessage: String? = null,
    val timerText: String = "05:00"
) {
    val currentQuestion: Question?
        get() = questions.getOrNull(currentQuestionIndex)
    val totalQuestions: Int
        get() = questions.size
}

@HiltViewModel
class QuizViewModel @Inject constructor(
    private val quizRepository: QuizRepository,
    private val prefsManager: PrefsManager,
    savedStateHandle: SavedStateHandle // Para receber o ID do quiz da navegação
) : ViewModel() {

    private val _uiState = MutableStateFlow(QuizState())

    private var countDownTimer: CountDownTimer? = null
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
                startTimer()
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
        viewModelScope.launch {
            // Lógica de cálculo da pontuação
            var correctAnswers = 0
            val questions = _uiState.value.questions
            val userAnswers = _uiState.value.userAnswers
            for (i in questions.indices) {
                if (questions[i].resposta_correta == userAnswers[i]) {
                    correctAnswers++
                }
            }

            // Cria o objeto de resultado e manda salvar
            val userId = prefsManager.getUserId()
            if (userId != null) {
                val result = QuizResult(
                    userId = userId,
                    score = correctAnswers,
                    category = _uiState.value.quizTitle,
                    totalQuestions = _uiState.value.totalQuestions
                )
                quizRepository.saveQuizResult(result) // Chama o repositório
            }

            // Atualiza a UI para navegar para a tela de resultados
            _uiState.update { it.copy(quizFinished = true, finalScore = correctAnswers) }
        }
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

    // ✅ 5. Crie a função para iniciar o timer
    private fun startTimer() {
        val totalTimeInMillis = 5 * 60 * 1000L // 5 minutos em milissegundos
        countDownTimer = object : CountDownTimer(totalTimeInMillis, 1000) { // Atualiza a cada 1 segundo

            override fun onTick(millisUntilFinished: Long) {
                // Converte milissegundos para formato MM:SS
                val minutes = (millisUntilFinished / 1000) / 60
                val seconds = (millisUntilFinished / 1000) % 60
                val formattedTime = String.format("%02d:%02d", minutes, seconds)
                _uiState.update { it.copy(timerText = formattedTime) }
            }

            override fun onFinish() {
                // Por enquanto não faz nada, como pedido.
                // No futuro, você poderia finalizar o quiz aqui.
                _uiState.update { it.copy(timerText = "00:00") }
            }
        }.start()
    }

    // ✅ 6. Cancele o timer quando o ViewModel for destruído para evitar memory leaks
    override fun onCleared() {
        super.onCleared()
        countDownTimer?.cancel()
    }

}