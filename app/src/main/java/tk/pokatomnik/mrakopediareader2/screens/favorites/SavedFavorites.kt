package tk.pokatomnik.mrakopediareader2.screens.favorites

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Divider
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.google.accompanist.swiperefresh.SwipeRefresh
import com.google.accompanist.swiperefresh.rememberSwipeRefreshState
import kotlinx.coroutines.launch
import tk.pokatomnik.mrakopediareader2.services.db.dao.favoritecategories.FavoriteCategory
import tk.pokatomnik.mrakopediareader2.services.db.dao.favoritestories.FavoriteStory
import tk.pokatomnik.mrakopediareader2.services.db.rememberDatabase
import tk.pokatomnik.mrakopediareader2.services.index.rememberMrakopediaIndex
import tk.pokatomnik.mrakopediareader2.ui.components.LIST_ITEM_PADDING
import tk.pokatomnik.mrakopediareader2.ui.components.ListItemWithClickableIcon

@Composable
fun SavedFavorites(
    onFavoriteStoryClick: (pageTitle: String) -> Unit,
    onFavoriteCategoryClick: (categoryTitle: String) -> Unit,
) {
    val mrakopediaIndex = rememberMrakopediaIndex()
    val generalCategory = mrakopediaIndex.getCategory(
        mrakopediaIndex.getGeneralCategoryTitle()
    )
    val mrakopediaDatabase = rememberDatabase()
    val favoriteStoriesDAO = mrakopediaDatabase.favoriteStoriesDAO()
    val favoriteCategoriesDAO = mrakopediaDatabase.favoriteCategoriesDAO()

    val coroutineScope = rememberCoroutineScope()

    val (favoriteStories, setFavoriteStories) = remember {
        mutableStateOf<Map<String, Boolean>?>(null)
    }

    val (favoriteCategories, setFavoriteCategories) = remember {
        mutableStateOf<Map<String, Boolean>?>(null)
    }

    val refresh = {
        coroutineScope.launch {
            val favoriteStoriesFromDatabase = favoriteStoriesDAO
                .getAll()
                .reversed()
                .fold(mutableMapOf<String, Boolean>()) { acc, current ->
                    acc.apply { set(current.title, true) }
                }
            val favoriteCategoriesFromDatabase = favoriteCategoriesDAO
                .getAll()
                .reversed()
                .fold(mutableMapOf<String, Boolean>()) { acc, current ->
                    acc.apply { set(current.title, true) }
                }
            setFavoriteStories(favoriteStoriesFromDatabase)
            setFavoriteCategories(favoriteCategoriesFromDatabase)
        }
    }

    val setIsFavoriteStory: (pageTitle: String, isFavorite: Boolean) -> Unit = { pageTitle, isFavorite ->
        val newMap = favoriteStories?.toMutableMap()?.apply {
            set(pageTitle, isFavorite)
        }
        setFavoriteStories(newMap)
        coroutineScope.launch {
            if (isFavorite) {
                favoriteStoriesDAO.add(FavoriteStory(pageTitle))
            } else {
                favoriteStoriesDAO.delete(FavoriteStory(pageTitle))
            }
        }
    }

    val setIsFavoriteCategory: (categoryTitle: String, isFavorite: Boolean) -> Unit = { categoryTitle, isFavorite ->
        val newMap = favoriteCategories?.toMutableMap()?.apply {
            set(categoryTitle, isFavorite)
        }
        setFavoriteCategories(newMap)
        coroutineScope.launch {
            if (isFavorite) {
                favoriteCategoriesDAO.add(FavoriteCategory(categoryTitle))
            } else {
                favoriteCategoriesDAO.delete(FavoriteCategory(categoryTitle))
            }
        }
    }

    LaunchedEffect(Unit) { refresh() }

    if (favoriteStories == null && favoriteCategories == null) {
        return
    }

    if (favoriteStories.isNullOrEmpty() && favoriteCategories.isNullOrEmpty()) {
        Column(
            modifier = Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Text(text = "?? ?????????????????? ??????????")
        }
        return
    }

    SwipeRefresh(
        state = rememberSwipeRefreshState(false),
        onRefresh = { refresh() },
        modifier = Modifier.fillMaxSize(),
        swipeEnabled = true,
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
        ) {
            if (!favoriteStories.isNullOrEmpty()) {
                Spacer(modifier = Modifier
                    .fillMaxWidth()
                    .height(16.dp))
                Column(modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 8.dp)) {
                    Text(
                        text = "?????????????????? ??????????????",
                        modifier = Modifier.padding(horizontal = LIST_ITEM_PADDING.dp)
                    )
                    Divider(modifier = Modifier.fillMaxWidth())
                    for ((favoriteStoryTitle, isFavorite) in favoriteStories) {
                        val pageMeta = generalCategory.getPageMetaByTitle(favoriteStoryTitle)
                        ListItemWithClickableIcon(
                            title = favoriteStoryTitle,
                            description = pageMeta?.formatDescription(),
                            icon = if (isFavorite) Icons.Filled.Favorite else Icons.Filled.FavoriteBorder,
                            contentDescription = if (isFavorite) "?? ??????????????????" else "???????????????? ?? ??????????????????",
                            onItemClick = { onFavoriteStoryClick(favoriteStoryTitle) },
                            onIconClick = { setIsFavoriteStory(favoriteStoryTitle, !isFavorite) }
                        )
                    }
                }
            }

            if (!favoriteCategories.isNullOrEmpty()) {
                Spacer(modifier = Modifier
                    .fillMaxWidth()
                    .height(16.dp))
                Column(modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 8.dp)) {
                    Text(
                        text = "?????????????????? ????????????????????",
                        modifier = Modifier.padding(horizontal = LIST_ITEM_PADDING.dp)
                    )
                    Divider(modifier = Modifier.fillMaxWidth())
                    for ((favoriteCategoryTitle, isFavorite) in favoriteCategories) {
                        val category = mrakopediaIndex.getCategory(favoriteCategoryTitle)
                        ListItemWithClickableIcon(
                            title = favoriteCategoryTitle,
                            description = category.formatDescription(),
                            icon = if (isFavorite) Icons.Filled.Favorite else Icons.Filled.FavoriteBorder,
                            contentDescription = if (isFavorite) "?? ??????????????????" else "?????????????? ???? ????????????????????",
                            onItemClick = { onFavoriteCategoryClick(favoriteCategoryTitle) },
                            onIconClick = { setIsFavoriteCategory(favoriteCategoryTitle, !isFavorite) }
                        )
                    }
                }
            }
        }
    }
}