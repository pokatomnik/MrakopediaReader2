package tk.pokatomnik.mrakopediareader2.ui.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ChevronRight
import androidx.compose.material.ripple.rememberRipple
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

private const val HEIGHT = 64

@Composable
fun ListNavItem(
    title: String,
    description: String?,
    onNavigate: () -> Unit,
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .height(HEIGHT.dp)
            .clickable(
                onClick = onNavigate,
                indication = rememberRipple(bounded = true),
                interactionSource = remember { MutableInteractionSource() }
            )
            .padding(horizontal = LIST_ITEM_PADDING.dp),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f)
        ) {
            Text(
                text = title,
                maxLines = 1,
                style = MaterialTheme.typography.h6,
                overflow = TextOverflow.Ellipsis,
                modifier = Modifier.alpha(0.8f)
            )
            if (description != null) {
                Text(
                    text = description,
                    modifier = Modifier.alpha(0.54f),
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis,
                    style = MaterialTheme.typography.body2,
                )
            }
        }
        Column(
            modifier = Modifier
                .requiredHeight(HEIGHT.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Icon(
                imageVector = Icons.Filled.ChevronRight,
                contentDescription = "Открыть $title"
            )
        }
    }
}

@Preview
@Composable
fun ListNavItemPreviewSimple() {
    ListNavItem(title = "test", description = "test", onNavigate = {})
}

@Preview
@Composable
fun ListNavItemPreviewLongText() {
    ListNavItem(
        title = "test test test test test test test test test ",
        description = "test test test test test test test test test test test test test test test test ",
        onNavigate = {}
    )
}