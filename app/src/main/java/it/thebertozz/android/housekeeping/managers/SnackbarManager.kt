package it.thebertozz.android.housekeeping.managers

import android.content.res.Resources
import androidx.annotation.StringRes
import it.thebertozz.android.housekeeping.R
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

/** Classe object per la gestione centralizzata delle SnackBar da mostrare all'utente */

object SnackbarManager {
    private val messages: MutableStateFlow<SnackbarMessage?> = MutableStateFlow(null)
    val snackbarMessages: StateFlow<SnackbarMessage?> get() = messages.asStateFlow()

    fun showMessage(@StringRes message: Int) {
        messages.value = SnackbarMessage.ResourceSnackbar(message)
    }
}

sealed class SnackbarMessage {
    class StringSnackbar(val message: String): SnackbarMessage()
    class ResourceSnackbar(@StringRes val message: Int): SnackbarMessage()

    companion object {
        fun SnackbarMessage.toMessage(resources: Resources): String {
            return when (this) {
                is StringSnackbar -> this.message
                is ResourceSnackbar -> resources.getString(this.message)
            }
        }
    }
}