package tk.pokatomnik.mrakopediareader2.screens.settings

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import tk.pokatomnik.mrakopediareader2.services.preferences.global.ThemeIdentifier
import tk.pokatomnik.mrakopediareader2.services.preferences.global.rememberThemeIdentifier
import tk.pokatomnik.mrakopediareader2.ui.components.SelectableRow

@Composable
fun ThemeSettingsSection() {
    val themeIdentifierState = rememberThemeIdentifier()
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 16.dp)
    ) {
        Text("Тема")
        SelectableRow(
            selected = themeIdentifierState.value == ThemeIdentifier.AUTO,
            onClick = { themeIdentifierState.value = ThemeIdentifier.AUTO },
            content = { Text(text = "Как в системе") }
        )
        SelectableRow(
            selected = themeIdentifierState.value == ThemeIdentifier.LIGHT,
            onClick = { themeIdentifierState.value = ThemeIdentifier.LIGHT },
            content = { Text(text = "Светлая") }
        )
        SelectableRow(
            selected = themeIdentifierState.value == ThemeIdentifier.DARK,
            onClick = { themeIdentifierState.value = ThemeIdentifier.DARK },
            content = { Text(text = "Темная") }
        )
    }
}