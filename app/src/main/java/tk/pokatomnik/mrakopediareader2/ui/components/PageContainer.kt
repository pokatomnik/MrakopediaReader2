package tk.pokatomnik.mrakopediareader2.ui.components

import androidx.compose.foundation.layout.*
import androidx.compose.material.Divider
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

private const val HEADER_HEIGHT = 48

@Composable
fun PageContainer(
    headerButton: (@Composable () -> Unit)? = null,
    header: (@Composable () -> Unit)? = null,
    body: @Composable () -> Unit,
) {
    Column(modifier = Modifier.fillMaxSize()) {
        if (header != null) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(HEADER_HEIGHT.dp)
                    .padding(horizontal = 8.dp),
            ) {
                if (headerButton != null) {
                    Column(modifier = Modifier.width(HEADER_HEIGHT.dp).fillMaxHeight()) {
                        headerButton()
                    }
                }
                Column(
                    modifier = Modifier.weight(1f).fillMaxWidth().fillMaxHeight().padding(
                        end = (if (headerButton == null) 0 else HEADER_HEIGHT).dp,
                        bottom = 0.dp,
                        top = 0.dp,
                        start = 0.dp
                    ),
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    header()
                }
            }
            Divider(modifier = Modifier.fillMaxWidth())
        }
        Column(modifier = Modifier.fillMaxSize()) {
            body()
        }
    }
}