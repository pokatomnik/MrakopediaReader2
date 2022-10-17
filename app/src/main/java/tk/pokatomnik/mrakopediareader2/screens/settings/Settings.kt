package tk.pokatomnik.mrakopediareader2.screens.settings

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.RadioButton
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import tk.pokatomnik.mrakopediareader2.services.preferences.global.ThemeIdentifier
import tk.pokatomnik.mrakopediareader2.services.preferences.global.rememberThemeIdentifier
import tk.pokatomnik.mrakopediareader2.ui.components.PageContainer
import tk.pokatomnik.mrakopediareader2.ui.components.PageTitle

@Composable
fun Settings() {
    val themeIdentifierState = rememberThemeIdentifier()
    
    PageContainer(
        header = { PageTitle(title = "Настройки") }
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 16.dp)
                .verticalScroll(rememberScrollState())
        ) {
            Column(modifier = Modifier
                .fillMaxWidth()
                .padding(top = 16.dp)) {
                Text("Тема")
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    RadioButton(
                        selected = themeIdentifierState.value == ThemeIdentifier.AUTO,
                        onClick = { themeIdentifierState.value = ThemeIdentifier.AUTO }
                    )
                    Text(text = "Как в системе")
                }
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    RadioButton(
                        selected = themeIdentifierState.value == ThemeIdentifier.LIGHT,
                        onClick = { themeIdentifierState.value = ThemeIdentifier.LIGHT }
                    )
                    Text(text = "Светлая")
                }
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    RadioButton(
                        selected = themeIdentifierState.value == ThemeIdentifier.DARK,
                        onClick = { themeIdentifierState.value = ThemeIdentifier.DARK }
                    )
                    Text(text = "Темная")
                }
            }
        }
    }
}