package com.example.brainquest.data.model

/**
 * Representa uma única pergunta.
 * Esta estrutura corresponde a cada objeto (map) dentro do array 'questions' no seu Firestore.
 */
data class Question(
    val enunciado: String = "",
    val opcoes: List<String> = emptyList(),
    val resposta_correta: Int = 0
)