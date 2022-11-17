package tk.pokatomnik.mrakopediareader2

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import com.google.accompanist.navigation.animation.AnimatedNavHost
import dagger.hilt.android.AndroidEntryPoint
import tk.pokatomnik.mrakopediareader2.screens.categories.Categories
import tk.pokatomnik.mrakopediareader2.screens.favorites.Favorites
import tk.pokatomnik.mrakopediareader2.screens.history.History
import tk.pokatomnik.mrakopediareader2.screens.search.Search
import tk.pokatomnik.mrakopediareader2.screens.settings.Settings
import tk.pokatomnik.mrakopediareader2.screens.stories.Stories
import tk.pokatomnik.mrakopediareader2.screens.story.Story
import tk.pokatomnik.mrakopediareader2.services.index.IndexPreparer
import tk.pokatomnik.mrakopediareader2.services.index.rememberMrakopediaIndex
import tk.pokatomnik.mrakopediareader2.services.navigation.rememberNavigation
import tk.pokatomnik.mrakopediareader2.services.preferences.rememberPreferences
import tk.pokatomnik.mrakopediareader2.ui.components.BottomNavItem
import tk.pokatomnik.mrakopediareader2.ui.components.SplashScreen
import tk.pokatomnik.mrakopediareader2.ui.components.rememberComputedPageTitle
import tk.pokatomnik.mrakopediareader2.ui.theme.MrakopediaReader2Theme


@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    @OptIn(ExperimentalAnimationApi::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MrakopediaReader2Theme {
                IndexPreparer(
                    loadingView = { SplashScreen() },
                    screenView = {
                        val mrakopediaIndex = rememberMrakopediaIndex()
                        val navigation = rememberNavigation()
                        val desiredPageTitle = rememberComputedPageTitle(intent.data)
                        val globalPreferences = rememberPreferences().globalPreferences

                        LaunchedEffect(desiredPageTitle) {
                            if (desiredPageTitle == null) {
                                val savedPath = globalPreferences.savedPath
                                if (savedPath != null) {
                                    navigation.navigateDistinct(savedPath)
                                }
                            } else {
                                navigation.navigateToStory(
                                    mrakopediaIndex.getCategory(mrakopediaIndex.getGeneralCategoryTitle()).name,
                                    desiredPageTitle
                                )
                            }
                        }

                        Scaffold(
                            bottomBar = {
                                BottomNavigation {
                                    BottomNavItem(
                                        icon = Icons.Filled.List,
                                        title = "Категории",
                                        selected = navigation.onCategories(),
                                        onClick = { navigation.navigateToCategories() }
                                    )
                                    BottomNavItem(
                                        icon = Icons.Filled.Favorite,
                                        title = "Избранное",
                                        selected = navigation.onFavorites(),
                                        onClick = { navigation.navigateToFavorites() }
                                    )
                                    BottomNavItem(
                                        icon = Icons.Filled.History,
                                        title = "История",
                                        selected = navigation.onHistory(),
                                        onClick = { navigation.navigateToHistory() }
                                    )
                                    BottomNavItem(
                                        icon = Icons.Filled.Settings,
                                        title = "Настройки",
                                        selected = navigation.onSettings(),
                                        onClick = { navigation.navigateToSettings() }
                                    )
                                }
                            }
                        ) { innerPadding ->
                            Surface(
                                modifier = Modifier.fillMaxSize(),
                                color = MaterialTheme.colors.background
                            ) {
                                AnimatedNavHost(
                                    navController = navigation.navController,
                                    startDestination = navigation.categoriesRoute,
                                    modifier = Modifier.padding(innerPadding)
                                ) {
                                    screen(route = navigation.categoriesRoute, main = true) {
                                        LaunchedEffect(Unit) {
                                            globalPreferences.savedPath = navigation.getCategoriesPath()
                                        }
                                        Categories(
                                            onSelectCategoryTitle = { categoryTitle ->
                                                navigation.navigateToStories(categoryTitle)
                                            },
                                            onPressSearch = { navigation.navigateToSearch() }
                                        )
                                    }
                                    screen(route = navigation.searchRoute) {
                                        LaunchedEffect(Unit) {
                                            globalPreferences.savedPath = navigation.getSearchPath()
                                        }
                                        Search(
                                            onSelectPage = { storyTitle ->
                                                navigation.navigateToStory(
                                                    mrakopediaIndex.getGeneralCategoryTitle(),
                                                    storyTitle
                                                )
                                            },
                                            onSelectCategory = { categoryTitle ->
                                                navigation.navigateToStories(categoryTitle)
                                            },
                                            onBackPress = { navigation.back() }
                                        )
                                    }
                                    screen(route = navigation.storiesRoute) {
                                        val categoryTitle = navigation.getCategoryTitle(it)
                                            ?: mrakopediaIndex.getGeneralCategoryTitle()
                                        LaunchedEffect(Unit) {
                                            globalPreferences.savedPath = navigation.getStoriesPath(categoryTitle)
                                        }
                                        Stories(
                                            selectedCategoryTitle = categoryTitle,
                                            onSelectPage = { storyTitle ->
                                                navigation.navigateToStory(
                                                    categoryTitle = categoryTitle,
                                                    storyTitle = storyTitle
                                                )
                                            }
                                        )
                                    }
                                    screen(route = navigation.storyRoute, keepScreenOn = true) {
                                        val categoryTitle = navigation.getCategoryTitle(it)
                                            ?: mrakopediaIndex.getGeneralCategoryTitle()
                                        val pageTitle = navigation.getStoryTitle(it) ?: ""
                                        LaunchedEffect(Unit) {
                                            globalPreferences.savedPath = navigation.getStoryPath(
                                                categoryTitle,
                                                pageTitle
                                            )
                                        }
                                        Story(
                                            selectedCategoryTitle = categoryTitle,
                                            selectedPageTitle = pageTitle,
                                            onNavigateToPage = { storyTitle ->
                                                navigation.navigateToStory(
                                                    categoryTitle = mrakopediaIndex.getGeneralCategoryTitle(),
                                                    storyTitle = storyTitle
                                                )
                                            },
                                            onNavigateToCategory = { newCategoryTitle ->
                                                navigation.navigateToStories(newCategoryTitle)
                                            },
                                            onNext = {
                                                navigation.navigateToRandom(); true
                                            },
                                            onPrevious = {
                                                navigation.back()
                                            }
                                        )
                                    }
                                    screen(route = navigation.favoritesRoute, main = true) {
                                        LaunchedEffect(Unit) {
                                            globalPreferences.savedPath = navigation.getFavoritesPath()
                                        }
                                        Favorites(
                                            onStoryClick = { storyTitle ->
                                                navigation.navigateToStory(
                                                    categoryTitle = mrakopediaIndex.getGeneralCategoryTitle(),
                                                    storyTitle = storyTitle
                                                )
                                            },
                                            onCategoryClick = { categoryTitle ->
                                                navigation.navigateToStories(categoryTitle)
                                            },
                                            onBackClick = {
                                                navigation.back()
                                            }
                                        )
                                    }
                                    screen(route = navigation.historyRoute, main = true) {
                                        LaunchedEffect(Unit) {
                                            globalPreferences.savedPath = navigation.getHistoryPath()
                                        }
                                        History(
                                            onSelectPage = { storyTitle ->
                                                navigation.navigateToStory(
                                                    categoryTitle = mrakopediaIndex.getGeneralCategoryTitle(),
                                                    storyTitle = storyTitle
                                                )
                                            },
                                            onBackClick = {
                                                navigation.back()
                                            }
                                        )
                                    }
                                    screen(route = navigation.settingsRoute, main = true) {
                                        LaunchedEffect(Unit) {
                                            globalPreferences.savedPath = navigation.getSettingsPath()
                                        }
                                        Settings(
                                            onBackClick = {
                                                navigation.back()
                                            }
                                        )
                                    }
                                }
                            }
                        }
                    }
                )
            }
        }
    }
}