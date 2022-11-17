package tk.pokatomnik.mrakopediareader2.screens.story

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.ScrollState
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.LinearProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

private fun Int.percentOf(max: Int): Int {
    return if (max == 0) 0 else 100 * this / max
}

@Composable
internal fun ScrollPositionIndication(scrollState: ScrollState) {
    val widthAnimated = animateFloatAsState(
        targetValue = scrollState
            .value
            .percentOf(scrollState.maxValue)
            .toFloat() / 100
    )
    LinearProgressIndicator(
        modifier = Modifier.fillMaxWidth(),
        progress = widthAnimated.value
    )
}