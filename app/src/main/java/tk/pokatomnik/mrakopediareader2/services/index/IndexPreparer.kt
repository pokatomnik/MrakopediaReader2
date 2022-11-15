package tk.pokatomnik.mrakopediareader2.services.index

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.withContext

@Composable
fun IndexPreparer(
    loadingView: @Composable () -> Unit,
    screenView: @Composable () -> Unit,
) {
    val mrakopediaIndex = rememberMrakopediaIndex()
    val (prepared, setPrepared) = remember { mutableStateOf(false) }

    LaunchedEffect(Unit) {
        withContext(Dispatchers.Default + SupervisorJob()) {
            mrakopediaIndex.warmUpIndex()
            setPrepared(true)
        }
    }

    if (!prepared) {
        loadingView()
    } else {
        screenView()
    }
}