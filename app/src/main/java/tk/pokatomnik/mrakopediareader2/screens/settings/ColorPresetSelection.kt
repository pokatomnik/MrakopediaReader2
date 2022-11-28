package tk.pokatomnik.mrakopediareader2.screens.settings

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.contentColorFor
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import tk.pokatomnik.mrakopediareader2.services.preferences.page.rememberColorPresetID
import tk.pokatomnik.mrakopediareader2.services.preferences.rememberPreferences
import tk.pokatomnik.mrakopediareader2.ui.components.SelectableRow

@Composable
fun ColorPresetSelection() {
    val colorPresets = rememberPreferences().pagePreferences.colorPresets
    val colorPresetState = rememberColorPresetID()

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 16.dp)
    ) {
        Text(
            text = "Выбор цветов",
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(horizontal = 16.dp)
        )
        colorPresets.entries.forEach { (colorPresetId, colorPreset) ->
            SelectableRow(
                selected = colorPresetState.value == colorPresetId,
                onClick = { colorPresetState.value = colorPresetId },
                content = {
                    Text(
                        text = colorPreset.name,
                        modifier = Modifier
                            .background(
                            colorPreset.backgroundColor ?: MaterialTheme.colors.surface
                            )
                            .padding(horizontal = 8.dp),
                        color = colorPreset.contentColor ?: contentColorFor(MaterialTheme.colors.surface)
                    )
                }
            )
        }
    }
}