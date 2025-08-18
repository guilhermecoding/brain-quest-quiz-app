
package com.example.brainquest.ui.home

import android.widget.Space
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Pets
import androidx.compose.material.icons.rounded.SportsBaseball
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.brainquest.ui.home.components.TopBarProfile

@Composable
fun HomeScreen(
    modifier: Modifier = Modifier,
    viewModel: HomeViewModel = hiltViewModel(),
    onLogout: () -> Unit
) {
    HomeScreenContent(
        modifier = modifier,
        onLogoutClicked = {
            viewModel.onLogoutClicked()
            onLogout()
        }
    )
}

@Composable
fun HomeScreenContent(
    modifier: Modifier = Modifier,
    onLogoutClicked: () -> Unit = {}
) {
    Scaffold { innerPadding ->
        Column(
            modifier = modifier
                .fillMaxSize()
                .padding(innerPadding)
                .padding(16.dp),
        ) {
            TopBarProfile()

            Spacer(modifier = Modifier.height(56.dp))

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

            Spacer(modifier = Modifier.height(24.dp))

            LazyRow(
                contentPadding = PaddingValues(horizontal = 16.dp),
                horizontalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                item {
                    QuizCategoryCard(
                        categoryName = "Animals Name",
                        questionCount = 10,
                        progress = 0.7f,
                        illustration = Icons.Rounded.Pets, // Ícone de exemplo
                        onStartQuiz = { /* TODO: Navegar para a tela do quiz */ }
                    )
                }
                item {
                    QuizCategoryCard(
                        categoryName = "Sports Trivia",
                        questionCount = 15,
                        progress = 0.2f,
                        illustration = Icons.Rounded.SportsBaseball, // Ícone de exemplo
                        onStartQuiz = { /* TODO: Navegar para a tela do quiz */ }
                    )
                }
                // Adicione mais items para mais categorias
            }
        }
    }
}

