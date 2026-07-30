package com.estebvac.urdudeutsch

import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Test

class CourseCatalogTest {
    @Test
    fun courseContainsTenOrderedLessons() {
        assertEquals((1..10).toList(), CourseCatalog.lessons.map(Lesson::id))
    }

    @Test
    fun everyLessonHasUsableContentAndValidQuiz() {
        CourseCatalog.lessons.forEach { lesson ->
            assertTrue(lesson.objective.isNotBlank())
            assertTrue(lesson.grammar.isNotBlank())
            assertTrue(lesson.dialogue.size >= 4)
            assertTrue(lesson.vocabulary.size >= 6)
            assertEquals(3, lesson.questions.size)
            lesson.questions.forEach { question ->
                assertTrue(question.options.size >= 3)
                assertTrue(question.correctIndex in question.options.indices)
            }
        }
    }

    @Test
    fun vocabularyContainsUrduGermanAndRomanization() {
        CourseCatalog.allVocabulary.forEach { phrase ->
            assertTrue(phrase.urdu.isNotBlank())
            assertTrue(phrase.german.isNotBlank())
            assertTrue(phrase.romanization.isNotBlank())
        }
    }
}
