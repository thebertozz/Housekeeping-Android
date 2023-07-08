package it.thebertozz.android.housekeeping.screens.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import it.thebertozz.android.housekeeping.managers.DatabaseManager
import it.thebertozz.android.housekeeping.models.Container
import it.thebertozz.android.housekeeping.models.Item
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.launch
import java.util.Date

class HomeScreenViewModel: ViewModel() {

    val test_container_id = "test_container"
    val test_item_id = "test_item"

    fun saveTestContainer() {
        viewModelScope.launch {
            DatabaseManager.getDB()?.save(Container(test_container_id, "test_container", "test_description"))
        }
    }

    fun saveTestItem() {
        viewModelScope.launch {
            DatabaseManager.getDB()?.save(Item(test_item_id, "test_item", System.currentTimeMillis(), test_container_id))
        }
    }

    fun getAll() {
        viewModelScope.launch {
            var inventory = DatabaseManager.getDB()?.getAll()

            print(inventory)
        }
    }
}
