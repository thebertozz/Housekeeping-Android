package it.thebertozz.android.housekeeping.screens.splash

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import kotlinx.coroutines.delay

private const val SPLASH_TIMEOUT = 100L

@Composable
fun SplashScreen(
  navigation: (String, String?) -> Unit,
  modifier: Modifier = Modifier,
  viewModel: SplashViewModel = viewModel()
) {
  Column(
    modifier =
      modifier
        .fillMaxWidth()
        .fillMaxHeight()
        .background(color = MaterialTheme.colors.background)
        .verticalScroll(rememberScrollState()),
    verticalArrangement = Arrangement.Center,
    horizontalAlignment = Alignment.CenterHorizontally
  )
  {
      CircularProgressIndicator(color = MaterialTheme.colors.onBackground)
  }

  LaunchedEffect(true) {
    delay(SPLASH_TIMEOUT)
    viewModel.onAppStart(navigation)
  }
}
