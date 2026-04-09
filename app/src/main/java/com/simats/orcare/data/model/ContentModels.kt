package com.simats.orcare.data.model

import com.google.gson.annotations.SerializedName

data class DiseaseDto(
    @SerializedName("_id") val id: String,
    val name: String,
    val iconName: String,
    val colorHex: String,
    val whatIsHappening: String,
    val whatPeopleNotice: String,
    val whyItHappens: String,
    val whyNotIgnore: String,
    val whenToSeeDentist: String
)

data class LearningCategoryDto(
    @SerializedName("_id") val id: String,
    val title: String,
    val iconName: String,
    val colorHex: String,
    val modules: List<LearningModuleDto>
)

data class LearningModuleDto(
    @SerializedName("_id") val id: String,
    val title: String,
    val duration: String,
    val lessonCount: Int,
    val objective: String,
    val iconName: String,
    val lessons: List<LessonDto>,
    val quiz: List<QuizQuestionDto>
)

data class LessonDto(
    val id: Int,
    val title: String,
    val content: String,
    val iconName: String? = "Check"
)

data class QuizQuestionDto(
    val id: Int,
    val question: String,
    val options: List<String>,
    val correctAnswerIndex: Int
)
