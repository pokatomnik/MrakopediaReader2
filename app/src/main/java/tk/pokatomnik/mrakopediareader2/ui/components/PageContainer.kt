package tk.pokatomnik.mrakopediareader2.ui.components

import androidx.compose.foundation.layout.*
import androidx.compose.material.Divider
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun PageContainer(
    header: (@Composable () -> Unit)? = null,
    body: @Composable () -> Unit,
) {
    Column(modifier = Modifier.fillMaxSize()) {
        if (header != null) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(48.dp)
                    .padding(horizontal = 8.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Center
            ) {
                header()
            }
            Divider(modifier = Modifier.fillMaxWidth())
        }
        Column(modifier = Modifier.fillMaxSize()) {
            body()
        }
    }
}