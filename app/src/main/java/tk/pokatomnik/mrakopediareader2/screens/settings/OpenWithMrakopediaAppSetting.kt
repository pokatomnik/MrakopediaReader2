package tk.pokatomnik.mrakopediareader2.screens.settings

import android.content.Intent
import android.net.Uri
import android.provider.Settings as AndroidSettings
import androidx.compose.foundation.layout.*
import androidx.compose.material.AlertDialog
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.material.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp

@Composable
fun OpenWithMrakopediaAppSetting() {
    val context = LocalContext.current

    val (alertVisible, setAlertVisible) = remember { mutableStateOf(false) }

    val openSettings = {
        val intent = Intent(AndroidSettings.ACTION_APPLICATION_DETAILS_SETTINGS)
        intent.data = Uri.parse("package:" + context.packageName)
        context.startActivity(intent)
    }

    if (alertVisible) {
        AlertDialog(
            onDismissRequest = { setAlertVisible(false) },
            title = { Text("Открывать в приложении") },
            text = {
                Text("Чтобы открывать ссылки Мракопедии в приложении, необходимо в настройках выбрать пункт \"Использовать по умолчанию\" или \"Открывать по умолчанию\", затем выбрать флажок \"Открывать поддерживаемые ссылки\"")
            },
            buttons = {
                Row(
                    modifier = Modifier.fillMaxWidth().padding(bottom = 8.dp),
                    horizontalArrangement = Arrangement.End,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    TextButton(onClick = { setAlertVisible(false) }) {
                        Text("Нет")
                    }
                    Spacer(modifier = Modifier.width(8.dp))
                    TextButton(onClick = { openSettings(); setAlertVisible(false) }) {
                        Text("Вперёд")
                    }
                    Spacer(modifier = Modifier.width(16.dp))
                }
            }
        )
    }

    Column(modifier = Modifier
        .fillMaxWidth()
        .padding(top = 16.dp, bottom = 16.dp)) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center
        ) {
            Button(onClick = { setAlertVisible(true) }) {
                Text("Открывать в приложении")
            }
        }
    }
}