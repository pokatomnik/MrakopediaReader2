package tk.pokatomnik.mrakopediareader2.screens.story

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.selection.SelectionContainer
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Divider
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kotlinx.coroutines.launch
import tk.pokatomnik.mrakopediareader2.services.index.rememberMrakopediaIndex
import tk.pokatomnik.mrakopediareader2.services.preferences.page.rememberContentTextSize
import tk.pokatomnik.mrakopediareader2.ui.components.KeepScreenOn
import tk.pokatomnik.mrakopediareader2.ui.components.PageContainer

@Composable
fun Story(
    selectedCategoryTitle: String,
    selectedPageTitle: String,
    onNavigateToPage: (pageTitle: String) -> Unit,
    onNavigateToCategory: (categoryTitle: String) -> Unit
) {
    val (controlsDisplayed, setControlsDisplayed) = remember { mutableStateOf(false) }
    val controlsAlpha = animateFloatAsState(
        targetValue = if (controlsDisplayed) 1f else 0f
    )

    val coroutineScope = rememberCoroutineScope()
    val pageContentSize = rememberContentTextSize()
    val category = rememberMrakopediaIndex()
        .getCategory(selectedCategoryTitle)
    val content = category
        .getPageContentByTitle(selectedPageTitle)
    val pageMeta = category.getPageMetaByTitle(selectedPageTitle)
    val seeAlso = pageMeta?.seeAlso ?: setOf()
    val categories = pageMeta?.categories ?: setOf()

    val scrollState = rememberScrollState()

    if (scrollState.isScrollInProgress && controlsDisplayed) {
        setControlsDisplayed(false)
    }

    KeepScreenOn {
        PageContainer {
            Box(modifier = Modifier.fillMaxSize()) {
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(horizontal = 16.dp)
                        .verticalScroll(scrollState)
                        .clickable(
                            remember { MutableInteractionSource() },
                            indication = null
                        ) {
                            setControlsDisplayed(!controlsDisplayed)
                        }
                ) {
                    SelectionContainer {
                        Text(
                            text = selectedPageTitle,
                            style = MaterialTheme.typography.h5,
                            textAlign = TextAlign.Center,
                        )
                    }
                    Divider(modifier = Modifier.fillMaxWidth())
                    SelectionContainer {
                        Text(
                            textAlign = TextAlign.Justify,
                            text = content,
                            fontSize = pageContentSize.value.sp
                        )
                    }
                    if (seeAlso.isNotEmpty()) {
                        SeeAlso(
                            seeAlso = seeAlso,
                            onClick = {
                                onNavigateToPage(it)
                                coroutineScope.launch {
                                    scrollState.animateScrollTo(0)
                                }
                            }
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
                Controls(alpha = controlsAlpha.value, pageContentSize = pageContentSize)
            }
        }
    }
}