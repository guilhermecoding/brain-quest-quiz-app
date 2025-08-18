package com.example.brainquest

import android.os.Bundle
import android.view.WindowManager
import androidx.activity.ComponentActivity
import androidx.activity.SystemBarStyle
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.tooling.preview.Preview
import com.example.brainquest.ui.auth.AuthScreen
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
                AuthScreen()
            }
        }
    }
}

@Preview(showBackground = true, showSystemUi = false)
@Composable
fun GreetingPreview() {
    BrainQuestTheme {
        AuthScreen()
    }
}