package it.thebertozz.android.housekeeping.screens.detail

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import it.thebertozz.android.housekeeping.managers.DatabaseManager
import it.thebertozz.android.housekeeping.managers.NotificationsManager
import it.thebertozz.android.housekeeping.models.Item
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import java.util.Calendar
import kotlin.random.Random

class DetailScreenViewModel() : ViewModel() {

    private val _uiState = MutableStateFlow(DetailUiState())

    var uiState: StateFlow<DetailUiState> = _uiState.asStateFlow()

    fun onItemNameChange(newValue: String) {
        _uiState.value = uiState.value.copy(newItemName = newValue)
    }

    fun onItemTypeChange(newValue: String) {
        _uiState.value = uiState.value.copy(newItemType = newValue)
    }

    fun onBestBeforeDateChange(newValue: String) {
        _uiState.value = uiState.value.copy(newItemBestBeforeDate = newValue)
    }

    fun onSaveNewItemClicked(context: Context, calendar: Calendar) {
        viewModelScope.launch {
            val notificationId = Random.nextInt()
            DatabaseManager.getDB()?.save(
                Item(
                    Random.nextInt().toString(),
                    _uiState.value.newItemName,
                    _uiState.value.newItemType,
                    _uiState.value.newItemBestBeforeDate ?: "",
                    _uiState.value.inventoryItem?.container?.id ?: "",
                    notificationId
                )
            )
            getSelectedContainer(_uiState.value.inventoryItem?.container?.id ?: "")
            if (!_uiState.value.newItemBestBeforeDate.isNullOrBlank()) {
                calendar.set(Calendar.AM_PM, Calendar.AM)
                calendar.set(Calendar.HOUR, 14)
                calendar.set(Calendar.MINUTE, 19)
                //Setto le 12 (AM) del giorno impostato per la notifica
                NotificationsManager.scheduleNotification(
                    context,
                    _uiState.value.newItemName,
                    notificationId,
                    calendar
                )
            }
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

    fun onDeleteItemConfirmation(context: Context, item: Item) {
        _uiState.value = uiState.value.copy(shouldShowDeletionAlert = false)
        _uiState.value = uiState.value.copy(selectedItemForDeletion = null)
        viewModelScope.launch {
            DatabaseManager.getDB()?.delete(item)
            getSelectedContainer(_uiState.value.inventoryItem?.container?.id ?: "")
            if (item.bestBeforeDate.isNotBlank()) {
                NotificationsManager.cancelNotification(
                    context,
                    item.name,
                    item.notificationID ?: 0
                )
            }
        }
    }

    fun onDeleteItemDismiss() {
        _uiState.value = uiState.value.copy(shouldShowDeletionAlert = false)
        _uiState.value = uiState.value.copy(selectedItemForDeletion = null)
    }
}
