package com.example.brainquest.data.repository


import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseAuth
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

class AuthRepository @Inject constructor(
    private val auth: FirebaseAuth
) {
    suspend fun signInWithEmailAndPassword(email: String, password: String): Result<AuthResult> {
        return try {
            val result = auth.signInWithEmailAndPassword(email, password).await()
            Result.success(result)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    // Você adicionará a função de cadastro aqui também
    // suspend fun createUserWithEmailAndPassword(...) { ... }
}