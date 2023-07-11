package it.thebertozz.android.housekeeping.screens.container

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Card
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Description
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import it.thebertozz.android.housekeeping.R
import it.thebertozz.android.housekeeping.utils.BasicToolbar
import it.thebertozz.android.housekeeping.utils.NormalTextField
import it.thebertozz.android.housekeeping.utils.SimpleButton

@Composable
fun NewContainerScreen(
    containerName: String?,
    navigation: (String, String?) -> Unit,
    modifier: Modifier = Modifier,
    viewModel: NewContainerScreenViewModel = viewModel()
) {
    val containerUiState by viewModel.uiState.collectAsState()

    viewModel.onContainerTypeChange(containerName ?: "")

    val textFieldBoxHeight = 24.dp

    Scaffold(
        topBar = { BasicToolbar(R.string.new_container) }
    ) { paddingValues ->

        Column(
            modifier = modifier
                .fillMaxWidth()
                .verticalScroll(rememberScrollState())
                .padding(paddingValues)
                .padding(start = 24.dp, end = 24.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Box(Modifier.height(24.dp))
            Text(
                stringResource(id = R.string.new_container_description),
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold
            )
            Box(Modifier.height(textFieldBoxHeight))
            NormalTextField(
                value = containerUiState.newContainerName,
                onNewValue = viewModel::onContainerNameChange,
                placeholder = R.string.name,
                leadingIcon = Icons.Default.Description
            )
            Box(Modifier.height(textFieldBoxHeight))
            NormalTextField(
                value = containerUiState.newContainerDescription,
                onNewValue = viewModel::onContainerDescriptionChange,
                placeholder = R.string.description,
                leadingIcon = Icons.Default.Description
            )
            Box(Modifier.height(textFieldBoxHeight))
            NormalTextField(
                value = containerUiState.newContainerType,
                onNewValue = viewModel::onContainerTypeChange,
                placeholder = R.string.type,
                leadingIcon = Icons.Default.Description
            )
            Box(Modifier.height(textFieldBoxHeight))
            SimpleButton(text = R.string.save) {
                if (containerUiState.newContainerName.isNotBlank()
                    && containerUiState.newContainerDescription.isNotBlank()
                    && containerUiState.newContainerType.isNotBlank()
                ) {
                    viewModel.onSaveNewContainerClicked(navigation)
                }
            }
        }
    }
}