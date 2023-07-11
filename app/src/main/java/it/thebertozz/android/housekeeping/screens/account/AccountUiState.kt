package it.thebertozz.android.housekeeping.screens.account

import com.google.firebase.auth.FirebaseUser

data class AccountUiState(
    var currentAccount: FirebaseUser? = null,
    val shouldShowLogoutAlert: Boolean = false,
)