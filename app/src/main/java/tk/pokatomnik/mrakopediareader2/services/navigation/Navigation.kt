package tk.pokatomnik.mrakopediareader2.services.navigation

import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavDestination
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import com.google.accompanist.navigation.animation.rememberAnimatedNavController

data class Navigation(
    val navController: NavHostController,
    private val serializer: Serializer
) {
    private fun NavHostController.navigateSingle(route: String) {
        navigate(route) {
            launchSingleTop = true
        }
    }

    private fun NavDestination?.on(route: String): Boolean {
        return this?.hierarchy?.any { it.route == route } == true
    }

    fun getCategoryTitle(entry: NavBackStackEntry): String? {
        return entry.arguments?.getString(CATEGORY_TITLE_KEY)?.let {
            serializer.parse(it)
        }
    }

    fun getStoryTitle(entry: NavBackStackEntry): String? {
        return entry.arguments?.getString(STORY_TITLE_KEY)?.let {
            serializer.parse(it)
        }
    }

    @Composable
    private fun rememberCurrentDestination(): NavDestination? {
        val navBackStackEntry by navController.currentBackStackEntryAsState()
        return navBackStackEntry?.destination
    }

    val categoriesRoute = "/categories"
    @Composable
    fun onCategories(): Boolean {
        val currentDestination = rememberCurrentDestination()
        return currentDestination.on(categoriesRoute)
    }
    fun navigateToCategories() {
        navController.navigateSingle(categoriesRoute)
    }

    val storiesRoute = "/categories/{$CATEGORY_TITLE_KEY}"
    fun navigateToStories(categoryTitle: String) {
        val serializedCategoryTitle = serializer.serialize(categoryTitle)
        navController.navigateSingle("/categories/$serializedCategoryTitle")
    }

    val storyRoute = "/categories/{$CATEGORY_TITLE_KEY}/{$STORY_TITLE_KEY}"
    fun navigateToStory(categoryTitle: String, storyTitle: String) {
        val serializedCategoryTitle = serializer.serialize(categoryTitle)
        val serializedStoryTitle = serializer.serialize(storyTitle)
        navController.navigate("/categories/$serializedCategoryTitle/$serializedStoryTitle")
    }

    val historyRoute = "/history"
    @Composable
    fun onHistory(): Boolean {
        val currentDestination = rememberCurrentDestination()
        return currentDestination.on(historyRoute)
    }
    fun navigateToHistory() {
        navController.navigateSingle(historyRoute)
    }

    val favoritesRoute = "/favorites"
    @Composable
    fun onFavorites(): Boolean {
        val currentDestination = rememberCurrentDestination()
        return currentDestination.on(favoritesRoute)
    }
    fun navigateToFavorites() {
        navController.navigateSingle(favoritesRoute)
    }

    val settingsRoute = "/settings"
    @Composable
    fun onSettings(): Boolean {
        val currentDestination = rememberCurrentDestination()
        return currentDestination.on(settingsRoute)
    }
    fun navigateToSettings() {
        navController.navigateSingle(settingsRoute)
    }

    val searchRoute = "/search"
    fun navigateToSearch() {
        navController.navigateSingle(searchRoute)
    }

    companion object {
        private const val CATEGORY_TITLE_KEY = "categoryTitle"
        private const val STORY_TITLE_KEY = "storyTitle"
    }
}

@OptIn(ExperimentalAnimationApi::class)
@Composable
fun rememberNavigation(): Navigation {
    val navHostController = rememberAnimatedNavController()
    val serializer = rememberSerializer()
    return Navigation(navHostController, serializer)
}