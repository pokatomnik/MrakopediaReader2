package tk.pokatomnik.mrakopediareader2.screens.categories

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material.Divider
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import tk.pokatomnik.mrakopediareader2.services.index.rememberMrakopediaIndex
import tk.pokatomnik.mrakopediareader2.ui.components.*
import tk.pokatomnik.mrakopediareader2.ui.components.PageTitle

@Composable
fun Categories(
    onSelectCategoryTitle: (categoryTitle: String) -> Unit,
) {
    val (sorting, setSorting) = remember { mutableStateOf<Sorting>(AlphaASC()) }
    val mrakopediaIndex = rememberMrakopediaIndex()

    val lazyListItems = remember(mrakopediaIndex, sorting) {
        sorting.sorted(mrakopediaIndex.getCategories().toList()).map { category ->
            val avgRating = category.avgRating
            val avgVoted = category.avgRating
            val pagesInCategory = category.size
            val description = "Историй: $pagesInCategory | Рейтинг: $avgRating | Голосов: $avgVoted"
            LazyListItem(
                id = category.name,
                title = category.name,
                description = description
            )
        }
    }

    PageContainer(
        header = {
            PageTitle(title = "Категории")
        }
    ) {
        Row(
            modifier = Modifier
                .height(48.dp)
                .fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceAround
        ) {
            SortButton(
                icon = Icons.Filled.SortByAlpha,
                sortingDescription = "По алфавиту",
                direction = if (sorting.sortType == SortingType.ALPHA) sorting.sortDirection else null,
                onSortDirectionChange = {
                    setSorting(when (it) {
                        SortDirection.DESC -> AlphaDESC()
                        else -> AlphaASC()
                    })
                }
            )
            SortButton(
                icon = Icons.Filled.Star,
                sortingDescription = "По рейтингу",
                direction = if (sorting.sortType == SortingType.RATING) sorting.sortDirection else null,
                onSortDirectionChange = {
                    setSorting(when (it) {
                        SortDirection.DESC -> RatingDESC()
                        else -> RatingASC()
                    })
                }
            )
            SortButton(
                icon = Icons.Filled.People,
                sortingDescription = "По голосам",
                direction = if (sorting.sortType == SortingType.VOTED) sorting.sortDirection else null,
                onSortDirectionChange = {
                    setSorting(when (it) {
                        SortDirection.DESC -> VotedDESC()
                        else -> VotedASC()
                    })
                }
            )
            SortButton(
                icon = Icons.Filled.Pin,
                sortingDescription = "По количеству историй",
                direction = if (sorting.sortType == SortingType.QUANTITY) sorting.sortDirection else null,
                onSortDirectionChange = {
                    setSorting(when (it) {
                        SortDirection.DESC -> QuantityDESC()
                        else -> QuantityASC()
                    })
                }
            )
        }
        Divider(modifier = Modifier.fillMaxWidth())
        LazyList(
            list = lazyListItems,
            onClick = {
                onSelectCategoryTitle(it.title)
            }
        )
    }
}