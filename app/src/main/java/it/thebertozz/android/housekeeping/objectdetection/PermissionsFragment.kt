package it.thebertozz.android.housekeeping.objectdetection

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import android.os.Bundle
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.navigation.Navigation
import it.thebertozz.android.housekeeping.R

private val PERMISSIONS_REQUIRED = arrayOf(Manifest.permission.CAMERA)

