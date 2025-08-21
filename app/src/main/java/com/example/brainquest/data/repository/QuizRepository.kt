package com.example.brainquest.data.repository

import com.example.brainquest.data.model.Quiz
import com.example.brainquest.data.model.QuizResult
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

class QuizRepository @Inject constructor(
    private val firestore: FirebaseFirestore,
    private val auth: FirebaseAuth
) {
    suspend fun getQuizzes(): Result<List<Quiz>> {
        return try {
            val querySnapshot = firestore.collection("quizzes").get().await()
            val quizzes = querySnapshot.toObjects(Quiz::class.java)
            Result.success(quizzes)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    suspend fun getQuizById(quizId: String): Result<Quiz> {
        return try {
            val document = firestore.collection("quizzes").document(quizId).get().await()
            val quiz = document.toObject(Quiz::class.java)
            if (quiz != null) {
                Result.success(quiz)
            } else {
                Result.failure(Exception("Quiz não encontrado"))
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    suspend fun saveQuizResult(result: QuizResult): Result<Unit> {
        return try {
            firestore.collection("quiz_history").add(result).await()

            // Ação 2: Usar uma transação para atualizar o perfil do usuário
            val userDocRef = firestore.collection("users").document(result.userId)

            firestore.runTransaction { transaction ->
                val userSnapshot = transaction.get(userDocRef)

                // Pega os valores atuais (ou 0 se não existirem)
                val currentHighScore = userSnapshot.getLong("highScore") ?: 0L
                val currentCompleted = userSnapshot.getLong("quizzesCompleted") ?: 0L
                val currentTotalScore = userSnapshot.getLong("totalScore") ?: 0L // ✅ Pega a soma atual

                // Lógica do High Score (continua igual)
                if (result.score > currentHighScore) {
                    transaction.update(userDocRef, "highScore", result.score)
                }

                // Lógica dos Quizzes Completados (continua igual)
                transaction.update(userDocRef, "quizzesCompleted", currentCompleted + 1)

                // ✅ LÓGICA PARA SOMAR O SCORE
                val newTotalScore = currentTotalScore + result.score
                transaction.update(userDocRef, "totalScore", newTotalScore)

            }.await()

            Result.success(Unit)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    /**
     * Busca todos os registros de histórico de um usuário.
     */
    suspend fun getQuizHistory(): Result<List<QuizResult>> {
        return try {
            val uid = auth.currentUser?.uid ?: return Result.failure(Exception("Usuário não logado"))
            val querySnapshot = firestore.collection("quiz_history")
                .whereEqualTo("userId", uid)
                .orderBy("completedAt", Query.Direction.DESCENDING)
                .get()
                .await()
            val history = querySnapshot.toObjects(QuizResult::class.java)
            Result.success(history)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}