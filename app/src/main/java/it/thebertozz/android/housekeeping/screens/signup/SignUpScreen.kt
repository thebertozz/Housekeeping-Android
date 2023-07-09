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
import it.thebertozz.android.housekeeping.utils.SimpleButton
import it.thebertozz.android.housekeeping.utils.BasicToolbar
import it.thebertozz.android.housekeeping.utils.EmailTextField
import it.thebertozz.android.housekeeping.utils.PasswordTextField
import it.thebertozz.android.housekeeping.utils.RepeatPasswordField

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
    EmailTextField(uiState.email, viewModel::onEmailChange, fieldModifier)
    PasswordTextField(uiState.password, viewModel::onPasswordChange, fieldModifier)
    RepeatPasswordField(uiState.repeatPassword, viewModel::onRepeatPasswordChange, fieldModifier)

    SimpleButton(R.string.create_account,
      Modifier
        .fillMaxWidth()
        .padding(16.dp, 8.dp)) {
      viewModel.onSignUpClick(navigation)
    }
  }
}
