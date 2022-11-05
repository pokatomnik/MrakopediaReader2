package tk.pokatomnik.mrakopediareader2.screens.story

import android.content.Intent
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext

@Composable
fun rememberShare(pageTitle: String): () -> Unit {
    val context = LocalContext.current
    return {
        val shareIntent = Intent(Intent.ACTION_SEND)
        val shareLink = resolveContentURL(pageTitle)
        val shareMessage = "\nЧитать на Мракопедии:\n\n"

        shareIntent.type = "text/plain"
        shareIntent.putExtra(Intent.EXTRA_SUBJECT, "MrakopediaReader2")
        shareIntent.putExtra(Intent.EXTRA_TEXT, shareMessage + shareLink)

        context.startActivity(Intent.createChooser(shareIntent, "Выберите"))
    }
}