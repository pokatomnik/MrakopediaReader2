package tk.pokatomnik.mrakopediareader2.screens.stories

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material.Divider
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.People
import androidx.compose.material.icons.filled.SortByAlpha
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.filled.Timer
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import tk.pokatomnik.mrakopediareader2.services.index.rememberMrakopediaIndex
import tk.pokatomnik.mrakopediareader2.services.preferences.rememberPreferences
import tk.pokatomnik.mrakopediareader2.ui.components.*
import kotlin.math.ceil

@Composable
fun Stories(
    selectedCategoryTitle: String,
    onSelectPage: (pageTitle: String) -> Unit
) {
    val preferences = rememberPreferences()
    val (sorting, setSorting) = remember {
        mutableStateOf(
            sortingMap[preferences.storiesPreferences.sortingType] ?: AlphaASC()
        )
    }
    val mrakopediaIndex = rememberMrakopediaIndex()
    val category = mrakopediaIndex.getCategory(selectedCategoryTitle)

    LaunchedEffect(sorting) {
        preferences.storiesPreferences.sortingType = sorting.toString()
    }

    val lazyListItems = remember(category, sorting) {
        sorting.sorted(category.pages)
            .map {
                val rating = it.rating
                val voted = it.voted
                val readingTime = ceil(it.charactersInPage.toFloat() / 1500).toInt()
                val description =
                    "Время: $readingTime мин. | Рейтинг: ${rating ?: 0} | Голосов: ${voted ?: 0}"
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
                direction = if (sorting.sortType === SortingType.ALPHA) sorting.sortDirection else null,
                onSortDirectionChange = {
                    setSorting(
                        when (it) {
                            SortDirection.DESC -> AlphaDESC()
                            else -> AlphaASC()
                        }
                    )
                }
            )
            SortButton(
                icon = Icons.Filled.Star,
                sortingDescription = "По рейтингу",
                direction = if (sorting.sortType === SortingType.RATING) sorting.sortDirection else null,
                onSortDirectionChange = {
                    setSorting(
                        when (it) {
                            SortDirection.DESC -> RatingDESC()
                            else -> RatingASC()
                        }
                    )
                }
            )
            SortButton(
                icon = Icons.Filled.People,
                sortingDescription = "По голосам",
                direction = if (sorting.sortType === SortingType.VOTED) sorting.sortDirection else null,
                onSortDirectionChange = {
                    setSorting(
                        when (it) {
                            SortDirection.DESC -> VotedDESC()
                            else -> VotedASC()
                        }
                    )
                }
            )
            SortButton(
                icon = Icons.Filled.Timer,
                sortingDescription = "По времени прочтения",
                direction = if (sorting.sortType === SortingType.TIME) sorting.sortDirection else null,
                onSortDirectionChange = {
                    setSorting(
                        when (it) {
                            SortDirection.DESC -> TimeDESC()
                            else -> TimeASC()
                        }
                    )
                }
            )
        }
        Divider(modifier = Modifier.fillMaxWidth())
        LazyList(list = lazyListItems, onClick = {
            onSelectPage(it.title)
        })
    }
}