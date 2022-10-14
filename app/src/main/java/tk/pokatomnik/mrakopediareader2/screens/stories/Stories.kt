package tk.pokatomnik.mrakopediareader2.screens.stories

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import tk.pokatomnik.mrakopediareader2.services.index.rememberMrakopediaIndex
import tk.pokatomnik.mrakopediareader2.ui.components.LazyList
import tk.pokatomnik.mrakopediareader2.ui.components.LazyListItem
import tk.pokatomnik.mrakopediareader2.ui.components.PageContainer
import tk.pokatomnik.mrakopediareader2.ui.components.PageTitle

@Composable
fun Stories(
    selectedCategoryTitle: String,
    onSelectPage: (pageTitle: String) -> Unit
) {
    val mrakopediaIndex = rememberMrakopediaIndex()
    val category = mrakopediaIndex.getCategory(selectedCategoryTitle)

    val lazyListItems = remember(category) {
        category.pages.map {
            val rating = it.rating
            val voted = it.voted
            val readingTime = it.charactersInPage / 1500
            val description = "Время: $readingTime | Рейтинг: $rating | Голосов: $voted"
            LazyListItem(
                id = it.title,
                title = it.title,
                description = description
            )
        }
    }

    return PageContainer(
        header = {
            PageTitle(title = "Категория: $selectedCategoryTitle")
        }
    ) {
        LazyList(list = lazyListItems, onClick = {
            onSelectPage(it.title)
        })
    }
}