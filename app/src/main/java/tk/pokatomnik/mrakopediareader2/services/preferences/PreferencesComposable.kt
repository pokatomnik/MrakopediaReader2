package tk.pokatomnik.mrakopediareader2.services.preferences

import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel

@Composable
fun rememberPreferences(): Preferences {
    return hiltViewModel<PreferencesViewModel>().preferences
}