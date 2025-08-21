package com.example.brainquest

import android.os.Bundle
import android.view.WindowManager
import androidx.activity.ComponentActivity
import androidx.activity.SystemBarStyle
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.brainquest.data.local.PrefsManager
import com.example.brainquest.ui.auth.AuthScreen
import com.example.brainquest.ui.home.HomeScreen
import com.example.brainquest.ui.theme.BrainQuestTheme
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject
import androidx.navigation.NavType
import androidx.navigation.navArgument
import com.example.brainquest.ui.home.HomeViewModel
import com.example.brainquest.ui.quiz.QuizScreen
import com.example.brainquest.ui.result.ResultScreen

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    @Inject
    lateinit var prefsManager: PrefsManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // <-- REMOVER ISSO ANTES DO BUILD FINAL --> Mantem a tela acesa
        window.addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON)

        val startDestination = if (prefsManager.getUserId().isNullOrBlank()) {
            "auth_screen" // Se NÃO houver ID de usuário salvo, comece no login
        } else {
            "home_screen" // Se houver, vá direto para a tela principal
        }

        enableEdgeToEdge(
            navigationBarStyle = SystemBarStyle.light(
                Color.White.toArgb(), Color.White.toArgb()
            )
        )
        setContent {
            BrainQuestTheme {
                val navController = rememberNavController()

                NavHost(navController = navController, startDestination = startDestination) {

                    composable("auth_screen") {
                        AuthScreen(
                            onLoginSuccess = {
                                navController.navigate("home_screen") {
                                    popUpTo("auth_screen") { inclusive = true }
                                }
                            }
                        )
                    }

                    composable("home_screen") {
                        // 1. Obtenha a instância do ViewModel.
                        val viewModel: HomeViewModel = hiltViewModel()

                        // 2. Colete o estado a partir do ViewModel.
                        val state by viewModel.uiState.collectAsState()

                        // 3. Chame a HomeScreen passando apenas os parâmetros necessários.
                        HomeScreen(
                            onLogout = {
                                viewModel.onLogoutClicked()
                                navController.navigate("auth_screen") {
                                    popUpTo(0) { inclusive = true }
                                }
                            },
                            onStartQuiz = { quizId ->
                                navController.navigate("quiz_screen/$quizId")
                            }
                        )
                    }

                    // ✅ NOVA ROTA PARA A TELA DO QUIZ:
                    composable(
                        route = "quiz_screen/{quizId}",
                        arguments = listOf(navArgument("quizId") { type = NavType.StringType })
                    ) {
                        QuizScreen(
                            onNavigateBack = { navController.popBackStack() },
                            // Passe o novo callback para a QuizScreen
                            onQuizFinished = { score, total ->
                                // Navega para a tela de resultado, limpando a tela do quiz do histórico
                                navController.navigate("result_screen/$score/$total") {
                                    popUpTo("quiz_screen/{quizId}") { inclusive = true }
                                }
                            }
                        )
                    }

                    composable(
                        route = "result_screen/{score}/{total}",
                        arguments = listOf(
                            navArgument("score") { type = NavType.IntType },
                            navArgument("total") { type = NavType.IntType }
                        )
                    ) { backStackEntry ->
                        val score = backStackEntry.arguments?.getInt("score") ?: 0
                        val total = backStackEntry.arguments?.getInt("total") ?: 0
                        ResultScreen(
                            score = score,
                            totalQuestions = total,
                            onNavigateHome = {
                                // Navega para a home, limpando todo o histórico de quiz
                                navController.navigate("home_screen") {
                                    popUpTo(0)
                                }
                            }
                        )
                    }
                }
            }
        }
    }
}

