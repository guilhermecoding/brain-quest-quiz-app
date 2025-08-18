package com.example.brainquest.ui.auth


import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
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
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Email
import androidx.compose.material.icons.rounded.Lock
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.brainquest.ui.components.StyledOutlinedTextField
import com.example.brainquest.ui.theme.AppDimens
import com.example.brainquest.ui.theme.PrimaryTextColor
import com.example.brainquest.ui.theme.PurpleTheme
import com.example.brainquest.ui.theme.YellowTheme

// "Container Inteligente": obtém o ViewModel e o estado.
@Composable
fun AuthScreen(
    viewModel: AuthViewModel = hiltViewModel(),
    // onLoginSuccess: () -> Unit // Callback para navegar para a próxima tela
) {
    val state by viewModel.uiState.collectAsState()

    if (state.loginSuccess) {
        // onLoginSuccess()
    }

    AuthScreenContent(
        state = state,
        onEmailChange = viewModel::onEmailChange,
        onPasswordChange = viewModel::onPasswordChange,
        onConfirmPasswordChange = viewModel::onConfirmPasswordChange,
        onEmailFocusChanged = viewModel::onEmailFocusChanged,
        onLoginClick = viewModel::attemptLogin,
        onSignUpClick = viewModel::attemptSignUp,
        onToggleScreen = viewModel::toggleScreen
    )
}

// "Composable Burro": Apenas exibe o que recebe. Não tem lógica própria.
@Composable
fun AuthScreenContent(
    state: AuthState,
    onEmailChange: (String) -> Unit,
    onPasswordChange: (String) -> Unit,
    onConfirmPasswordChange: (String) -> Unit,
    onEmailFocusChanged: () -> Unit,
    onLoginClick: () -> Unit,
    onSignUpClick: () -> Unit,
    onToggleScreen: () -> Unit
) {
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
                        if (state.isLoginScreen) {
                            LoginContainer(
                                state = state,
                                onEmailChange = onEmailChange,
                                onPasswordChange = onPasswordChange,
                                onEmailFocusChanged = onEmailFocusChanged
                            )
                        } else {
                            SignUpContainer(
                                state = state,
                                onEmailChange = onEmailChange,
                                onPasswordChange = onPasswordChange,
                                onConfirmPasswordChange = onConfirmPasswordChange,
                                onEmailFocusChanged = onEmailFocusChanged
                            )
                        }

                        Spacer(Modifier.height(42.dp))

                        OutlinedButton(
                            onClick = { if (state.isLoginScreen) onLoginClick() else onSignUpClick() },
                            enabled = !state.isLoading,
                            modifier = Modifier.fillMaxWidth(0.8f),
                            colors = ButtonDefaults.buttonColors(
                                containerColor = YellowTheme,
                                contentColor = PrimaryTextColor
                            ),
                            border = BorderStroke(AppDimens.DefaultBorderWidth.width, AppDimens.DefaultBorderWidth.color),
                            contentPadding = PaddingValues(horizontal = 40.dp, vertical = 16.dp)
                        ) {
                            if (state.isLoading) {
                                CircularProgressIndicator(modifier = Modifier.size(24.dp), color = PrimaryTextColor, strokeWidth = 2.dp)
                            } else {
                                Text(
                                    text = if (state.isLoginScreen) "ENTRAR" else "CADASTRAR",
                                    style = MaterialTheme.typography.titleMedium.copy(
                                        fontWeight = FontWeight.Bold,
                                        fontSize = 18.sp
                                    )
                                )
                            }
                        }

                        Spacer(Modifier.height(16.dp))

                        TextButton(
                            onClick = onToggleScreen,
                            modifier = Modifier.fillMaxWidth(0.8f)
                        ) {
                            Text(
                                text = if (state.isLoginScreen) "Não tem uma conta? Cadastre-se" else "Já tem uma conta? Faça login",
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

@Composable
fun LoginContainer(
    state: AuthState,
    onEmailChange: (String) -> Unit,
    onPasswordChange: (String) -> Unit,
    onEmailFocusChanged: () -> Unit,
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier.fillMaxWidth(0.8f)
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

        Spacer(Modifier.height(48.dp))

        StyledOutlinedTextField(
            value = state.emailValue,
            onValueChange = onEmailChange,
            modifier = Modifier
                .fillMaxWidth()
                .onFocusChanged { focusState ->
                    if (!focusState.isFocused && state.emailValue.isNotBlank()) {
                        onEmailFocusChanged()
                    }
                },
            label = "Email",
            placeholder = "Aqui vai o seu email",
            leadingIcon = { Icon(imageVector = Icons.Rounded.Email, contentDescription = "Icone de email") },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email, imeAction = ImeAction.Next),
            isError = state.isEmailFormatError,
            supportingText = null
        )

        Spacer(Modifier.height(16.dp))

        StyledOutlinedTextField(
            value = state.passwordValue,
            onValueChange = onPasswordChange,
            modifier = Modifier.fillMaxWidth(),
            label = "Senha",
            placeholder = "Aqui vai a sua senha",
            leadingIcon = { Icon(imageVector = Icons.Rounded.Lock, contentDescription = "Icone de cadeado") },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password, imeAction = ImeAction.Done),
            visualTransformation = PasswordVisualTransformation(),
            isError = state.generalErrorMessage != null && !state.isEmailFormatError,
            supportingText = null
        )

        if (state.generalErrorMessage != null) {
            Spacer(Modifier.height(20.dp))
            Text(
                text = state.generalErrorMessage,
                color = MaterialTheme.colorScheme.error,
                modifier = Modifier.fillMaxWidth(),
                textAlign = TextAlign.Center,
                style = MaterialTheme.typography.bodySmall.copy(fontSize = 16.sp)
            )
        }
    }
}


@Composable
fun SignUpContainer(
    state: AuthState,
    onEmailChange: (String) -> Unit,
    onPasswordChange: (String) -> Unit,
    onConfirmPasswordChange: (String) -> Unit,
    onEmailFocusChanged: () -> Unit,
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier.fillMaxWidth(0.8f)
    ) {
        Text(
            text = "Hora de criar uma conta",
            style = MaterialTheme.typography.titleLarge.copy(fontWeight = FontWeight.Bold, fontSize = 28.sp),
            textAlign = TextAlign.Center,
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(Modifier.height(32.dp))

        StyledOutlinedTextField(
            value = state.emailValue,
            onValueChange = onEmailChange,
            modifier = Modifier.fillMaxWidth().onFocusChanged { focusState ->
                if (!focusState.isFocused && state.emailValue.isNotBlank()) { onEmailFocusChanged() }
            },
            label = "Email",
            placeholder = "Primeiro, coloque seu email",
            leadingIcon = { Icon(imageVector = Icons.Rounded.Email, contentDescription = "Icone de email") },
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Email,
                imeAction = ImeAction.Next
            ),
            isError = state.isEmailFormatError,
            supportingText = null
        )

        Spacer(Modifier.height(16.dp))

        StyledOutlinedTextField(
            value = state.passwordValue,
            onValueChange = onPasswordChange,
            modifier = Modifier.fillMaxWidth(),
            label = "Criar senha",
            placeholder = "Agora, crie uma senha forte",
            leadingIcon = { Icon(imageVector = Icons.Rounded.Lock, contentDescription = "Icone de cadeado") },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password, imeAction = ImeAction.Next),
            visualTransformation = PasswordVisualTransformation(),
            isError = false, // Lógica de erro de senha aqui
            supportingText = null
        )

        Spacer(Modifier.height(16.dp))

        StyledOutlinedTextField(
            value = state.confirmPasswordValue,
            onValueChange = onConfirmPasswordChange,
            modifier = Modifier.fillMaxWidth(),
            label = "Confirmar senha",
            placeholder = "Digite a mesma senha para confirmar",
            leadingIcon = { Icon(imageVector = Icons.Rounded.Lock, contentDescription = "Icone de cadeado") },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password, imeAction = ImeAction.Done),
            visualTransformation = PasswordVisualTransformation(),
            isError = false, // Lógica de erro de confirmação de senha aqui
            supportingText = null
        )

        if (state.generalErrorMessage != null) {
            Spacer(Modifier.height(20.dp))
            Text(
                text = state.generalErrorMessage,
                color = MaterialTheme.colorScheme.error,
                modifier = Modifier.fillMaxWidth(),
                textAlign = TextAlign.Center,
                style = MaterialTheme.typography.bodySmall.copy(fontSize = 16.sp)
            )
        }
    }
}