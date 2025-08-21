package com.example.brainquest.data.repository


import com.example.brainquest.data.local.PrefsManager
import com.example.brainquest.data.model.Quiz
import com.example.brainquest.data.model.User
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

class AuthRepository @Inject constructor(
    private val auth: FirebaseAuth,
    private val firestore: FirebaseFirestore,
    private val prefsManager: PrefsManager
) {
    suspend fun signInWithEmailAndPassword(email: String, password: String): Result<AuthResult> {
        return try {
            val result = auth.signInWithEmailAndPassword(email, password).await()
            result.user?.let { firebaseUser ->
                // 3. MANDA O PREFSMANAGER SALVAR O USUÁRIO LOCALMENTE
                prefsManager.saveUser(firebaseUser)
            }

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
    /**
     * Busca os dados do perfil do usuário logado no momento na coleção 'users'.
     */
    suspend fun getCurrentUserProfile(): Result<User?> {
        return try {
            val uid = auth.currentUser?.uid
            if (uid == null) {
                return Result.success(null) // Nenhum usuário logado
            }
            val document = firestore.collection("users").document(uid).get().await()
            val user = document.toObject(User::class.java)
            Result.success(user)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    fun logout() {
        auth.signOut() // Desloga do Firebase Authentication
        prefsManager.clearUser() // Limpa os dados do SharedPreferences
    }
}