package tk.pokatomnik.mrakopediareader2.screens.story

import android.content.Intent
import android.net.Uri
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.Button
import androidx.compose.material.Divider
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext

@Composable
fun Source(pageTitle: String) {
    val context = LocalContext.current
    val openSource = {
        val url = resolveContentURL(pageTitle)
        val intent = Intent(Intent.ACTION_VIEW, Uri.parse(url))
        context.startActivity(intent)
    }

    Divider(modifier = Modifier.fillMaxWidth())
    Column(modifier = Modifier.fillMaxWidth()) {
        Button(onClick = openSource) {
            Text(text = "Читать в источнике")
        }
    }
}