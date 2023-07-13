package it.thebertozz.android.housekeeping.screens.container

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import it.thebertozz.android.housekeeping.HOME_SCREEN
import it.thebertozz.android.housekeeping.NEW_CONTAINER_SCREEN
import it.thebertozz.android.housekeeping.R
import it.thebertozz.android.housekeeping.managers.DatabaseManager
import it.thebertozz.android.housekeeping.managers.SnackbarManager
import it.thebertozz.android.housekeeping.models.Container
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import java.util.UUID

class NewContainerScreenViewModel : ViewModel() {

    private val _uiState = MutableStateFlow(ContainerUiState())

    private val TAG = "NewContainerScreenViewModel"

    var uiState: StateFlow<ContainerUiState> = _uiState.asStateFlow()

    fun onContainerNameChange(newValue: String) {
        _uiState.value = uiState.value.copy(newContainerName = newValue)
    }

    fun onContainerDescriptionChange(newValue: String) {
        _uiState.value = uiState.value.copy(newContainerDescription = newValue)
    }

    fun onContainerTypeChange(newValue: String) {
        _uiState.value = uiState.value.copy(newContainerType = newValue)
    }

    fun onSaveNewContainerClicked(navigation: (String, String?) -> Unit) {

        val uuid = UUID.randomUUID().toString()

        viewModelScope.launch {
            DatabaseManager.getDB()?.save(
                Container(
                    uuid,
                    _uiState.value.newContainerName.trimEnd(),
                    _uiState.value.newContainerDescription.trimEnd(),
                    _uiState.value.newContainerType.trimEnd()
                )
            )
            onNewContainerAdded(navigation)
        }
    }

    private fun onNewContainerAdded(navigation: (String, String?) -> Unit) {
        viewModelScope.launch() {
            navigation(HOME_SCREEN, NEW_CONTAINER_SCREEN)
        }
    }

    fun onEmptyFields() {
        viewModelScope.launch {
            SnackbarManager.showMessage(R.string.complete_fields)
        }
    }
}
