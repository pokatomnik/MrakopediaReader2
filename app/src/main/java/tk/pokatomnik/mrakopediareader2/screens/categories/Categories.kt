package tk.pokatomnik.mrakopediareader2.screens.categories

import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.runtime.*
import kotlinx.coroutines.launch
import tk.pokatomnik.mrakopediareader2.services.index.rememberMrakopediaIndex
import tk.pokatomnik.mrakopediareader2.services.preferences.rememberPreferences
import tk.pokatomnik.mrakopediareader2.ui.components.*

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun Categories(
    onPressSearch: () -> Unit,
    onSelectCategoryTitle: (categoryTitle: String) -> Unit,
) {
    val scope = rememberCoroutineScope()
    val preferences = rememberPreferences()
    val (sorting, setSorting) = remember {
        mutableStateOf(
            sortingMap[preferences.categoriesPreferences.sortingType] ?: AlphaASC()
        )
    }
    val mrakopediaIndex = rememberMrakopediaIndex()

    LaunchedEffect(sorting) {
        preferences.categoriesPreferences.sortingType = sorting.toString()
    }

    val lazyListItems = remember(mrakopediaIndex, sorting) {
        sorting.sorted(mrakopediaIndex.getCategories().toList()).map { category ->
            LazyListItem(
                id = category.name,
                title = category.name,
                description = category.formatDescription()
            )
        }
    }

    val drawerState = rememberBottomDrawerState(initialValue = BottomDrawerValue.Closed)

    val closeDrawer = {
        scope.launch { drawerState.close() }
    }

    BottomSheet(
        drawerState = drawerState,
        drawerContent = {
            SelectableRow(
                selected = sorting.sortType == SortingType.ALPHA && sorting.sortDirection == SortDirection.ASC,
                onClick = { setSorting(AlphaASC()); closeDrawer() },
                content = { Text("Алфавит: по возрастанию") }
            )
            SelectableRow(
                selected = sorting.sortType == SortingType.ALPHA && sorting.sortDirection == SortDirection.DESC,
                onClick = { setSorting(AlphaDESC()); closeDrawer() },
                content = { Text("Алфавит: по убыванию") }
            )
            SelectableRow(
                selected = sorting.sortType == SortingType.RATING && sorting.sortDirection == SortDirection.ASC,
                onClick = { setSorting(RatingASC()); closeDrawer() },
                content = { Text("Рейтинг: по возрастанию") }
            )
            SelectableRow(
                selected = sorting.sortType == SortingType.RATING && sorting.sortDirection == SortDirection.DESC,
                onClick = { setSorting(RatingDESC()); closeDrawer() },
                content = { Text("Рейтинг: по убыванию") }
            )
            SelectableRow(
                selected = sorting.sortType == SortingType.VOTED && sorting.sortDirection == SortDirection.ASC,
                onClick = { setSorting(VotedASC()); closeDrawer() },
                content = { Text("Голоса: по возрастанию") }
            )
            SelectableRow(
                selected = sorting.sortType == SortingType.VOTED && sorting.sortDirection == SortDirection.DESC,
                onClick = { setSorting(VotedDESC()); closeDrawer() },
                content = { Text("Голоса: по убыванию") }
            )
            SelectableRow(
                selected = sorting.sortType == SortingType.QUANTITY && sorting.sortDirection == SortDirection.ASC,
                onClick = { setSorting(QuantityASC()); closeDrawer() },
                content = { Text("Количество: по возрастанию") }
            )
            SelectableRow(
                selected = sorting.sortType == SortingType.QUANTITY && sorting.sortDirection == SortDirection.DESC,
                onClick = { setSorting(QuantityDESC()); closeDrawer() },
                content = { Text("Количество: по убыванию") }
            )
        },
        content = {
            PageContainer(
                priorButton = {
                    IconButton(onClick = onPressSearch) {
                        Icon(
                            imageVector = Icons.Filled.Search,
                            contentDescription = "Назад к поиску"
                        )
                    }
                },
                header = { PageTitle(title = "Категории") },
                trailingButton = {
                    IconButton(
                        onClick = {
                            scope.launch { if (drawerState.isOpen) drawerState.close() else drawerState.open() }
                        },
                        content = {
                            Icon(
                                imageVector = Icons.Filled.Sort,
                                contentDescription = "Сортировка"
                            )
                        }
                    )
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
    )
}