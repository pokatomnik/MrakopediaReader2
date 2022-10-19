package tk.pokatomnik.mrakopediareader2.ui.components

import android.widget.Toast
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext

@Composable
fun rememberToast(duration: Int = Toast.LENGTH_SHORT): (message: String) -> Unit {
    val context = LocalContext.current

    return fun (message: String) {
        Toast.makeText(context, message, duration).show()
    }
}