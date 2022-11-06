package tk.pokatomnik.mrakopediareader2.services.readonlyparams

import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel

@Composable
fun rememberReadonlyParameters(): ReadonlyParameters {
    return hiltViewModel<ReadonlyParametersViewModel>().readonlyParameters
}