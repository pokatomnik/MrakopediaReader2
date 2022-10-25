package tk.pokatomnik.mrakopediareader2.screens.favorites

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import tk.pokatomnik.mrakopediareader2.services.index.rememberMrakopediaIndex
import tk.pokatomnik.mrakopediareader2.ui.components.LazyList
import tk.pokatomnik.mrakopediareader2.ui.components.LazyListItem

@Composable
fun GoodStories(
    onStoryClick: (pageTitle: String) -> Unit
) {
    val mrakopediaIndex = rememberMrakopediaIndex()
    val goodStories = mrakopediaIndex.getGoodStories()
    val generalCategory = mrakopediaIndex.getCategory(mrakopediaIndex.getGeneralCategoryTitle())
    val lazyListItems = remember(goodStories) {
        goodStories.fold(mutableListOf<LazyListItem>()) { acc, current ->
            val pageMeta = generalCategory.getPageMetaByTitle(current) ?: return@fold acc
            acc.apply {
                val lazyListItem = LazyListItem(
                    id = current,
                    title = current,
                    description = pageMeta.formatDescription()
                )
                add(lazyListItem)
            }
        }
    }

    LazyList(
        list = lazyListItems,
        onClick = {
            onStoryClick(it.title)
        }
    )
}