package it.thebertozz.android.housekeeping.screens.account

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import it.thebertozz.android.housekeeping.R
import it.thebertozz.android.housekeeping.composables.BasicToolbar

@Composable
fun AccountScreen(
    navigation: (String, String?) -> Unit,
    modifier: Modifier = Modifier,
    viewModel: AccountScreenViewModel = viewModel()
) {
    BasicToolbar(R.string.account)

    Column(
        modifier = modifier
            .fillMaxWidth()
            .fillMaxHeight()
            .verticalScroll(rememberScrollState())
            .padding(top = 64.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        Text(text = "Account")
    }
}
