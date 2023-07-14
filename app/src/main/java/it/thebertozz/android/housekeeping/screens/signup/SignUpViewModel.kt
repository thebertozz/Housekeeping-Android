package it.thebertozz.android.housekeeping.screens.signup

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import it.thebertozz.android.housekeeping.LOGIN_SCREEN
import it.thebertozz.android.housekeeping.R
import it.thebertozz.android.housekeeping.SIGN_UP_SCREEN
import it.thebertozz.android.housekeeping.managers.FirebaseManager
import it.thebertozz.android.housekeeping.managers.SnackbarManager
import kotlinx.coroutines.launch

class SignUpViewModel: ViewModel() {
  var uiState = mutableStateOf(SignUpUiState())
    private set

  private val email
    get() = uiState.value.email
  private val password
    get() = uiState.value.password

  fun onEmailChange(newValue: String) {
    uiState.value = uiState.value.copy(email = newValue)
  }

  fun onPasswordChange(newValue: String) {
    uiState.value = uiState.value.copy(password = newValue)
  }

  fun onRepeatPasswordChange(newValue: String) {
    uiState.value = uiState.value.copy(repeatPassword = newValue)
  }

  fun onSignUpClick(navigation: (String, String?) -> Unit) {
    if (email.isBlank()) {
      SnackbarManager.showMessage(R.string.blank_email)
      return
    }

    if (password.isBlank()) {
      SnackbarManager.showMessage(R.string.blank_password)
      return
    }

    if (password != uiState.value.repeatPassword) {
      SnackbarManager.showMessage(R.string.password_not_corresponding)
      return
    }

    viewModelScope.launch() {
      SnackbarManager.showMessage(R.string.account_created)
      FirebaseManager.createUser(email, password) { error ->
        if (error != null) {
          SnackbarManager.showMessage(R.string.signup_error)
        }
        else {
          SnackbarManager.showMessage(R.string.account_created)
          navigation(LOGIN_SCREEN, SIGN_UP_SCREEN)
        }
      }
    }
  }
}
