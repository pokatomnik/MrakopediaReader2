package tk.pokatomnik.mrakopediareader2.ui.components

import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.compositionLocalOf
import androidx.compose.ui.platform.LocalView
import java.util.concurrent.atomic.AtomicInteger

val LocalScreenOnCounter = compositionLocalOf {
    AtomicInteger(0)
}

@Composable
fun KeepScreenOn(
    content: @Composable () -> Unit
) {
    val screenOnCounter = LocalScreenOnCounter.current
    val localView = LocalView.current
    DisposableEffect(Unit) {
        screenOnCounter.incrementAndGet()
        localView.keepScreenOn = true
        onDispose {
            val left = screenOnCounter.decrementAndGet()
            if (left == 0) {
                localView.keepScreenOn = false
            }
        }
    }

    content()
}
