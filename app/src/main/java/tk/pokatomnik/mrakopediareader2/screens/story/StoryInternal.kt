package tk.pokatomnik.mrakopediareader2.screens.story

import androidx.compose.animation.core.Animatable
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.gestures.Orientation
import androidx.compose.foundation.gestures.draggable
import androidx.compose.foundation.gestures.rememberDraggableState
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
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.launch
import tk.pokatomnik.mrakopediareader2.services.index.rememberMrakopediaIndex
import tk.pokatomnik.mrakopediareader2.services.preferences.page.rememberContentTextSize
import tk.pokatomnik.mrakopediareader2.services.preferences.rememberPreferences
import tk.pokatomnik.mrakopediareader2.ui.components.LikeBox
import tk.pokatomnik.mrakopediareader2.ui.components.PageContainer
import kotlin.math.abs
import kotlin.math.roundToInt

@OptIn(ExperimentalFoundationApi::class)
@Composable
internal fun StoryInternal(
    scrollPosition: Int,
    selectedCategoryTitle: String,
    selectedPageTitle: String,
    onNavigateToPage: (pageTitle: String) -> Unit,
    onNavigateToCategory: (categoryTitle: String) -> Unit,
    onNext: () -> Boolean,
    onPrevious: () -> Boolean
) {
    val coroutineScope = rememberCoroutineScope()
    val pagePreferences = rememberPreferences().pagePreferences
    val pageContentSize = rememberContentTextSize()
    ShowHelpOnceSideEffect()

    val category = rememberMrakopediaIndex()
        .getCategory(selectedCategoryTitle)
    val pageMeta = category.getPageMetaByTitle(selectedPageTitle)
    val content = remember(selectedPageTitle) { category.getPageContentByTitle(selectedPageTitle) }
    val seeAlso = pageMeta?.seeAlso ?: setOf()
    val categories = pageMeta?.categories ?: setOf()

    val favoriteState = rememberStoryFavorite(selectedPageTitle = selectedPageTitle)

    val (controlsDisplayed, setControlsDisplayed) = remember { mutableStateOf(false) }

    val scrollState = rememberStoryScrollState(
        scrollPosition = scrollPosition,
        selectedPageTitle = selectedPageTitle
    )

    if (scrollState.isScrollInProgress && controlsDisplayed) {
        setControlsDisplayed(false)
    }

    PageContainer {
        val offsetX = remember { Animatable(0f) }
        val maxDrag = 200f
        Box(
            modifier = Modifier
                .fillMaxSize()
                .offset { IntOffset(offsetX.value.roundToInt(), 0) }
                .draggable(
                    orientation = Orientation.Horizontal,
                    state = rememberDraggableState {
                        coroutineScope.launch {
                            offsetX.snapTo(
                                targetValue = (offsetX.value + it / 2)
                                    .coerceIn(-maxDrag..maxDrag),
                            )
                        }
                    },
                    onDragStopped = {
                        val offsetXValue = -offsetX.value
                        val absoluteOffset = abs(offsetXValue)
                        val smallDrag = absoluteOffset < maxDrag - (maxDrag / 4)
                        if (smallDrag) {
                            offsetX.animateTo(0f)
                            return@draggable
                        }
                        val navigated = (if (offsetXValue < 0) onPrevious else onNext)()
                        if (!navigated) {
                            offsetX.animateTo(0f)
                        }
                    }
                )
        ) {
            ContentSurface {
                ScrollPositionIndication(scrollState)
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(horizontal = 16.dp)
                        .verticalScroll(scrollState)
                        .combinedClickable(
                            interactionSource = remember { MutableInteractionSource() },
                            indication = null,
                            onClick = {
                                setControlsDisplayed(!controlsDisplayed)
                            },
                            onDoubleClick = {
                                val oldLiked = favoriteState.state.value
                                val newLiked = if (oldLiked == null) true else !oldLiked
                                favoriteState.onFavoritePress(newLiked)
                            }
                        )
                ) {
                    Column(
                        modifier = Modifier.fillMaxWidth(),
                        verticalArrangement = Arrangement.Center,
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Spacer(
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(32.dp)
                        )
                        Text(
                            text = selectedPageTitle,
                            style = MaterialTheme.typography.h4,
                            textAlign = TextAlign.Center,
                        )
                        RatingAndVoted(
                            rating = pageMeta?.rating ?: 0,
                            voted = pageMeta?.voted ?: 0
                        )
                        if (categories.isNotEmpty()) {
                            Categories(
                                categories = categories,
                                onClick = onNavigateToCategory
                            )
                        }
                        Spacer(
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(8.dp)
                        )
                    }
                    StoryContent(content = content, fontSize = pageContentSize.value)
                    if (seeAlso.isNotEmpty()) {
                        SeeAlso(
                            seeAlso = seeAlso,
                            onClick = { onNavigateToPage(it) }
                        )
                    }
                    Spacer(modifier = Modifier.height(16.dp))
                }
                Controls(
                    visible = controlsDisplayed,
                    pageContentSize = pageContentSize,
                    maxFontSize = pagePreferences.maxFontSize,
                    minFontSize = pagePreferences.minFontSize,
                    onScrollUpPress = {
                        coroutineScope.launch { scrollState.animateScrollTo(0) }
                    },
                    onScrollDownPress = {
                        coroutineScope.launch { scrollState.animateScrollTo(scrollState.maxValue) }
                    }
                )
            }
        }
    }
    favoriteState.state.value?.let {
        ColorPreset { _, contentColor ->
            LikeBox(liked = it, color = contentColor)
        }
    }
}