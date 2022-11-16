package tk.pokatomnik.mrakopediareader2.services.preferences.page

import androidx.compose.runtime.*
import tk.pokatomnik.mrakopediareader2.services.preferences.rememberPreferences

@Composable
fun rememberContentFontFamily(): MutableState<String> {
    val pagePreferences = rememberPreferences().pagePreferences
    val state = remember {
        mutableStateOf(pagePreferences.contentFontFamily ?: pagePreferences.defaultFontKey)
    }
    LaunchedEffect(state.value) {
        pagePreferences.contentFontFamily = state.value
    }
    return state
}