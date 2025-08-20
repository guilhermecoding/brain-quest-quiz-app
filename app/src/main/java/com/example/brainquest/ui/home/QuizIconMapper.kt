package com.example.brainquest.ui.home

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.rounded.HelpOutline
import androidx.compose.material.icons.rounded.Computer
import androidx.compose.material.icons.rounded.HelpOutline
import androidx.compose.material.icons.rounded.Pets
import androidx.compose.material.icons.rounded.Quiz
import androidx.compose.material.icons.rounded.SportsBasketball
import androidx.compose.ui.graphics.vector.ImageVector

/**
 * Um objeto singleton para mapear o ID de um quiz do Firestore
 * para um ImageVector (ícone) do Material Design.
 */
object QuizIconMapper {

    // O mapa que faz a correlação entre a String do ID e o Ícone
    private val iconMap = mapOf(
        "quiz_animais" to Icons.Rounded.Pets,
        "quiz_esportes" to Icons.Rounded.SportsBasketball,
        "quiz_curiosidades" to Icons.AutoMirrored.Rounded.HelpOutline,
        "quiz_computacao" to Icons.Rounded.Computer
    )

    // Um ícone padrão para caso um quiz não tenha um ícone mapeado
    private val defaultIcon = Icons.Rounded.Quiz

    /**
     * Função que recebe o ID de um quiz e retorna o ícone correspondente.
     * Se o ID não for encontrado no mapa, retorna o ícone padrão.
     */
    fun getIconForQuiz(quizId: String): ImageVector {
        return iconMap[quizId] ?: defaultIcon
    }
}