package tk.pokatomnik.mrakopediareader2.ui.components

import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.runtime.Composable
import androidx.compose.foundation.lazy.items

data class LazyListItem(
    val id: String,
    val title: String,
    val description: String?
)

@Composable
fun LazyList(
    list: List<LazyListItem>,
    onClick: (item: LazyListItem) -> Unit,
) {
    val lazyListState = rememberLazyListState()
    LazyColumn(state = lazyListState) {
        items(list) {
            ListNavItem(
                title = it.title,
                description = it.description,
                onNavigate = { onClick(it) }
            )
        }
    }
}