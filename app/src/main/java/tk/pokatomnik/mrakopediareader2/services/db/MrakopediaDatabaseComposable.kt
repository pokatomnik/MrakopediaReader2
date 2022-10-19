package tk.pokatomnik.mrakopediareader2.services.db

import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel

@Composable
fun rememberDatabase(): MrakopediaDatabase {
    return hiltViewModel<MrakopediaDatabaseViewModel>().database
}
