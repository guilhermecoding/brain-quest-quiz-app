package com.example.brainquest.data.repository

import com.example.brainquest.data.model.Quiz
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.tasks.await
import javax.inject.Inject
import com.example.brainquest.data.local.dao.QuizResultDao
import com.example.brainquest.data.model.QuizResult

class QuizRepository @Inject constructor(
    private val firestore: FirebaseFirestore,
    private val quizResultDao: QuizResultDao
) {
    // ... (função de upload que você pode apagar depois) ...

    /**
     * Busca todos os documentos da coleção 'quizzes' no Firestore.
     * Converte cada documento em um objeto Quiz.
     */
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
            // Ação 1: Salvar no Cloud Firestore (para a nuvem)
            firestore.collection("quiz_history").add(result).await()

            // Ação 2: Salvar no banco de dados local (Room)
            quizResultDao.insert(result)

            // Você pode adicionar a lógica de atualizar o 'highScore' aqui também

            Result.success(Unit)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}