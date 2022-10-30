package tk.pokatomnik.mrakopediareader2.screens.stories

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material.Divider
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.launch
import tk.pokatomnik.mrakopediareader2.services.db.dao.favoritecategories.FavoriteCategory
import tk.pokatomnik.mrakopediareader2.services.db.rememberDatabase
import tk.pokatomnik.mrakopediareader2.services.index.rememberMrakopediaIndex
import tk.pokatomnik.mrakopediareader2.services.preferences.rememberPreferences
import tk.pokatomnik.mrakopediareader2.ui.components.*

@Composable
fun Stories(
    selectedCategoryTitle: String,
    onSelectPage: (pageTitle: String) -> Unit
) {
    val coroutineScope = rememberCoroutineScope()
    val mrakopediaDatabase = rememberDatabase()
    val favoriteCategoriesDAO = mrakopediaDatabase.favoriteCategoriesDAO()
    val preferences = rememberPreferences()
    val (sorting, setSorting) = remember {
        mutableStateOf(
            sortingMap[preferences.storiesPreferences.sortingType] ?: AlphaASC()
        )
    }
    val mrakopediaIndex = rememberMrakopediaIndex()
    val category = mrakopediaIndex.getCategory(selectedCategoryTitle)

    val (isFavorite, setIsFavorite) = remember { mutableStateOf(false) }

    LaunchedEffect(selectedCategoryTitle) {
        val favorite = favoriteCategoriesDAO.has(selectedCategoryTitle)
        setIsFavorite(favorite != null)
    }

    LaunchedEffect(sorting) {
        preferences.storiesPreferences.sortingType = sorting.toString()
    }

    val lazyListItems = remember(category, sorting) {
        sorting.sorted(category.pages)
            .map {
                LazyListItem(
                    id = it.title,
                    title = it.title,
                    description = it.formatDescription()
                )
            }
    }

    val updateFavoriteStatus: (isFavorite: Boolean) -> Unit = { newFavoriteStatus ->
        setIsFavorite(newFavoriteStatus)
        coroutineScope.launch {
            if (newFavoriteStatus) {
                favoriteCategoriesDAO.add(FavoriteCategory(selectedCategoryTitle))
            } else {
                favoriteCategoriesDAO.delete(FavoriteCategory(selectedCategoryTitle))
            }
        }
    }

    return PageContainer(
        header = { PageTitle(title = "Категория: $selectedCategoryTitle") },
        priorButton = {
            IconButton(onClick = { updateFavoriteStatus(!isFavorite) }) {
                Icon(
                    imageVector = if (isFavorite) Icons.Filled.Favorite else Icons.Filled.FavoriteBorder,
                    contentDescription = if (isFavorite) "В избранном" else "Добавить в избранное"
                )
            }
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