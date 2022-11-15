package tk.pokatomnik.mrakopediareader2.screens.settings

import androidx.compose.foundation.layout.*
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import tk.pokatomnik.mrakopediareader2.BuildConfig
import tk.pokatomnik.mrakopediareader2.services.index.rememberMrakopediaIndex

@Composable
fun StatisticsSection() {
    val versionCode = BuildConfig.VERSION_CODE
    val versionName = BuildConfig.VERSION_NAME
    val mrakopediaIndex = rememberMrakopediaIndex()

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(all = 16.dp)
    ) {
        Text("Версия: $versionName ($versionCode)")
        Spacer(modifier = Modifier.height(8.dp))
        Text("Всего историй: ${mrakopediaIndex.uniquePagesTotalComputed}")
        Spacer(modifier = Modifier.height(8.dp))
        Text("Истории за ${mrakopediaIndex.formatCreationDate("dd.MM.yyyy")}")
    }
}