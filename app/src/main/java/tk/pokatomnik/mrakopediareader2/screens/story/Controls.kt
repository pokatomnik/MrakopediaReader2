package tk.pokatomnik.mrakopediareader2.screens.story

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.material.FloatingActionButton
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.unit.dp

@Composable
internal fun BoxScope.Controls(
    visible: Boolean,
    pageContentSize: MutableState<Int>,
    maxFontSize: Int,
    minFontSize: Int,
    isFavorite: Boolean,
    onFavoritePress: (isFavorite: Boolean) -> Unit,
    onSharePress: () -> Unit,
    onShowGalleryPress: (() -> Unit)?,
) {
    val (renderControls, setRenderControls) = remember { mutableStateOf(visible) }
    val controlsAlpha = animateFloatAsState(
        targetValue = if (visible) 1f else 0f,
        finishedListener = {
            if (it.toInt() == 0) { setRenderControls(false) }
        }
    )

    LaunchedEffect(visible) {
        if (visible) setRenderControls(true)
    }

    if (!renderControls) return

    Row(
        modifier = Modifier
            .padding(
                start = 16.dp,
                end = 16.dp,
                top = 16.dp,
                bottom = 160.dp
            )
            .align(alignment = Alignment.BottomEnd)
            .alpha(controlsAlpha.value)
    ) {
        FloatingActionButton(
            contentColor = MaterialTheme.colors.onPrimary,
            modifier = Modifier.padding(horizontal = 8.dp),
            onClick = onSharePress,
        ) {
            Icon(
                imageVector = Icons.Filled.Share,
                contentDescription = "Поделиться"
            )
        }
    }
    Row(
        modifier = Modifier
            .padding(
                start = 16.dp,
                end = 16.dp,
                top = 16.dp,
                bottom = 88.dp
            )
            .align(alignment = Alignment.BottomEnd)
            .alpha(controlsAlpha.value)
    ) {
        if (onShowGalleryPress != null) {
            FloatingActionButton(
                contentColor = MaterialTheme.colors.onPrimary,
                modifier = Modifier.padding(horizontal = 8.dp),
                onClick = onShowGalleryPress
            ) {
                Icon(
                    imageVector = Icons.Filled.Collections,
                    contentDescription = "Галерея"
                )
            }
        }
        FloatingActionButton(
            contentColor = MaterialTheme.colors.onPrimary,
            modifier = Modifier.padding(horizontal = 8.dp),
            onClick = { onFavoritePress(!isFavorite) }
        ) {
            Icon(
                imageVector = if (isFavorite) Icons.Filled.Favorite else Icons.Filled.FavoriteBorder,
                contentDescription = if (isFavorite) "В избранном" else "Добавить в избранное"
            )
        }
    }
    Row(
        modifier = Modifier
            .padding(all = 16.dp)
            .align(alignment = Alignment.BottomEnd)
            .alpha(controlsAlpha.value)
    ) {
        FloatingActionButton(
            contentColor = MaterialTheme.colors.onPrimary,
            modifier = Modifier.padding(horizontal = 8.dp),
            onClick = {
                val newSize = pageContentSize.value + 1
                if (newSize <= maxFontSize) {
                    pageContentSize.value = newSize
                }
            },
        ) {
            Icon(
                imageVector = Icons.Filled.Add,
                contentDescription = "Увеличить масштаб текста"
            )
        }
        FloatingActionButton(
            contentColor = MaterialTheme.colors.onPrimary,
            modifier = Modifier.padding(horizontal = 8.dp),
            onClick = {
                val newSize = pageContentSize.value - 1
                if (newSize >= minFontSize) {
                    pageContentSize.value = newSize
                }
            },
        ) {
            Icon(
                imageVector = Icons.Filled.Remove,
                contentDescription = "Уменьшить масштаб текста"
            )
        }
    }
}