package com.estebvac.urdudeutsch

import android.os.Bundle
import android.speech.tts.TextToSpeech
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import java.util.Locale

class MainActivity : ComponentActivity(), TextToSpeech.OnInitListener {
    private val viewModel: LearningViewModel by viewModels {
        LearningViewModel.Factory(SharedPreferencesProgressRepository(applicationContext))
    }
    private var textToSpeech: TextToSpeech? = null
    private var urduSpeechAvailable = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        textToSpeech = TextToSpeech(this, this)
        setContent {
            UrduMitDeutschTheme {
                UrduApp(viewModel = viewModel, onSpeak = ::speakUrdu)
            }
        }
    }

    override fun onInit(status: Int) {
        if (status != TextToSpeech.SUCCESS) return
        val engine = textToSpeech ?: return
        val locale = Locale.Builder().setLanguage("ur").setRegion("PK").build()
        urduSpeechAvailable = engine.isLanguageAvailable(locale) >= TextToSpeech.LANG_AVAILABLE
        if (urduSpeechAvailable) {
            engine.language = locale
            engine.setSpeechRate(0.82f)
        }
    }

    private fun speakUrdu(text: String) {
        val engine = textToSpeech
        if (engine == null || !urduSpeechAvailable) {
            Toast.makeText(this, R.string.tts_unavailable, Toast.LENGTH_SHORT).show()
            return
        }
        engine.speak(text, TextToSpeech.QUEUE_FLUSH, null, "urdu_phrase")
    }

    override fun onDestroy() {
        textToSpeech?.stop()
        textToSpeech?.shutdown()
        textToSpeech = null
        super.onDestroy()
    }
}
