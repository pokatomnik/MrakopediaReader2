package tk.pokatomnik.mrakopediareader2.screens.story

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material.Button
import androidx.compose.material.Divider
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
internal fun GalleryButton(onClick: () -> Unit) {
    Spacer(modifier = Modifier.height(16.dp))
    Divider(modifier = Modifier.fillMaxWidth())
    Spacer(modifier = Modifier.height(8.dp))
    Column(modifier = Modifier.fillMaxWidth()) {
        Button(onClick = onClick) {
            Text(text = "Галерея")
        }
    }
}