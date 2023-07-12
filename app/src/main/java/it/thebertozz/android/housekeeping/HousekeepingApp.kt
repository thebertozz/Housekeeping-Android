package it.thebertozz.android.housekeeping

import android.Manifest
import android.content.res.Resources
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.padding
import androidx.compose.material.BottomNavigation
import androidx.compose.material.BottomNavigationItem
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.ScaffoldState
import androidx.compose.material.Snackbar
import androidx.compose.material.SnackbarHost
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.rememberScaffoldState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.ReadOnlyComposable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.isGranted
import com.google.accompanist.permissions.rememberPermissionState
import com.google.accompanist.permissions.shouldShowRationale
import it.thebertozz.android.housekeeping.utils.commons.PermissionDialog
import it.thebertozz.android.housekeeping.utils.commons.PermissionRationaleDialog
import it.thebertozz.android.housekeeping.managers.SnackbarManager
import it.thebertozz.android.housekeeping.screens.account.AccountScreen
import it.thebertozz.android.housekeeping.screens.container.NewContainerScreen
import it.thebertozz.android.housekeeping.screens.detail.DetailScreen
import it.thebertozz.android.housekeeping.screens.home.HomeScreen
import it.thebertozz.android.housekeeping.screens.login.LoginScreen
import it.thebertozz.android.housekeeping.screens.signup.SignUpScreen
import it.thebertozz.android.housekeeping.screens.splash.SplashScreen
import it.thebertozz.android.housekeeping.ui.theme.HousekeepingTheme
import kotlinx.coroutines.CoroutineScope

/**
Classe componente principale per Compose che gestisce tutta la grafica di contesto, lo stato
ed il grafo delle navigazioni
*/

@Composable
@ExperimentalMaterialApi
fun HousekeepingApp() {
    HousekeepingTheme {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            //RequestNotificationPermissionDialog()
        }

        Surface(color = MaterialTheme.colors.background) {
            val appState = rememberAppState()

            val navBackStackEntry by appState.navController.currentBackStackEntryAsState()
            val currentScreen = navBackStackEntry?.destination

            Scaffold(
                bottomBar = {
                    if (appState.shouldShowBottomBar) {
                        BottomNavigation {
                            BottomNavigationItem(
                                selected = currentScreen?.route == HOME_SCREEN,
                                onClick = { appState.manageNavigation(HOME_SCREEN, null) },
                                icon = { Icon(Icons.Filled.Home, contentDescription = null) },
                                label = { Text(stringResource(id = R.string.home)) }
                            )

                            BottomNavigationItem(
                                selected = currentScreen?.route == ACCOUNT_SCREEN,
                                onClick = { appState.manageNavigation(ACCOUNT_SCREEN, null) },
                                icon = { Icon(Icons.Filled.Person, contentDescription = null) },
                                label = { Text(stringResource(id = R.string.account)) }
                            )
                        }
                    }
                },
                snackbarHost = {
                    SnackbarHost(
                        hostState = it,
                        modifier = Modifier.padding(8.dp),
                        snackbar = { snackbarData ->
                            Snackbar(snackbarData, contentColor = MaterialTheme.colors.onPrimary)
                        }
                    )
                },
                scaffoldState = appState.scaffoldState
            ) { innerPaddingModifier ->
                NavHost(
                    navController = appState.navController,
                    startDestination = SPLASH_SCREEN,
                    modifier = Modifier.padding(innerPaddingModifier)
                ) {
                    housekeepingGraph(appState)
                }
            }
        }
    }
}

@Composable
fun rememberAppState(
    scaffoldState: ScaffoldState = rememberScaffoldState(),
    navController: NavHostController = rememberNavController(),
    snackbarManager: SnackbarManager = SnackbarManager,
    resources: Resources = resources(),
    coroutineScope: CoroutineScope = rememberCoroutineScope()
) = remember(scaffoldState, navController, snackbarManager, resources, coroutineScope) {
    HouseKeepingAppState(scaffoldState, navController, snackbarManager, resources, coroutineScope)
}

@Composable
@ReadOnlyComposable
fun resources(): Resources {
    LocalConfiguration.current
    return LocalContext.current.resources
}

@ExperimentalMaterialApi
fun NavGraphBuilder.housekeepingGraph(appState: HouseKeepingAppState) {
    composable(SPLASH_SCREEN) {
        SplashScreen(
            navigation = { route, popUp -> appState.manageNavigation(route, popUp) }
        )
    }

    composable(LOGIN_SCREEN) {
        LoginScreen(
            navigation = { route, popUp -> appState.manageNavigation(route, popUp) }
        )
    }

    composable(SIGN_UP_SCREEN) {
        SignUpScreen(
            navController = appState.navController,
            navigation = { route, popUp -> appState.manageNavigation(route, popUp) }
        )
    }

    composable(HOME_SCREEN) {
        HomeScreen(
            navigation = { route, popUp -> appState.manageNavigation(route, popUp) }
        )
    }

    composable(
        "$DETAIL_SCREEN/{containerId}",
        arguments = listOf(navArgument("containerId") { NavType.StringType })
    ) { backStackEntry ->
        DetailScreen(
            appState.navController,
            backStackEntry.arguments?.getString("containerId") ?: "",
            navigation = { route, popUp -> appState.manageNavigation(route, popUp) }
        )
    }

    composable(
        "$NEW_CONTAINER_SCREEN?containerType={containerType}",
        arguments = listOf(navArgument("containerType") { defaultValue = "" })
    ) { backStackEntry ->
        NewContainerScreen(
            appState.navController,
            backStackEntry.arguments?.getString("containerType") ?: "",
            navigation = { route, popUp -> appState.manageNavigation(route, popUp) }
        )
    }

    composable(ACCOUNT_SCREEN) {
        AccountScreen(
            navigation = { route, popUp -> appState.manageNavigation(route, popUp) }
        )
    }

}

@RequiresApi(Build.VERSION_CODES.TIRAMISU)
@OptIn(ExperimentalPermissionsApi::class)
@Composable
fun RequestNotificationPermissionDialog() {
    val permissionState =
        rememberPermissionState(permission = Manifest.permission.POST_NOTIFICATIONS)

    if (!permissionState.status.isGranted) {
        if (permissionState.status.shouldShowRationale) PermissionRationaleDialog()
        else PermissionDialog { permissionState.launchPermissionRequest() }
    }
}