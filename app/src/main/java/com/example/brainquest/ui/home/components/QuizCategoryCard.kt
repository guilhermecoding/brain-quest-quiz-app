package com.example.brainquest.ui.home

// ... (todas as suas outras importações)
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
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

// Adicione este Composable ao seu arquivo
@Composable
fun QuizCategoryCard(
    modifier: Modifier = Modifier,
    categoryName: String,
    questionCount: Int,
    progress: Float,
    illustration: ImageVector, // Usando um ícone como placeholder
    onStartQuiz: () -> Unit
) {
    Card(
        modifier = modifier
            .width(240.dp) // Largura definida para o card
            .height(280.dp), // Altura definida para o card
        shape = RoundedCornerShape(24.dp),
        colors = CardDefaults.cardColors(
            containerColor = Color(0xFF6A6AEE) // Cor roxa de fundo do card
        )
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(20.dp)
        ) {
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
                text = "$questionCount questions",
                style = MaterialTheme.typography.bodyMedium.copy(
                    color = Color.White.copy(alpha = 0.7f)
                )
            )

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
                Button(
                    onClick = onStartQuiz,
                    modifier = Modifier.weight(1f),
                    shape = RoundedCornerShape(16.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color(0xFFFDD835) // Cor amarela do botão
                    )
                ) {
                    Text(
                        text = "Bora!",
                        color = Color.Black,
                        fontWeight = FontWeight.Bold
                    )
                }

                Spacer(modifier = Modifier.width(16.dp))

                Box(
                    modifier = Modifier
                        .weight(1f)
                        .fillMaxHeight(),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        imageVector = illustration,
                        contentDescription = null,
                        tint = Color.White.copy(alpha = 0.5f),
                        modifier = Modifier.size(80.dp)
                    )
                }
            }
        }
    }
}