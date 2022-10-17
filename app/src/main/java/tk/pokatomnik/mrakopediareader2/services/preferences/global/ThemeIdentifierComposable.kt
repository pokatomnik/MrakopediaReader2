package tk.pokatomnik.mrakopediareader2.services.preferences.global

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.compositionLocalOf
import tk.pokatomnik.mrakopediareader2.services.preferences.rememberPreferences

val LocalThemeIdentifier = compositionLocalOf<MutableState<ThemeIdentifier>?> { null }

@Composable
fun rememberThemeIdentifier(): MutableState<ThemeIdentifier> {
    val globalPreferences = rememberPreferences().globalPreferences
    val state = LocalThemeIdentifier.current
    LaunchedEffect(state?.value) {
        globalPreferences.themeIdentifier = state?.value ?: ThemeIdentifier.AUTO
    }
    if (state == null) {
        throw Error("Failed to initialize Theme Identifier")
    }
    return state
}