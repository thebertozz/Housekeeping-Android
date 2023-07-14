package it.thebertozz.android.housekeeping.screens.login

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Divider
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import it.thebertozz.android.housekeeping.commons.SimpleButton
import it.thebertozz.android.housekeeping.commons.SimpleTextButton
import it.thebertozz.android.housekeeping.commons.EmailTextField
import it.thebertozz.android.housekeeping.R
import it.thebertozz.android.housekeeping.commons.BasicAppBar
import it.thebertozz.android.housekeeping.commons.PasswordTextField

/**
Classe per il login utente.
Il view model permette di gestire i dati dei text field e di
gestire il tap sul pulsante di registrazione
*/
@Composable
fun LoginScreen(
    navigation: (String, String?) -> Unit,
    modifier: Modifier = Modifier,
    viewModel: LoginViewModel = viewModel()
) {
    val uiState by viewModel.uiState

    Scaffold(topBar = { BasicAppBar(R.string.login) }) { paddingValues ->

        Column(
            modifier = modifier
                .fillMaxWidth()
                .fillMaxHeight()
                .verticalScroll(rememberScrollState())
                .padding(paddingValues)
                .padding(horizontal = 24.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            Text(text = stringResource(id = R.string.welcome), fontSize = 21.sp, fontWeight = FontWeight.Bold, textAlign = TextAlign.Justify, modifier = Modifier
                .padding(horizontal = 16.dp)
                .align(Alignment.CenterHorizontally)
            )

            Box(Modifier.height(8.dp))

            Text(text = stringResource(id = R.string.welcome_description), fontSize = 20.sp, textAlign = TextAlign.Center, modifier = Modifier
                .padding(horizontal = 16.dp)
                .align(Alignment.CenterHorizontally)
            )

            Box(Modifier.height(48.dp))

            EmailTextField(uiState.email, viewModel::onEmailChange,
                Modifier
                    .fillMaxWidth())

            Box(Modifier.height(8.dp))

            PasswordTextField(uiState.password, R.string.password_login, viewModel::onPasswordChange,
                Modifier
                    .fillMaxWidth())

            Box(Modifier.height(16.dp))

            SimpleButton(R.string.login) {
                viewModel.onLoginClick(navigation)
            }

            Box(Modifier.height(16.dp))

            Divider()

            Text(stringResource(id = R.string.first_access), Modifier
                .padding(16.dp, 16.dp)
                .align(Alignment.CenterHorizontally), fontSize = 16.sp)

            SimpleButton(R.string.sign_up) {
                viewModel.onSignUpClick(navigation)
            }

            SimpleTextButton(R.string.forgot_password,
                Modifier
                    .fillMaxWidth()
                    .padding(16.dp, 8.dp, 16.dp, 0.dp)) {
                viewModel.onForgotPasswordClick()
            }
        }

    }
}
