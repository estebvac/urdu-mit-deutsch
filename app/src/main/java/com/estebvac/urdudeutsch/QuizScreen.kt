package com.estebvac.urdudeutsch

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun QuizScreen(lesson: Lesson, onBack: () -> Unit, onComplete: (Int) -> Unit) {
    var index by remember { mutableIntStateOf(0) }
    var selected by remember { mutableStateOf<Int?>(null) }
    var correct by remember { mutableIntStateOf(0) }
    val current = lesson.questions[index]

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Mini-Test") },
                navigationIcon = {
                    IconButton(onClick = onBack) {
                        Icon(Icons.Default.ArrowBack, contentDescription = "Zurück")
                    }
                },
            )
        },
    ) { padding ->
        Column(
            modifier = Modifier.fillMaxSize().padding(padding).padding(20.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp),
        ) {
            LinearProgressIndicator(
                progress = { (index + 1f) / lesson.questions.size },
                modifier = Modifier.fillMaxWidth(),
            )
            Text("Frage ${index + 1} von ${lesson.questions.size}")
            Text(current.prompt, style = MaterialTheme.typography.headlineSmall)
            current.options.forEachIndexed { optionIndex, option ->
                OutlinedButton(
                    onClick = { selected = optionIndex },
                    enabled = selected == null,
                    modifier = Modifier.fillMaxWidth().heightIn(min = 52.dp),
                ) { Text(option, textAlign = TextAlign.Center) }
            }
            selected?.let { answer ->
                val isCorrect = answer == current.correctIndex
                Text(if (isCorrect) "Richtig! ${current.explanation}" else "Noch nicht. ${current.explanation}")
                Button(
                    onClick = {
                        val nextCorrect = correct + if (isCorrect) 1 else 0
                        if (index == lesson.questions.lastIndex) {
                            onComplete((nextCorrect * 100) / lesson.questions.size)
                        } else {
                            correct = nextCorrect
                            index += 1
                            selected = null
                        }
                    },
                    modifier = Modifier.fillMaxWidth(),
                ) { Text(if (index == lesson.questions.lastIndex) "Abschließen" else "Weiter") }
            }
        }
    }
}
