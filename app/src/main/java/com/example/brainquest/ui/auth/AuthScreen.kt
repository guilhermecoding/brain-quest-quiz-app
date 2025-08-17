package com.example.brainquest.ui.auth

import android.util.Patterns
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.brainquest.ui.auth.components.LoginContainer // Verifique o caminho do import
import com.example.brainquest.ui.theme.BrainQuestTheme
import com.example.brainquest.ui.theme.PurpleTheme // Certifique-se que está definido no seu tema
import com.example.brainquest.ui.theme.YellowTheme // Certifique-se que está definido no seu tema

@Composable
fun AuthScreen() {
    // Se AuthScreen precisar gerenciar navegação ou callbacks de nível superior,
    // eles seriam passados para AuthScreenContent aqui.
    // Ex: AuthScreenContent(onLoginSuccess = { /* código para navegar para home */ })
    AuthScreenContent()
}

@Composable
fun AuthScreenContent(
    // Ex: onLoginSuccess: (() -> Unit)? = null // Callback opcional
) {
    // --- Estados da UI ---
    var emailValue by remember { mutableStateOf("") }
    var passwordValue by remember { mutableStateOf("") }
    var generalErrorMessage by remember { mutableStateOf<String?>(null) }
    var isEmailFormatError by remember { mutableStateOf(false) }
    var isLoading by remember { mutableStateOf(false) }

    // --- Funções de Validação e Lógica ---
    val isValidEmailFormat = remember(emailValue) {
        if (emailValue.isBlank()) true // Considera formato válido se em branco (obrigatório é checado separadamente)
        else Patterns.EMAIL_ADDRESS.matcher(emailValue).matches()
    }

    fun validateEmailField(performUpdate: Boolean = true): Boolean {
        val isBlank = emailValue.isBlank()
        val hasFormatError = !isValidEmailFormat && !isBlank // Erro de formato só se não estiver em branco E formato inválido

        if (performUpdate) {
            isEmailFormatError = hasFormatError // Apenas erro de formato aqui
            if (hasFormatError) {
                generalErrorMessage = "Formato de e-mail inválido."
            } else if (isBlank) {
                // Não define generalErrorMessage para "obrigatório" aqui,
                // isso será tratado no attemptLogin para não poluir ao sair do campo.
                // Mas pode marcar o campo como erro se a regra for campo obrigatório sempre.
                // isEmailFormatError = true // Descomente se campo vazio deve sempre marcar como erro visual.
                if (generalErrorMessage == "Formato de e-mail inválido.") generalErrorMessage = null
            } else {
                // Limpa APENAS se o erro ATUAL é de formato de email.
                if (generalErrorMessage == "Formato de e-mail inválido.") {
                    generalErrorMessage = null
                }
            }
        }
        return !hasFormatError && !isBlank // Válido se não tem erro de formato E não está em branco
    }

    fun validatePasswordField(performUpdate: Boolean = true): Boolean {
        val isBlank = passwordValue.isBlank()
        // Adicione outras regras como comprimento mínimo aqui
        // val isTooShort = passwordValue.length < 6 && !isBlank

        if (performUpdate) {
            // Lógica para definir isPasswordFormatError e generalErrorMessage para senha
            // Ex: if (isTooShort) generalErrorMessage = "Senha muito curta."
            // Se estiver em branco e houver um erro de email, não sobrescreva a mensagem.
            if (isBlank && (generalErrorMessage == null || isEmailFormatError)) {
                // Não define generalErrorMessage para "obrigatório" aqui,
                // isso será tratado no attemptLogin
            } else if (!isBlank && generalErrorMessage == "A senha é obrigatória.") {
                generalErrorMessage = null // Limpa erro de "senha obrigatória" se algo for digitado
            }
        }
        return !isBlank // && !isTooShort
    }

    fun attemptLogin() {
        isLoading = true
        generalErrorMessage = null // Limpa mensagens de erro anteriores antes de validar
        isEmailFormatError = false // Reseta erro de formato de email

        val isEmailFieldValid = validateEmailField(performUpdate = true) // Valida e atualiza UI
        val isPasswordFieldValid = validatePasswordField(performUpdate = true) // Valida e atualiza UI

        // Checagens de campos obrigatórios prioritárias após as de formato
        if (emailValue.isBlank()) {
            generalErrorMessage = "O e-mail é obrigatório."
            isEmailFormatError = true // Marcar o campo de email como erro se estiver vazio
        } else if (!isEmailFieldValid) { // Se não estiver em branco mas formato inválido
            generalErrorMessage = "Formato de e-mail inválido." // Garante que a mensagem de formato apareça
            isEmailFormatError = true
        }


        if (passwordValue.isBlank()) {
            // Define mensagem de senha obrigatória apenas se não houver erro de email mais prioritário
            if (generalErrorMessage == null) {
                generalErrorMessage = "A senha é obrigatória."
            }
            // Aqui você precisaria de um 'isPasswordFieldError = true' para o campo de senha
        }

        if (generalErrorMessage == null && isEmailFieldValid && isPasswordFieldValid) {
            // LÓGICA DE LOGIN REAL AQUI (ex: chamada a ViewModel)
            println("AUTH_SCREEN_CONTENT: Tentando login com E-mail: ${emailValue}, Senha: [PROTEGIDO]")
            // Simulando chamada de API
            // viewModelScope.launch {
            //    delay(1500) // Simular atraso da rede
            if (emailValue == "test@example.com" && passwordValue == "password123") {
                generalErrorMessage = null
                isEmailFormatError = false
                isLoading = false
                println("AUTH_SCREEN_CONTENT: Login BEM-SUCEDIDO!")
                // onLoginSuccess?.invoke() // Chamar se passado como parâmetro
            } else {
                generalErrorMessage = "Usuário ou senha inválidos."
                isEmailFormatError = false // O formato do email pode estar correto, mas as credenciais não
                isLoading = false
                println("AUTH_SCREEN_CONTENT: Login FALHOU.")
            }
            // }
        } else {
            isLoading = false // Se a validação dos campos falhou ou já mostrou erro
        }
    }

    Scaffold(
        containerColor = PurpleTheme
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
        ) {
            Row(
                modifier = Modifier
                    .weight(1f)
                    .fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Center
            ) {
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    Text(
                        text = "Este é o",
                        style = MaterialTheme.typography.titleLarge.copy(
                            color = Color.White,
                            fontWeight = FontWeight.SemiBold
                        )
                    )
                    Text(
                        text = "Brain Quest",
                        style = MaterialTheme.typography.titleLarge.copy(
                            fontWeight = FontWeight.Bold,
                            color = YellowTheme,
                            fontSize = 48.sp
                        )
                    )
                }
            }

            Row(
                modifier = Modifier
                    .weight(2f)
                    .fillMaxWidth()
            ) {
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .clip(RoundedCornerShape(topStart = 32.dp, topEnd = 32.dp))
                        .background(Color.White)
                ) {
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 32.dp),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        LoginContainer(
                            valueEmail = emailValue,
                            onValueChangeEmail = { newValue ->
                                emailValue = newValue
                                if (isEmailFormatError && generalErrorMessage == "Formato de e-mail inválido.") {
                                    // Revalida para tentar limpar o erro de formato enquanto digita
                                    validateEmailField()
                                } else if (generalErrorMessage != null && generalErrorMessage != "O e-mail é obrigatório.") {
                                    // Limpa outros erros (como de login) se não for erro de campo obrigatório
                                    generalErrorMessage = null
                                }
                            },
                            isEmailFieldError = isEmailFormatError,
                            onEmailFocusChanged = { isFocused ->
                                if (!isFocused && emailValue.isNotBlank()) {
                                    validateEmailField() // Valida formato ao perder foco se não estiver em branco
                                }
                            },
                            valuePassword = passwordValue,
                            onValueChangePassword = { newValue ->
                                passwordValue = newValue
                                if (generalErrorMessage != null && generalErrorMessage != "A senha é obrigatória."){
                                    generalErrorMessage = null // Limpa erros (como de login)
                                }
                                // Aqui você também pode adicionar lógica para limpar o 'isPasswordFormatError'
                            },
                            generalErrorMessage = generalErrorMessage,
                            onLoginClick = {
                                attemptLogin()
                            },
                            isLoginButtonEnabled = !isLoading,
                            modifier = Modifier.fillMaxWidth(0.8f)
                        )
                    }
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun AuthScreenContentPreview() {
    BrainQuestTheme {
        AuthScreenContent()
    }
}
