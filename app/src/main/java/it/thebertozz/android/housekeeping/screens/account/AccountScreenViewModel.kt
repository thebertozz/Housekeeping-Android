package it.thebertozz.android.housekeeping.screens.account

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import it.thebertozz.android.housekeeping.ACCOUNT_SCREEN
import it.thebertozz.android.housekeeping.LOGIN_SCREEN
import it.thebertozz.android.housekeeping.managers.DatabaseManager
import it.thebertozz.android.housekeeping.managers.FirebaseManager
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class AccountScreenViewModel : ViewModel() {

    private val _uiState = MutableStateFlow(AccountUiState())

    var uiState: StateFlow<AccountUiState> = _uiState.asStateFlow()

    fun getCurrentUser() {
        _uiState.value.currentAccount = FirebaseManager.getUser()
    }

    fun onLogoutClicked() {
        _uiState.value = uiState.value.copy(shouldShowLogoutAlert = true)
    }

    fun onLogoutConfirmation(navigation: (String, String?) -> Unit) {
        _uiState.value = uiState.value.copy(shouldShowLogoutAlert = false)
        viewModelScope.launch {
            DatabaseManager.getDB()?.deleteAllContainers()
            FirebaseManager.signOut()
            navigation(LOGIN_SCREEN, ACCOUNT_SCREEN)
        }
    }

    fun onLogoutDismiss() {
        _uiState.value = uiState.value.copy(shouldShowLogoutAlert = false)
    }
}