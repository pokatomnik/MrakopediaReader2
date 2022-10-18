package tk.pokatomnik.mrakopediareader2.screens.search

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Divider
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.launch
import tk.pokatomnik.mrakopediareader2.services.index.SearchResult
import tk.pokatomnik.mrakopediareader2.services.index.rememberMrakopediaIndex
import tk.pokatomnik.mrakopediareader2.ui.components.LIST_NAV_ITEM_PADDING
import tk.pokatomnik.mrakopediareader2.ui.components.ListNavItem
import tk.pokatomnik.mrakopediareader2.ui.components.PageContainer
import tk.pokatomnik.mrakopediareader2.ui.components.PageTitle
import java.lang.Integer.min

private const val MAX_RESULTS = 10

@Composable
fun SearchResults(
    searchText: String,
    onBackPress: () -> Unit,
    onSelectCategory: (categoryTitle: String) -> Unit,
    onSelectPage: (pageTitle: String) -> Unit,
) {
    val mrakopediaIndex = rememberMrakopediaIndex()
    val (results, setResults) = remember {
        mutableStateOf<SearchResult?>(null)
    }

    LaunchedEffect(searchText) {
        launch {
            val newResults = mrakopediaIndex.globalSearch(searchText)
            setResults(newResults)
        }
    }

    PageContainer(
        headerButton = {
            IconButton(onClick = onBackPress) {
                Icon(
                    imageVector = Icons.Filled.ArrowBack,
                    contentDescription = "Назад к поиску"
                )
            }
        },
        header = {
            PageTitle(title = "Результаты поиска")
        }
    ) {
        if (results?.pageMeta.isNullOrEmpty() && results?.categories.isNullOrEmpty()) {
            Column(
                modifier = Modifier.fillMaxSize(),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                Text(
                    text = if (results == null) "Загрузка..." else "Ничего не найдено, уточните строку поиска"
                )
            }
        } else if (results != null) {
            Column(modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
            ) {
                val pageMetaLimited = results.pageMeta.toList().subList(0, min(
                    MAX_RESULTS - 1,
                    results.pageMeta.size
                ))
                val categoriesLimited = results.categories.toList().subList(0, min(
                    MAX_RESULTS - 1,
                    results.categories.size
                ))
                if (results.categories.isNotEmpty()) {
                    Spacer(modifier = Modifier.fillMaxWidth().height(16.dp))
                    Column {
                        Text(
                            text = "Категории",
                            modifier = Modifier.padding(horizontal = LIST_NAV_ITEM_PADDING.dp)
                        )
                        Divider(modifier = Modifier.fillMaxWidth())
                        for (category in categoriesLimited) {
                            ListNavItem(
                                title = category.name,
                                description = category.formatDescription(),
                                onNavigate = {
                                    onSelectCategory(category.name)
                                }
                            )
                        }
                        if (results.categories.size > categoriesLimited.size) {
                            Text(
                                text = "Показаны не все категории, уточните строку поиска",
                                modifier = Modifier.padding(horizontal = (LIST_NAV_ITEM_PADDING * 2).dp).fillMaxWidth(),
                                textAlign = TextAlign.Center
                            )
                        }
                    }
                }
                if (results.pageMeta.isNotEmpty()) {
                    Spacer(modifier = Modifier.fillMaxWidth().height(16.dp))
                    Column(modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 8.dp)
                    ) {
                        Text(
                            text = "Истории",
                            modifier = Modifier.padding(horizontal = LIST_NAV_ITEM_PADDING.dp)
                        )
                        Divider(modifier = Modifier.fillMaxWidth())
                        for (pageMeta in pageMetaLimited) {
                            ListNavItem(
                                title = pageMeta.title,
                                description = pageMeta.formatDescription(),
                                onNavigate = {
                                    onSelectPage(pageMeta.title)
                                }
                            )
                        }
                        if (results.pageMeta.size > pageMetaLimited.size) {
                            Text(
                                text = "Показаны не все истории, уточните строку поиска",
                                modifier = Modifier.padding(horizontal = (LIST_NAV_ITEM_PADDING * 2).dp).fillMaxWidth(),
                                textAlign = TextAlign.Center
                            )
                        }
                    }
                    Spacer(modifier = Modifier.fillMaxWidth().height(16.dp))
                }
            }
        }
    }
}