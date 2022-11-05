package tk.pokatomnik.mrakopediareader2.ui.components

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun BottomSheet(
    maxHeight: Float = 0.5f,
    drawerState: BottomDrawerState,
    content: @Composable () -> Unit,
    drawerContent: @Composable () -> Unit,
) {
    BottomDrawer(
        scrimColor = Color.Transparent,
        gesturesEnabled = drawerState.isOpen,
        drawerState = drawerState,
        drawerBackgroundColor = Color.Transparent,
        drawerContent = {
            BottomDrawerSurface(maxHeight = maxHeight, content = drawerContent)
        },
        content = content
    )
}

@Composable
private fun BottomDrawerSurface(
    maxHeight: Float,
    content: @Composable () -> Unit
) {
    Surface(
        color = MaterialTheme.colors.background,
        shape = RoundedCornerShape(16.dp, 16.dp, 0.dp, 0.dp)
    ) {
        Column(modifier = Modifier.fillMaxWidth().fillMaxHeight(maxHeight)) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(18.dp),
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Divider(
                    modifier = Modifier
                        .fillMaxWidth(0.2f)
                        .height(3.dp)
                )
            }
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(horizontal = 16.dp)
                    .verticalScroll(rememberScrollState())
            ) {
                Spacer(modifier = Modifier.fillMaxWidth().height(16.dp))
                content()
                Spacer(modifier = Modifier.fillMaxWidth().height(16.dp))
            }
        }
    }
}