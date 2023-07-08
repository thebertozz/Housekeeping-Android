package it.thebertozz.android.housekeeping.screens.splash

import androidx.lifecycle.ViewModel
import it.thebertozz.android.housekeeping.HOME_SCREEN
import it.thebertozz.android.housekeeping.LOGIN_SCREEN
import it.thebertozz.android.housekeeping.SPLASH_SCREEN
import it.thebertozz.android.housekeeping.managers.FirebaseManager

class SplashViewModel : ViewModel() {
    fun onAppStart(openAndPopUp: (String, String?) -> Unit) {

        if (FirebaseManager.hasUser()) {
            openAndPopUp(HOME_SCREEN, SPLASH_SCREEN)
        } else {
            openAndPopUp(LOGIN_SCREEN, SPLASH_SCREEN)
        }
    }
}
