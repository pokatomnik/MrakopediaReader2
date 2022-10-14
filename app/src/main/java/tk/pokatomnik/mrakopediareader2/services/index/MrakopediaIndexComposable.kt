package tk.pokatomnik.mrakopediareader2.services.index

import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel

@Composable
fun rememberMrakopediaIndex(): MrakopediaIndex {
    return hiltViewModel<MrakopediaIndexViewModel>().mrakopediaIndex
}