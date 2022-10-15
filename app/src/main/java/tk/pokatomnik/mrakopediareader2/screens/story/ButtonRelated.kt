package tk.pokatomnik.mrakopediareader2.screens.story

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.google.accompanist.flowlayout.FlowRow

@Composable
fun ButtonRelated(
    title: String,
    onClick: () -> Unit,
) {
    Button(
        modifier = Modifier.padding(end = 8.dp),
        onClick = onClick,
    ) {
        Text(text = cropString(36, title))
    }
}

@Preview
@Composable
fun ButtonRelatedRow() {
    FlowRow(modifier = Modifier.fillMaxWidth()) {
        ButtonRelated(title = "Test", onClick = {})
        ButtonRelated(title = "Test", onClick = {})
        ButtonRelated(title = "Test", onClick = {})
        ButtonRelated(title = "Test", onClick = {})
        ButtonRelated(title = "Test", onClick = {})
        ButtonRelated(title = "Test", onClick = {})
        ButtonRelated(title = "Test", onClick = {})
    }
}

fun cropString(max: Int, source: String): String {
    if (source.length > max) {
        return "${source.slice(0..max)}..."
    }
    return source
}