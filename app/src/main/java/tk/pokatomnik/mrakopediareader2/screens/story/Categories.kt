package tk.pokatomnik.mrakopediareader2.screens.story

import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material.Text
import androidx.compose.material.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.unit.dp
import com.google.accompanist.flowlayout.FlowRow
import com.google.accompanist.flowlayout.MainAxisAlignment

@Composable
fun Categories(
    categories: Collection<String>,
    onClick: (categoryTitle: String) -> Unit
) {
    Spacer(
        modifier = Modifier
            .fillMaxWidth()
            .height(8.dp)
    )
    FlowRow(
        modifier = Modifier.fillMaxSize(),
        mainAxisAlignment = MainAxisAlignment.Center
    ) {
        categories.forEach {
            TextButton(
                onClick = { onClick(it) }
            ) {
                Text(
                    text = "#$it",
                    modifier = Modifier.alpha(0.7f)
                )
            }
        }
    }
}