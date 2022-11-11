package tk.pokatomnik.mrakopediareader2.screens.search

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.*
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
import com.google.accompanist.flowlayout.FlowRow
import tk.pokatomnik.mrakopediareader2.services.index.rememberMrakopediaIndex
import tk.pokatomnik.mrakopediareader2.ui.components.ColoredChip

@Composable
fun SearchView(
    onSearch: (searchText: String) -> Unit,
    onSelectPageTitle: (pageTitle: String) -> Unit,
) {
    val mrakopediaIndex = rememberMrakopediaIndex()
    val (searchText, setSearchText) = remember { mutableStateOf("") }

    val doSearch = {
        if (searchText.isNotBlank()) {
            onSearch(searchText.trim())
        }
    }

    val randomPageTitles = remember {
        mrakopediaIndex.getRandomTitles(10)
    }

    return Column(
        modifier = Modifier
            .fillMaxSize()
            .background(color = MaterialTheme.colors.surface),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Spacer(modifier = Modifier.fillMaxWidth().height(16.dp))
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
                    contentDescription = "Поиск",
                )
            }
        )
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 16.dp)
                .verticalScroll(rememberScrollState()),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Spacer(modifier = Modifier
                .fillMaxWidth()
                .height(16.dp))
            Text(text = "Возможно также заинтересует:")
            Spacer(modifier = Modifier
                .fillMaxWidth()
                .height(16.dp))
            FlowRow(modifier = Modifier.fillMaxWidth()) {
                randomPageTitles.forEach {
                    ColoredChip(
                        text = it,
                        modifier = Modifier.padding(end = 16.dp, bottom = 16.dp),
                        maxChars = 40,
                        onClick = { onSelectPageTitle(it) }
                    )
                }
            }
            Spacer(modifier = Modifier
                .fillMaxWidth()
                .height(16.dp))
        }
    }
}