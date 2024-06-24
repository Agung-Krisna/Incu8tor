package id.my.kaorikizuna.incu8tor.ui.components

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.outlined.Search
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LocalTextStyle
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.SearchBarColors
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ModifierLocalBeyondBoundsLayout
import androidx.compose.ui.modifier.modifierLocalMapOf
import androidx.compose.ui.node.InteroperableComposeUiNode
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.unit.dp
import id.my.kaorikizuna.incu8tor.R


// title changed if searching or not

fun onSearchClicked(searchTitle: String) {
    println("Searched for $searchTitle")
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Incu8torSearchBar(onSearchClicked: (String) -> Unit) {
    var showSearchField by remember { mutableStateOf(false) }
    CenterAlignedTopAppBar(
        title = { Text(stringResource(id = R.string.app_name)) },
        actions = {
            IconButton(onClick = { showSearchField = !showSearchField }) {
                if (showSearchField) {
                    Icon(imageVector = Icons.Filled.Close, contentDescription = "Close")
                } else {
                    Icon(imageVector = Icons.Filled.Search, contentDescription = "Search")
                }
            }
        },
        modifier = Modifier.fillMaxWidth(),
    )

    if (showSearchField) {
        SearchField(onSearchClicked)
    }
}

@Preview
@Composable
fun Incu8torSearchBarPreview() {
    // function reference (::) is used to pass the function as a parameter
    Incu8torSearchBar(::onSearchClicked)
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SearchField(
    onSearchClicked: (String) -> Unit
) {
    var searchText by remember { mutableStateOf("") }

    TextField(
        value = searchText,
        onValueChange = { searchText = it },
        label = { Text("Search") },
        modifier = Modifier.fillMaxWidth(),
        leadingIcon = {
            Icon(
                imageVector = Icons.Filled.Search, contentDescription = "Search"
            )
        },
        trailingIcon = {
            IconButton(onClick = { searchText = "" }) {
                Icon(
                    imageVector = Icons.Filled.Clear, contentDescription = "Clear"
                )
            }
        },
        // display search icon when displaying the virtual keyboard
        keyboardOptions = KeyboardOptions(
            imeAction = ImeAction.Search
        ),

        // on search clicked, callback to the onSearchClicked function
        keyboardActions = KeyboardActions(onSearch = {
            onSearchClicked(searchText)
        })

    )
}


@Preview
@Composable
fun SearchFieldPreview() {
    var searchText by remember {
        mutableStateOf("")
    }
    OutlinedTextField(value = searchText,
        onValueChange = { searchText = it },
        label = { Text("Search") },
        modifier = Modifier.fillMaxWidth(),
        leadingIcon = { Icon(imageVector = Icons.Filled.Search, contentDescription = "search") },
        trailingIcon = { Icon(imageVector = Icons.Filled.Close, contentDescription = "close") }
    )
}