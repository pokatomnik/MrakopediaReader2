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
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import dagger.hilt.android.AndroidEntryPoint
import tk.pokatomnik.mrakopediareader2.screens.categories.Categories
import tk.pokatomnik.mrakopediareader2.screens.favorites.Favorites
import tk.pokatomnik.mrakopediareader2.screens.search.Search
import tk.pokatomnik.mrakopediareader2.screens.settings.Settings
import tk.pokatomnik.mrakopediareader2.screens.story.Story
import tk.pokatomnik.mrakopediareader2.screens.stories.Stories
import tk.pokatomnik.mrakopediareader2.services.index.rememberMrakopediaIndex
import tk.pokatomnik.mrakopediareader2.ui.components.BottomNavItem
import tk.pokatomnik.mrakopediareader2.ui.theme.MrakopediaReader2Theme

private fun NavHostController.navigateSingle(route: String) {
    navigate(route) {
        launchSingleTop = true
    }
}

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
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
                                enabled = true,
                                selected = currentDestination?.hierarchy?.any { it.route == "categories" } == true,
                                onClick = { navController.navigateSingle("categories") }
                            )
                            BottomNavItem(
                                icon = Icons.Filled.FileCopy,
                                title = "Список Историй",
                                enabled = categoryTitle != null,
                                selected = currentDestination?.hierarchy?.any { it.route == "stories" } == true,
                                onClick = { navController.navigateSingle("stories") }
                            )
                            BottomNavItem(
                                icon = Icons.Filled.AutoStories,
                                title = "История",
                                enabled = pageTitle != null,
                                selected = currentDestination?.hierarchy?.any { it.route == "story" } == true,
                                onClick = { navController.navigateSingle("story") }
                            )
                            BottomNavItem(
                                icon = Icons.Filled.Favorite,
                                title = "Избранное",
                                enabled = true,
                                selected = currentDestination?.hierarchy?.any { it.route == "favorites" } == true,
                                onClick = { navController.navigateSingle("favorites") }
                            )
                            BottomNavItem(
                                icon = Icons.Filled.Settings,
                                title = "Настройки",
                                enabled = true,
                                selected = currentDestination?.hierarchy?.any { it.route == "settings" } == true,
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
                                            navController.navigateSingle("stories")
                                        }
                                    )
                                }
                            }
                            composable(route = "favorites") {
                                Favorites(
                                    onItemClick = {
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