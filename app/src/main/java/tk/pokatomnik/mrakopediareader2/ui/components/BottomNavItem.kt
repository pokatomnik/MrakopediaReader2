package tk.pokatomnik.mrakopediareader2.ui.components

import androidx.compose.foundation.layout.RowScope
import androidx.compose.material.BottomNavigationItem
import androidx.compose.material.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.vector.ImageVector


@Composable
fun RowScope.BottomNavItem(
    selected: Boolean,
    icon: ImageVector,
    title: String,
    onClick: () -> Unit,
) {
    BottomNavigationItem(
        selected = selected,
        onClick = onClick,
        icon = {
            Icon(
                imageVector = icon,
                contentDescription = title
            )
        }
    )
}