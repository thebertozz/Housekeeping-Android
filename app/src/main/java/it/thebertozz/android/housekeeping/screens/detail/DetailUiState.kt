package it.thebertozz.android.housekeeping.screens.detail

import it.thebertozz.android.housekeeping.models.InventoryItem
import it.thebertozz.android.housekeeping.models.Item

data class DetailUiState(
    val inventoryItem: InventoryItem? = null,
    val newItemName: String = "",
    val newItemType: String = "",
    val newItemBestBeforeDate: String? = "",
    val shouldShowDeletionAlert: Boolean = false,
    var selectedItemForDeletion: Item? = null
)