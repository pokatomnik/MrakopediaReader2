package tk.pokatomnik.mrakopediareader2.ui.components

import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.material.Button
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.blur
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

@Composable
fun LikeBox(liked: Boolean) {
    val (animationPlaying, setAnimationPlaying) = remember { mutableStateOf(false) }

    val sizeState = animateDpAsState(
        animationSpec = tween(durationMillis = 1000),
        targetValue = (if (liked) 100 else 0).dp,
        finishedListener = { setAnimationPlaying(false) }
    )

    DisposableEffect(liked) {
        onDispose {
            setAnimationPlaying(true)
        }
    }

    if (!animationPlaying) return
    
    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
        Icon(
            modifier = Modifier
                .size(sizeState.value)
                .alpha(sizeState.value.value / 150)
                .blur(2.dp),
            imageVector = Icons.Filled.Favorite,
            contentDescription = "Like"
        )
    }
}

@Preview
@Composable
fun LikeBoxPreview() {
    val (value, setValue) = remember { mutableStateOf(false) }
    
    Button(onClick = { setValue(!value) }) {
        Text(text = "Tap!")
    }
    LikeBox(liked = value)
}