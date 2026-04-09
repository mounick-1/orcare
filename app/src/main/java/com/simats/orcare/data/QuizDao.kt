package com.simats.orcare.data

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface QuizDao {
    @Insert
    suspend fun insertQuizResult(result: QuizEntity)

    @Query("SELECT * FROM quiz_results ORDER BY timestamp DESC")
    fun getAllQuizResults(): Flow<List<QuizEntity>>

    @Query("SELECT * FROM quiz_results WHERE moduleId = :moduleId ORDER BY timestamp DESC")
    fun getQuizResultsForModule(moduleId: String): Flow<List<QuizEntity>>
}
