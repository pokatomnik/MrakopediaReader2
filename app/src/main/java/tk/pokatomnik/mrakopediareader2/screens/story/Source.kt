package tk.pokatomnik.mrakopediareader2.screens.story

import android.content.Intent
import android.net.Uri
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material.Button
import androidx.compose.material.Divider
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import tk.pokatomnik.mrakopediareader2.services.readonlyparams.rememberReadonlyParameters

@Composable
fun Source(pageTitle: String) {
    val readonlyParameters = rememberReadonlyParameters()
    val context = LocalContext.current
    val openSource = {
        val url = resolveContentURL(readonlyParameters.originURL, pageTitle)
        val intent = Intent(Intent.ACTION_VIEW, Uri.parse(url))
        context.startActivity(intent)
    }

    Spacer(modifier = Modifier.height(16.dp))
    Divider(modifier = Modifier.fillMaxWidth())
    Column(modifier = Modifier.fillMaxWidth()) {
        Button(onClick = openSource) {
            Text(text = "Читать в источнике")
        }
    }
    Spacer(modifier = Modifier.height(16.dp))
}