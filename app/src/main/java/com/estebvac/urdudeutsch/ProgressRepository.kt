package com.estebvac.urdudeutsch

import android.content.Context
import androidx.core.content.edit
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

data class LearningProgress(
    val completedLessonIds: Set<Int> = emptySet(),
    val bestScores: Map<Int, Int> = emptyMap(),
    val reviewedWords: Int = 0,
    val knownWords: Int = 0,
    val showRomanization: Boolean = true,
    val textScale: Float = 1f,
)

interface ProgressRepository {
    val progress: StateFlow<LearningProgress>
    fun completeLesson(lessonId: Int, score: Int)
    fun recordReview(known: Boolean)
    fun setRomanization(enabled: Boolean)
    fun setTextScale(scale: Float)
    fun reset()
}

class SharedPreferencesProgressRepository(context: Context) : ProgressRepository {
    private val preferences = context.getSharedPreferences(PREFERENCES_NAME, Context.MODE_PRIVATE)
    private val _progress = MutableStateFlow(readProgress())
    override val progress: StateFlow<LearningProgress> = _progress.asStateFlow()

    override fun completeLesson(lessonId: Int, score: Int) {
        _progress.update { current ->
            val updatedScore = maxOf(score, current.bestScores[lessonId] ?: 0)
            current.copy(
                completedLessonIds = current.completedLessonIds + lessonId,
                bestScores = current.bestScores + (lessonId to updatedScore),
            )
        }
        persist()
    }

    override fun recordReview(known: Boolean) {
        _progress.update {
            it.copy(
                reviewedWords = it.reviewedWords + 1,
                knownWords = it.knownWords + if (known) 1 else 0,
            )
        }
        persist()
    }

    override fun setRomanization(enabled: Boolean) {
        _progress.update { it.copy(showRomanization = enabled) }
        persist()
    }

    override fun setTextScale(scale: Float) {
        _progress.update { it.copy(textScale = scale.coerceIn(0.85f, 1.35f)) }
        persist()
    }

    override fun reset() {
        _progress.value = LearningProgress()
        preferences.edit { clear() }
    }

    private fun readProgress(): LearningProgress {
        val completed = preferences.getStringSet(KEY_COMPLETED, emptySet()).orEmpty()
            .mapNotNull(String::toIntOrNull)
            .toSet()
        val scores = preferences.getString(KEY_SCORES, "").orEmpty()
            .split(',')
            .mapNotNull { item ->
                val parts = item.split(':')
                val lesson = parts.getOrNull(0)?.toIntOrNull()
                val score = parts.getOrNull(1)?.toIntOrNull()
                if (lesson != null && score != null) lesson to score else null
            }
            .toMap()
        return LearningProgress(
            completedLessonIds = completed,
            bestScores = scores,
            reviewedWords = preferences.getInt(KEY_REVIEWED, 0),
            knownWords = preferences.getInt(KEY_KNOWN, 0),
            showRomanization = preferences.getBoolean(KEY_ROMANIZATION, true),
            textScale = preferences.getFloat(KEY_TEXT_SCALE, 1f),
        )
    }

    private fun persist() {
        val value = _progress.value
        preferences.edit {
            putStringSet(KEY_COMPLETED, value.completedLessonIds.map(Int::toString).toSet())
            putString(KEY_SCORES, value.bestScores.entries.joinToString(",") { "${it.key}:${it.value}" })
            putInt(KEY_REVIEWED, value.reviewedWords)
            putInt(KEY_KNOWN, value.knownWords)
            putBoolean(KEY_ROMANIZATION, value.showRomanization)
            putFloat(KEY_TEXT_SCALE, value.textScale)
        }
    }

    private companion object {
        const val PREFERENCES_NAME = "urdu_learning_progress"
        const val KEY_COMPLETED = "completed_lessons"
        const val KEY_SCORES = "best_scores"
        const val KEY_REVIEWED = "reviewed_words"
        const val KEY_KNOWN = "known_words"
        const val KEY_ROMANIZATION = "show_romanization"
        const val KEY_TEXT_SCALE = "text_scale"
    }
}
