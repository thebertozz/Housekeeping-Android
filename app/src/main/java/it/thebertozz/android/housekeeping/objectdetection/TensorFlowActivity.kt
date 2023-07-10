package it.thebertozz.android.housekeeping.objectdetection

import android.content.Intent
import android.os.Build
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import it.thebertozz.android.housekeeping.databinding.ActivityMainBinding


/**
 * Main entry point into our app. This app follows the single-activity pattern, and all
 * functionality is implemented in the form of fragments.
 */
class TensorFlowActivity : AppCompatActivity() {

    private lateinit var activityMainBinding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        activityMainBinding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(activityMainBinding.root)
    }

    override fun onBackPressed() {
        if (Build.VERSION.SDK_INT == Build.VERSION_CODES.Q) {
            // Workaround for Android Q memory leak issue in IRequestFinishCallback$Stub.
            // (https://issuetracker.google.com/issues/139738913)
            setupResultIntent()
            finishAfterTransition()
        } else {
            setupResultIntent()
            super.onBackPressed()
        }
    }

    fun setupResultIntent() {
        val data = Intent()
        data.putExtra("object", "bookcase")
        setResult(RESULT_OK, data)
    }
}
