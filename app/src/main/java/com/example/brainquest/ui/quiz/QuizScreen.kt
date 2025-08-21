package com.example.brainquest.ui.quiz

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
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
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.rounded.ArrowBackIos
import androidx.compose.material.icons.rounded.ChevronLeft
import androidx.compose.material.icons.rounded.ChevronRight
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.brainquest.R
import com.example.brainquest.ui.theme.AppDimens
import com.example.brainquest.ui.theme.PrimaryTextColor
import com.example.brainquest.ui.theme.PurpleTheme
import com.example.brainquest.ui.theme.YellowTheme

@Composable
fun QuizScreen(
    viewModel: QuizViewModel = hiltViewModel(),
    onNavigateBack: () -> Unit,
    onQuizFinished: (score: Int, total: Int) -> Unit
) {
    val state by viewModel.uiState.collectAsState()
    val currentQuestion = state.currentQuestion

    LaunchedEffect(key1 = state.quizFinished) {
        if (state.quizFinished) {
            onQuizFinished(state.finalScore, state.totalQuestions)
        }
    }

    Scaffold(
        containerColor = PurpleTheme,
        topBar = {
            QuizTopBar(
                title = state.quizTitle,
                onBackClicked = onNavigateBack
            )
        },
    ) { innerPadding ->
        when {
            state.isLoading -> {
                Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                    CircularProgressIndicator(color = Color.White)
                }
            }
            state.errorMessage != null -> {
                Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                    Text(text = "Erro: ${state.errorMessage}", color = Color.White)
                }
            }
            currentQuestion != null -> {
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(innerPadding)
                        .padding(horizontal = 24.dp, vertical = 16.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Spacer(modifier = Modifier.height(24.dp))

                    QuizProgressIndicator(
                        totalQuestions = state.totalQuestions,
                        currentQuestionIndex = state.currentQuestionIndex
                    )
                    Spacer(modifier = Modifier.height(24.dp))


                    Box(contentAlignment = Alignment.Center) {
                        Image(painter = painterResource(id = R.drawable.avatar), contentDescription = null, modifier = Modifier.height(120.dp))
                        Text(text = state.timerText, color = Color.Black, modifier = Modifier.align(Alignment.TopEnd).background(Color(0xFFFFC107), RoundedCornerShape(12.dp)).padding(horizontal = 12.dp, vertical = 4.dp))
                    }

                    Spacer(modifier = Modifier.height(24.dp))

                    Text(
                        text = currentQuestion.enunciado,
                        style = MaterialTheme.typography.headlineSmall.copy(
                            color = Color.White,
                            fontSize = 32.sp,
                            fontWeight = FontWeight.Bold
                        )
                    )
                    Spacer(modifier = Modifier.height(52.dp))
                    Text(
                        text = "Escolha uma opção",
                        color = Color.White.copy(alpha = 0.7f),
                        style = MaterialTheme.typography.bodyLarge
                    )
                    Spacer(modifier = Modifier.height(32.dp))

                    AnswerGrid(
                        options = currentQuestion.opcoes,
                        selectedAnswerIndex = state.selectedAnswerIndex,
                        onAnswerSelected = viewModel::onAnswerSelected
                    )

                    Spacer(modifier = Modifier.weight(1f))

                    QuizBottomBar(
                        onPreviousClicked = viewModel::onPreviousClicked,
                        onNextClicked = viewModel::onNextClicked,
                        currentQuestionIndex = state.currentQuestionIndex,
                        // ✅ 3. Passe o estado de ativação para o botão "Próximo"
                        isNextEnabled = state.selectedAnswerIndex != null
                    )
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun QuizTopBar(title: String, onBackClicked: () -> Unit) {
    TopAppBar(
        title = { Text(text = title, color = Color.White, fontWeight = FontWeight.Bold, modifier = Modifier.fillMaxWidth(), textAlign = TextAlign.Center) },
        navigationIcon = {
            Box(
                modifier = Modifier
                    .size(40.dp)
                    .border(
                        width = 1.dp,
                        color = Color.White.copy(alpha = 0.5f), // Borda branca com 50% de transparência
                        shape = CircleShape
                    ),
                contentAlignment = Alignment.Center,
            ) {
                IconButton(onClick = onBackClicked) {
                    Icon(
                        imageVector = Icons.AutoMirrored.Rounded.ArrowBackIos,
                        contentDescription = "Voltar",
                        tint = Color.White,
                        modifier = Modifier.size(20.dp) // Ícone um pouco menor que o círculo
                    )
                }
            }
        },
        colors = TopAppBarDefaults.topAppBarColors(containerColor = Color.Transparent),
        modifier = Modifier.padding(start = 12.dp)
    )
}

@Composable
fun QuizProgressIndicator(totalQuestions: Int, currentQuestionIndex: Int) {
    Column {
        Text(text = "Questão ${currentQuestionIndex + 1}", color = Color.White.copy(alpha = 0.7f))
        Spacer(modifier = Modifier.height(8.dp))
        Row(horizontalArrangement = Arrangement.spacedBy(4.dp)) {
            repeat(totalQuestions) { index ->
                val color = if (index <= currentQuestionIndex) Color.White else Color.White.copy(alpha = 0.3f)
                Box(modifier = Modifier
                    .weight(1f)
                    .height(4.dp)
                    .background(color, RoundedCornerShape(2.dp)))
            }
        }
    }
}

@Composable
fun AnswerGrid(
    options: List<String>,
    selectedAnswerIndex: Int?,
    onAnswerSelected: (Int) -> Unit
) {
    // Uma coluna principal para as duas linhas de botões
    Column(verticalArrangement = Arrangement.spacedBy(16.dp)) {
        // Primeira linha de botões (A e B)
        Row(horizontalArrangement = Arrangement.spacedBy(16.dp)) {
            // Botão A
            AnswerOption(
                modifier = Modifier.weight(1f), // Ocupa metade do espaço
                text = options.getOrNull(0) ?: "",
                isSelected = selectedAnswerIndex == 0,
                index = 0,
                onClick = { onAnswerSelected(0) }
            )
            // Botão B
            AnswerOption(
                modifier = Modifier.weight(1f), // Ocupa a outra metade
                text = options.getOrNull(1) ?: "",
                isSelected = selectedAnswerIndex == 1,
                index = 1,
                onClick = { onAnswerSelected(1) }
            )
        }

        // Segunda linha de botões (C e D)
        Row(horizontalArrangement = Arrangement.spacedBy(16.dp)) {
            // Botão C
            AnswerOption(
                modifier = Modifier.weight(1f),
                text = options.getOrNull(2) ?: "",
                isSelected = selectedAnswerIndex == 2,
                index = 2,
                onClick = { onAnswerSelected(2) }
            )
            // Botão D
            AnswerOption(
                modifier = Modifier.weight(1f),
                text = options.getOrNull(3) ?: "",
                isSelected = selectedAnswerIndex == 3,
                index = 3,
                onClick = { onAnswerSelected(3) }
            )
        }
    }
}

@Composable
fun AnswerOption(
    modifier: Modifier = Modifier, // ✅ Adicionado para flexibilidade
    text: String,
    isSelected: Boolean,
    index: Int,
    onClick: () -> Unit
) {
    val backgroundColor = if (isSelected) YellowTheme else Color.White
    val border = if (isSelected) BorderStroke(2.dp, Color.Black) else BorderStroke(1.dp, Color.Black)
    val prefix = ('A' + index)

    Button(
        onClick = onClick,
        modifier = modifier.height(60.dp),
        shape = RoundedCornerShape(50),
        colors = ButtonDefaults.buttonColors(containerColor = backgroundColor),
        border = border
    ) {
        Text(text = "$prefix. $text", color = Color.Black, fontWeight = FontWeight.Bold, fontSize = 20.sp)
    }
}

@Composable
fun QuizBottomBar(
    onPreviousClicked: () -> Unit,
    onNextClicked: () -> Unit,
    currentQuestionIndex: Int,
    isNextEnabled: Boolean
) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        // --- BOTÃO ANTERIOR (CORRIGIDO) ---
        OutlinedButton(
            onClick = onPreviousClicked, // ✅ 1. Ação correta
            enabled = currentQuestionIndex > 0, // ✅ 2. Lógica de ativação correta
            shape = RoundedCornerShape(50.dp),
            colors = ButtonDefaults.outlinedButtonColors(
                contentColor = Color.White,
                disabledContentColor = Color.White.copy(alpha = 0.3f)
            ),
            border = BorderStroke(
                width = AppDimens.DefaultBorderWidth.width,
                color = if (currentQuestionIndex > 0) Color.White else Color.White.copy(alpha = 0.3f)
            ),
            contentPadding = PaddingValues(horizontal = 24.dp, vertical = 16.dp)
        ) {
            Icon(
                Icons.Rounded.ChevronLeft,
                contentDescription = "Seta para a esquerda",
                modifier = Modifier.size(32.dp)
            )
            Spacer(Modifier.width(12.dp))
            Text("Anterior", fontSize = 20.sp)
        }

        // --- BOTÃO PRÓXIMO (CORRIGIDO) ---
        OutlinedButton(
            onClick = onNextClicked,
            enabled = isNextEnabled, // ✅ 3. Lógica de ativação adicionada
            shape = RoundedCornerShape(50.dp),
            colors = ButtonDefaults.buttonColors(
                // Muda a cor do container quando desativado
                containerColor = Color.White,
                contentColor = PrimaryTextColor,
                disabledContainerColor = Color.White.copy(alpha = 0.3f),
                disabledContentColor = PrimaryTextColor.copy(alpha = 0.3f)
            ),
            border = null, // Borda não é necessária para o botão preenchido
            contentPadding = PaddingValues(horizontal = 24.dp, vertical = 16.dp)
        ) {
            Text("Próximo", fontSize = 20.sp)
            Spacer(Modifier.width(12.dp))
            Icon(
                Icons.Rounded.ChevronRight,
                contentDescription = "Seta para a direita",
                modifier = Modifier.size(32.dp)
            )
        }
    }
}