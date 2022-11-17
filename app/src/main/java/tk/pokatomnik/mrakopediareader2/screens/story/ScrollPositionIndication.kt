package tk.pokatomnik.mrakopediareader2.screens.story

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.ScrollState
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

private fun Int.percentOf(max: Int): Int {
    return if (max == 0) 0 else 100 * this / max
}

@Composable
internal fun ScrollPositionIndication(scrollState: ScrollState) {
    val withAnimated = animateFloatAsState(
        targetValue = scrollState
            .value
            .percentOf(scrollState.maxValue)
            .toFloat() / 100
    )
    Row(
        modifier = Modifier.fillMaxWidth()
    ) {
        Row(
            modifier = Modifier
                .height(3.dp)
                .fillMaxWidth(withAnimated.value)
                .background(MaterialTheme.colors.secondary)
        ) {}
    }
}