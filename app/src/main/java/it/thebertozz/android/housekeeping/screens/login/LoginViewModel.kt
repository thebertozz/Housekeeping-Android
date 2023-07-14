package it.thebertozz.android.housekeeping.screens.login

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import it.thebertozz.android.housekeeping.HOME_SCREEN
import it.thebertozz.android.housekeeping.LOGIN_SCREEN
import it.thebertozz.android.housekeeping.R
import it.thebertozz.android.housekeeping.SIGN_UP_SCREEN
import it.thebertozz.android.housekeeping.managers.FirebaseManager
import it.thebertozz.android.housekeeping.managers.SnackbarManager
import kotlinx.coroutines.launch

class LoginViewModel: ViewModel() {
    var uiState = mutableStateOf(LoginUiState())
        private set

    private val email get() = uiState.value.email
    private val password get() = uiState.value.password

    fun onEmailChange(newValue: String) {
        uiState.value = uiState.value.copy(email = newValue)
    }

    fun onPasswordChange(newValue: String) {
        uiState.value = uiState.value.copy(password = newValue)
    }

    fun onLoginClick(navigation: (String, String?) -> Unit) {
        if (email.isBlank()) {
            SnackbarManager.showMessage(R.string.blank_email)
            return
        }

        if (password.isBlank()) {
            SnackbarManager.showMessage(R.string.blank_password)
            return
        }

        //Per la gestione errori si potrebbe usare anche CoroutineExceptionHandler

        viewModelScope.launch() {
            SnackbarManager.showMessage(R.string.authenticating)
            FirebaseManager.authenticate(email, password) { error ->
                if (error == null) {
                    navigation(HOME_SCREEN, LOGIN_SCREEN)
                } else {
                    SnackbarManager.showMessage(R.string.login_error)
                }
            }
        }
    }

    fun onForgotPasswordClick() {
        if (email.isBlank()) {
            SnackbarManager.showMessage(R.string.blank_email)
            return
        }

        viewModelScope.launch() {
            FirebaseManager.sendRecoveryEmail(email) { error ->
                if (error != null) {
                    //onError(error)
                }
                else { SnackbarManager.showMessage(R.string.recovery_email) }
            }
        }
    }

    fun onSignUpClick(navigation: (String, String?) -> Unit) {
        viewModelScope.launch {
            navigation(SIGN_UP_SCREEN, null)
        }
    }
}