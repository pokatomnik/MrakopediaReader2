package tk.pokatomnik.mrakopediareader2.screens.stories

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import kotlinx.coroutines.launch
import tk.pokatomnik.mrakopediareader2.services.db.dao.favoritecategories.FavoriteCategory
import tk.pokatomnik.mrakopediareader2.services.db.rememberDatabase
import tk.pokatomnik.mrakopediareader2.services.index.rememberMrakopediaIndex
import tk.pokatomnik.mrakopediareader2.services.preferences.rememberPreferences
import tk.pokatomnik.mrakopediareader2.ui.components.*

@OptIn(ExperimentalMaterialApi::class)
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
    
    val drawerState = rememberBottomDrawerState(initialValue = BottomDrawerValue.Closed)

    val closeDrawer = {
        coroutineScope.launch { drawerState.close() }
    }

    BottomSheet(
        drawerState = drawerState,
        drawerContent = {
            Column(modifier = Modifier.fillMaxWidth()) {
                SelectableRow(
                    selected = sorting.sortType == SortingType.ALPHA && sorting.sortDirection == SortDirection.ASC,
                    onClick = { setSorting(AlphaASC()); closeDrawer() },
                    content = { Text("??????????????: ???? ??????????????????????") }
                )
                SelectableRow(
                    selected = sorting.sortType == SortingType.ALPHA && sorting.sortDirection == SortDirection.DESC,
                    onClick = { setSorting(AlphaDESC()); closeDrawer() },
                    content = { Text("??????????????: ???? ????????????????") }
                )
                SelectableRow(
                    selected = sorting.sortType == SortingType.RATING && sorting.sortDirection == SortDirection.ASC,
                    onClick = { setSorting(RatingASC()); closeDrawer() },
                    content = { Text("??????????????: ???? ??????????????????????") }
                )
                SelectableRow(
                    selected = sorting.sortType == SortingType.RATING && sorting.sortDirection == SortDirection.DESC,
                    onClick = { setSorting(RatingDESC()); closeDrawer() },
                    content = { Text("??????????????: ???? ????????????????") }
                )
                SelectableRow(
                    selected = sorting.sortType == SortingType.VOTED && sorting.sortDirection == SortDirection.ASC,
                    onClick = { setSorting(VotedASC()); closeDrawer() },
                    content = { Text("????????????: ???? ??????????????????????") }
                )
                SelectableRow(
                    selected = sorting.sortType == SortingType.VOTED && sorting.sortDirection == SortDirection.DESC,
                    onClick = { setSorting(VotedDESC()); closeDrawer() },
                    content = { Text("????????????: ???? ????????????????") }
                )
                SelectableRow(
                    selected = sorting.sortType == SortingType.TIME && sorting.sortDirection == SortDirection.ASC,
                    onClick = { setSorting(TimeASC()); closeDrawer() },
                    content = { Text("??????????: ???? ??????????????????????") }
                )
                SelectableRow(
                    selected = sorting.sortType == SortingType.TIME && sorting.sortDirection == SortDirection.DESC,
                    onClick = { setSorting(TimeDESC()); closeDrawer() },
                    content = { Text("??????????: ???? ????????????????") }
                )
            }
        },
        content = {
            PageContainer(
                priorButton = {
                    IconButton(onClick = { updateFavoriteStatus(!isFavorite) }) {
                        Icon(
                            imageVector = if (isFavorite) Icons.Filled.Favorite else Icons.Filled.FavoriteBorder,
                            contentDescription = if (isFavorite) "?? ??????????????????" else "???????????????? ?? ??????????????????"
                        )
                    }
                },
                header = { PageTitle(title = selectedCategoryTitle) },
                trailingButton = {
                    IconButton(
                        onClick = {
                            coroutineScope.launch { if (drawerState.isOpen) drawerState.close() else drawerState.open() }
                        },
                        content = {
                            Icon(
                                imageVector = Icons.Filled.Sort,
                                contentDescription = "????????????????????"
                            )
                        }
                    )
                }
            ) {
                LazyList(list = lazyListItems, onClick = {
                    onSelectPage(it.title)
                })
            }
        }
    )
}