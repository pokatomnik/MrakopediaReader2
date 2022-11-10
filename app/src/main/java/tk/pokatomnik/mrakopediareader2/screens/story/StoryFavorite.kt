package tk.pokatomnik.mrakopediareader2.screens.story

import androidx.compose.runtime.*
import kotlinx.coroutines.launch
import tk.pokatomnik.mrakopediareader2.services.db.dao.favoritestories.FavoriteStory
import tk.pokatomnik.mrakopediareader2.services.db.rememberDatabase

internal data class StoryFavoriteState(
    val state: MutableState<Boolean?>,
    val onFavoritePress: (isFavorite: Boolean) -> Unit
)

@Composable
internal fun rememberStoryFavorite(
    selectedPageTitle: String
): StoryFavoriteState {
    val coroutineScope = rememberCoroutineScope()
    val favoriteStoriesDAO = rememberDatabase().favoriteStoriesDAO()
    val favoriteState = remember { mutableStateOf<Boolean?>(null) }

    LaunchedEffect(selectedPageTitle) {
        val isCurrentFavorite = favoriteStoriesDAO.has(selectedPageTitle) != null
        favoriteState.value = isCurrentFavorite
    }
    val onFavoritePress: (isFavorite: Boolean) -> Unit = {
        favoriteState.value = it
        coroutineScope.launch {
            if (it) {
                favoriteStoriesDAO.add(FavoriteStory(selectedPageTitle))
            } else {
                favoriteStoriesDAO.delete(FavoriteStory(selectedPageTitle))
            }
        }
    }

    return StoryFavoriteState(
        state = favoriteState,
        onFavoritePress = onFavoritePress
    )
}