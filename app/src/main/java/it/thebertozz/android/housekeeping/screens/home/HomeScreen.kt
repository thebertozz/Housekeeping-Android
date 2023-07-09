package it.thebertozz.android.housekeeping.screens.home

import android.Manifest
import android.app.Activity
import android.content.Intent
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import it.thebertozz.android.housekeeping.R
import it.thebertozz.android.housekeeping.utils.SimpleButton
import it.thebertozz.android.housekeeping.utils.BasicToolbar
import androidx.compose.material.Card
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.Scaffold
import androidx.compose.ui.platform.LocalContext
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.isGranted
import com.google.accompanist.permissions.rememberPermissionState
import it.thebertozz.android.housekeeping.objectdetection.TensorFlowActivity

@OptIn(ExperimentalMaterialApi::class, ExperimentalPermissionsApi::class)
@Composable
fun HomeScreen(
    navigation: (String, String?) -> Unit,
    modifier: Modifier = Modifier,
    viewModel: HomeScreenViewModel = viewModel()
) {
    val currentContext = LocalContext.current

    val homeUiState by viewModel.uiState.collectAsState()

    viewModel.getAll()

    Scaffold(
        floatingActionButton = {
                var shouldAskCameraPermission = false
                val permissionState =
                    rememberPermissionState(permission = Manifest.permission.CAMERA)
                if (permissionState.status.isGranted) {
                    Text("Camera permission Granted")
                } else {
                    shouldAskCameraPermission = true
                }

//                ExtendedFloatingActionButton(
//                    text = { Text(text = "Nuovo contenitore") },
//                    onClick = {
//
//                        if (shouldAskCameraPermission) {
//                            permissionState.launchPermissionRequest()
//                        } else {
//                            val tensorFlowActivity = rememberLauncherForActivityResult(
//                                contract = ActivityResultContracts.StartActivityForResult(),
//                                onResult = { result ->
//                                    // TODO
//                                }
//                            )
//                        }
//                    },
//                    icon = { Icon(Icons.Filled.AddCircle, "") }
//                )
        },
        topBar = { BasicToolbar(R.string.home) }) {
        paddingValues ->
        Column(
            modifier = modifier
                .fillMaxWidth()
                .fillMaxHeight()
                .verticalScroll(rememberScrollState())
                .padding(paddingValues),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            Column {
                homeUiState.currentInventory?.forEach { inventoryItem ->
                    Card(elevation = 8.dp, modifier = Modifier
                        .fillMaxWidth()
                        .wrapContentHeight()
                        .padding(16.dp), onClick = {
                        viewModel.onContainerDetailClick(inventoryItem.container.id, navigation)
                    }) {
                        Column() {
                            Text(inventoryItem.container.name ?: "")
                            Text("Contiene ${inventoryItem.items.size} elementi")
                        }
                    }
                }
            }

            SimpleButton(
                R.string.save_test_container,
                Modifier
                    .fillMaxWidth()
                    .padding(16.dp, 8.dp)
            ) {
                viewModel.saveTestContainer()
            }

            SimpleButton(
                R.string.save_test_item,
                Modifier
                    .fillMaxWidth()
                    .padding(16.dp, 8.dp)
            ) {
                viewModel.saveTestItem()
            }

            SimpleButton(
                R.string.get_all_items,
                Modifier
                    .fillMaxWidth()
                    .padding(16.dp, 8.dp)
            ) {
                viewModel.getAll()
            }

            val activityLauncher = rememberLauncherForActivityResult(
                contract = ActivityResultContracts.StartActivityForResult()
            ) { result ->
                // Qui puoi gestire il risultato ottenuto dall'Activity TensorFlowActivity
                if (result.resultCode == Activity.RESULT_OK) {
                    val selectedObject = result.data?.getStringExtra("object")
                    print("selected object: $selectedObject")
                }
            }

            Button(onClick = {
                activityLauncher.launch(Intent(currentContext, TensorFlowActivity::class.java))
            }) {
                Text(text = "Apri TensorFlowActivity")
            }
        }
    }
}
