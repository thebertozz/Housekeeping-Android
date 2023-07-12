package it.thebertozz.android.housekeeping

import android.content.res.Resources
import androidx.compose.material.ScaffoldState
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import it.thebertozz.android.housekeeping.managers.SnackbarManager
import it.thebertozz.android.housekeeping.managers.SnackbarMessage.Companion.toMessage
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.launch

/**
Classe che gestisce lo stato dell'app e permette le navigazioni
 */


class HouseKeepingAppState(
    val scaffoldState: ScaffoldState,
    val navController: NavHostController,
    private val snackbarManager: SnackbarManager,
    private val resources: Resources,
    coroutineScope: CoroutineScope
) {

    private val bottomBarRoutes = arrayListOf(HOME_SCREEN, ACCOUNT_SCREEN)

    val shouldShowBottomBar: Boolean
        @Composable get() = navController
            .currentBackStackEntryAsState().value?.destination?.route in bottomBarRoutes

    init {
        coroutineScope.launch {
            snackbarManager.snackbarMessages.filterNotNull().collect { snackbarMessage ->
                val text = snackbarMessage.toMessage(resources)
                scaffoldState.snackbarHostState.showSnackbar(text)
            }
        }
    }

    fun manageNavigation(route: String, popUp: String?) {
        navController.navigate(route) {
            launchSingleTop = true
            if (popUp != null) {
                popUpTo(popUp) { inclusive = true }
            }
        }
    }
}