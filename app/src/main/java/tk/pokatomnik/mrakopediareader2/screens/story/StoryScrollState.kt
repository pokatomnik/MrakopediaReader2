package tk.pokatomnik.mrakopediareader2.screens.story

import androidx.compose.foundation.ScrollState
import androidx.compose.foundation.rememberScrollState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.rememberCoroutineScope
import kotlinx.coroutines.launch
import tk.pokatomnik.mrakopediareader2.services.db.rememberDatabase

@Composable
fun rememberStoryScrollState(
    scrollPosition: Int,
    selectedPageTitle: String,
): ScrollState {
    val historyDAO = rememberDatabase().historyDAO()
    val coroutineScope = rememberCoroutineScope()
    val scrollState = rememberScrollState()

    LaunchedEffect(scrollPosition) {
        scrollState.animateScrollTo(scrollPosition)
    }

    if (scrollState.isScrollInProgress) {
        DisposableEffect(Unit) {
            onDispose {
                coroutineScope.launch {
                    historyDAO.setScrollPosition(
                        selectedPageTitle,
                        scrollState.value
                    )
                }
            }
        }
    }

    return scrollState
}