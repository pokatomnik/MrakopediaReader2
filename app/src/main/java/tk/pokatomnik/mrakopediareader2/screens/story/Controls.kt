package tk.pokatomnik.mrakopediareader2.screens.story

import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.material.FloatingActionButton
import androidx.compose.material.Icon
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Remove
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.unit.dp
import tk.pokatomnik.mrakopediareader2.services.preferences.rememberPreferences

@Composable
fun BoxScope.Controls(alpha: Float, pageContentSize: MutableState<Int>) {
    val pagePreferences = rememberPreferences().pagePreferences
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
                if (newSize <= pagePreferences.maxFontSize) {
                    pageContentSize.value = newSize
                }
            },
        ) {
            Icon(Icons.Filled.Add, "Увеличить масштаб текста")
        }
        FloatingActionButton(
            modifier = Modifier.padding(horizontal = 8.dp),
            onClick = {
                val newSize = pageContentSize.value - 1
                if (newSize >= pagePreferences.minFontSize) {
                    pageContentSize.value = newSize
                }
            },
        ) {
            Icon(Icons.Filled.Remove, "Уменьшить масштаб текста")
        }
    }
}