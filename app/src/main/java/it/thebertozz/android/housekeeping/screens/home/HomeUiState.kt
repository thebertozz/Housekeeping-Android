package it.thebertozz.android.housekeeping.screens.home

import it.thebertozz.android.housekeeping.models.InventoryItem

data class HomeUiState(val currentInventory: List<InventoryItem>? = null)