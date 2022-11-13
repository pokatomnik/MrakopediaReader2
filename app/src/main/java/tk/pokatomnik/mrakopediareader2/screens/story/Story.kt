package tk.pokatomnik.mrakopediareader2.screens.story

import androidx.compose.runtime.Composable

@Composable
fun Story(
    selectedCategoryTitle: String,
    selectedPageTitle: String,
    onNavigateToPage: (pageTitle: String) -> Unit,
    onNavigateToCategory: (categoryTitle: String) -> Unit,
    onNext: () -> Boolean,
    onPrevious: () -> Boolean
) {
    ScrollPosition(pageTitle = selectedPageTitle) { scrollPosition ->  
        StoryInternal(
            scrollPosition = scrollPosition,
            selectedCategoryTitle = selectedCategoryTitle,
            selectedPageTitle = selectedPageTitle,
            onNavigateToPage = onNavigateToPage,
            onNavigateToCategory = onNavigateToCategory,
            onNext = onNext,
            onPrevious = onPrevious
        )
    }
}