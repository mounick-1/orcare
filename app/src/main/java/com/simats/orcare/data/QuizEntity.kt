package com.simats.orcare.data

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "quiz_results")
data class QuizEntity(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val moduleId: String,
    val score: Int,
    val total: Int,
    val timestamp: Long = System.currentTimeMillis()
)
