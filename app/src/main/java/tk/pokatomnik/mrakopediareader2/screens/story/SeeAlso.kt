package tk.pokatomnik.mrakopediareader2.screens.story

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.Divider
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.google.accompanist.flowlayout.FlowRow

@Composable
fun SeeAlso(
    seeAlso: Collection<String>,
    onClick: (pageTitle: String) -> Unit,
) {
    Divider(modifier = Modifier.fillMaxWidth())
    Column(modifier = Modifier.fillMaxWidth()) {
        Text(
            text = "См. также:",
            style = MaterialTheme.typography.h6
        )
        FlowRow(modifier = Modifier.fillMaxWidth()) {
            for (seeAlsoTitle in seeAlso) {
                ButtonRelated(
                    title = seeAlsoTitle,
                    onClick = { onClick(seeAlsoTitle) }
                )
            }
        }
    }
}