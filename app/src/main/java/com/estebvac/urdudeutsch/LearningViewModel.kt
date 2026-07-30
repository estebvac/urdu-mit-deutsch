package com.estebvac.urdudeutsch

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn

data class LearningUiState(
    val lessons: List<Lesson> = CourseCatalog.lessons,
    val completedLessonIds: Set<Int> = emptySet(),
    val bestScores: Map<Int, Int> = emptyMap(),
    val reviewedWords: Int = 0,
    val knownWords: Int = 0,
    val showRomanization: Boolean = true,
    val textScale: Float = 1f,
) {
    val completionFraction: Float
        get() = if (lessons.isEmpty()) 0f else completedLessonIds.size.toFloat() / lessons.size
}

class LearningViewModel(
    private val repository: ProgressRepository,
) : ViewModel() {
    val uiState: StateFlow<LearningUiState> = repository.progress
        .map { progress ->
            LearningUiState(
                completedLessonIds = progress.completedLessonIds,
                bestScores = progress.bestScores,
                reviewedWords = progress.reviewedWords,
                knownWords = progress.knownWords,
                showRomanization = progress.showRomanization,
                textScale = progress.textScale,
            )
        }
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5_000), LearningUiState())

    fun completeLesson(lessonId: Int, score: Int) = repository.completeLesson(lessonId, score)
    fun recordReview(known: Boolean) = repository.recordReview(known)
    fun setRomanization(enabled: Boolean) = repository.setRomanization(enabled)
    fun setTextScale(scale: Float) = repository.setTextScale(scale)
    fun reset() = repository.reset()

    class Factory(
        private val repository: ProgressRepository,
    ) : ViewModelProvider.Factory {
        @Suppress("UNCHECKED_CAST")
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            require(modelClass.isAssignableFrom(LearningViewModel::class.java))
            return LearningViewModel(repository) as T
        }
    }
}
