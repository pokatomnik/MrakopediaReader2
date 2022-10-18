package tk.pokatomnik.mrakopediareader2.screens.search

import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.Icon
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.ripple.rememberRipple
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import tk.pokatomnik.mrakopediareader2.ui.components.PageTitle

@Composable
fun SearchView(onSearch: (searchText: String) -> Unit) {
    val (searchText, setSearchText) = remember { mutableStateOf("") }

    val doSearch = {
        if (searchText.isNotBlank()) {
            onSearch(searchText.trim())
        }
    }

    return Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        PageTitle(title = "Поиск")
        OutlinedTextField(
            modifier = Modifier
                .padding(horizontal = 16.dp)
                .fillMaxWidth(),
            value = searchText,
            onValueChange = setSearchText,
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Text,
                imeAction = ImeAction.Search,
            ),
            keyboardActions = KeyboardActions(
                onSearch = { doSearch() }
            ),
            placeholder = { Text("Категория или название истории") },
            trailingIcon = {
                Icon(
                    modifier = Modifier
                        .clickable(
                            interactionSource = remember { MutableInteractionSource() },
                            indication = rememberRipple(
                                bounded = false,
                                radius = 16.dp
                            ),
                            onClick = doSearch
                        ),
                    imageVector = Icons.Filled.Search,
                    contentDescription = "",
                )
            }
        )
    }
}