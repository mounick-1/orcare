package com.simats.orcare.data.model

data class ChatHistoryRequest(
    val sessionId: String,
    val title: String? = null,
    val messages: List<RemoteChatMessage>
)

data class RemoteChatMessage(
    val text: String,
    val isFromUser: Boolean
)

data class ChatHistoryResponse(
    val success: Boolean,
    val message: String? = null
)

data class RemoteChatSession(
    val _id: String,
    val sessionId: String,
    val title: String,
    val messages: List<RemoteChatMessage>,
    val updatedAt: String
)
