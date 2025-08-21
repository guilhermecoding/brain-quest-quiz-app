package com.example.brainquest.ui.home

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
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.brainquest.ui.home.components.QuizHistoryItem
import com.example.brainquest.ui.home.components.TopBarProfile
import java.util.Calendar

@Composable
fun HomeScreen(
    modifier: Modifier = Modifier,
    viewModel: HomeViewModel = hiltViewModel(),
    onLogout: () -> Unit,
    onStartQuiz: (String) -> Unit
) {
    // A assinatura da HomeScreen foi corrigida para não receber mais o 'state',
    // pois ela já o obtém do ViewModel.
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
    val firstNameUser = state.currentUser?.name?.trim()?.split(" ")?.firstOrNull() ?: "Jogador"
    val calendar = Calendar.getInstance()
    val hourOfDay = calendar.get(Calendar.HOUR_OF_DAY)
    val greetingText = when (hourOfDay) {
        in 5..11 -> "Bom dia"
        in 12..17 -> "Boa tarde"
        else -> "Boa noite"
    }

    Scaffold { innerPadding ->
        // ✅ A LazyColumn é o container principal para permitir rolagem de toda a tela.
        LazyColumn(
            modifier = modifier
                .fillMaxSize()
                .padding(innerPadding)
        ) {
            // -- SEÇÃO SUPERIOR --
            // Usamos item { } para adicionar um único Composable complexo à lista.
            item {
                Column(modifier = Modifier.padding(16.dp)) {
                    TopBarProfile(user = state.currentUser, onLogoutClicked = onLogoutClicked)
                    Spacer(modifier = Modifier.height(40.dp))
                    Row {
                        Text(
                            text = "$greetingText, ",
                            style = MaterialTheme.typography.titleLarge.copy(fontSize = 20.sp)
                        )
                        Text(
                            text = "$firstNameUser!",
                            style = MaterialTheme.typography.titleLarge.copy(fontSize = 20.sp, fontWeight = FontWeight.Bold)
                        )
                    }
                    Text(
                        text = "Escolha um quiz!",
                        style = MaterialTheme.typography.titleLarge.copy(fontSize = 36.sp, fontWeight = FontWeight.Bold)
                    )
                    Spacer(modifier = Modifier.height(12.dp))
                }
            }

            // -- SEÇÃO DE CARDS DE QUIZ (LAZYROW) --
            item {
                if (state.isLoading) {
                    Box(modifier = Modifier.fillMaxWidth().height(200.dp), contentAlignment = Alignment.Center) {
                        CircularProgressIndicator()
                    }
                } else {
                    LazyRow(
                        contentPadding = PaddingValues(horizontal = 16.dp),
                        horizontalArrangement = Arrangement.spacedBy(16.dp)
                    ) {
                        items(state.quizzes) { quiz ->
                            val answeredCount = state.progressMap[quiz.title] ?: 0
                            QuizCategoryCard(
                                categoryName = quiz.title,
                                questionCount = quiz.questionCount,
                                questionsAnswered = answeredCount,
                                illustration = QuizIconMapper.getIconForQuiz(quiz.id),
                                onStartQuiz = { onStartQuiz(quiz.id) }
                            )
                        }
                    }
                }
            }

            // -- ✅ NOVA SEÇÃO: HISTÓRICO --
            item {
                Column(modifier = Modifier.padding(start = 16.dp, end = 16.dp, top = 24.dp, bottom = 12.dp)) {
                    Text(
                        text = "Meu Histórico",
                        style = MaterialTheme.typography.titleLarge.copy(fontWeight = FontWeight.Bold)
                    )
                }
            }

            // Renderiza a lista vertical de histórico
            items(state.quizHistory) { result ->
                QuizHistoryItem(
                    modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp),
                    result = result
                )
            }
        }
    }
}