package it.thebertozz.android.housekeeping.screens.detail

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Card
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import it.thebertozz.android.housekeeping.R
import it.thebertozz.android.housekeeping.utils.BasicToolbar
import it.thebertozz.android.housekeeping.utils.NormalTextField
import it.thebertozz.android.housekeeping.utils.SimpleButton

@Composable
fun DetailScreen(
    containerId: String?,
    navigation: (String, String?) -> Unit,
    modifier: Modifier = Modifier,
    viewModel: DetailScreenViewModel = viewModel()
) {
    val detailUiState by viewModel.uiState.collectAsState()

    viewModel.getSelectedContainer(containerId ?: "")

    Scaffold(
        topBar = { BasicToolbar(R.string.container_detail) }
    ) {
        paddingValues ->

        Column(modifier = modifier
            .fillMaxWidth()
            .fillMaxHeight()
            .verticalScroll(rememberScrollState())
            .padding(paddingValues),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally) {

            Text("Aggiungi nuovo oggetto")
            NormalTextField(value = detailUiState.newItemName, onNewValue = viewModel::onItemNameChange, placeholder = R.string.item_name)
            NormalTextField(value = detailUiState.newItemType, onNewValue = viewModel::onItemTypeChange, placeholder = R.string.item_type)
            SimpleButton(text = R.string.save, modifier = Modifier) {
                if (detailUiState.newItemName.isNotBlank() && detailUiState.newItemType.isNotBlank()) {
                    viewModel.onSaveNewItemClicked()
                }
            }

            detailUiState.inventoryItem?.items?.forEach { singleItem ->
                Card(elevation = 8.dp, modifier = Modifier
                    .fillMaxWidth()
                    .wrapContentHeight()
                    .padding(16.dp)) {
                    Column() {
                        Text(singleItem.name ?: "")
                        Text("Tipologia: ${singleItem.type}")
                    }
                }
            }
        }
    }
}