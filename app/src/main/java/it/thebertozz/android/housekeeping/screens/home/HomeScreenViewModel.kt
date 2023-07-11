package it.thebertozz.android.housekeeping.screens.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import it.thebertozz.android.housekeeping.DETAIL_SCREEN
import it.thebertozz.android.housekeeping.NEW_CONTAINER_SCREEN
import it.thebertozz.android.housekeeping.managers.DatabaseManager
import it.thebertozz.android.housekeeping.models.Container
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class HomeScreenViewModel: ViewModel() {

    private val _uiState = MutableStateFlow(HomeUiState())

    val uiState: StateFlow<HomeUiState> = _uiState.asStateFlow()

    fun getAll() {
        viewModelScope.launch {
            val inventory = DatabaseManager.getDB()?.getAll()
            _uiState.value = HomeUiState(inventory)
        }
    }

    fun onContainerDetailClick(containerId: String?, navigation: (String, String?) -> Unit) {
        viewModelScope.launch() {
            navigation("$DETAIL_SCREEN/$containerId", null)
        }
    }

    fun onNewContainerClick(containerType: String, navigation: (String, String?) -> Unit) {
        viewModelScope.launch() {
            navigation("$NEW_CONTAINER_SCREEN?containerType=$containerType", null)
        }
    }

    fun onContainerLongTap(container: Container) {
        _uiState.value = uiState.value.copy(shouldShowDeletionAlert = true)
        _uiState.value = uiState.value.copy(selectedContainerForDeletion = container)
    }

    fun onDeleteItemConfirmation(container: Container) {
        _uiState.value = uiState.value.copy(shouldShowDeletionAlert = false)
        _uiState.value = uiState.value.copy(selectedContainerForDeletion = null)
        viewModelScope.launch {
            DatabaseManager.getDB()?.delete(container)
            val inventory = DatabaseManager.getDB()?.getAll()
            _uiState.value = HomeUiState(inventory)
        }
    }

    fun onDeleteItemDismiss() {
        _uiState.value = uiState.value.copy(shouldShowDeletionAlert = false)
        _uiState.value = uiState.value.copy(selectedContainerForDeletion = null)
    }
}
