package com.estebvac.urdudeutsch

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.weight
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Slider
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
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
