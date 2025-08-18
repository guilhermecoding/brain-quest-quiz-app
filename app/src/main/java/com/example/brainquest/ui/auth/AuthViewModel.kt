package com.example.brainquest.ui.auth

import android.health.connect.datatypes.units.Length
import android.util.Log
import android.util.Patterns
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.brainquest.data.repository.AuthRepository
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException
import com.google.firebase.auth.FirebaseAuthInvalidUserException
import com.google.firebase.auth.FirebaseAuthUserCollisionException
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

// 1. Classe de Estado: um único objeto para representar tudo na tela.
data class AuthState(
    val emailValue: String = "",
    val passwordValue: String = "",
    val confirmPasswordValue: String = "",
    val nameUser: String = "",
    val isLoading: Boolean = false,
    val generalErrorMessage: String? = null,
    val isEmailFormatError: Boolean = false,
    val isLoginScreen: Boolean = true,
    val loginSuccess: Boolean = false
)

@HiltViewModel
class AuthViewModel @Inject constructor(
    private val authRepository: AuthRepository
) : ViewModel() {

    // 2. StateFlow: A fonte da verdade para a UI.
    private val _uiState = MutableStateFlow(AuthState())
    val uiState = _uiState.asStateFlow()

    // 3. Funções de Evento: A UI chama essas funções para notificar o ViewModel.
    fun onEmailChange(newValue: String) {
        _uiState.update { it.copy(emailValue = newValue) }
        // A lógica de validação ao digitar pode ser adicionada aqui se necessário
    }

    fun onPasswordChange(newValue: String) {
        _uiState.update { it.copy(passwordValue = newValue) }
    }

    fun onConfirmPasswordChange(newValue: String) {
        _uiState.update { it.copy(confirmPasswordValue = newValue) }
    }

    fun onNameChange(newValue: String) {
        _uiState.update { it.copy(nameUser = newValue) }
    }

    fun onEmailFocusChanged() {
        validateEmailField()
    }

    fun toggleScreen() {
        // Reseta o estado ao trocar de tela para limpar os campos e erros
        _uiState.value = AuthState(isLoginScreen = !_uiState.value.isLoginScreen)
    }

    private fun validateEmailField(performUpdate: Boolean = true): Boolean {
        val currentState = _uiState.value
        val isBlank = currentState.emailValue.isBlank()
        val isValidEmailFormat = Patterns.EMAIL_ADDRESS.matcher(currentState.emailValue).matches()
        val hasFormatError = !isValidEmailFormat && !isBlank

        if (performUpdate) {
            _uiState.update {
                it.copy(
                    isEmailFormatError = hasFormatError,
                    generalErrorMessage = if (hasFormatError) "Formato de e-mail inválido." else null
                )
            }
        }
        return !hasFormatError && !isBlank
    }

    private fun validatePasswordField(): Boolean {
        return _uiState.value.passwordValue.isNotBlank()
    }

    fun attemptLogin() {
        viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true, generalErrorMessage = null, isEmailFormatError = false) }

            val currentState = _uiState.value
            val isEmailFieldValid = validateEmailField()
            val isPasswordFieldValid = validatePasswordField()

            if (currentState.emailValue.isBlank()) {
                _uiState.update { it.copy(generalErrorMessage = "O e-mail é obrigatório.", isEmailFormatError = true, isLoading = false) }
                return@launch
            }
            if (!isEmailFieldValid) {
                _uiState.update { it.copy(generalErrorMessage = "Formato de e-mail inválido.", isEmailFormatError = true, isLoading = false) }
                return@launch
            }
            if (!isPasswordFieldValid) {
                _uiState.update { it.copy(generalErrorMessage = "A senha é obrigatória.", isLoading = false) }
                return@launch
            }

            val result = authRepository.signInWithEmailAndPassword(
                email = currentState.emailValue,
                password = currentState.passwordValue
            )


            result.onSuccess {
                // O Firebase confirmou o login!
                println("AUTH_VIEW_MODEL: Login BEM-SUCEDIDO!")
                _uiState.update { it.copy(isLoading = false, loginSuccess = true) }
            }

            // 5. Tratando o resultado de falha com mensagens específicas
            result.onFailure { exception ->
                println("AUTH_VIEW_MODEL: Login FALHOU. Erro: ${exception.message}")
                val errorMessage = when (exception) {
                    is FirebaseAuthInvalidUserException, is FirebaseAuthInvalidCredentialsException -> {
                        "Usuário ou senha inválidos."
                    }
                    else -> {
                        "Ocorreu um erro inesperado. Tente novamente."
                    }
                }
                _uiState.update { it.copy(isLoading = false, generalErrorMessage = errorMessage) }
            }
        }
    }

    fun attemptSignUp() {
        val currentState = _uiState.value

        // --- Validações ---
        // Adicione um campo para nome no seu AuthState e na sua UI (SignUpContainer)
        // val name = currentState.nameValue
        // if (name.isBlank()) { /* erro */ }

        if (currentState.emailValue.isBlank() || currentState.passwordValue.isBlank()) {
            _uiState.update { it.copy(generalErrorMessage = "E-mail e senha são obrigatórios.") }
            return
        }
        if (currentState.passwordValue != currentState.confirmPasswordValue) {
            _uiState.update { it.copy(generalErrorMessage = "As senhas não conferem.") }
            return
        }

        viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true, generalErrorMessage = null) }

            // Chama a função do repositório com os dados da UI
            val result = authRepository.createUser(
                name = currentState.nameUser, // Substitua pelo valor do campo de nome: currentState.nameValue
                email = currentState.emailValue,
                password = currentState.passwordValue
            )

            result.onSuccess {
                _uiState.update { it.copy(
                    isLoading = false,
                    // Mensagem de sucesso e muda para a tela de login
                    generalErrorMessage = "Cadastro realizado com sucesso! Por favor, faça o login.",
                    isLoginScreen = true
                )}
            }

            result.onFailure { exception ->
                val errorMessage = when (exception) {
                    // Erro específico para quando o e-mail já existe
                    is FirebaseAuthUserCollisionException -> "Este e-mail já está em uso por outra conta."
                    else -> "Ocorreu um erro no cadastro. Tente novamente."
                }
                _uiState.update { it.copy(isLoading = false, generalErrorMessage = errorMessage) }
            }
        }
    }
}