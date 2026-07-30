package com.estebvac.urdudeutsch

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.VolumeUp
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.semantics.heading
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LessonScreen(
    lesson: Lesson,
    state: LearningUiState,
    onBack: () -> Unit,
    onSpeak: (String) -> Unit,
    onQuiz: () -> Unit,
) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Lektion ${lesson.id}") },
                navigationIcon = {
                    IconButton(onClick = onBack) {
                        Icon(Icons.Default.ArrowBack, contentDescription = "Zurück")
                    }
                },
            )
        },
    ) { padding ->
        LazyColumn(
            modifier = Modifier.fillMaxSize().padding(padding),
            contentPadding = PaddingValues(16.dp),
            verticalArrangement = Arrangement.spacedBy(14.dp),
        ) {
            item {
                Text(
                    lesson.title,
                    style = MaterialTheme.typography.headlineMedium,
                    modifier = Modifier.semantics { heading() },
                )
                Text(
                    lesson.urduTitle,
                    fontSize = (32 * state.textScale).sp,
                    textAlign = TextAlign.End,
                    modifier = Modifier.fillMaxWidth(),
                )
                Text(lesson.objective)
            }
            item { SectionCard("Grammatik") { Text(lesson.grammar) } }
            item {
                Text(
                    "Dialog",
                    style = MaterialTheme.typography.headlineSmall,
                    modifier = Modifier.semantics { heading() },
                )
            }
            items(lesson.dialogue) { PhraseCard(it, state, onSpeak) }
            item {
                Text(
                    "Wortschatz",
                    style = MaterialTheme.typography.headlineSmall,
                    modifier = Modifier.semantics { heading() },
                )
            }
            items(lesson.vocabulary) { PhraseCard(it, state, onSpeak) }
            item {
                Button(
                    onClick = onQuiz,
                    modifier = Modifier.fillMaxWidth().heightIn(min = 52.dp),
                ) { Text("Mini-Test starten") }
            }
        }
    }
}

@Composable
private fun PhraseCard(
    phrase: Phrase,
    state: LearningUiState,
    onSpeak: (String) -> Unit,
) {
    ElevatedCard(Modifier.fillMaxWidth()) {
        Column(
            Modifier.padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(6.dp),
        ) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                IconButton(onClick = { onSpeak(phrase.urdu) }) {
                    Icon(Icons.Default.VolumeUp, contentDescription = "Urdu-Satz anhören")
                }
                Text(
                    phrase.urdu,
                    fontSize = (29 * state.textScale).sp,
                    textAlign = TextAlign.End,
                    modifier = Modifier.weight(1f),
                )
            }
            if (state.showRomanization) {
                Text(phrase.romanization, style = MaterialTheme.typography.bodyMedium)
            }
            Text(phrase.german, style = MaterialTheme.typography.titleSmall)
        }
    }
}

@Composable
private fun SectionCard(
    title: String,
    content: @Composable ColumnScope.() -> Unit,
) {
    ElevatedCard(Modifier.fillMaxWidth()) {
        Column(
            Modifier.padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp),
        ) {
            Text(
                title,
                style = MaterialTheme.typography.titleLarge,
                modifier = Modifier.semantics { heading() },
            )
            content()
        }
    }
}
