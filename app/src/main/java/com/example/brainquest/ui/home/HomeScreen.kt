package com.example.brainquest.ui.home

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.brainquest.R
import com.example.brainquest.ui.home.components.TopBarProfile

@Composable
fun HomeScreen(
    modifier: Modifier = Modifier,
    viewModel: HomeViewModel = hiltViewModel(),
    onLogout: () -> Unit,
    onStartQuiz: (String) -> Unit
) {
    val state by viewModel.uiState.collectAsState()

    HomeScreenContent(
        modifier = modifier,
        state = state,
        onLogoutClicked = {
            viewModel.onLogoutClicked()
            onLogout()
        },
        onStartQuiz = onStartQuiz
    )
}


@Composable
fun HomeScreenContent(
    modifier: Modifier = Modifier,
    state: HomeState,
    onLogoutClicked: () -> Unit,
    onStartQuiz: (String) -> Unit
) {
    Scaffold { innerPadding ->
        Column(
            modifier = modifier
                .fillMaxSize()
                .padding(innerPadding)
        ) {
            Column(modifier = Modifier.padding(16.dp)) {

                TopBarProfile()

                Spacer(modifier = Modifier.height(40.dp))

                Row {
                    Text(
                        text = "Bom dia, ",
                        style = MaterialTheme.typography.titleLarge.copy(
                            fontSize = 20.sp
                        )
                    )

                    Text(
                        text = "Matheus!",
                        style = MaterialTheme.typography.titleLarge.copy(
                            fontSize = 20.sp,
                            fontWeight = FontWeight.Bold
                        )
                    )
                }

                Text(
                    text = "Escolha um quiz!",
                    style = MaterialTheme.typography.titleLarge.copy(
                        fontSize = 36.sp,
                        fontWeight = FontWeight.Bold
                    )
                )
                Spacer(modifier = Modifier.height(12.dp))
            }

            if (state.isLoading) {
                Box(
                    modifier = Modifier.fillMaxWidth(),
                    contentAlignment = Alignment.Center
                ) {
                    CircularProgressIndicator()
                }
            } else {
                LazyRow(
                    contentPadding = PaddingValues(horizontal = 16.dp),
                    horizontalArrangement = Arrangement.spacedBy(16.dp)
                ) {
                    items(state.quizzes) { quiz ->
                        QuizCategoryCard(
                            categoryName = quiz.title,
                            questionCount = quiz.questionCount,
                            progress = 0.0f,
                            illustration = QuizIconMapper.getIconForQuiz(quiz.id),
                            onStartQuiz = { onStartQuiz(quiz.id) }
                        )
                    }
                }
            }
        }
    }
}