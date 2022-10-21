package tk.pokatomnik.mrakopediareader2.screens.history

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import kotlinx.coroutines.launch
import tk.pokatomnik.mrakopediareader2.services.db.dao.history.HistoryItem
import tk.pokatomnik.mrakopediareader2.services.db.rememberDatabase
import tk.pokatomnik.mrakopediareader2.services.index.rememberMrakopediaIndex
import tk.pokatomnik.mrakopediareader2.ui.components.LazyList
import tk.pokatomnik.mrakopediareader2.ui.components.LazyListItem
import tk.pokatomnik.mrakopediareader2.ui.components.PageContainer
import tk.pokatomnik.mrakopediareader2.ui.components.PageTitle

@Composable
fun History(
    onSelectPage: (pageTitle: String) -> Unit,
) {
    val mrakopediaIndex = rememberMrakopediaIndex()
    val generalCategory = mrakopediaIndex.getCategory(mrakopediaIndex.getGeneralCategoryTitle())
    val mrakopediaDatabase = rememberDatabase()
    val historyDAO = mrakopediaDatabase.historyDAO()

    val (historyItems, setHistoryItems) = remember { mutableStateOf<List<HistoryItem>?>(null) }

    LaunchedEffect(Unit) {
        launch {
            val currentHistoryItems = historyDAO.getAll()
            setHistoryItems(currentHistoryItems)
        }
    }

    PageContainer(
        header = { PageTitle(title = "История") }
    ) {
        if (historyItems == null) {
            return@PageContainer
        }

        if (historyItems.isEmpty()) {
            Column(
                modifier = Modifier.fillMaxSize(),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                Text("В Истории пока пусто.")
            }
            return@PageContainer
        }

        val lazyListItems = remember(historyItems) {
            historyItems.fold(mutableListOf<LazyListItem>()) { acc, current ->
                acc.apply {
                    val pageMeta = generalCategory.getPageMetaByTitle(current.title)
                    if (pageMeta != null) {
                        val lazyListItem = LazyListItem(
                            id = pageMeta.title,
                            title = pageMeta.title,
                            description = pageMeta.formatDescription()
                        )
                        add(lazyListItem)
                    }
                }
            }
        }

        LazyList(
            list = lazyListItems,
            onClick = { onSelectPage(it.title) }
        )
    }
}