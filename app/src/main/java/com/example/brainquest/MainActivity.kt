package com.example.brainquest

import android.os.Bundle
import android.view.WindowManager
import androidx.activity.ComponentActivity
import androidx.activity.SystemBarStyle
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.brainquest.ui.auth.AuthScreen
import com.example.brainquest.ui.home.HomeScreen
import com.example.brainquest.ui.theme.BrainQuestTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // <-- REMOVER ISSO ANTES DO BUILD FINAL --> Mantem a tela acesa
        window.addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON)

        enableEdgeToEdge(
            navigationBarStyle = SystemBarStyle.light(
                Color.White.toArgb(), Color.White.toArgb()
            )
        )
        setContent {
            BrainQuestTheme {
                val navController = rememberNavController()

                NavHost(navController = navController, startDestination = "auth_screen") {

                    // 3. Defina cada tela (rota) do seu aplicativo
                    composable("auth_screen") {
                        AuthScreen(
                            onLoginSuccess = {
                                // Navega para a home e remove a tela de login da pilha
                                navController.navigate("home_screen") {
                                    popUpTo("auth_screen") {
                                        inclusive = true
                                    }
                                }
                            }
                        )
                    }

                    composable("home_screen") {
                        HomeScreen()
                    }
                }
            }
        }
    }
}

