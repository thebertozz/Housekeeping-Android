package it.thebertozz.android.housekeeping.screens.signup

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import it.thebertozz.android.housekeeping.R
import it.thebertozz.android.housekeeping.composables.BasicButton
import it.thebertozz.android.housekeeping.composables.BasicToolbar
import it.thebertozz.android.housekeeping.composables.EmailField
import it.thebertozz.android.housekeeping.composables.PasswordField
import it.thebertozz.android.housekeeping.composables.RepeatPasswordField

@Composable
fun SignUpScreen(
  navigation: (String, String?) -> Unit,
  modifier: Modifier = Modifier,
  viewModel: SignUpViewModel = viewModel()
) {
  val uiState by viewModel.uiState
  val fieldModifier = Modifier
    .fillMaxWidth()
    .padding(16.dp, 4.dp)

  BasicToolbar(R.string.create_account)

  Column(
    modifier = modifier
      .fillMaxWidth()
      .fillMaxHeight()
      .verticalScroll(rememberScrollState()),
    verticalArrangement = Arrangement.Center,
    horizontalAlignment = Alignment.CenterHorizontally
  ) {
    EmailField(uiState.email, viewModel::onEmailChange, fieldModifier)
    PasswordField(uiState.password, viewModel::onPasswordChange, fieldModifier)
    RepeatPasswordField(uiState.repeatPassword, viewModel::onRepeatPasswordChange, fieldModifier)

    BasicButton(R.string.create_account,
      Modifier
        .fillMaxWidth()
        .padding(16.dp, 8.dp)) {
      viewModel.onSignUpClick(navigation)
    }
  }
}