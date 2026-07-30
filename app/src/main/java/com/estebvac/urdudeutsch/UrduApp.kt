package com.estebvac.urdudeutsch

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.semantics.heading
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle

private sealed interface Screen {
    data object Home : Screen
    data class LessonDetail(val lessonId: Int) : Screen
    data class Quiz(val lessonId: Int) : Screen
    data object Review : Screen
    data object Settings : Screen
}

@Composable
fun UrduApp(viewModel: LearningViewModel, onSpeak: (String) -> Unit) {
    val state by viewModel.uiState.collectAsStateWithLifecycle()
    var screen by remember { mutableStateOf<Screen>(Screen.Home) }

    when (val current = screen) {
        Screen.Home -> HomeScreen(
            state = state,
            onLesson = { screen = Screen.LessonDetail(it) },
            onReview = { screen = Screen.Review },
            onSettings = { screen = Screen.Settings },
        )
        is Screen.LessonDetail -> LessonScreen(
            lesson = requireNotNull(CourseCatalog.lesson(current.lessonId)),
            state = state,
            onBack = { screen = Screen.Home },
            onSpeak = onSpeak,
            onQuiz = { screen = Screen.Quiz(current.lessonId) },
        )
        is Screen.Quiz -> QuizScreen(
            lesson = requireNotNull(CourseCatalog.lesson(current.lessonId)),
            onBack = { screen = Screen.LessonDetail(current.lessonId) },
            onComplete = { score ->
                viewModel.completeLesson(current.lessonId, score)
                screen = Screen.Home
            },
        )
        Screen.Review -> ReviewScreen(
            state = state,
            onBack = { screen = Screen.Home },
            onAnswer = viewModel::recordReview,
            onSpeak = onSpeak,
        )
        Screen.Settings -> SettingsScreen(
            state = state,
            onBack = { screen = Screen.Home },
            onRomanization = viewModel::setRomanization,
            onTextScale = viewModel::setTextScale,
            onReset = viewModel::reset,
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun HomeScreen(
    state: LearningUiState,
    onLesson: (Int) -> Unit,
    onReview: () -> Unit,
    onSettings: () -> Unit,
) {
    Scaffold(topBar = { TopAppBar(title = { Text("Urdu mit Deutsch") }) }) { padding ->
        LazyColumn(
            modifier = Modifier.fillMaxSize().padding(padding),
            contentPadding = PaddingValues(16.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp),
        ) {
            item {
                ElevatedCard(Modifier.fillMaxWidth()) {
                    Column(
                        Modifier.padding(20.dp),
                        verticalArrangement = Arrangement.spacedBy(10.dp),
                    ) {
                        Text(
                            text = "خوش آمدید",
                            fontSize = 34.sp,
                            textAlign = TextAlign.End,
                            modifier = Modifier.fillMaxWidth(),
                        )
                        Text(
                            "Willkommen! Lerne Urdu Schritt für Schritt auf Deutsch.",
                            style = MaterialTheme.typography.titleMedium,
                        )
                        LinearProgressIndicator(
                            progress = { state.completionFraction },
                            modifier = Modifier.fillMaxWidth(),
                        )
                        Text("${state.completedLessonIds.size} von ${state.lessons.size} Lektionen abgeschlossen")
                        Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                            Button(
                                onClick = onReview,
                                modifier = Modifier.weight(1f).heightIn(min = 48.dp),
                            ) { Text("Wiederholen") }
                            OutlinedButton(
                                onClick = onSettings,
                                modifier = Modifier.weight(1f).heightIn(min = 48.dp),
                            ) { Text("Einstellungen") }
                        }
                    }
                }
            }
            item {
                Text(
                    "A0-Kurs",
                    style = MaterialTheme.typography.headlineSmall,
                    modifier = Modifier.semantics { heading() },
                )
            }
            items(state.lessons, key = Lesson::id) { lesson ->
                val complete = lesson.id in state.completedLessonIds
                ElevatedCard(
                    onClick = { onLesson(lesson.id) },
                    modifier = Modifier.fillMaxWidth(),
                ) {
                    Row(
                        Modifier.padding(16.dp),
                        verticalAlignment = Alignment.CenterVertically,
                    ) {
                        Column(
                            Modifier.weight(1f),
                            verticalArrangement = Arrangement.spacedBy(4.dp),
                        ) {
                            Text(
                                "Lektion ${lesson.id}: ${lesson.title}",
                                style = MaterialTheme.typography.titleMedium,
                            )
                            Text(
                                lesson.urduTitle,
                                fontSize = 24.sp,
                                textAlign = TextAlign.End,
                                modifier = Modifier.fillMaxWidth(),
                            )
                            Text(lesson.objective, style = MaterialTheme.typography.bodyMedium)
                        }
                        Text(
                            if (complete) "✓ ${state.bestScores[lesson.id] ?: 0}%" else "›",
                            style = MaterialTheme.typography.titleMedium,
                        )
                    }
                }
            }
        }
    }
}
