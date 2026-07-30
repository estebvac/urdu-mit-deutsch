package com.estebvac.urdudeutsch

import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SettingsScreen(
    state: LearningUiState,
    onBack: () -> Unit,
    onRomanization: (Boolean) -> Unit,
    onTextScale: (Float) -> Unit,
    onReset: () -> Unit,
) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Einstellungen") },
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
            verticalArrangement = Arrangement.spacedBy(18.dp),
        ) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Column(Modifier.weight(1f)) {
                    Text("Umschrift anzeigen", style = MaterialTheme.typography.titleMedium)
                    Text("Zeigt eine lateinische Aussprachehilfe.")
                }
                Switch(
                    checked = state.showRomanization,
                    onCheckedChange = onRomanization,
                )
            }
            Text("Urdu-Textgröße", style = MaterialTheme.typography.titleMedium)
            Slider(
                value = state.textScale,
                onValueChange = onTextScale,
                valueRange = 0.85f..1.35f,
            )
            HorizontalDivider()
            OutlinedButton(
                onClick = onReset,
                modifier = Modifier.fillMaxWidth().heightIn(min = 48.dp),
            ) { Text("Fortschritt zurücksetzen") }
            Text("Die App arbeitet offline und verwendet keine Netzwerkberechtigung.")
        }
    }
}
