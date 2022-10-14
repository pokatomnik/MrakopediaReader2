package tk.pokatomnik.mrakopediareader2.ui.components

import androidx.compose.foundation.layout.*
import androidx.compose.material.Button
import androidx.compose.material.Icon
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDownward
import androidx.compose.material.icons.filled.ArrowUpward
import androidx.compose.material.icons.filled.List
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

enum class SortDirection { ASC, DESC }

@Composable
fun SortButton(
    icon: ImageVector,
    sortingDescription: String,
    direction: SortDirection?,
    onSortDirectionChange: (sortDirection: SortDirection) -> Unit
) {
    Button(
        modifier = Modifier.width(72.dp),
        onClick = {
            val newSortDirection = when (direction) {
                SortDirection.ASC -> SortDirection.DESC
                SortDirection.DESC -> SortDirection.ASC
                else -> SortDirection.DESC
            }
            onSortDirectionChange(newSortDirection)
        },
    ) {
        Icon(
            imageVector = icon,
            contentDescription = sortingDescription
        )
        if (direction == null) {
            Spacer(modifier = Modifier.weight(1f))
        } else {
            Icon(
                imageVector = if (direction == SortDirection.ASC) {
                    Icons.Filled.ArrowUpward
                } else {
                    Icons.Filled.ArrowDownward
                },
                contentDescription = if (direction == SortDirection.ASC) {
                    "Сортировка по возрастанию"
                } else {
                    "Сортировка по убыванию"
                }
            )
        }
    }
}

@Preview
@Composable
fun SortButtonNull() {
    Row(
        modifier = Modifier.fillMaxWidth().height(48.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceAround
    ) {
        SortButton(
            icon = Icons.Filled.List,
            sortingDescription = "Категория",
            direction = null,
            onSortDirectionChange = {}
        )
        SortButton(
            icon = Icons.Filled.List,
            sortingDescription = "Категория",
            direction = SortDirection.ASC,
            onSortDirectionChange = {}
        )
        SortButton(
            icon = Icons.Filled.List,
            sortingDescription = "Категория",
            direction = SortDirection.DESC,
            onSortDirectionChange = {}
        )
    }
}