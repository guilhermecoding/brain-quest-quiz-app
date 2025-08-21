package com.example.brainquest.data.model

import androidx.annotation.Keep

// Representa os dados que serão salvos na coleção "users" do Firestore
@Keep
data class User(
    val uid: String = "",
    val name: String = "",
    val email: String = "",
    val highScore: Long = 0L, // Use Long para números no Firestore
    val quizzesCompleted: Long = 0L,
    val totalScore: Long = 0L
)