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
import tk.pokatomnik.mrakopediareader2.services.index.MrakopediaIndex
import tk.pokatomnik.mrakopediareader2.services.index.rememberMrakopediaIndex

data class Navigation(
    val navController: NavHostController,
    private val serializer: Serializer,
    private val mrakopediaIndex: MrakopediaIndex
) {
    private fun NavHostController.navigateDistinct(route: String) {
        navigate(route) {
            launchSingleTop = true
        }
    }

    private fun NavHostController.navigateAllowSame(route: String) {
        navigate(route)
    }

    private fun NavDestination?.on(route: String): Boolean {
        return this?.hierarchy?.any { it.route == route } == true
    }

    fun navigateDistinct(route: String) {
        navController.navigateDistinct(route)
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
    fun getCategoriesPath(): String {
        return categoriesRoute
    }
    @Composable
    fun onCategories(): Boolean {
        val currentDestination = rememberCurrentDestination()
        return currentDestination.on(categoriesRoute)
    }
    fun navigateToCategories() {
        navController.navigateDistinct(getCategoriesPath())
    }

    val storiesRoute = "/categories/{$CATEGORY_TITLE_KEY}"
    fun getStoriesPath(categoryTitle: String): String {
        val serializedCategoryTitle = serializer.serialize(categoryTitle)
        return "/categories/$serializedCategoryTitle"
    }
    fun navigateToStories(categoryTitle: String) {
        navController.navigateDistinct(getStoriesPath(categoryTitle))
    }

    val storyRoute = "/categories/{$CATEGORY_TITLE_KEY}/{$STORY_TITLE_KEY}"
    fun getStoryPath(categoryTitle: String, storyTitle: String): String {
        val serializedCategoryTitle = serializer.serialize(categoryTitle)
        val serializedStoryTitle = serializer.serialize(storyTitle)
        return "/categories/$serializedCategoryTitle/$serializedStoryTitle"
    }
    fun navigateToStory(categoryTitle: String, storyTitle: String) {
        navController.navigateAllowSame(getStoryPath(categoryTitle, storyTitle))
    }

    val historyRoute = "/history"
    fun getHistoryPath(): String {
        return historyRoute
    }
    @Composable
    fun onHistory(): Boolean {
        val currentDestination = rememberCurrentDestination()
        return currentDestination.on(historyRoute)
    }
    fun navigateToHistory() {
        navController.navigateDistinct(getHistoryPath())
    }

    val favoritesRoute = "/favorites"
    fun getFavoritesPath(): String {
        return favoritesRoute
    }
    @Composable
    fun onFavorites(): Boolean {
        val currentDestination = rememberCurrentDestination()
        return currentDestination.on(favoritesRoute)
    }
    fun navigateToFavorites() {
        navController.navigateDistinct(getFavoritesPath())
    }

    val settingsRoute = "/settings"
    fun getSettingsPath(): String {
        return settingsRoute
    }
    @Composable
    fun onSettings(): Boolean {
        val currentDestination = rememberCurrentDestination()
        return currentDestination.on(settingsRoute)
    }
    fun navigateToSettings() {
        navController.navigateDistinct(getSettingsPath())
    }

    val searchRoute = "/search"
    fun getSearchPath(): String {
        return searchRoute
    }
    fun navigateToSearch() {
        navController.navigateDistinct(getSearchPath())
    }

    fun back(): Boolean {
        return navController.popBackStack()
    }

    fun navigateToRandom() {
        val generalCategoryTitle = mrakopediaIndex.getGeneralCategoryTitle()
        mrakopediaIndex
            .getRandomTitles(1)
            .takeIf { it.isNotEmpty() }
            ?.first()
            ?.let { navigateToStory(generalCategoryTitle, it) }
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
    val mrakopediaIndex = rememberMrakopediaIndex()
    return Navigation(navHostController, serializer, mrakopediaIndex)
}