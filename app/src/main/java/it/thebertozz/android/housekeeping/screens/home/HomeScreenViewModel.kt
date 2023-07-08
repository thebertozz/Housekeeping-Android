package it.thebertozz.android.housekeeping.screens.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import it.thebertozz.android.housekeeping.DETAIL_SCREEN
import it.thebertozz.android.housekeeping.managers.DatabaseManager
import it.thebertozz.android.housekeeping.models.Container
import it.thebertozz.android.housekeeping.models.Item
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import kotlin.random.Random

class HomeScreenViewModel: ViewModel() {

    private val _uiState = MutableStateFlow(HomeUiState())

    val uiState: StateFlow<HomeUiState> = _uiState.asStateFlow()

    val test_container_id = "test_container"
    val test_item_id = "test_item"

    fun saveTestContainer() {
        viewModelScope.launch {
            DatabaseManager.getDB()?.save(Container(test_container_id, "test_container", "test_description", "test"))
            getAll()
        }
    }

    fun saveTestItem() {
        viewModelScope.launch {
            DatabaseManager.getDB()?.save(Item(test_item_id  + Random.nextInt().toString(), "test_item", "test", System.currentTimeMillis(), test_container_id))

            getAll()
        }
    }

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
}
