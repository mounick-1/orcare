package com.simats.orcare.ui.viewmodel

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.*
import androidx.compose.material.icons.automirrored.rounded.*
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.simats.orcare.data.api.RetrofitClient
import com.simats.orcare.data.model.DiseaseDto
import com.simats.orcare.data.model.LearningCategoryDto
import com.simats.orcare.data.model.LearningModuleDto
import com.simats.orcare.data.model.LessonDto
import com.simats.orcare.data.model.QuizQuestionDto
import com.simats.orcare.data.Disease
import com.simats.orcare.data.DiseaseRepository
import com.simats.orcare.data.LearningCategory
import com.simats.orcare.data.LearningModule
import com.simats.orcare.data.LearningRepository
import com.simats.orcare.data.Lesson
import com.simats.orcare.data.QuizQuestion
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.simats.orcare.ui.components.*

sealed class ContentState<out T> {
    object Idle : ContentState<Nothing>()
    object Loading : ContentState<Nothing>()
    data class Success<out T>(val data: T) : ContentState<T>()
    data class Error(val message: String) : ContentState<Nothing>()
}

class ContentViewModel : ViewModel() {

    private val _diseasesState = MutableStateFlow<ContentState<List<Disease>>>(ContentState.Success(DiseaseRepository.diseases))
    val diseasesState: StateFlow<ContentState<List<Disease>>> = _diseasesState.asStateFlow()

    private val _learningState = MutableStateFlow<ContentState<List<LearningCategory>>>(ContentState.Success(LearningRepository.categories))
    val learningState: StateFlow<ContentState<List<LearningCategory>>> = _learningState.asStateFlow()

    fun fetchDiseases() {
        viewModelScope.launch {
            _diseasesState.value = ContentState.Loading
            try {
                val response = RetrofitClient.getInstance().getDiseases()
                if (response.isSuccessful) {
                    val dtos = response.body() ?: emptyList()
                    if (dtos.isNotEmpty()) {
                        val domain = dtos.map { it.toDomain() }
                        _diseasesState.value = ContentState.Success(domain)
                    } else {
                        _diseasesState.value = ContentState.Success(DiseaseRepository.diseases)
                    }
                } else {
                    _diseasesState.value = ContentState.Success(DiseaseRepository.diseases)
                }
            } catch (e: Exception) {
                _diseasesState.value = ContentState.Success(DiseaseRepository.diseases)
            }
        }
    }

    fun fetchLearningContent() {
        viewModelScope.launch {
            _learningState.value = ContentState.Loading
            try {
                val response = RetrofitClient.getInstance().getLearning()
                if (response.isSuccessful) {
                    val dtos = response.body() ?: emptyList()
                    if (dtos.isNotEmpty()) {
                        val domain = dtos.map { it.toDomain() }
                        _learningState.value = ContentState.Success(domain)
                    } else {
                        _learningState.value = ContentState.Success(LearningRepository.categories)
                    }
                } else {
                    _learningState.value = ContentState.Success(LearningRepository.categories)
                }
            } catch (e: Exception) {
                _learningState.value = ContentState.Success(LearningRepository.categories)
            }
        }
    }

    private fun DiseaseDto.toDomain(): Disease {
        return Disease(
            id = id,
            name = name,
            icon = mapIcon(iconName),
            color = Color(android.graphics.Color.parseColor(colorHex)),
            whatIsHappening = whatIsHappening,
            whatPeopleNotice = whatPeopleNotice,
            whyItHappens = whyItHappens,
            whyNotIgnore = whyNotIgnore,
            whenToSeeDentist = whenToSeeDentist
        )
    }

    private fun LearningCategoryDto.toDomain(): LearningCategory {
        return LearningCategory(
            id = id,
            title = title,
            icon = mapIcon(iconName),
            color = Color(android.graphics.Color.parseColor(colorHex)),
            modules = modules.map { it.toDomain() }
        )
    }

    private fun LearningModuleDto.toDomain(): LearningModule {
        return LearningModule(
            id = id,
            title = title,
            duration = duration,
            lessonCount = lessonCount,
            objective = objective,
            icon = mapIcon(iconName),
            lessons = lessons.map { it.toDomain() },
            quiz = quiz.map { it.toDomain() }
        )
    }

    private fun LessonDto.toDomain(): Lesson {
        return Lesson(
            id = id,
            title = title,
            content = content,
            icon = mapIcon(iconName ?: "Check")
        )
    }

    private fun QuizQuestionDto.toDomain(): QuizQuestion {
        return QuizQuestion(
            id = id,
            question = question,
            options = options,
            correctAnswerIndex = correctAnswerIndex
        )
    }

    private fun mapIcon(name: String): ImageVector {
        return when (name) {
            "Bloodtype" -> Icons.Rounded.Bloodtype
            "Warning" -> Icons.Rounded.Warning
            "Air" -> Icons.Rounded.Air
            "Coronavirus" -> Icons.Rounded.Coronavirus
            "Bolt" -> Icons.Rounded.Bolt
            "Whatshot" -> Icons.Rounded.Whatshot
            "CleaningServices" -> Icons.Rounded.CleaningServices
            "Schedule" -> Icons.Rounded.Schedule
            "Info" -> Icons.Rounded.Info
            "List" -> Icons.Rounded.List
            "WaterDrop" -> Icons.Rounded.WaterDrop
            "Nightlight" -> Icons.Rounded.Nightlight
            "TouchApp" -> Icons.Rounded.TouchApp
            "CleanHands" -> Icons.Rounded.CleanHands
            "Brush" -> Icons.Rounded.Brush
            "RotateRight" -> Icons.Rounded.RotateRight
            "Loop" -> Icons.Rounded.Loop
            "Straighten" -> Icons.Rounded.Straighten
            "Timer" -> Icons.Rounded.Timer
            "VisibilityOff" -> Icons.Rounded.VisibilityOff
            "Architecture" -> Icons.Rounded.Architecture
            "CheckCircle" -> Icons.Rounded.CheckCircle
            "RecordVoiceOver" -> Icons.Rounded.RecordVoiceOver
            "KeyboardDoubleArrowDown" -> Icons.Rounded.KeyboardDoubleArrowDown
            "BugReport" -> Icons.Rounded.BugReport
            "Restaurant" -> Icons.Rounded.Restaurant
            "NoFood" -> Icons.Rounded.NoFood
            "SmokeFree" -> Icons.Rounded.SmokeFree
            "HealthAndSafety" -> Icons.Rounded.HealthAndSafety
            "Science" -> Icons.Rounded.Science
            "Construction" -> Icons.Rounded.Construction
            "InvertColors" -> Icons.Rounded.InvertColors
            "Check" -> Icons.Rounded.Check
            else -> Icons.Rounded.Help
        }
    }
}
