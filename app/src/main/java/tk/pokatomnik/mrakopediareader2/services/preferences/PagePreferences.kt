package tk.pokatomnik.mrakopediareader2.services.preferences

import android.content.SharedPreferences
import androidx.compose.runtime.*

class PagePreferences(private val sharedPreferences: SharedPreferences) {
    var contentTextSize: Int = sharedPreferences.getInt(FONT_SIZE_KEY, 14)
        set(value) {
            field = value
            sharedPreferences.edit().putInt(FONT_SIZE_KEY, value).apply()
        }

    val minFontSize = 14

    val maxFontSize = 64

    @Composable
    fun rememberContentTextSize(): MutableState<Int> {
        val state = remember { mutableStateOf(contentTextSize) }
        LaunchedEffect(state.value) {
            contentTextSize = state.value
        }
        return state
    }

    companion object {
        const val FONT_SIZE_KEY = "font:size"
    }
}