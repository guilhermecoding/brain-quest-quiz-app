package com.example.brainquest.data.local.database

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.brainquest.data.local.dao.QuizResultDao
import com.example.brainquest.data.model.QuizResult
import java.util.Date

// Adicionamos a classe QuizResult à lista de 'entities'
@Database(entities = [QuizResult::class], version = 1)
@TypeConverters(Converters::class) // Adicionamos o conversor de data
abstract class QuizDatabase : RoomDatabase() {
    abstract fun quizResultDao(): QuizResultDao
}

// Classe auxiliar para o Room saber como salvar o tipo 'Date'
class Converters {
    @androidx.room.TypeConverter
    fun fromTimestamp(value: Long?): Date? = value?.let { Date(it) }

    @androidx.room.TypeConverter
    fun dateToTimestamp(date: Date?): Long? = date?.time
}