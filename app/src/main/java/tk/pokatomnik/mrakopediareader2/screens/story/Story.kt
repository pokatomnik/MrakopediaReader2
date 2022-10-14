package tk.pokatomnik.mrakopediareader2.screens.story

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import tk.pokatomnik.mrakopediareader2.services.index.rememberMrakopediaIndex
import tk.pokatomnik.mrakopediareader2.ui.components.PageContainer
import tk.pokatomnik.mrakopediareader2.ui.components.PageTitle

@Composable
fun Story(
    selectedCategoryTitle: String,
    selectedPageTitle: String
) {
    val content = rememberMrakopediaIndex()
        .getCategory(selectedCategoryTitle)
        .getPageContentByTitle(selectedPageTitle)

    return PageContainer(
        header = {
            PageTitle(title = selectedPageTitle)
        }
    ) {
        Column(modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 16.dp)
            .verticalScroll(rememberScrollState())
        ) {
            Text(
                textAlign = TextAlign.Justify,
                text = content
            )
        }
    }
}