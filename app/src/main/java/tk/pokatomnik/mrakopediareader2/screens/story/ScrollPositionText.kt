package tk.pokatomnik.mrakopediareader2.screens.story

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.ScrollState
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.layout.Row
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.text.style.TextAlign

private fun Int.percentOf(max: Int): Int {
    return if (max == 0) 0 else 100 * this / max
}

@Composable
internal fun BoxScope.ScrollPositionText(scrollState: ScrollState) {
    val scrollPositionAlpha = animateFloatAsState(
        targetValue = if (scrollState.isScrollInProgress) 0.7f else 0f
    )

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
}