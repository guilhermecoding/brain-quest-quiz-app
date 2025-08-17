package com.example.brainquest.ui.auth.components

import android.annotation.SuppressLint
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Email
import androidx.compose.material.icons.rounded.Lock
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
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
import com.example.brainquest.ui.theme.AppDimens
import com.example.brainquest.ui.theme.BrainQuestTheme
import com.example.brainquest.ui.theme.PrimaryTextColor
import com.example.brainquest.ui.theme.YellowTheme

@Composable
fun LoginContainer(
    valueEmail: String,
    onValueChangeEmail: (String) -> Unit,
    isEmailFieldError: Boolean,
    onEmailFocusChanged: (Boolean) -> Unit,
    valuePassword: String,
    onValueChangePassword: (String) -> Unit,
    generalErrorMessage: String?,
    onLoginClick: () -> Unit,
    isLoginButtonEnabled: Boolean = true,
    @SuppressLint("ModifierParameter") modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = "Vamos fazer login?",
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
                placeholder = "Aqui vai o seu email",
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

            StyledOutlinedTextField(
                value = valuePassword,
                onValueChange = onValueChangePassword,
                modifier = Modifier.fillMaxWidth(),
                label = "Senha",
                placeholder = "Aqui vai a sua senha",
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

            Spacer(Modifier.height(42.dp))

            OutlinedButton(
                onClick = onLoginClick,
                enabled = isLoginButtonEnabled,
                modifier = Modifier.fillMaxWidth(),
                colors = ButtonDefaults.buttonColors(
                    containerColor = YellowTheme,
                    contentColor = PrimaryTextColor
                ),
                border = BorderStroke(AppDimens.DefaultBorderWidth.width, AppDimens.DefaultBorderWidth.color),
                contentPadding = PaddingValues(horizontal = 40.dp, vertical = 16.dp)
            ) {
                Text(
                    text = "ENTRAR",
                    style = MaterialTheme.typography.titleMedium.copy(
                        fontWeight = FontWeight.Bold,
                        fontSize = 18.sp
                    )
                )
            }
        }
    }
}


// --- Previews Atualizadas ---
@Preview(showBackground = true, widthDp = 360)
@Composable
private fun LoginContainerPreview_NoError() {
    BrainQuestTheme {
        LoginContainer(
            valueEmail = "test@example.com",
            onValueChangeEmail = {},
            isEmailFieldError = false,
            onEmailFocusChanged = {},
            valuePassword = "password",
            onValueChangePassword = {},
            generalErrorMessage = null,
            onLoginClick = {},
            isLoginButtonEnabled = true,
            modifier = Modifier.padding(16.dp)
        )
    }
}

@Preview(showBackground = true, widthDp = 360)
@Composable
private fun LoginContainerPreview_EmailFormatError() {
    BrainQuestTheme {
        LoginContainer(
            valueEmail = "test@",
            onValueChangeEmail = {},
            isEmailFieldError = true,
            onEmailFocusChanged = {},
            valuePassword = "password",
            onValueChangePassword = {},
            generalErrorMessage = "Formato de e-mail inválido.",
            onLoginClick = {},
            isLoginButtonEnabled = true, // Geralmente o botão ainda estaria habilitado
            modifier = Modifier.padding(16.dp)
        )
    }
}

@Preview(showBackground = true, widthDp = 360)
@Composable
private fun LoginContainerPreview_AuthErrorAndButtonDisabled() {
    BrainQuestTheme {
        LoginContainer(
            valueEmail = "test@example.com",
            onValueChangeEmail = {},
            isEmailFieldError = false,
            onEmailFocusChanged = {},
            valuePassword = "wrongpassword",
            onValueChangePassword = {},
            generalErrorMessage = "Usuário ou senha inválidos.",
            onLoginClick = {},
            isLoginButtonEnabled = false, // Exemplo com botão desabilitado durante um erro/loading
            modifier = Modifier.padding(16.dp)
        )
    }
}

