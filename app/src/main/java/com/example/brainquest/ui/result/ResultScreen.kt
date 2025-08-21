package com.example.brainquest.ui.result

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.brainquest.ui.theme.AppDimens
import com.example.brainquest.ui.theme.PrimaryTextColor
import com.example.brainquest.ui.theme.PurpleTheme
import com.example.brainquest.ui.theme.YellowTheme
import androidx.compose.runtime.getValue
import com.airbnb.lottie.compose.LottieAnimation
import com.airbnb.lottie.compose.LottieCompositionSpec
import com.airbnb.lottie.compose.LottieConstants
import com.airbnb.lottie.compose.rememberLottieComposition
import com.example.brainquest.R

@Composable
fun ResultScreen(
    score: Int,
    totalQuestions: Int,
    onNavigateHome: () -> Unit
) {
    val composition by rememberLottieComposition(
        spec = LottieCompositionSpec.RawRes(R.raw.congrats)
    )

    Scaffold { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .padding(32.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {


            LottieAnimation(
                composition = composition,
                iterations = 1,
                modifier = Modifier.size(250.dp)
            )

            Spacer(modifier = Modifier.height(16.dp))

            Text(
                text = "Sua Pontuação",
                style = MaterialTheme.typography.titleLarge.copy(
                    fontSize = 16.sp
                )
            )

            Text(
                text = "$score/$totalQuestions",
                style = MaterialTheme.typography.displayMedium.copy(
                    fontWeight = FontWeight.ExtraBold,
                    fontSize = 46.sp,
                    color = PurpleTheme
                )
            )

            Spacer(modifier = Modifier.height(16.dp))

            Text(
                text = "Parabéns!",
                style = MaterialTheme.typography.displaySmall.copy(
                    fontWeight = FontWeight.Bold,
                    fontSize = 58.sp,
                    color = PurpleTheme
                )
            )

            Spacer(modifier = Modifier.height(4.dp))

            Text(
                text = "Que show! Seu quiz foi concluido com êxito!",
                style = MaterialTheme.typography.headlineSmall.copy(
                    color = Color.Black.copy(alpha = 0.7f),
                    fontSize = 18.sp,
                    textAlign = TextAlign.Center
                )
            )

            Spacer(modifier = Modifier.height(48.dp))
            Button(
                onClick = onNavigateHome,
                shape = RoundedCornerShape(50.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = YellowTheme,
                    contentColor = PrimaryTextColor
                ),
                border = BorderStroke(AppDimens.DefaultBorderWidth.width, AppDimens.DefaultBorderWidth.color),
                contentPadding = PaddingValues(horizontal = 40.dp, vertical = 16.dp)
            ) {
                Text(
                    text = "Voltar para o Início",
                    style = MaterialTheme.typography.titleMedium.copy(
                        fontWeight = FontWeight.Bold,
                        fontSize = 18.sp
                    )
                )
            }
        }
    }
}