package tk.pokatomnik.mrakopediareader2

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
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
import tk.pokatomnik.mrakopediareader2.ui.theme.MrakopediaReader2Theme

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val mrakopediaIndex = rememberMrakopediaIndex()
            val navigation = rememberNavigation()
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
                        NavHost(
                            navController = navigation.navController,
                            startDestination = navigation.categoriesRoute,
                            modifier = Modifier.padding(innerPadding)
                        ) {
                            composable(route = navigation.categoriesRoute) {
                                Categories(
                                    onSelectCategoryTitle = { categoryTitle ->
                                        navigation.navigateToStories(categoryTitle)
                                    },
                                    onPressSearch = { navigation.navigateToSearch() }
                                )
                            }
                            composable(route = navigation.searchRoute) {
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
                            composable(route = navigation.storiesRoute) {
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
                            composable(route = navigation.storyRoute) {
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
                            composable(route = navigation.favoritesRoute) {
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
                            composable(route = navigation.historyRoute) {
                                History(
                                    onSelectPage = { storyTitle ->
                                        navigation.navigateToStory(
                                            categoryTitle = mrakopediaIndex.getGeneralCategoryTitle(),
                                            storyTitle = storyTitle
                                        )
                                    }
                                )
                            }
                            composable(route = navigation.settingsRoute) {
                                Settings()
                            }
                        }
                    }
                }
            }
        }
    }
}