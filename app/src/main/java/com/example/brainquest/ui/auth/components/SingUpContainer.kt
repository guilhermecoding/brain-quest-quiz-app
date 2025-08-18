package com.example.brainquest.ui.auth.components

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Email
import androidx.compose.material.icons.rounded.Lock
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.brainquest.ui.components.StyledOutlinedTextField
import com.example.brainquest.ui.theme.BrainQuestTheme

@Composable
fun SingUpContainer(
    valueEmail: String,
    onValueChangeEmail: (String) -> Unit,
    isEmailFieldError: Boolean,
    onEmailFocusChanged: (Boolean) -> Unit,
    valuePassword: String,
    @SuppressLint("ModifierParameter") modifier: Modifier = Modifier
) {
    var passwordConfirmed by remember { mutableStateOf("") }

    Box(
        modifier = modifier
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = "Hora de criar uma conta",
                style = MaterialTheme.typography.titleLarge.copy(
                    fontWeight = FontWeight.Bold,
                    fontSize = 28.sp
                ),
                textAlign = TextAlign.Center,
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(Modifier.height(32.dp))

            StyledOutlinedTextField(
                value = valueEmail,
                onValueChange = onValueChangeEmail,
                modifier = Modifier
                    .fillMaxWidth()
                    .onFocusChanged { focusState ->
                        onEmailFocusChanged(focusState.isFocused)
                    },
                label = "Email",
                placeholder = "Primeiro, coloque seu email",
                leadingIcon = {
                    Icon(
                        imageVector = Icons.Rounded.Email,
                        contentDescription = "Icone de email"
                    )
                },
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Email,
                    imeAction = ImeAction.Next
                ),
                isError = isEmailFieldError,
                supportingText = null
            )

            Spacer(Modifier.height(16.dp))

            // -- SENHA --
            StyledOutlinedTextField(
                value = valuePassword,
                onValueChange = onValueChangePassword,
                modifier = Modifier.fillMaxWidth(),
                label = "Criar senha",
                placeholder = "Agora, crie uma senha forte",
                leadingIcon = {
                    Icon(
                        imageVector = Icons.Rounded.Lock,
                        contentDescription = "Icone de cadeado"
                    )
                },
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Password,
                    imeAction = ImeAction.Done // Ação "Done" aqui, pois o botão está logo abaixo
                ),
                visualTransformation = PasswordVisualTransformation(),
                // Você precisará de isPasswordFieldError para melhor controle
                isError = generalErrorMessage != null && !isEmailFieldError && valuePassword.isNotEmpty(),
                supportingText = null
            )

            Spacer(Modifier.height(16.dp))

            // -- CONFIRMAR SENHA --
            StyledOutlinedTextField(
                value = passwordConfirmed,
                onValueChange = onValueChangePassword,
                modifier = Modifier.fillMaxWidth(),
                label = "Confirmar senha",
                placeholder = "Digite a mesma senha para confirmar",
                leadingIcon = {
                    Icon(
                        imageVector = Icons.Rounded.Lock,
                        contentDescription = "Icone de cadeado"
                    )
                },
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Password,
                    imeAction = ImeAction.Done // Ação "Done" aqui, pois o botão está logo abaixo
                ),
                visualTransformation = PasswordVisualTransformation(),
                // Você precisará de isPasswordFieldError para melhor controle
                isError = generalErrorMessage != null && !isEmailFieldError && valuePassword.isNotEmpty(),
                supportingText = null
            )

            // Exibe a mensagem de erro geral, se houver
            if (generalErrorMessage != null) {
                Spacer(Modifier.height(20.dp))
                Text(
                    text = generalErrorMessage,
                    color = MaterialTheme.colorScheme.error,
                    modifier = Modifier.fillMaxWidth(),
                    textAlign = TextAlign.Center,
                    style = MaterialTheme.typography.bodySmall.copy(
                        fontSize = 16.sp
                    )
                )
            }
        }
    }
}


// --- Previews Atualizadas ---
@Preview(showBackground = true, widthDp = 360)
@Composable
private fun SingUpContainerPreview_NoError() {
    BrainQuestTheme {
        SingUpContainer(
            valueEmail = "test@example.com",
            onValueChangeEmail = {},
            isEmailFieldError = false,
            onEmailFocusChanged = {},
            valuePassword = "password",
            onValueChangePassword = {},
            generalErrorMessage = null,
            modifier = Modifier.padding(16.dp)
        )
    }
}

@Preview(showBackground = true, widthDp = 360)
@Composable
private fun SingUpContainerPreview_EmailFormatError() {
    BrainQuestTheme {
        SingUpContainer(
            valueEmail = "test@",
            onValueChangeEmail = {},
            isEmailFieldError = true,
            onEmailFocusChanged = {},
            valuePassword = "password",
            onValueChangePassword = {},
            generalErrorMessage = "Formato de e-mail inválido.",
            modifier = Modifier.padding(16.dp)
        )
    }
}

@Preview(showBackground = true, widthDp = 360)
@Composable
private fun SingUpContainerPreview_AuthErrorAndButtonDisabled() {
    BrainQuestTheme {
        SingUpContainer(
            valueEmail = "test@example.com",
            onValueChangeEmail = {},
            isEmailFieldError = false,
            onEmailFocusChanged = {},
            valuePassword = "wrongpassword",
            onValueChangePassword = {},
            generalErrorMessage = "Usuário ou senha inválidos.",
            modifier = Modifier.padding(16.dp)
        )
    }
}

