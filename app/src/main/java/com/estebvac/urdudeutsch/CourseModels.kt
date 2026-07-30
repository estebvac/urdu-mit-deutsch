package com.estebvac.urdudeutsch

data class Phrase(
    val urdu: String,
    val romanization: String,
    val german: String,
)

data class QuizQuestion(
    val prompt: String,
    val options: List<String>,
    val correctIndex: Int,
    val explanation: String,
)

data class Lesson(
    val id: Int,
    val title: String,
    val urduTitle: String,
    val objective: String,
    val grammar: String,
    val dialogue: List<Phrase>,
    val vocabulary: List<Phrase>,
    val questions: List<QuizQuestion>,
)

internal fun phrase(urdu: String, romanization: String, german: String) =
    Phrase(urdu = urdu, romanization = romanization, german = german)

internal fun question(
    prompt: String,
    correct: String,
    wrongOne: String,
    wrongTwo: String,
    explanation: String,
) = QuizQuestion(
    prompt = prompt,
    options = listOf(correct, wrongOne, wrongTwo),
    correctIndex = 0,
    explanation = explanation,
)
