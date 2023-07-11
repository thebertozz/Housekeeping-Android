package it.thebertozz.android.housekeeping.screens.home

import it.thebertozz.android.housekeeping.models.Container
import it.thebertozz.android.housekeeping.models.InventoryItem

data class HomeUiState(
    val currentInventory: List<InventoryItem>? = null,
    val shouldShowDeletionAlert: Boolean = false,
    var selectedContainerForDeletion: Container? = null
)