package tk.pokatomnik.mrakopediareader2.screens.settings

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import tk.pokatomnik.mrakopediareader2.services.preferences.page.rememberContentFontFamily
import tk.pokatomnik.mrakopediareader2.services.preferences.rememberPreferences
import tk.pokatomnik.mrakopediareader2.ui.components.SelectableRow

@Composable
fun ContentFontFamilySection() {
    val fontsMap = rememberPreferences().pagePreferences.storyFontsMap
    val contentFamilyState = rememberContentFontFamily()

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 16.dp)
    ) {
        Text(
            text = "Шрифт контента",
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(horizontal = 16.dp)
        )
        fontsMap.entries.forEach { (fontFamily, resourceId) ->
            SelectableRow(
                selected = contentFamilyState.value == fontFamily,
                onClick = { contentFamilyState.value = fontFamily },
                content = {
                    Text(
                        text = fontFamily,
                        fontFamily = if (resourceId == null) null else FontFamily(
                            Font(
                                resId = resourceId,
                                weight = FontWeight.Normal,
                                style = FontStyle.Normal
                            )
                        )
                    )
                }
            )
        }
    }
}