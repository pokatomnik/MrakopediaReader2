package tk.pokatomnik.mrakopediareader2.services.preferences.page

import androidx.compose.runtime.*
import tk.pokatomnik.mrakopediareader2.services.preferences.rememberPreferences

@Composable
fun rememberColorPresetID(): MutableState<String> {
    val pagePreferences = rememberPreferences().pagePreferences
    val state = remember {
        mutableStateOf(pagePreferences.colorPresetID ?: pagePreferences.defaultColorPresetID)
    }
    LaunchedEffect(state.value) {
        pagePreferences.colorPresetID = state.value
    }
    return state
}