package tk.pokatomnik.mrakopediareader2

import android.app.Activity
import android.os.Bundle
import android.view.WindowManager
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.AnimatedContentScope
import androidx.compose.animation.AnimatedVisibilityScope
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.core.tween
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavGraphBuilder
import com.google.accompanist.navigation.animation.AnimatedNavHost
import com.google.accompanist.navigation.animation.composable as AccompanistComposable
import dagger.hilt.android.AndroidEntryPoint
import tk.pokatomnik.mrakopediareader2.screens.categories.Categories
import tk.pokatomnik.mrakopediareader2.screens.favorites.Favorites
import tk.pokatomnik.mrakopediareader2.screens.history.History
import tk.pokatomnik.mrakopediareader2.screens.search.Search
import tk.pokatomnik.mrakopediareader2.screens.settings.Settings
import tk.pokatomnik.mrakopediareader2.screens.stories.Stories
import tk.pokatomnik.mrakopediareader2.screens.story.Story
import tk.pokatomnik.mrakopediareader2.services.index.rememberMrakopediaIndex
import tk.pokatomnik.mrakopediareader2.services.navigation.rememberNavigation
import tk.pokatomnik.mrakopediareader2.ui.components.BottomNavItem
import tk.pokatomnik.mrakopediareader2.ui.components.rememberComputedPageTitle
import tk.pokatomnik.mrakopediareader2.ui.theme.MrakopediaReader2Theme

@OptIn(ExperimentalAnimationApi::class)
private fun NavGraphBuilder.screen(
    route: String,
    main: Boolean = false,
    keepScreenOn: Boolean = false,
    content: @Composable (AnimatedVisibilityScope.(NavBackStackEntry) -> Unit)
) {
    AccompanistComposable(
        route = route,
        enterTransition = {
            if (main) {
                slideIntoContainer(
                    AnimatedContentScope.SlideDirection.Up,
                    animationSpec = tween(700)
                )
            } else {
                slideIntoContainer(
                    AnimatedContentScope.SlideDirection.Left,
                    animationSpec = tween(700)
                )
            }
        },
        exitTransition = {
            slideOutOfContainer(
                AnimatedContentScope.SlideDirection.Left,
                animationSpec = tween(700)
            )
        },
        popEnterTransition = {
            slideIntoContainer(
                AnimatedContentScope.SlideDirection.Right,
                animationSpec = tween(700)
            )
        },
        popExitTransition = {
            slideOutOfContainer(
                AnimatedContentScope.SlideDirection.Right,
                animationSpec = tween(700)
            )
        },
        content = { navBackStackEntry ->
            val activity = LocalContext.current as? Activity
            DisposableEffect(Unit) {
                if (keepScreenOn) {
                    activity?.window?.addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON)
                }
                onDispose {
                    if (keepScreenOn) {
                        activity?.window?.clearFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON)
                    }
                }
            }
            content(navBackStackEntry)
        },
    )
}

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    @OptIn(ExperimentalAnimationApi::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val mrakopediaIndex = rememberMrakopediaIndex()
            val navigation = rememberNavigation()
            val desiredPageTitle = rememberComputedPageTitle(intent.data)

            LaunchedEffect(desiredPageTitle) {
                if (desiredPageTitle != null) {
                    navigation.navigateToStory(
                        mrakopediaIndex.getCategory(mrakopediaIndex.getGeneralCategoryTitle()).name,
                        desiredPageTitle
                    )
                }
            }

            MrakopediaReader2Theme {
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
                                Categories(
                                    onSelectCategoryTitle = { categoryTitle ->
                                        navigation.navigateToStories(categoryTitle)
                                    },
                                    onPressSearch = { navigation.navigateToSearch() }
                                )
                            }
                            screen(route = navigation.searchRoute) {
                                Search(
                                    onSelectPage = { storyTitle ->
                                        navigation.navigateToStory(
                                            mrakopediaIndex.getGeneralCategoryTitle(),
                                            storyTitle
                                        )
                                    },
                                    onSelectCategory = { categoryTitle ->
                                        navigation.navigateToStories(categoryTitle)
                                    }
                                )
                            }
                            screen(route = navigation.storiesRoute) {
                                val categoryTitle = navigation.getCategoryTitle(it)
                                    ?: mrakopediaIndex.getGeneralCategoryTitle()
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
                                    }
                                )
                            }
                            screen(route = navigation.favoritesRoute, main = true) {
                                Favorites(
                                    onStoryClick = { storyTitle ->
                                        navigation.navigateToStory(
                                            categoryTitle = mrakopediaIndex.getGeneralCategoryTitle(),
                                            storyTitle = storyTitle
                                        )
                                    },
                                    onCategoryClick = { categoryTitle ->
                                        navigation.navigateToStories(categoryTitle)
                                    }
                                )
                            }
                            screen(route = navigation.historyRoute, main = true) {
                                History(
                                    onSelectPage = { storyTitle ->
                                        navigation.navigateToStory(
                                            categoryTitle = mrakopediaIndex.getGeneralCategoryTitle(),
                                            storyTitle = storyTitle
                                        )
                                    }
                                )
                            }
                            screen(route = navigation.settingsRoute, main = true) {
                                Settings()
                            }
                        }
                    }
                }
            }
        }
    }
}