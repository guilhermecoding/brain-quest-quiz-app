package com.example.brainquest.ui.quiz

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Apps
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.brainquest.R

@Composable
fun QuizScreen(
    viewModel: QuizViewModel = hiltViewModel(),
    onNavigateBack: () -> Unit
) {
    val state by viewModel.uiState.collectAsState()
    val currentQuestion = state.currentQuestion

    Scaffold(
        containerColor = Color(0xFF7B61FF) // Cor de fundo roxa
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
                    QuizTopBar(
                        title = state.quizTitle,
                        onBackClicked = onNavigateBack
                    )
                    Spacer(modifier = Modifier.height(24.dp))
                    QuizProgressIndicator(
                        totalQuestions = state.totalQuestions,
                        currentQuestionIndex = state.currentQuestionIndex
                    )
                    Spacer(modifier = Modifier.height(24.dp))

                    // Placeholder para a ilustração e timer
                    Box(contentAlignment = Alignment.Center) {
                        Image(painter = painterResource(id = R.drawable.avatar), contentDescription = null, modifier = Modifier.height(120.dp))
                        Text(text = "03:31", color = Color.Black, modifier = Modifier.align(Alignment.TopEnd).background(Color(0xFFFFC107), RoundedCornerShape(12.dp)).padding(horizontal = 12.dp, vertical = 4.dp))
                    }

                    Spacer(modifier = Modifier.height(24.dp))

                    Text(
                        text = currentQuestion.enunciado,
                        color = Color.White,
                        style = MaterialTheme.typography.headlineSmall,
                        textAlign = TextAlign.Center,
                        fontWeight = FontWeight.Bold
                    )
                    Spacer(modifier = Modifier.height(16.dp))
                    Text(
                        text = "Choose your answer",
                        color = Color.White.copy(alpha = 0.7f),
                        style = MaterialTheme.typography.bodyLarge
                    )
                    Spacer(modifier = Modifier.height(32.dp))

                    currentQuestion.opcoes.forEachIndexed { index, option ->
                        AnswerOption(
                            text = option,
                            isSelected = state.selectedAnswerIndex == index,
                            onClick = { viewModel.onAnswerSelected(index) }
                        )
                        Spacer(modifier = Modifier.height(16.dp))
                    }

                    Spacer(modifier = Modifier.weight(1f))

                    QuizBottomBar(
                        onPreviousClicked = viewModel::onPreviousClicked,
                        onNextClicked = viewModel::onNextClicked
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
            IconButton(onClick = onBackClicked) {
                Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Voltar", tint = Color.White)
            }
        },
        actions = {
            IconButton(onClick = { /*TODO*/ }) {
                Icon(Icons.Default.Apps, contentDescription = "Menu", tint = Color.White)
            }
        },
        colors = TopAppBarDefaults.topAppBarColors(containerColor = Color.Transparent)
    )
}

@Composable
fun QuizProgressIndicator(totalQuestions: Int, currentQuestionIndex: Int) {
    Column {
        Text(text = "${currentQuestionIndex + 1} Question", color = Color.White.copy(alpha = 0.7f))
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
fun AnswerOption(text: String, isSelected: Boolean, onClick: () -> Unit) {
    val backgroundColor = if (isSelected) Color(0xFFFFC107) else Color.White
    val textColor = if (isSelected) Color.Black else Color(0xFF7B61FF)
    val border = if (isSelected) BorderStroke(2.dp, Color.White) else null

    Button(
        onClick = onClick,
        modifier = Modifier.fillMaxWidth().height(60.dp),
        shape = RoundedCornerShape(16.dp),
        colors = ButtonDefaults.buttonColors(containerColor = backgroundColor),
        border = border
    ) {
        Text(text = text, color = textColor, fontWeight = FontWeight.Bold, fontSize = 16.sp)
    }
}

@Composable
fun QuizBottomBar(onPreviousClicked: () -> Unit, onNextClicked: () -> Unit) {
    Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween, verticalAlignment = Alignment.CenterVertically) {
        TextButton(onClick = onPreviousClicked) { Text("Previous", color = Color.White.copy(alpha = 0.7f)) }
        FloatingActionButton(
            onClick = { /*TODO*/ },
            containerColor = Color.White,
            contentColor = Color(0xFF7B61FF)
        ) {
            Icon(Icons.Default.Apps, contentDescription = "Dica")
        }
        TextButton(onClick = onNextClicked) { Text("Next", color = Color.White) }
    }
}