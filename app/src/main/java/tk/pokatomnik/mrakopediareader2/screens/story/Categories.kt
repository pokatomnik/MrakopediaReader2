package tk.pokatomnik.mrakopediareader2.screens.story

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material.Divider
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.google.accompanist.flowlayout.FlowRow

@Composable
fun Categories(
    categories: Collection<String>,
    onClick: (categoryTitle: String) -> Unit
) {
    Spacer(modifier = Modifier.height(16.dp))
    Divider(modifier = Modifier.fillMaxWidth())
    Spacer(modifier = Modifier.height(8.dp))
    Column(modifier = Modifier.fillMaxWidth()) {
        Text(
            text = "Категории:",
            style = MaterialTheme.typography.h6
        )
    }
    Spacer(modifier = Modifier.height(8.dp))
    FlowRow(modifier = Modifier.fillMaxWidth()) {
        for (categoryTitle in categories) {
            ButtonRelated(
                title = categoryTitle,
                onClick = { onClick(categoryTitle) }
            )
        }
    }
}