package tk.pokatomnik.mrakopediareader2.screens.settings

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Divider
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import tk.pokatomnik.mrakopediareader2.ui.components.PageContainer
import tk.pokatomnik.mrakopediareader2.ui.components.PageTitle

@Composable
fun Settings() {
    PageContainer(
        header = { PageTitle(title = "Настройки") }
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
        ) {
            ThemeSettingsSection()
            Divider(modifier = Modifier.fillMaxWidth())
            OpenWithMrakopediaAppSetting()
            Divider(modifier = Modifier.fillMaxWidth())
            StatisticsSection()
            Divider(modifier = Modifier.fillMaxWidth())
            AboutAndFeedbackSection()
        }
    }
}