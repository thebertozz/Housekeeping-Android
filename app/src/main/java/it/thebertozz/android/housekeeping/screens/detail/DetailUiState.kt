package it.thebertozz.android.housekeeping.screens.detail

import it.thebertozz.android.housekeeping.models.InventoryItem

data class DetailUiState(val inventoryItem: InventoryItem? = null, val newItemName: String = "", val newItemType: String = "")