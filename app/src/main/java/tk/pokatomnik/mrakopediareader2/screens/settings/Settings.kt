package tk.pokatomnik.mrakopediareader2.screens.settings

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Divider
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import tk.pokatomnik.mrakopediareader2.ui.components.PageContainer
import tk.pokatomnik.mrakopediareader2.ui.components.PageTitle

@Composable
fun Settings(
    onBackClick: () -> Unit,
) {
    PageContainer(
        priorButton = {
            IconButton(onClick = onBackClick) {
                Icon(imageVector = Icons.Filled.ArrowBack, contentDescription = "Назад")
            }
        },
        header = { PageTitle(title = "Настройки") }
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
        ) {
            ThemeSettingsSection()
            Divider(modifier = Modifier.fillMaxWidth())
            ContentFontFamilySection()
            Divider(modifier = Modifier.fillMaxWidth())
            ColorPresetSelection()
            Divider(modifier = Modifier.fillMaxWidth())
            OpenWithMrakopediaAppSetting()
            Divider(modifier = Modifier.fillMaxWidth())
            StatisticsSection()
            Divider(modifier = Modifier.fillMaxWidth())
            AboutAndFeedbackSection()
        }
    }
}