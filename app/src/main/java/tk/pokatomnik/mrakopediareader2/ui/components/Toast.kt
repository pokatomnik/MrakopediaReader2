package tk.pokatomnik.mrakopediareader2.ui.components

import android.widget.Toast
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext

@Composable
fun rememberToast(): (message: String, duration: Int) -> Unit {
    val context = LocalContext.current

    return fun (message: String, duration: Int) {
        Toast.makeText(context, message, duration).show()
    }
}