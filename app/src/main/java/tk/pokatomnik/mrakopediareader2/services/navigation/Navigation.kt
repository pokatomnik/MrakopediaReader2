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
import tk.pokatomnik.mrakopediareader2.services.preferences.global.GlobalPreferences
import tk.pokatomnik.mrakopediareader2.services.preferences.rememberPreferences

data class Navigation(
    val navController: NavHostController,
    private val serializer: Serializer,
    private val globalPreferences: GlobalPreferences,
    private val mrakopediaIndex: MrakopediaIndex
) {
    private fun NavHostController.navigateDistinct(route: String) {
        navigate(route) {
            launchSingleTop = true
        }
        globalPreferences.savedPath = route
    }

    private fun NavHostController.navigateAllowSame(route: String) {
        navigate(route)
        globalPreferences.savedPath = route
    }

    fun navigateToSaved() {
        val savedPath = globalPreferences.savedPath
        if (savedPath != null) {
            navController.navigateDistinct(savedPath)
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
        navController.navigateDistinct(categoriesRoute)
    }

    val storiesRoute = "/categories/{$CATEGORY_TITLE_KEY}"
    fun navigateToStories(categoryTitle: String) {
        val serializedCategoryTitle = serializer.serialize(categoryTitle)
        navController.navigateDistinct("/categories/$serializedCategoryTitle")
    }

    val storyRoute = "/categories/{$CATEGORY_TITLE_KEY}/{$STORY_TITLE_KEY}"
    fun navigateToStory(categoryTitle: String, storyTitle: String) {
        val serializedCategoryTitle = serializer.serialize(categoryTitle)
        val serializedStoryTitle = serializer.serialize(storyTitle)
        navController.navigateAllowSame("/categories/$serializedCategoryTitle/$serializedStoryTitle")
    }

    val historyRoute = "/history"
    @Composable
    fun onHistory(): Boolean {
        val currentDestination = rememberCurrentDestination()
        return currentDestination.on(historyRoute)
    }
    fun navigateToHistory() {
        navController.navigateDistinct(historyRoute)
    }

    val favoritesRoute = "/favorites"
    @Composable
    fun onFavorites(): Boolean {
        val currentDestination = rememberCurrentDestination()
        return currentDestination.on(favoritesRoute)
    }
    fun navigateToFavorites() {
        navController.navigateDistinct(favoritesRoute)
    }

    val settingsRoute = "/settings"
    @Composable
    fun onSettings(): Boolean {
        val currentDestination = rememberCurrentDestination()
        return currentDestination.on(settingsRoute)
    }
    fun navigateToSettings() {
        navController.navigateDistinct(settingsRoute)
    }

    val searchRoute = "/search"
    fun navigateToSearch() {
        navController.navigateDistinct(searchRoute)
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
    val globalPreferences = rememberPreferences().globalPreferences
    val navHostController = rememberAnimatedNavController()
    val serializer = rememberSerializer()
    val mrakopediaIndex = rememberMrakopediaIndex()
    return Navigation(navHostController, serializer, globalPreferences, mrakopediaIndex)
}