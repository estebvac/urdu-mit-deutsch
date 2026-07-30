package com.estebvac.urdudeutsch

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.VolumeUp
import androidx.compose.material3.Button
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
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
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ReviewScreen(
    state: LearningUiState,
    onBack: () -> Unit,
    onAnswer: (Boolean) -> Unit,
    onSpeak: (String) -> Unit,
) {
    val words = remember { CourseCatalog.allVocabulary }
    var index by remember { mutableIntStateOf(0) }
    var revealed by remember { mutableStateOf(false) }
    val current = words[index % words.size]

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Wortschatztrainer") },
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
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center,
        ) {
            ElevatedCard(Modifier.fillMaxWidth()) {
                Column(
                    modifier = Modifier.padding(24.dp),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.spacedBy(14.dp),
                ) {
                    Text(current.urdu, fontSize = (38 * state.textScale).sp, textAlign = TextAlign.Center)
                    IconButton(onClick = { onSpeak(current.urdu) }) {
                        Icon(Icons.Default.VolumeUp, contentDescription = "Wort anhören")
                    }
                    if (revealed) {
                        if (state.showRomanization) Text(current.romanization)
                        Text(current.german, style = MaterialTheme.typography.headlineSmall)
                        Row(horizontalArrangement = Arrangement.spacedBy(10.dp)) {
                            OutlinedButton(onClick = {
                                onAnswer(false)
                                index += 1
                                revealed = false
                            }) { Text("Noch üben") }
                            Button(onClick = {
                                onAnswer(true)
                                index += 1
                                revealed = false
                            }) { Text("Gewusst") }
                        }
                    } else {
                        Button(onClick = { revealed = true }) { Text("Lösung zeigen") }
                    }
                    Text("Wiederholt: ${state.reviewedWords} · Gewusst: ${state.knownWords}")
                }
            }
        }
    }
}
