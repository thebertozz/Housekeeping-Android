package it.thebertozz.android.housekeeping.screens.home

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
import it.thebertozz.android.housekeeping.composables.BasicButton
import it.thebertozz.android.housekeeping.composables.BasicToolbar

@Composable
fun HomeScreen(
    navigation: (String, String?) -> Unit,
    modifier: Modifier = Modifier,
    viewModel: HomeScreenViewModel = viewModel()
) {
    BasicToolbar(R.string.home)

    Column(
        modifier = modifier
            .fillMaxWidth()
            .fillMaxHeight()
            .verticalScroll(rememberScrollState())
            .padding(top = 64.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        Text(text = "Home")

        BasicButton( R.string.save_test_container,
            Modifier
                .fillMaxWidth()
                .padding(16.dp, 8.dp)) {
            viewModel.saveTestContainer()
        }

        BasicButton( R.string.save_test_item,
            Modifier
                .fillMaxWidth()
                .padding(16.dp, 8.dp)) {
            viewModel.saveTestItem()
        }

        BasicButton( R.string.get_all_items,
            Modifier
                .fillMaxWidth()
                .padding(16.dp, 8.dp)) {
            viewModel.getAll()
        }
    }
}
