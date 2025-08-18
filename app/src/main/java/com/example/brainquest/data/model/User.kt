package com.example.brainquest.data.model

// Representa os dados que serão salvos na coleção "users" do Firestore
data class User(
    val uid: String = "",
    val name: String = "",
    val email: String = ""
)