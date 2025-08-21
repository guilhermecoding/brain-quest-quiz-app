package com.example.brainquest.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.Date

// @Entity diz ao Room para criar uma tabela baseada nesta classe
@Entity(tableName = "quiz_history")
data class QuizResult(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0, // Chave primária para o banco de dados Room
    val userId: String = "",
    val score: Int = 0,
    val category: String = "",
    val totalQuestions: Int = 0,
    val completedAt: Date = Date()
)