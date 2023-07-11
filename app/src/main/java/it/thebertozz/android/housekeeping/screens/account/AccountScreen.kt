package it.thebertozz.android.housekeeping.screens.account

import android.graphics.Paint.Align
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Icon
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import it.thebertozz.android.housekeeping.R
import it.thebertozz.android.housekeeping.utils.AlertDialog
import it.thebertozz.android.housekeeping.utils.BasicToolbar
import it.thebertozz.android.housekeeping.utils.SimpleButton

@Composable
fun AccountScreen(
    navigation: (String, String?) -> Unit,
    modifier: Modifier = Modifier,
    viewModel: AccountScreenViewModel = viewModel()
) {
    val accountUiScreen by viewModel.uiState.collectAsState()

    viewModel.getCurrentUser()

    val heightPadding = 24.dp

    Scaffold(
        topBar = { BasicToolbar(R.string.account) }
    ) { paddingValues ->

        Column(
            modifier = modifier
                .fillMaxWidth()
                .fillMaxHeight()
                .padding(paddingValues)
                .padding(start = 24.dp, end = 24.dp),
        ) {
            Box(Modifier.height(24.dp))
            Icon(imageVector = Icons.Default.AccountCircle, contentDescription = null, Modifier.size(80.dp).align(Alignment.CenterHorizontally))
            Box(Modifier.height(24.dp))
            Text(accountUiScreen.currentAccount?.email ?: "", modifier = Modifier.align(Alignment.CenterHorizontally), fontSize = 20.sp)
            Box(Modifier.height(24.dp))
            SimpleButton(text = R.string.logout, action = {
                viewModel.onLogoutClicked()
            })
        }

        if(accountUiScreen.shouldShowLogoutAlert) {
            AlertDialog(confirmClicked = {
                viewModel.onLogoutConfirmation(navigation)
            }, dismissClicked = {
                viewModel.onLogoutDismiss()
            }, text = R.string.logout_alert)
        }
    }
}
