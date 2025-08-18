package com.example.brainquest.ui.components

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape // Manteve para a forma
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.brainquest.ui.theme.BrainQuestTheme
import com.example.brainquest.ui.theme.GreyTheme
import com.example.brainquest.ui.theme.PrimaryTextColor
import com.example.brainquest.ui.theme.PurpleTheme

/**
 * Campo de texto personalisado.
 */
@OptIn(ExperimentalMaterial3Api::class) // Para OutlinedTextFieldDefaults
@Composable
fun StyledOutlinedTextField( // Nome alterado para refletir o tipo
    value: String,
    onValueChange: (String) -> Unit,
    modifier: Modifier = Modifier,
    label: String? = null,
    placeholder: String? = null,
    leadingIcon: @Composable (() -> Unit)? = null,
    trailingIcon: @Composable (() -> Unit)? = null,
    isError: Boolean = false,
    supportingText: @Composable (() -> Unit)? = null,
    visualTransformation: VisualTransformation = VisualTransformation.None,
    keyboardOptions: androidx.compose.foundation.text.KeyboardOptions = androidx.compose.foundation.text.KeyboardOptions.Default,
    keyboardActions: androidx.compose.foundation.text.KeyboardActions = androidx.compose.foundation.text.KeyboardActions.Default,
    singleLine: Boolean = false,
    maxLines: Int = if (singleLine) 1 else Int.MAX_VALUE,
    minLines: Int = 1,
    readOnly: Boolean = false,
    enabled: Boolean = true
) {
    // Usando OutlinedTextFieldDefaults para cores específicas do OutlinedTextField
    val outlinedTextFieldColors = OutlinedTextFieldDefaults.colors(
        // === Cores do Container (Fundo do Campo) ===
        // Para OutlinedTextField, o fundo é geralmente transparente por padrão,
        // mas você pode configurá-lo se necessário.
        focusedContainerColor = GreyTheme,
        unfocusedContainerColor = GreyTheme,
        disabledContainerColor = Color.Transparent, // Ou uma cor levemente acinzentada se preferir
        errorContainerColor = GreyTheme,    // Ou uma cor de erro sutil para o fundo

        // === CORES DA BORDA DO CONTORNO ===
        focusedBorderColor = PurpleTheme,    // Cor da borda quando focado
        unfocusedBorderColor = Color.Gray,                         // Cor da borda quando não focado
        disabledBorderColor = Color.LightGray.copy(alpha = 0.7f),  // Cor da borda quando desabilitado
        errorBorderColor = MaterialTheme.colorScheme.error,        // Cor da borda quando em erro

        // === Cores do Texto (do input) ===
        // Geralmente herdado do textStyle ou tema, mas pode ser sobrescrito:
        // focusedTextColor = MaterialTheme.colorScheme.onSurface,
        // unfocusedTextColor = MaterialTheme.colorScheme.onSurface,

        // === Cores do Cursor ===
        cursorColor = MaterialTheme.colorScheme.primary,

        // === Cores do Label ===
        focusedLabelColor = PrimaryTextColor,
        unfocusedLabelColor = MaterialTheme.colorScheme.onSurfaceVariant, // Cor padrão para label não focado
        errorLabelColor = MaterialTheme.colorScheme.error,
        // disabledLabelColor = ...,

        // === Cores do Placeholder ===
        focusedPlaceholderColor = Color.Gray,
        unfocusedPlaceholderColor = Color.LightGray,
        // errorPlaceholderColor = ...,
        // disabledPlaceholderColor = ...,

        // === Cores do Texto de Suporte ===
        focusedSupportingTextColor = MaterialTheme.colorScheme.onSurfaceVariant,
        unfocusedSupportingTextColor = MaterialTheme.colorScheme.onSurfaceVariant,
        errorSupportingTextColor = MaterialTheme.colorScheme.error,
        // disabledSupportingTextColor = ...,

        // === Cores dos Ícones ===
        focusedLeadingIconColor = MaterialTheme.colorScheme.onSurfaceVariant,
        unfocusedLeadingIconColor = MaterialTheme.colorScheme.onSurfaceVariant,
        disabledLeadingIconColor = MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = 0.5f),
        errorLeadingIconColor = MaterialTheme.colorScheme.error,
        // O mesmo para trailingIcon...
        focusedTrailingIconColor = MaterialTheme.colorScheme.onSurfaceVariant,
        unfocusedTrailingIconColor = MaterialTheme.colorScheme.onSurfaceVariant,
        disabledTrailingIconColor = MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = 0.5f),
        errorTrailingIconColor = MaterialTheme.colorScheme.error
    )

    OutlinedTextField( // Alterado de TextField para OutlinedTextField
        value = value,
        onValueChange = onValueChange,
        modifier = modifier.fillMaxWidth(), // Padrão para ocupar a largura
        label = label?.let { { Text(it) } },
        placeholder = placeholder?.let { { Text(it) } },
        leadingIcon = leadingIcon,
        trailingIcon = trailingIcon,
        isError = isError,
        supportingText = supportingText,
        visualTransformation = visualTransformation,
        keyboardOptions = keyboardOptions,
        keyboardActions = keyboardActions,
        singleLine = singleLine,
        maxLines = maxLines,
        minLines = minLines,
        readOnly = readOnly,
        enabled = enabled,
        textStyle = TextStyle( // Estilo de texto padrão para o texto digitado
            color = MaterialTheme.colorScheme.onSurface, // Cor do texto principal
            fontSize = MaterialTheme.typography.bodyLarge.fontSize
            // fontFamily = SuaFontePersonalizada, // Se tiver uma fonte específica
        ),
        shape = RoundedCornerShape(8.dp), // Forma do contorno e do fundo
        colors = outlinedTextFieldColors   // Aplicando suas cores padronizadas para OutlinedTextField
    )
}


@Preview(showBackground = true)
@Composable
private fun StyledTextFieldPreview() {
    BrainQuestTheme {
        StyledOutlinedTextField(
            value = "",
            onValueChange = {},
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            label = "Email",
            placeholder = "Digite seu email",
        )
    }
}

