package com.example.brainquest.data.repository


import com.example.brainquest.data.model.User
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

class AuthRepository @Inject constructor(
    private val auth: FirebaseAuth,
    private val firestore: FirebaseFirestore
) {
    suspend fun signInWithEmailAndPassword(email: String, password: String): Result<AuthResult> {
        return try {
            val result = auth.signInWithEmailAndPassword(email, password).await()
            Result.success(result)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    suspend fun createUser(name: String, email: String, password: String): Result<Unit> {
        return try {
            // Etapa 1: Criar o usuário no Firebase Authentication
            val authResult = auth.createUserWithEmailAndPassword(email, password).await()
            val firebaseUser = authResult.user
                ?: throw IllegalStateException("Usuário do Firebase não encontrado após a criação.")

            // Etapa 2: Salvar as informações do perfil no Cloud Firestore
            val userProfile = User(
                uid = firebaseUser.uid,
                name = name,
                email = email
            )

            // Cria um documento na coleção "users" com o ID sendo o UID do usuário
            firestore.collection("users").document(firebaseUser.uid).set(userProfile).await()

            Result.success(Unit) // Retorna sucesso
        } catch (e: Exception) {
            Result.failure(e) // Retorna a exceção em caso de falha
        }
    }
}