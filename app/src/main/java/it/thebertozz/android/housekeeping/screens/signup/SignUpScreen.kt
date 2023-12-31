package it.thebertozz.android.housekeeping.screens.signup

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.MaterialTheme
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import it.thebertozz.android.housekeeping.R
import it.thebertozz.android.housekeeping.commons.SimpleButton
import it.thebertozz.android.housekeeping.commons.BasicAppBar
import it.thebertozz.android.housekeeping.commons.EmailTextField
import it.thebertozz.android.housekeeping.commons.PasswordTextField

/**
Classe per la registrazione utente.
Il view model permette di gestire i dati dei text field e di
gestire il tap sul pulsante di registrazione
*/
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SignUpScreen(
    navController: NavController?,
    navigation: (String, String?) -> Unit,
    modifier: Modifier = Modifier,
    viewModel: SignUpViewModel = viewModel()
) {
    val uiState by viewModel.uiState
    val fieldModifier = Modifier
      .fillMaxWidth()
      .padding(16.dp, 4.dp)

    Scaffold(topBar = { BasicAppBar(R.string.create_account, navController = navController) }) { paddingValues ->
        Column(
            modifier = modifier
                .background(color = MaterialTheme.colors.background)
              .fillMaxWidth()
              .fillMaxHeight()
              .padding(paddingValues)
              .padding(start = 24.dp, end = 24.dp)
              .verticalScroll(rememberScrollState()),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            EmailTextField(uiState.email, viewModel::onEmailChange, fieldModifier)
            Box(Modifier.height(8.dp))
            PasswordTextField(uiState.password, R.string.password_registration, viewModel::onPasswordChange, fieldModifier)
            Box(Modifier.height(8.dp))
            PasswordTextField(
                uiState.repeatPassword,
                R.string.repeat_password,
                viewModel::onRepeatPasswordChange,
                fieldModifier
            )
            Box(Modifier.height(24.dp))
            SimpleButton(R.string.create_account) {
                viewModel.onSignUpClick(navigation)
            }
        }
    }
}
