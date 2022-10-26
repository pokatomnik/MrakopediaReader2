package tk.pokatomnik.mrakopediareader2.screens.search

import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember

@Composable
fun Search(
    onSelectCategory: (categoryTitle: String) -> Unit,
    onSelectPage: (pageTitle: String) -> Unit,
) {
    val (viewStep, setViewStep) = remember { mutableStateOf(ViewStep.SEARCH_VIEW) }
    val (searchText, setSearchText) = remember { mutableStateOf<String?>(null) }

    if (viewStep == ViewStep.SEARCH_VIEW) {
        SearchView(
            onSearch = {
                setSearchText(it)
                setViewStep(ViewStep.SEARCH_RESULTS)
            },
            onSelectPageTitle = onSelectPage
        )
    }

    if (viewStep == ViewStep.SEARCH_RESULTS && !searchText.isNullOrBlank()) {
        SearchResults(
            searchText = searchText,
            onBackPress = {
                setViewStep(ViewStep.SEARCH_VIEW)
                setSearchText("")
            },
            onSelectCategory = onSelectCategory,
            onSelectPage = onSelectPage
        )
    }
}