package tk.pokatomnik.mrakopediareader2.services.navigation

import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel

@Composable
fun rememberSerializer(): Serializer {
    return hiltViewModel<SerializerViewModel>().serializer
}