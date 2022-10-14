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
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import dagger.hilt.android.AndroidEntryPoint
import tk.pokatomnik.mrakopediareader2.screens.categories.Categories
import tk.pokatomnik.mrakopediareader2.screens.story.Story
import tk.pokatomnik.mrakopediareader2.screens.stories.Stories
import tk.pokatomnik.mrakopediareader2.ui.components.BottomNavItem
import tk.pokatomnik.mrakopediareader2.ui.theme.MrakopediaReader2Theme

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
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
                                onClick = {
                                    navController.navigate("categories") {
                                        launchSingleTop = true
                                    }
                                }
                            )
                            BottomNavItem(
                                icon = Icons.Filled.FileCopy,
                                title = "Список Историй",
                                enabled = categoryTitle != null,
                                selected = currentDestination?.hierarchy?.any { it.route == "stories" } == true,
                                onClick = {
                                    navController.navigate("stories") {
                                        launchSingleTop = true
                                    }
                                }
                            )
                            BottomNavItem(
                                icon = Icons.Filled.AutoStories,
                                title = "История",
                                enabled = pageTitle != null,
                                selected = currentDestination?.hierarchy?.any { it.route == "story" } == true,
                                onClick = {
                                    navController.navigate("story") {
                                        launchSingleTop = true
                                    }
                                }
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
                                        navController.navigate("stories") {
                                            launchSingleTop = true
                                        }
                                    }
                                )
                            }
                            composable(route = "stories") {
                                if (categoryTitle != null) {
                                    Stories(
                                        selectedCategoryTitle = categoryTitle,
                                        onSelectPage = {
                                            setPageTitle(it)
                                            navController.navigate("story") {
                                                launchSingleTop = true
                                            }
                                        }
                                    )
                                }
                            }
                            composable(route = "story") {
                                if (pageTitle != null && categoryTitle != null) {
                                    Story(
                                        selectedCategoryTitle = categoryTitle,
                                        selectedPageTitle = pageTitle
                                    )
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}