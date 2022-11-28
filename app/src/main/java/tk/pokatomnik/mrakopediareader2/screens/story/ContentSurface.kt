package tk.pokatomnik.mrakopediareader2.screens.story

import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.contentColorFor
import androidx.compose.runtime.Composable
import tk.pokatomnik.mrakopediareader2.services.preferences.rememberPreferences

@Composable
fun ContentSurface(
    content: @Composable () -> Unit,
) {
    val pagePreferences = rememberPreferences().pagePreferences
    val colorPreset = pagePreferences.colorPresets[pagePreferences.colorPresetID]

    val color = colorPreset?.backgroundColor ?: MaterialTheme.colors.surface
    val contentColor = colorPreset?.contentColor ?: contentColorFor(MaterialTheme.colors.surface)

    Surface(
        color = color,
        contentColor = contentColor
    ) { content() }
}