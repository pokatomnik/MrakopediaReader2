package tk.pokatomnik.mrakopediareader2.screens.story

import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.material.FloatingActionButton
import androidx.compose.material.Icon
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material.icons.filled.Remove
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.unit.dp

@Composable
internal fun BoxScope.Controls(
    alpha: Float,
    pageContentSize: MutableState<Int>,
    maxFontSize: Int,
    minFontSize: Int,
    isFavorite: Boolean,
    onToggleFavorite: (isFavorite: Boolean) -> Unit
) {
    Row(
        modifier = Modifier
            .padding(
                start = 16.dp,
                end = 16.dp,
                top = 16.dp,
                bottom = 88.dp
            )
            .align(alignment = Alignment.BottomEnd)
            .alpha(alpha)
    ) {
        FloatingActionButton(
            modifier = Modifier.padding(horizontal = 8.dp),
            onClick = { onToggleFavorite(!isFavorite) }
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
            .alpha(alpha)
    ) {
        FloatingActionButton(
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