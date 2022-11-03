package tk.pokatomnik.mrakopediareader2.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.Divider
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.zIndex

private const val HEADER_HEIGHT = 48

@Composable
fun PageContainer(
    priorButton: (@Composable () -> Unit)? = null,
    header: (@Composable () -> Unit)? = null,
    trailingButton: (@Composable () -> Unit)? = null,
    body: @Composable () -> Unit,
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(color = MaterialTheme.colors.surface)
    ) {
        if (header != null) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(HEADER_HEIGHT.dp)
                    .background(color = MaterialTheme.colors.surface)
                    .zIndex(1f)
                    .padding(horizontal = 8.dp),
            ) {
                if (priorButton != null) {
                    Column(modifier = Modifier
                        .width(HEADER_HEIGHT.dp)
                        .fillMaxHeight()) {
                        priorButton()
                    }
                }
                Column(
                    modifier = Modifier
                        .weight(1f)
                        .fillMaxWidth()
                        .fillMaxHeight()
                        .padding(
                            end = (if (priorButton == null) 0 else HEADER_HEIGHT).dp,
                            bottom = 0.dp,
                            top = 0.dp,
                            start = (if (trailingButton == null) 0 else HEADER_HEIGHT).dp
                        ),
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    header()
                }
                if (trailingButton != null) {
                    Column(modifier = Modifier
                        .width(HEADER_HEIGHT.dp)
                        .fillMaxHeight()) {
                        trailingButton()
                    }
                }
            }
            Divider(
                modifier = Modifier
                    .fillMaxWidth()
                    .shadow(10.dp)
                    .zIndex(0f)
            )
        }
        Column(modifier = Modifier.fillMaxSize()) {
            body()
        }
    }
}