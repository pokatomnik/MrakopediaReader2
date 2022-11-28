package tk.pokatomnik.mrakopediareader2.services.preferences.page

import androidx.compose.ui.graphics.Color

class ColorPreset(
    val id: String,
    val name: String,
    val backgroundColor: Color? = null,
    val contentColor: Color? = null,
)