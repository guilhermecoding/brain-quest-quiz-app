package com.example.brainquest.data.model

import com.google.firebase.firestore.Exclude

/**
 * Representa um quiz completo.
 * Esta estrutura corresponde a um documento inteiro na sua coleção 'quizzes' no Firestore.
 */
data class Quiz(
    val id: String = "",
    val title: String = "",
    val description: String = "",
    val questionCount: Int = 0,
    val questions: List<Question> = emptyList(),

    @get:Exclude
    var isCurrentlyPlaying: Boolean = false
)