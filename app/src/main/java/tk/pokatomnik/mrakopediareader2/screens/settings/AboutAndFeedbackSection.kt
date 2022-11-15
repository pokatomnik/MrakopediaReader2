package tk.pokatomnik.mrakopediareader2.screens.settings

import android.content.Intent
import android.content.Intent.ACTION_VIEW
import android.net.Uri
import androidx.compose.foundation.layout.*
import androidx.compose.material.Text
import androidx.compose.material.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import com.google.accompanist.flowlayout.FlowRow

@Composable
fun AboutAndFeedbackSection() {
    val context = LocalContext.current

    val handleFeedbackClick = {
        val intent = Intent(ACTION_VIEW, Uri.parse("mailto:pokatomnik@yandex.ru"))
        context.startActivity(intent)
    }

    val handleSourceCodeClick = {
        val intent = Intent(ACTION_VIEW, Uri.parse("https://github.com/pokatomnik/MrakopediaReader2"))
        context.startActivity(intent)
    }

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(all = 16.dp)
    ) {
        Text("Приложение для чтения историй с сайта https://mrakopedia.net")
        Spacer(modifier = Modifier.height(8.dp))
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center
        ) {
            FlowRow {
                TextButton(onClick = handleFeedbackClick) {
                    Text("Написать автору")
                }
                TextButton(onClick = handleSourceCodeClick) {
                    Text("Исходный код")
                }
            }
        }
    }
}