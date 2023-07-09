package it.thebertozz.android.housekeeping.screens.account

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import it.thebertozz.android.housekeeping.R
import it.thebertozz.android.housekeeping.utils.BasicToolbar

@Composable
fun AccountScreen(
    navigation: (String, String?) -> Unit,
    modifier: Modifier = Modifier,
    viewModel: AccountScreenViewModel = viewModel()
) {

    Scaffold(topBar = { BasicToolbar(R.string.account) }) { paddingValues ->
        Column(
            modifier = modifier
                .fillMaxWidth()
                .fillMaxHeight()
                .verticalScroll(rememberScrollState())
                .padding(paddingValues),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(text = "Account")
        }
    }
}
