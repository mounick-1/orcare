package com.simats.orcare.data

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface ChatDao {
    @Query("SELECT * FROM chat_messages WHERE sessionId = :sessionId ORDER BY timestamp ASC")
    fun getMessagesForSession(sessionId: Int): Flow<List<ChatEntity>>

    @Query("SELECT * FROM chat_sessions WHERE id = :sessionId")
    fun getSessionById(sessionId: Int): Flow<ChatSession?>

    @Query("SELECT * FROM chat_sessions ORDER BY startTime DESC")
    fun getAllSessions(): Flow<List<ChatSession>>

    @Insert
    suspend fun insertSession(session: ChatSession): Long

    @Insert
    suspend fun insertMessage(message: ChatEntity)
    
    @Query("UPDATE chat_sessions SET title = :title WHERE id = :sessionId")
    suspend fun updateSessionTitle(sessionId: Int, title: String)

    @Query("DELETE FROM chat_sessions WHERE id = :sessionId")
    suspend fun deleteSession(sessionId: Int)
    
    @Query("DELETE FROM chat_sessions")
    suspend fun deleteAllSessions()
}
