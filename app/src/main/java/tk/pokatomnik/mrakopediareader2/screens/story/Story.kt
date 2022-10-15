package tk.pokatomnik.mrakopediareader2.screens.story

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.selection.SelectionContainer
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Divider
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.google.accompanist.flowlayout.FlowRow
import kotlinx.coroutines.launch
import tk.pokatomnik.mrakopediareader2.services.index.rememberMrakopediaIndex
import tk.pokatomnik.mrakopediareader2.ui.components.PageContainer

@Composable
fun Story(
    selectedCategoryTitle: String,
    selectedPageTitle: String,
    onNavigateToPage: (pageTitle: String) -> Unit,
    onNavigateToCategory: (categoryTitle: String) -> Unit
) {
    val coroutineScope = rememberCoroutineScope()
    val category = rememberMrakopediaIndex()
        .getCategory(selectedCategoryTitle)
    val content = category
        .getPageContentByTitle(selectedPageTitle)
    val pageMeta = category.getPageMetaByTitle(selectedPageTitle)
    val seeAlso = pageMeta?.seeAlso ?: setOf()
    val categories = pageMeta?.categories ?: setOf()

    val scrollState = rememberScrollState()

    return PageContainer {
        Column(modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 16.dp)
            .verticalScroll(scrollState)
        ) {
            SelectionContainer {
                Text(
                    text = selectedPageTitle,
                    style = MaterialTheme.typography.h5,
                    textAlign = TextAlign.Center,
                )
            }
            Divider(modifier = Modifier.fillMaxWidth())
            SelectionContainer {
                Text(
                    textAlign = TextAlign.Justify,
                    text = content
                )
            }
            if (seeAlso.isNotEmpty()) {
                Divider(modifier = Modifier.fillMaxWidth())
                Column(modifier = Modifier.fillMaxWidth()) {
                    Text(
                        text = "См. также:",
                        style = MaterialTheme.typography.h6
                    )
                    FlowRow(modifier = Modifier.fillMaxWidth()) {
                        for (seeAlsoTitle in seeAlso) {
                            ButtonRelated(
                                title = seeAlsoTitle,
                                onClick = {
                                    onNavigateToPage(seeAlsoTitle)
                                    coroutineScope.launch {
                                        scrollState.animateScrollTo(0)
                                    }
                                }
                            )
                        }
                    }
                }
            }
            if (categories.isNotEmpty()) {
                Divider(modifier = Modifier.fillMaxWidth())
                Column(modifier = Modifier.fillMaxWidth()) {
                    Text(
                        text = "Категории:",
                        style = MaterialTheme.typography.h6
                    )
                }
                FlowRow(modifier = Modifier.fillMaxWidth()) {
                    for (categoryTitle in categories) {
                        ButtonRelated(
                            title = categoryTitle,
                            onClick = { onNavigateToCategory(categoryTitle) }
                        )
                    }
                }
            }
        }
    }
}