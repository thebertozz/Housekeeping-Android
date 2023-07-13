package it.thebertozz.android.housekeeping.screens.detail

import DetailListTile
import android.app.DatePickerDialog
import android.os.Build
import android.widget.DatePicker
import androidx.annotation.RequiresApi
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
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.isGranted
import com.google.accompanist.permissions.rememberPermissionState
import com.google.accompanist.permissions.shouldShowRationale
import it.thebertozz.android.housekeeping.R
import it.thebertozz.android.housekeeping.commons.AlertDialog
import it.thebertozz.android.housekeeping.commons.BasicAppBar
import it.thebertozz.android.housekeeping.commons.NormalTextField
import it.thebertozz.android.housekeeping.commons.SimpleButton
import it.thebertozz.android.housekeeping.permissions.PermissionDialog
import it.thebertozz.android.housekeeping.permissions.PermissionRationaleDialog
import java.util.Calendar

/**
Classe che mostra il dettaglio di un contenitore.
Il view model gestisce le azioni dei pulsanti e dei text field e l'eliminazione di un elemento
*/

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun DetailScreen(
    navController: NavController?,
    containerId: String?,
    navigation: (String, String?) -> Unit,
    modifier: Modifier = Modifier,
    viewModel: DetailScreenViewModel = viewModel()
) {

    val currentContext = LocalContext.current

    val detailUiState by viewModel.uiState.collectAsState()

    viewModel.getSelectedContainer(containerId ?: "")

    val heightPadding = 24.dp

    //Variabili per il calendario
    val calendar = Calendar.getInstance()
    val currentYear = calendar.get(Calendar.YEAR)
    val currentMonth = calendar.get(Calendar.MONTH)
    val currentDay = calendar.get(Calendar.DAY_OF_MONTH)

    val datePickerDialog = DatePickerDialog(
        LocalContext.current,
        { _: DatePicker, selectedYear: Int, selectedMonth: Int, selectedDay: Int ->
            viewModel.onBestBeforeDateChange("$selectedDay/${selectedMonth+1}/$selectedYear")
            calendar.set(Calendar.DAY_OF_MONTH, selectedDay)
            calendar.set(Calendar.MONTH, selectedMonth)
            calendar.set(Calendar.YEAR, selectedYear)
        }, currentYear, currentMonth, currentDay
    )

    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
        RequestNotificationPermissionDialog()
    }

    Scaffold(
        topBar = { BasicAppBar(R.string.container_detail, navController = navController) }
    ) { paddingValues ->

        Column(
            modifier = modifier
                .fillMaxWidth()
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
            NormalTextField(
                readOnly = true,
                value = detailUiState.newItemBestBeforeDate.toString(),
                onNewValue = viewModel::onItemTypeChange,
                placeholder = R.string.best_before_date,
                leadingIcon = Icons.Default.Description
            )
            Box(Modifier.height(heightPadding))
            SimpleButton(text = R.string.best_before_date_button, action = {
                datePickerDialog.show()
            })
            Box(Modifier.height(heightPadding))
            SimpleButton(text = R.string.save) {
                if (detailUiState.newItemName.isNotBlank() && detailUiState.newItemType.isNotBlank()) {
                    viewModel.onSaveNewItemClicked(currentContext, calendar)
                } else {
                    viewModel.onEmptyFields()
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
                        DetailListTile(singleItem,
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
                viewModel.onDeleteItemConfirmation(currentContext, detailUiState.selectedItemForDeletion!!)
            }, dismissClicked = {
                viewModel.onDeleteItemDismiss()
            }, text = R.string.delete_item_confirmation)
        }
    }
}

@RequiresApi(Build.VERSION_CODES.TIRAMISU)
@OptIn(ExperimentalPermissionsApi::class)
@Composable
fun RequestNotificationPermissionDialog() {
    val permissionState =
        rememberPermissionState(permission = android.Manifest.permission.POST_NOTIFICATIONS)

    if (!permissionState.status.isGranted) {
        if (permissionState.status.shouldShowRationale) PermissionRationaleDialog(R.string.notification_permission_title, R.string.notification_permission_rationale)
        else PermissionDialog(R.string.notification_permission_title, R.string.notification_permission_rationale) { permissionState.launchPermissionRequest() }
    }
}