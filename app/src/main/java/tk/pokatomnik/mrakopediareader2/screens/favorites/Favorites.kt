package tk.pokatomnik.mrakopediareader2.screens.favorites

import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.HorizontalPager
import com.google.accompanist.pager.rememberPagerState
import kotlinx.coroutines.launch
import tk.pokatomnik.mrakopediareader2.ui.components.PageContainer
import tk.pokatomnik.mrakopediareader2.ui.components.PageTitle

const val SAVED_FAVORITES = 0
const val NEW_STORIES = 1
const val STORIES_OF_MONTH = 2
const val GOOD_STORIES = 3

@OptIn(ExperimentalPagerApi::class)
@Composable
fun Favorites(
    onBackClick: () -> Unit,
    onStoryClick: (pageTitle: String) -> Unit,
    onCategoryClick: (categoryTitle: String) -> Unit,
) {
    val scope = rememberCoroutineScope()
    val pagerState = rememberPagerState(SAVED_FAVORITES)

    PageContainer(
        priorButton = {
            IconButton(onClick = onBackClick) {
                Icon(
                    imageVector = Icons.Filled.ArrowBack,
                    contentDescription = "Назад"
                )
            }
        },
        header = { PageTitle(title = "Избранное") }
    ) {
        ScrollableTabRow(
            selectedTabIndex = pagerState.currentPage,
            modifier = Modifier
                .wrapContentWidth()
                .height(48.dp),
            edgePadding = 16.dp
        ) {
            Tab(
                selected = pagerState.currentPage == SAVED_FAVORITES,
                onClick = {
                    scope.launch { pagerState.animateScrollToPage(SAVED_FAVORITES) }
                },
                text = { Text(text = "Избранное") }
            )
            Tab(
                selected = pagerState.currentPage == NEW_STORIES,
                onClick = {
                    scope.launch { pagerState.animateScrollToPage(NEW_STORIES) }
                },
                text = { Text(text = "Новые истории") }
            )
            Tab(
                selected = pagerState.currentPage == STORIES_OF_MONTH,
                onClick = {
                    scope.launch { pagerState.animateScrollToPage(STORIES_OF_MONTH) }
                },
                text = { Text(text = "Истории месяца") }
            )
            Tab(
                selected = pagerState.currentPage == GOOD_STORIES,
                onClick = {
                    scope.launch { pagerState.animateScrollToPage(GOOD_STORIES) }
                },
                text = { Text(text = "Хорошие истории") }
            )
        }
        
        HorizontalPager(count = 4, state = pagerState) {
            if (it == SAVED_FAVORITES) {
                SavedFavorites(
                    onFavoriteStoryClick = onStoryClick,
                    onFavoriteCategoryClick = onCategoryClick
                )
            }
            if (it == NEW_STORIES) {
                NewStories(onStoryClick = onStoryClick)
            }
            if (it == STORIES_OF_MONTH) {
                StoriesOfMonth(onStoryTitleClick = onStoryClick)
            }
            if (it == GOOD_STORIES) {
                GoodStories(onStoryClick = onStoryClick)
            }
        }
    }
}