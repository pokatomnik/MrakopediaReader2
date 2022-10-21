package tk.pokatomnik.mrakopediareader2

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.navigation.NavDestination
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import dagger.hilt.android.AndroidEntryPoint
import tk.pokatomnik.mrakopediareader2.screens.categories.Categories
import tk.pokatomnik.mrakopediareader2.screens.favorites.Favorites
import tk.pokatomnik.mrakopediareader2.screens.history.History
import tk.pokatomnik.mrakopediareader2.screens.search.Search
import tk.pokatomnik.mrakopediareader2.screens.settings.Settings
import tk.pokatomnik.mrakopediareader2.screens.story.Story
import tk.pokatomnik.mrakopediareader2.screens.stories.Stories
import tk.pokatomnik.mrakopediareader2.services.index.rememberMrakopediaIndex
import tk.pokatomnik.mrakopediareader2.ui.components.BottomNavItem
import tk.pokatomnik.mrakopediareader2.ui.components.rememberToast
import tk.pokatomnik.mrakopediareader2.ui.theme.MrakopediaReader2Theme

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    private fun NavHostController.navigateSingle(route: String) {
        navigate(route) {
            launchSingleTop = true
        }
    }

    private fun NavDestination?.on(route: String): Boolean {
        return this?.hierarchy?.any { it.route == route } == true
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val showToast = rememberToast()
            val mrakopediaIndex = rememberMrakopediaIndex()
            val (categoryTitle, setCategoryTitle) = remember { mutableStateOf<String?>(null) }
            val (pageTitle, setPageTitle) = remember { mutableStateOf<String?>(null) }

            MrakopediaReader2Theme {
                val navController = rememberNavController()
                Scaffold(
                    bottomBar = {
                        BottomNavigation  {
                            val navBackStackEntry by navController.currentBackStackEntryAsState()
                            val currentDestination = navBackStackEntry?.destination

                            BottomNavItem(
                                icon = Icons.Filled.List,
                                title = "Категории",
                                selected = currentDestination.on("categories"),
                                onClick = { navController.navigateSingle("categories") }
                            )
                            BottomNavItem(
                                icon = Icons.Filled.FileCopy,
                                title = "Список Историй",
                                selected = currentDestination.on("stories"),
                                onClick = {
                                    if (categoryTitle != null) {
                                        navController.navigateSingle("stories")
                                    } else {
                                        showToast("Выберите категорию")
                                    }
                                }
                            )
                            BottomNavItem(
                                icon = Icons.Filled.AutoStories,
                                title = "История",
                                selected = currentDestination.on("story"),
                                onClick = {
                                    if (pageTitle != null) {
                                        navController.navigateSingle("story")
                                    } else {
                                        showToast("Выберите историю")
                                    }
                                }
                            )
                            BottomNavItem(
                                icon = Icons.Filled.Favorite,
                                title = "Избранное",
                                selected = currentDestination.on("favorites"),
                                onClick = { navController.navigateSingle("favorites") }
                            )
                            BottomNavItem(
                                icon = Icons.Filled.History,
                                title = "История",
                                selected = currentDestination.on("history"),
                                onClick = { navController.navigateSingle("history") }
                            )
                            BottomNavItem(
                                icon = Icons.Filled.Settings,
                                title = "Настройки",
                                selected = currentDestination.on("settings"),
                                onClick = { navController.navigateSingle("settings") }
                            )
                        }
                    }
                ) { innerPadding ->
                    Surface(
                        modifier = Modifier.fillMaxSize(),
                        color = MaterialTheme.colors.background
                    ) {
                        NavHost(
                            navController = navController,
                            startDestination = "categories",
                            modifier = Modifier.padding(innerPadding)
                        ) {
                            composable(route = "categories") {
                                Categories(
                                    onSelectCategoryTitle = {
                                        setCategoryTitle(it)
                                        setPageTitle(null)
                                        navController.navigateSingle("stories")
                                    },
                                    onPressSearch = { navController.navigateSingle("search") }
                                )
                            }
                            composable(route = "search") {
                                Search(
                                    onSelectPage = {
                                        setCategoryTitle(mrakopediaIndex.getGeneralCategoryTitle())
                                        setPageTitle(it)
                                        navController.navigateSingle("story")
                                    },
                                    onSelectCategory = {
                                        setCategoryTitle(it)
                                        setPageTitle(null)
                                        navController.navigateSingle("stories")
                                    }
                                )
                            }
                            composable(route = "stories") {
                                if (categoryTitle != null) {
                                    Stories(
                                        selectedCategoryTitle = categoryTitle,
                                        onSelectPage = {
                                            setPageTitle(it)
                                            navController.navigateSingle("story")
                                        }
                                    )
                                }
                            }
                            composable(route = "story") {
                                if (pageTitle != null && categoryTitle != null) {
                                    Story(
                                        selectedCategoryTitle = categoryTitle,
                                        selectedPageTitle = pageTitle,
                                        onNavigateToPage = {
                                            setCategoryTitle(mrakopediaIndex.getGeneralCategoryTitle())
                                            setPageTitle(it)
                                        },
                                        onNavigateToCategory = {
                                            setCategoryTitle(it)
                                            setPageTitle(null)
                                            navController.navigateSingle("stories")
                                        }
                                    )
                                }
                            }
                            composable(route = "favorites") {
                                Favorites(
                                    onFavoriteStoryClick = {
                                        setCategoryTitle(mrakopediaIndex.getGeneralCategoryTitle())
                                        setPageTitle(it)
                                        navController.navigateSingle("story")
                                    },
                                    onFavoriteCategoryClick = {
                                        setCategoryTitle(it)
                                        setPageTitle(null)
                                        navController.navigateSingle("stories")
                                    }
                                )
                            }
                            composable(route = "history") {
                                History(
                                    onSelectPage = {
                                        setCategoryTitle(mrakopediaIndex.getGeneralCategoryTitle())
                                        setPageTitle(it)
                                        navController.navigateSingle("story")
                                    }
                                )
                            }
                            composable(route = "settings") {
                                Settings()
                            }
                        }
                    }
                }
            }
        }
    }
}