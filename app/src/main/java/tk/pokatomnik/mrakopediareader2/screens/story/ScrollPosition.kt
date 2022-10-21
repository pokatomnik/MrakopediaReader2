package tk.pokatomnik.mrakopediareader2.screens.story

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import kotlinx.coroutines.launch
import tk.pokatomnik.mrakopediareader2.services.db.rememberDatabase

@Composable
fun ScrollPosition(
    pageTitle: String,
    content: @Composable (scrollPosition: Int) -> Unit
) {
    val mrakopediaDatabase = rememberDatabase()
    val historyDAO = mrakopediaDatabase.historyDAO()
    val (scrollPosition, setScrollPosition) = remember { mutableStateOf<Int?>(null) }

    LaunchedEffect(pageTitle) {
        setScrollPosition(null)
        launch {
            val historyItem = historyDAO.getByTitle(pageTitle)
            setScrollPosition(historyItem?.scrollPosition ?: 0)
        }
    }

    if (scrollPosition != null) {
        content(scrollPosition)
    }
}