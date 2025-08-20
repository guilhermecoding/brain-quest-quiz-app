package com.example.brainquest.ui.home

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Slider
import androidx.compose.material3.SliderDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.brainquest.ui.theme.AppDimens
import com.example.brainquest.ui.theme.PrimaryTextColor
import com.example.brainquest.ui.theme.PurpleTheme
import com.example.brainquest.ui.theme.YellowTheme

@Composable
fun QuizCategoryCard(
    modifier: Modifier = Modifier,
    categoryName: String,
    questionCount: Int,
    progress: Float,
    illustration: ImageVector,
    onStartQuiz: () -> Unit
) {
    Card(
        modifier = modifier
            .width(360.dp)
            .height(200.dp)
            .border(width = 1.dp, color = Color.Black, shape = RoundedCornerShape(24.dp)),
        shape = RoundedCornerShape(24.dp),
        colors = CardDefaults.cardColors(
            containerColor = PurpleTheme
        )
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(20.dp)
        ) {
            Row {
                // Placeholder para a Ilustração
                Icon(
                    imageVector = illustration,
                    contentDescription = null,
                    tint = Color.White,
                    modifier = Modifier.size(36.dp)
                )

                Spacer(modifier = Modifier.width(16.dp))

                Column {
                    // Título da Categoria
                    Text(
                        text = categoryName,
                        style = MaterialTheme.typography.headlineSmall.copy(
                            fontWeight = FontWeight.Bold,
                            color = Color.White
                        )
                    )
                    // Número de Questões
                    Text(
                        text = "$questionCount questões",
                        style = MaterialTheme.typography.bodyMedium.copy(
                            color = Color.White.copy(alpha = 0.7f)
                        )
                    )
                }
            }

            Spacer(modifier = Modifier.height(12.dp))

            // Barra de Progresso Estilizada (usando um Slider)
            Slider(
                value = progress,
                onValueChange = {}, // Deixamos vazio pois é apenas para exibição
                enabled = false, // Desabilitamos a interação do usuário
                colors = SliderDefaults.colors(
                    disabledThumbColor = Color.White, // Cor da "bolinha"
                    disabledActiveTrackColor = Color.White, // Cor da parte preenchida
                    disabledInactiveTrackColor = Color.White.copy(alpha = 0.3f) // Cor do fundo
                )
            )

            Spacer(modifier = Modifier.weight(1f)) // Empurra o conteúdo abaixo para o final

            // Seção inferior com o botão e a ilustração
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                OutlinedButton (
                    onClick = onStartQuiz,
                    modifier = Modifier.weight(1f),
                    shape = RoundedCornerShape(50.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = YellowTheme,
                        contentColor = PrimaryTextColor
                    ),
                    border = BorderStroke(AppDimens.DefaultBorderWidth.width, AppDimens.DefaultBorderWidth.color),
                    contentPadding = PaddingValues(horizontal = 40.dp, vertical = 16.dp)
                ) {
                    Text(
                        text = "Bora lá!!",
                        style = MaterialTheme.typography.titleMedium.copy(
                            fontWeight = FontWeight.Bold,
                            fontSize = 18.sp
                        )
                    )
                }

                Spacer(modifier = Modifier.width(16.dp))
            }
        }
    }
}