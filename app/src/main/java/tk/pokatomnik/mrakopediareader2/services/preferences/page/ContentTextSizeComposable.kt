package tk.pokatomnik.mrakopediareader2.services.preferences.page

import androidx.compose.runtime.*
import tk.pokatomnik.mrakopediareader2.services.preferences.rememberPreferences

@Composable
fun rememberContentTextSize(): MutableState<Int> {
    val pagePreferences = rememberPreferences().pagePreferences
    val state = remember { mutableStateOf(pagePreferences.contentTextSize) }
    LaunchedEffect(state.value) {
        pagePreferences.contentTextSize = state.value
    }
    return state
}