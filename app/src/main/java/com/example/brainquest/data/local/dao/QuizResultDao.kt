package com.example.brainquest.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.brainquest.data.model.QuizResult
import kotlinx.coroutines.flow.Flow

@Dao
interface QuizResultDao {
    // Salva ou atualiza um resultado no banco
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(result: QuizResult)

    // Busca todo o histórico de um usuário específico, ordenado por data
    @Query("SELECT * FROM quiz_history WHERE userId = :userId ORDER BY completedAt DESC")
    fun getHistoryForUser(userId: String): Flow<List<QuizResult>>
}