package tk.pokatomnik.mrakopediareader2.screens.story

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Divider
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import dev.jeziellago.compose.markdowntext.MarkdownText
import kotlinx.coroutines.launch
import tk.pokatomnik.mrakopediareader2.services.db.dao.favoritestories.FavoriteStory
import tk.pokatomnik.mrakopediareader2.services.db.rememberDatabase
import tk.pokatomnik.mrakopediareader2.services.index.rememberMrakopediaIndex
import tk.pokatomnik.mrakopediareader2.services.preferences.page.rememberContentTextSize
import tk.pokatomnik.mrakopediareader2.services.preferences.rememberPreferences
import tk.pokatomnik.mrakopediareader2.ui.components.KeepScreenOn
import tk.pokatomnik.mrakopediareader2.ui.components.PageContainer

@Composable
fun StoryContent(
    content: String,
    fontSize: Int,
) {
    key(fontSize) {
        MarkdownText(
            markdown = content,
            textAlign = TextAlign.Justify,
            fontSize = fontSize.sp,
            disableLinkMovementMethod = true,
        )
    }
}

private fun Int.percentOf(max: Int): Int {
    return if (max == 0) 0 else 100 * this / max
}

@Composable
private fun StoryInternal(
    scrollPosition: Int,
    selectedCategoryTitle: String,
    selectedPageTitle: String,
    onNavigateToPage: (pageTitle: String) -> Unit,
    onNavigateToCategory: (categoryTitle: String) -> Unit
) {
    val coroutineScope = rememberCoroutineScope()
    val mrakopediaDatabase = rememberDatabase()
    val favoriteStoriesDAO = mrakopediaDatabase.favoriteStoriesDAO()
    val historyDAO = mrakopediaDatabase.historyDAO()
    val pagePreferences = rememberPreferences().pagePreferences
    val pageContentSize = rememberContentTextSize()
    val category = rememberMrakopediaIndex()
        .getCategory(selectedCategoryTitle)

    val (isFavorite, setIsFavorite) = remember { mutableStateOf(false) }
    LaunchedEffect(selectedPageTitle) {
        val isCurrentFavorite = favoriteStoriesDAO.has(selectedPageTitle) != null
        setIsFavorite(isCurrentFavorite)
    }
    val onFavoritePress: (isFavorite: Boolean) -> Unit = {
        setIsFavorite(it)
        coroutineScope.launch {
            if (it) {
                favoriteStoriesDAO.add(FavoriteStory(selectedPageTitle))
            } else {
                favoriteStoriesDAO.delete(FavoriteStory(selectedPageTitle))
            }
        }
    }

    val (controlsDisplayed, setControlsDisplayed) = remember { mutableStateOf(false) }
    val controlsAlpha = animateFloatAsState(
        targetValue = if (controlsDisplayed) 1f else 0f
    )

    val content = remember(selectedPageTitle) { category.getPageContentByTitle(selectedPageTitle) }
    val pageMeta = category.getPageMetaByTitle(selectedPageTitle)
    val seeAlso = pageMeta?.seeAlso ?: setOf()
    val categories = pageMeta?.categories ?: setOf()

    val scrollState = rememberScrollState()

    val scrollPositionAlpha = animateFloatAsState(
        targetValue = if (scrollState.isScrollInProgress) 0.7f else 0f
    )

    LaunchedEffect(scrollPosition) {
        scrollState.animateScrollTo(scrollPosition)
    }

    if (scrollState.isScrollInProgress) {
        DisposableEffect(Unit) {
            onDispose {
                coroutineScope.launch {
                    historyDAO.setScrollPosition(
                        selectedPageTitle,
                        scrollState.value
                    )
                }
            }
        }
    }

    if (scrollState.isScrollInProgress && controlsDisplayed) {
        setControlsDisplayed(false)
    }

    KeepScreenOn {
        PageContainer {
            Box(modifier = Modifier.fillMaxSize()) {
                Row(
                    modifier = Modifier
                        .align(Alignment.TopEnd)
                        .alpha(scrollPositionAlpha.value),
                ) {
                    Text(
                        text = "${scrollState.value.percentOf(scrollState.maxValue)}%",
                        textAlign = TextAlign.End
                    )
                }
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(horizontal = 16.dp)
                        .verticalScroll(scrollState)
                        .clickable(
                            remember { MutableInteractionSource() },
                            indication = null,
                            onClick = { setControlsDisplayed(!controlsDisplayed) }
                        )
                ) {
                    Text(
                        text = selectedPageTitle,
                        style = MaterialTheme.typography.h5,
                        textAlign = TextAlign.Center,
                    )
                    Divider(modifier = Modifier.fillMaxWidth())
                    StoryContent(content = content, fontSize = pageContentSize.value)
                    RatingAndVoted(
                        rating = pageMeta?.rating ?: 0,
                        voted = pageMeta?.voted ?: 0,
                        fontSize = pageContentSize.value,
                    )
                    if (seeAlso.isNotEmpty()) {
                        SeeAlso(
                            seeAlso = seeAlso,
                            onClick = { onNavigateToPage(it) }
                        )
                    }
                    if (categories.isNotEmpty()) {
                        Categories(
                            categories = categories,
                            onClick = {
                                onNavigateToCategory(it)
                            }
                        )
                    }
                    Source(pageTitle = selectedPageTitle)
                }
                Controls(
                    alpha = controlsAlpha.value,
                    pageContentSize = pageContentSize,
                    maxFontSize = pagePreferences.maxFontSize,
                    minFontSize = pagePreferences.minFontSize,
                    isFavorite = isFavorite,
                    onFavoritePress = onFavoritePress,
                )
            }
        }
    }
}

@Composable
fun Story(
    selectedCategoryTitle: String,
    selectedPageTitle: String,
    onNavigateToPage: (pageTitle: String) -> Unit,
    onNavigateToCategory: (categoryTitle: String) -> Unit
) {
    ScrollPosition(pageTitle = selectedPageTitle) { scrollPosition ->  
        StoryInternal(
            scrollPosition = scrollPosition,
            selectedCategoryTitle = selectedCategoryTitle,
            selectedPageTitle = selectedPageTitle,
            onNavigateToPage = onNavigateToPage,
            onNavigateToCategory = onNavigateToCategory
        )
    }
}