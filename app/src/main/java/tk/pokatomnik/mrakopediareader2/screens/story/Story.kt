package tk.pokatomnik.mrakopediareader2.screens.story

import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.HorizontalPager
import kotlinx.coroutines.launch
import tk.pokatomnik.mrakopediareader2.R
import tk.pokatomnik.mrakopediareader2.services.index.rememberMrakopediaIndex
import tk.pokatomnik.mrakopediareader2.services.preferences.page.rememberContentTextSize
import tk.pokatomnik.mrakopediareader2.services.preferences.rememberPreferences
import tk.pokatomnik.mrakopediareader2.services.readonlyparams.rememberReadonlyParameters
import tk.pokatomnik.mrakopediareader2.ui.components.BottomSheet
import tk.pokatomnik.mrakopediareader2.ui.components.PageContainer

@OptIn(ExperimentalMaterialApi::class, ExperimentalPagerApi::class)
@Composable
private fun StoryInternal(
    scrollPosition: Int,
    selectedCategoryTitle: String,
    selectedPageTitle: String,
    onNavigateToPage: (pageTitle: String) -> Unit,
    onNavigateToCategory: (categoryTitle: String) -> Unit
) {
    val coroutineScope = rememberCoroutineScope()
    val pagePreferences = rememberPreferences().pagePreferences
    val pageContentSize = rememberContentTextSize()

    val readonlyParameters = rememberReadonlyParameters()
    val category = rememberMrakopediaIndex()
        .getCategory(selectedCategoryTitle)
    val pageMeta = category.getPageMetaByTitle(selectedPageTitle)
    val content = remember(selectedPageTitle) { category.getPageContentByTitle(selectedPageTitle) }
    val seeAlso = pageMeta?.seeAlso ?: setOf()
    val categories = pageMeta?.categories ?: setOf()
    val images = (pageMeta?.images ?: listOf()).toList()

    val favoriteState = rememberStoryFavorite(selectedPageTitle = selectedPageTitle)

    val onSharePress = rememberShare(selectedPageTitle)

    val (controlsDisplayed, setControlsDisplayed) = remember { mutableStateOf(false) }

    val scrollState = rememberStoryScrollState(
        scrollPosition = scrollPosition,
        selectedPageTitle = selectedPageTitle
    )

    if (scrollState.isScrollInProgress && controlsDisplayed) {
        setControlsDisplayed(false)
    }
    
    val drawerState = rememberBottomDrawerState(initialValue = BottomDrawerValue.Closed)

    val onShowGalleryPress: () -> Unit = {
        coroutineScope.launch { drawerState.open() }
    }

    BottomSheet(
        height = 500,
        drawerState = drawerState,
        drawerContent = {
            HorizontalPager(
                count = images.size,
                modifier = Modifier.fillMaxSize()
            ) {
                val currentImage = images[it]
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(500.dp),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center,
                ) {
                    Text(
                        text = currentImage.imgCaption ?: "Без названия",
                        textAlign = TextAlign.Center
                    )
                    AsyncImage(
                        modifier = Modifier.fillMaxSize(),
                        model = "${readonlyParameters.originURL}/${currentImage.imgPath}",
                        contentScale = ContentScale.FillHeight,
                        contentDescription = currentImage.imgCaption,
                        placeholder = painterResource(id = R.drawable.spinner),
                        error = painterResource(id = R.drawable.broken)
                    )
                }
            }
        },
        content = {
            PageContainer {
                Box(modifier = Modifier.fillMaxSize()) {
                    ScrollPositionText(scrollState)
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
                        visible = controlsDisplayed,
                        pageContentSize = pageContentSize,
                        maxFontSize = pagePreferences.maxFontSize,
                        minFontSize = pagePreferences.minFontSize,
                        isFavorite = favoriteState.state.value,
                        onFavoritePress = favoriteState.onFavoritePress,
                        onSharePress = onSharePress,
                        onShowGalleryPress = if (images.isEmpty()) null else onShowGalleryPress
                    )
                }
            }
        }
    )
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