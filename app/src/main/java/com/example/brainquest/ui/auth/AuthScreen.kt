package com.example.brainquest.ui.auth

import android.util.Patterns
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
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
import com.example.brainquest.ui.auth.components.SingUpContainer
import com.example.brainquest.ui.theme.AppDimens
import com.example.brainquest.ui.theme.BrainQuestTheme
import com.example.brainquest.ui.theme.PrimaryTextColor
import com.example.brainquest.ui.theme.PurpleTheme // Certifique-se que está definido no seu tema
import com.example.brainquest.ui.theme.YellowTheme // Certifique-se que está definido no seu tema

@Composable
fun AuthScreen() {
    AuthScreenContent()
}

@Composable
fun AuthScreenContent() {
    var emailValue by remember { mutableStateOf("") }
    var passwordValue by remember { mutableStateOf("") }
    // Adicione estados para os campos do SignUpContainer se forem diferentes
    // var nameValue by remember { mutableStateOf("") }
    // var confirmPasswordValue by remember { mutableStateOf("") }
    var generalErrorMessage by remember { mutableStateOf<String?>(null) }
    var isEmailFormatError by remember { mutableStateOf(false) }
    var isLoading by remember { mutableStateOf(false) }
    var showLogin by remember { mutableStateOf(true) } // Estado para alternar entre Login e Cadastro

    val isValidEmailFormat = remember(emailValue) {
        if (emailValue.isBlank()) true
        else Patterns.EMAIL_ADDRESS.matcher(emailValue).matches()
    }

    fun validateEmailField(performUpdate: Boolean = true): Boolean {
        val isBlank = emailValue.isBlank()
        val hasFormatError = !isValidEmailFormat && !isBlank

        if (performUpdate) {
            isEmailFormatError = hasFormatError
            if (hasFormatError) {
                generalErrorMessage = "Formato de e-mail inválido."
            } else if (isBlank) {
                if (generalErrorMessage == "Formato de e-mail inválido.") generalErrorMessage = null
            } else {
                if (generalErrorMessage == "Formato de e-mail inválido.") {
                    generalErrorMessage = null
                }
            }
        }
        return !hasFormatError && !isBlank
    }

    fun validatePasswordField(performUpdate: Boolean = true): Boolean {
        val isBlank = passwordValue.isBlank()
        if (performUpdate) {
            if (isBlank && (generalErrorMessage == null || isEmailFormatError)) {
                // Não define generalErrorMessage para "obrigatório" aqui
            } else if (!isBlank && generalErrorMessage == "A senha é obrigatória.") {
                generalErrorMessage = null
            }
        }
        return !isBlank
    }
    
    // Você precisará de uma função attemptSignUp similar a attemptLogin
    fun attemptSignUp() {
        isLoading = true
        generalErrorMessage = null
        isEmailFormatError = false
        // Validações para campos de cadastro (nome, email, senha, confirmar senha)
        // ...
        // Lógica de cadastro
        println("AUTH_SCREEN_CONTENT: Tentando cadastro com E-mail: ${emailValue}, Senha: [PROTEGIDO]")
        // Simulação
        isLoading = false
        generalErrorMessage = "Funcionalidade de cadastro ainda não implementada."
        // Ou, se sucesso:
        // showLogin = true // Volta para login após cadastro bem-sucedido, por exemplo
    }


    fun attemptLogin() {
        isLoading = true
        generalErrorMessage = null 
        isEmailFormatError = false 

        val isEmailFieldValid = validateEmailField(performUpdate = true) 
        val isPasswordFieldValid = validatePasswordField(performUpdate = true)

        if (emailValue.isBlank()) {
            generalErrorMessage = "O e-mail é obrigatório."
            isEmailFormatError = true 
        } else if (!isEmailFieldValid) { 
            generalErrorMessage = "Formato de e-mail inválido."
            isEmailFormatError = true
        }

        if (passwordValue.isBlank()) {
            if (generalErrorMessage == null) {
                generalErrorMessage = "A senha é obrigatória."
            }
        }

        if (generalErrorMessage == null && isEmailFieldValid && isPasswordFieldValid) {
            println("AUTH_SCREEN_CONTENT: Tentando login com E-mail: ${emailValue}, Senha: [PROTEGIDO]")
            if (emailValue == "test@example.com" && passwordValue == "password123") {
                generalErrorMessage = null
                isEmailFormatError = false
                isLoading = false
                println("AUTH_SCREEN_CONTENT: Login BEM-SUCEDIDO!")
            } else {
                generalErrorMessage = "Usuário ou senha inválidos."
                isEmailFormatError = false 
                isLoading = false
                println("AUTH_SCREEN_CONTENT: Login FALHOU.")
            }
        } else {
            isLoading = false 
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
                        if (showLogin) {
                            LoginContainer(
                                valueEmail = emailValue,
                                onValueChangeEmail = { newValue ->
                                    emailValue = newValue
                                    if (isEmailFormatError && generalErrorMessage == "Formato de e-mail inválido.") {
                                        validateEmailField()
                                    } else if (generalErrorMessage != null && generalErrorMessage != "O e-mail é obrigatório.") {
                                        generalErrorMessage = null
                                    }
                                },
                                isEmailFieldError = isEmailFormatError,
                                onEmailFocusChanged = { isFocused ->
                                    if (!isFocused && emailValue.isNotBlank()) {
                                        validateEmailField()
                                    }
                                },
                                valuePassword = passwordValue,
                                onValueChangePassword = { newValue ->
                                    passwordValue = newValue
                                    if (generalErrorMessage != null && generalErrorMessage != "A senha é obrigatória."){
                                        generalErrorMessage = null
                                    }
                                },
                                generalErrorMessage = generalErrorMessage,
                                modifier = Modifier.fillMaxWidth(0.8f)
                            )
                        } else {
                            SingUpContainer(
                                valueEmail = emailValue,
                                onValueChangeEmail = { newValue ->
                                    emailValue = newValue
                                    if (isEmailFormatError && generalErrorMessage == "Formato de e-mail inválido.") {
                                        validateEmailField()
                                    } else if (generalErrorMessage != null && generalErrorMessage != "O e-mail é obrigatório.") {
                                        generalErrorMessage = null
                                    }
                                },
                                isEmailFieldError = isEmailFormatError,
                                onEmailFocusChanged = { isFocused ->
                                    if (!isFocused && emailValue.isNotBlank()) {
                                        validateEmailField()
                                    }
                                },
                                valuePassword = passwordValue,
                                onValueChangePassword = { newValue ->
                                    passwordValue = newValue
                                    if (generalErrorMessage != null && generalErrorMessage != "A senha é obrigatória."){
                                        generalErrorMessage = null
                                    }
                                },
                                generalErrorMessage = generalErrorMessage,
                                modifier = Modifier.fillMaxWidth(0.8f)
                            )
                        }

                        Spacer(Modifier.height(42.dp))

                        OutlinedButton(
                            onClick = { if (showLogin) attemptLogin() else attemptSignUp() }, // Ação do botão muda
                            enabled = !isLoading,
                            modifier = Modifier.fillMaxWidth(0.8f),
                            colors = ButtonDefaults.buttonColors(
                                containerColor = YellowTheme,
                                contentColor = PrimaryTextColor
                            ),
                            border = BorderStroke(AppDimens.DefaultBorderWidth.width, AppDimens.DefaultBorderWidth.color),
                            contentPadding = PaddingValues(horizontal = 40.dp, vertical = 16.dp)
                        ) {
                            Text(
                                text = if (showLogin) "ENTRAR" else "CADASTRAR", // Texto do botão muda
                                style = MaterialTheme.typography.titleMedium.copy(
                                    fontWeight = FontWeight.Bold,
                                    fontSize = 18.sp
                                )
                            )
                        }

                        Spacer(Modifier.height(16.dp)) // Espaço antes do TextButton

                        TextButton(
                            onClick = {
                                showLogin = !showLogin
                                generalErrorMessage = null // Limpa erros ao alternar
                                isEmailFormatError = false // Limpa erro de email ao alternar
                                // Resete os valores dos campos ao alternar para não manter dados entre formulários
                                emailValue = ""
                                passwordValue = ""
                                // nameValue = ""
                                // confirmPasswordValue = ""

                            },
                            modifier = Modifier.fillMaxWidth(0.8f)
                        ) {
                            Text(
                                text = if (showLogin) "Não tem uma conta? Cadastre-se" else "Já tem uma conta? Faça login",
                                color = PrimaryTextColor,
                                style = MaterialTheme.typography.bodyMedium.copy(fontWeight = FontWeight.SemiBold)
                            )
                        }
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
