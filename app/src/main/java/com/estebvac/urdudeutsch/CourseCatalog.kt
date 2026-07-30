package com.estebvac.urdudeutsch

object CourseCatalog {
    val lessons: List<Lesson> = lessonsOneToFive + lessonsSixToTen
    val allVocabulary: List<Phrase> = lessons.flatMap(Lesson::vocabulary)

    fun lesson(id: Int): Lesson? = lessons.firstOrNull { it.id == id }
}
