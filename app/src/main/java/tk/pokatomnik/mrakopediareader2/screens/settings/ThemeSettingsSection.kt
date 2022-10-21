package tk.pokatomnik.mrakopediareader2.screens.settings

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.selection.selectable
import androidx.compose.material.RadioButton
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.unit.dp
import tk.pokatomnik.mrakopediareader2.services.preferences.global.ThemeIdentifier
import tk.pokatomnik.mrakopediareader2.services.preferences.global.rememberThemeIdentifier

@Composable
fun ThemeSettingsSection() {
    val themeIdentifierState = rememberThemeIdentifier()
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 16.dp)
    ) {
        Text("Тема")
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .selectable(
                    selected = themeIdentifierState.value == ThemeIdentifier.AUTO,
                    onClick = { themeIdentifierState.value = ThemeIdentifier.AUTO },
                    role = Role.RadioButton
                )
                .padding(vertical = 8.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            RadioButton(
                selected = themeIdentifierState.value == ThemeIdentifier.AUTO,
                onClick = null
            )
            Spacer(modifier = Modifier.width(4.dp))
            Text(text = "Как в системе")
        }
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .selectable(
                    selected = themeIdentifierState.value == ThemeIdentifier.LIGHT,
                    onClick = { themeIdentifierState.value = ThemeIdentifier.LIGHT },
                    role = Role.RadioButton
                )
                .padding(vertical = 8.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            RadioButton(
                selected = themeIdentifierState.value == ThemeIdentifier.LIGHT,
                onClick = null
            )
            Spacer(modifier = Modifier.width(4.dp))
            Text(text = "Светлая")
        }
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .selectable(
                    selected = themeIdentifierState.value == ThemeIdentifier.DARK,
                    onClick = { themeIdentifierState.value = ThemeIdentifier.DARK },
                    role = Role.RadioButton
                )
                .padding(vertical = 8.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            RadioButton(
                selected = themeIdentifierState.value == ThemeIdentifier.DARK,
                onClick = null
            )
            Spacer(modifier = Modifier.width(4.dp))
            Text(text = "Темная")
        }
    }
}