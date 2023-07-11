package it.thebertozz.android.housekeeping.screens.detail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import it.thebertozz.android.housekeeping.managers.DatabaseManager
import it.thebertozz.android.housekeeping.models.Item
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import kotlin.random.Random

class DetailScreenViewModel : ViewModel() {

    private val _uiState = MutableStateFlow(DetailUiState())

    var uiState: StateFlow<DetailUiState> = _uiState.asStateFlow()

    fun onItemNameChange(newValue: String) {
        _uiState.value = uiState.value.copy(newItemName = newValue)
    }

    fun onItemTypeChange(newValue: String) {
        _uiState.value = uiState.value.copy(newItemType = newValue)
    }

    fun onSaveNewItemClicked() {
        viewModelScope.launch {
            DatabaseManager.getDB()?.save(
                Item(
                    Random.nextInt().toString(),
                    _uiState.value.newItemName,
                    _uiState.value.newItemType,
                    System.currentTimeMillis(),
                    _uiState.value.inventoryItem?.container?.id ?: ""
                )
            )
            getSelectedContainer(_uiState.value.inventoryItem?.container?.id ?: "")
        }
    }

    fun getSelectedContainer(containerId: String) {
        viewModelScope.launch {
            val container = DatabaseManager.getDB()?.getByContainerId(containerId)
            _uiState.value = DetailUiState(container)
        }
    }

    fun onItemLongTap(item: Item) {
        _uiState.value = uiState.value.copy(shouldShowDeletionAlert = true)
        _uiState.value = uiState.value.copy(selectedItemForDeletion = item)
    }

    fun onDeleteItemConfirmation(item: Item) {
        _uiState.value = uiState.value.copy(shouldShowDeletionAlert = false)
        _uiState.value = uiState.value.copy(selectedItemForDeletion = null)
        viewModelScope.launch {
            DatabaseManager.getDB()?.delete(item)
            getSelectedContainer(_uiState.value.inventoryItem?.container?.id ?: "")
        }
    }

    fun onDeleteItemDismiss() {
        _uiState.value = uiState.value.copy(shouldShowDeletionAlert = false)
        _uiState.value = uiState.value.copy(selectedItemForDeletion = null)
    }
}
