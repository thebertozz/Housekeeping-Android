package it.thebertozz.android.housekeeping.objectdetection

import android.os.Build
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import it.thebertozz.android.housekeeping.R
import it.thebertozz.android.housekeeping.databinding.ActivityMainBinding

/**
Classe entry point per la componente TensorfFlow
 */

class TensorFlowActivity : AppCompatActivity() {

    private lateinit var activityMainBinding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        activityMainBinding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(activityMainBinding.root)

        activityMainBinding.toolbar.setNavigationOnClickListener({
            onBackPressed()
        })

        activityMainBinding.toolbar.title = resources.getString(R.string.choose_object)
    }

    override fun onBackPressed() {
        if (Build.VERSION.SDK_INT == Build.VERSION_CODES.Q) {
            // Workaround for Android Q memory leak issue in IRequestFinishCallback$Stub.
            // (https://issuetracker.google.com/issues/139738913)
            finishAfterTransition()
        } else {
            super.onBackPressed()
        }
    }
}
