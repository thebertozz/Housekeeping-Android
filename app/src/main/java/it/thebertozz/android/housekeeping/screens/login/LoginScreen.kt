package it.thebertozz.android.housekeeping.screens.login

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Divider
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import it.thebertozz.android.housekeeping.composables.BasicButton
import it.thebertozz.android.housekeeping.composables.BasicTextButton
import it.thebertozz.android.housekeeping.composables.EmailField
import it.thebertozz.android.housekeeping.composables.PasswordField
import it.thebertozz.android.housekeeping.R
import it.thebertozz.android.housekeeping.composables.BasicToolbar

@Composable
fun LoginScreen(
    navigation: (String, String?) -> Unit,
    modifier: Modifier = Modifier,
    viewModel: LoginViewModel = viewModel()
) {
    val uiState by viewModel.uiState

    BasicToolbar(R.string.login)

    Column(
        modifier = modifier
            .fillMaxWidth()
            .fillMaxHeight()
            .verticalScroll(rememberScrollState())
            .padding(top = 64.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        Text(text = stringResource(id = R.string.welcome), fontSize = 20.sp, fontWeight = FontWeight.Bold, textAlign = TextAlign.Center, modifier = Modifier
            .padding(all = 16.dp)
            .align(Alignment.CenterHorizontally)
        )

        Divider(color = Color.Transparent)

        Text(text = stringResource(id = R.string.welcome_description), fontSize = 20.sp, textAlign = TextAlign.Center, modifier = Modifier
            .padding(all = 16.dp)
            .align(Alignment.CenterHorizontally)
        )

        Divider(color = Color.Transparent)

        EmailField(uiState.email, viewModel::onEmailChange,
            Modifier
                .fillMaxWidth()
                .padding(16.dp, 4.dp))
        PasswordField(uiState.password, viewModel::onPasswordChange,
            Modifier
                .fillMaxWidth()
                .padding(16.dp, 4.dp))

        BasicButton(R.string.login,
            Modifier
                .fillMaxWidth()
                .padding(16.dp, 8.dp)) {
            viewModel.onLoginClick(navigation)
        }

        Divider()

        Text(stringResource(id = R.string.first_access), Modifier
            .padding(16.dp, 16.dp)
            .align(Alignment.CenterHorizontally), fontSize = 16.sp)

        BasicButton(R.string.sign_up,
            Modifier
                .fillMaxWidth()
                .padding(16.dp, 8.dp)) {
            viewModel.onSignUpClick(navigation)
        }

        BasicTextButton(R.string.forgot_password,
            Modifier
                .fillMaxWidth()
                .padding(16.dp, 8.dp, 16.dp, 0.dp)) {
            viewModel.onForgotPasswordClick()
        }
    }
}
