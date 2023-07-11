package it.thebertozz.android.housekeeping.screens.detail

import DetailListTile
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Divider
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Description
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import it.thebertozz.android.housekeeping.R
import it.thebertozz.android.housekeeping.utils.AlertDialog
import it.thebertozz.android.housekeeping.utils.BasicToolbar
import it.thebertozz.android.housekeeping.utils.NormalTextField
import it.thebertozz.android.housekeeping.utils.SimpleButton

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun DetailScreen(
    containerId: String?,
    navigation: (String, String?) -> Unit,
    modifier: Modifier = Modifier,
    viewModel: DetailScreenViewModel = viewModel()
) {
    val detailUiState by viewModel.uiState.collectAsState()

    viewModel.getSelectedContainer(containerId ?: "")

    val heightPadding = 24.dp

    Scaffold(
        topBar = { BasicToolbar(R.string.container_detail) }
    ) { paddingValues ->

        Column(
            modifier = modifier
                .fillMaxWidth()
                //.verticalScroll(rememberScrollState())
                .padding(paddingValues)
                .padding(start = 24.dp, end = 24.dp),
        ) {
            Box(Modifier.height(24.dp))
            Text(
                stringResource(id = R.string.selected_container) + " " + detailUiState.inventoryItem?.container?.name,
                fontSize = 21.sp,
                fontWeight = FontWeight.Bold,
            )
            Box(Modifier.height(heightPadding))
            Text(stringResource(id = R.string.add_new_item), fontSize = 16.sp)
            Box(Modifier.height(heightPadding))
            NormalTextField(
                value = detailUiState.newItemName,
                onNewValue = viewModel::onItemNameChange,
                placeholder = R.string.name,
                leadingIcon = Icons.Default.Description
            )
            Box(Modifier.height(heightPadding))
            NormalTextField(
                value = detailUiState.newItemType,
                onNewValue = viewModel::onItemTypeChange,
                placeholder = R.string.type,
                leadingIcon = Icons.Default.Description
            )
            Box(Modifier.height(heightPadding))
            SimpleButton(text = R.string.save) {
                if (detailUiState.newItemName.isNotBlank() && detailUiState.newItemType.isNotBlank()) {
                    viewModel.onSaveNewItemClicked()
                }
            }

            Box(Modifier.height(heightPadding / 2))

            if (!detailUiState.inventoryItem?.items.isNullOrEmpty()) {

                Text(
                    text = "Oggetti censiti:", fontSize = 18.sp,
                    fontWeight = FontWeight.Bold, modifier = modifier.padding(vertical = 8.dp)
                )

                Box(Modifier.height(heightPadding / 2))

                Column(
                    modifier
                        .fillMaxWidth()
                        .verticalScroll(rememberScrollState())
                        .padding(paddingValues)
                ) {

                    detailUiState.inventoryItem?.items?.forEach { singleItem ->
                        DetailListTile(name = singleItem.name,
                            type = singleItem.type ?: "",
                            modifier = Modifier.combinedClickable(
                                onLongClick = {
                                    viewModel.onItemLongTap(singleItem)
                                }
                            ) {  })
                        Divider()
                    }

                    Box(Modifier.height(heightPadding))
                }
            }
        }

        if(detailUiState.shouldShowDeletionAlert) {
            AlertDialog(confirmClicked = {
                viewModel.onDeleteItemConfirmation(detailUiState.selectedItemForDeletion!!)
            }, dismissClicked = {
                viewModel.onDeleteItemDismiss()
            })
        }
    }
}