package com.example.brainquest.data.repository

import com.example.brainquest.data.model.User
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

class UserRepository @Inject constructor(
    private val firestore: FirebaseFirestore
) {
    /**
     * Busca os usuários no Firestore, ordenados pela maior pontuação (highScore).
     * Limita o resultado aos top 50 jogadores para performance.
     */
    suspend fun getRanking(): Result<List<User>> {
        return try {
            val querySnapshot = firestore.collection("users")
                .orderBy("highScore", Query.Direction.DESCENDING)
                .limit(50)
                .get()
                .await()
            val rankingList = querySnapshot.toObjects(User::class.java)
            Result.success(rankingList)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}