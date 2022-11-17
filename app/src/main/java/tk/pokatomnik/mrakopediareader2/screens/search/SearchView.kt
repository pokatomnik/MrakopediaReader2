package tk.pokatomnik.mrakopediareader2.screens.search

import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.*
import androidx.compose.material.TextFieldDefaults.TextFieldDecorationBox
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Search
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import com.google.accompanist.flowlayout.FlowRow
import tk.pokatomnik.mrakopediareader2.services.index.rememberMrakopediaIndex
import tk.pokatomnik.mrakopediareader2.ui.components.ColoredChip
import tk.pokatomnik.mrakopediareader2.ui.components.PageContainer

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun SearchView(
    onBackPress: () -> Unit,
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

    PageContainer(
        priorButton = {
            IconButton(onClick = onBackPress) {
                Icon(
                    imageVector = Icons.Filled.ArrowBack,
                    contentDescription = "Назад"
                )
            }
        },
        header = {
            val searchTextColor = contentColorFor(MaterialTheme.colors.primarySurface)
            BasicTextField(
                modifier = Modifier
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
                maxLines = 1,
                cursorBrush = SolidColor(searchTextColor),
                textStyle = TextStyle(color = searchTextColor),
                decorationBox = {
                    TextFieldDecorationBox(
                        value = searchText,
                        innerTextField = it,
                        enabled = true,
                        singleLine = true,
                        visualTransformation = VisualTransformation.None,
                        interactionSource = remember { MutableInteractionSource() },
                        placeholder = {
                            Text(
                                text = "Введите текст...",
                                color = searchTextColor
                            )
                        }
                    )
                }
            )
        },
        trailingButton = {
            IconButton(onClick = { doSearch() }) {
                Icon(
                    imageVector = Icons.Filled.Search,
                    contentDescription = "Искать"
                )
            }
        }
    ) {
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