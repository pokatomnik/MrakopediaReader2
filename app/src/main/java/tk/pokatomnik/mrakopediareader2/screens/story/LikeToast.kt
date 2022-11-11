package tk.pokatomnik.mrakopediareader2.screens.story

import android.widget.Toast
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import tk.pokatomnik.mrakopediareader2.services.preferences.rememberPreferences
import tk.pokatomnik.mrakopediareader2.ui.components.rememberToast

@Composable
fun ShowHelpOnceSideEffect() {
    val toast = rememberToast()
    val pagePreferences = rememberPreferences().pagePreferences
    LaunchedEffect(Unit) {
        if (!pagePreferences.likeNotificationShown) {
            toast("Тапните дважды чтобы добавить в избранное", Toast.LENGTH_LONG)
            pagePreferences.likeNotificationShown = true
        }
    }
}