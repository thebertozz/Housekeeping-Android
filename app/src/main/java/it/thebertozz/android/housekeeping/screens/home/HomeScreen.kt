package it.thebertozz.android.housekeeping.screens.home

import HomeListTile
import android.app.Activity
import android.content.Intent
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
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
import androidx.compose.material.Divider
import androidx.compose.material.ExtendedFloatingActionButton
import androidx.compose.material.FloatingActionButton
import androidx.compose.material.Icon
import androidx.compose.material.Scaffold
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.CameraAlt
import androidx.compose.material.icons.filled.LibraryBooks
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.sp
import it.thebertozz.android.housekeeping.objectdetection.TensorFlowActivity
import it.thebertozz.android.housekeeping.utils.AlertDialog

@OptIn(
    ExperimentalFoundationApi::class
)
@Composable
fun HomeScreen(
    navigation: (String, String?) -> Unit,
    modifier: Modifier = Modifier,
    viewModel: HomeScreenViewModel = viewModel()
) {
    val currentContext = LocalContext.current

    val homeUiState by viewModel.uiState.collectAsState()

    viewModel.getAll()

    val activityLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.StartActivityForResult()
    ) { result ->
        // Qui viene gestito il risultato ottenuto dall'Activity TensorFlowActivity

        if (result.resultCode == Activity.RESULT_OK) {
            val selectedObjectType =
                result.data?.getStringExtra("object")?.replaceFirstChar { it.uppercase() }
            print("selected object: $selectedObjectType")
            viewModel.onNewContainerClick(selectedObjectType ?: "", navigation)
        }
    }

    Scaffold(
        floatingActionButton = {
            Row() {
                ExtendedFloatingActionButton(
                    text = { Text(text = stringResource(id = R.string.new_container)) },
                    onClick = { viewModel.onNewContainerClick("", navigation) },
                    icon = { Icon(Icons.Filled.Add, "") }
                )
                Box(Modifier.width(8.dp))
                FloatingActionButton(onClick = {
                    activityLauncher.launch(Intent(currentContext, TensorFlowActivity::class.java))
                }) {
                    Icon(Icons.Default.CameraAlt, contentDescription = null)
                }
            }
        },
        topBar = { BasicToolbar(R.string.home) }) { paddingValues ->

        if (homeUiState.currentInventory.isNullOrEmpty()) {

            Column(
                modifier = modifier
                    .fillMaxWidth()
                    .fillMaxHeight()
                    .verticalScroll(rememberScrollState())
                    .padding(paddingValues),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Column(
                    modifier = Modifier.padding(16.dp),
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Icon(
                        Icons.Default.LibraryBooks,
                        contentDescription = null,
                        Modifier.size(80.dp)
                    )
                    Box(Modifier.height(16.dp))
                    Text(
                        "Non sono ancora stati aggiunti contenitori.\n\nPer iniziare, usa i pulsanti in basso per aggiungere il primo.",
                        fontSize = 18.sp,
                        textAlign = TextAlign.Center
                    )
                }
            }

        } else {

            Column(
                modifier = modifier
                    .fillMaxWidth()
                    .verticalScroll(rememberScrollState())
                    .padding(paddingValues),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {

                Box(Modifier.height(16.dp))

                Text(
                    stringResource(id = R.string.your_containers),
                    fontSize = 21.sp,
                    fontWeight = FontWeight.Bold,
                    modifier = modifier
                        .padding(horizontal = 16.dp)
                        .align(Alignment.Start)
                )

                Box(Modifier.height(8.dp))

                Column(
                    modifier = modifier
                        .fillMaxWidth()
                        .padding(paddingValues)
                        .padding(horizontal = 16.dp),
                    horizontalAlignment = Alignment.Start
                ) {
                    homeUiState.currentInventory?.forEach { inventoryItem ->
                        HomeListTile(
                            inventoryItem.container.name,
                            inventoryItem.container.description,
                            inventoryItem.container.type,
                            inventoryItem.items.size,
                            modifier = Modifier.combinedClickable(
                                onClick = {
                                    viewModel.onContainerDetailClick(
                                        inventoryItem.container.id,
                                        navigation
                                    )
                                },
                                onLongClick = {
                                    viewModel.onContainerLongTap(inventoryItem.container)
                                }
                            )
                        )
                        Divider()
                    }

                    if (homeUiState.shouldShowDeletionAlert) {
                        AlertDialog(confirmClicked = {
                            viewModel.onDeleteItemConfirmation(homeUiState.selectedContainerForDeletion!!)
                        }, dismissClicked = {
                            viewModel.onDeleteItemDismiss()
                        }, text = R.string.delete_item_confirmation)
                    }
                }
            }
        }
    }
}