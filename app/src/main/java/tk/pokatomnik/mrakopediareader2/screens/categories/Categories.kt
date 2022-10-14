package tk.pokatomnik.mrakopediareader2.screens.categories

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import tk.pokatomnik.mrakopediareader2.services.index.rememberMrakopediaIndex
import tk.pokatomnik.mrakopediareader2.ui.components.LazyList
import tk.pokatomnik.mrakopediareader2.ui.components.LazyListItem
import tk.pokatomnik.mrakopediareader2.ui.components.PageContainer
import tk.pokatomnik.mrakopediareader2.ui.components.PageTitle

@Composable
fun Categories(
    onSelectCategoryTitle: (categoryTitle: String) -> Unit,
) {
    val mrakopediaIndex = rememberMrakopediaIndex()
    val lazyListItems = remember(mrakopediaIndex) {
        mrakopediaIndex.getCategoryNames().toList().map {
            val category = mrakopediaIndex.getCategory(it)
            val avgRating = category.avgRating
            val avgVoted = category.avgRating
            val pagesInCategory = category.size
            val description = "Историй: $pagesInCategory | Рейтинг: $avgRating | Голосов: $avgVoted"
            LazyListItem(
                id = it,
                title = it,
                description = description
            )
        }
    }
    return PageContainer(
        header = {
            PageTitle(title = "Категории")
        }
    ) {
        LazyList(
            list = lazyListItems,
            onClick = {
                onSelectCategoryTitle(it.title)
            }
        )
    }
}