package it.thebertozz.android.housekeeping.objectdetection.fragments

import android.annotation.SuppressLint
import android.content.Intent
import android.content.res.Configuration
import android.graphics.Bitmap
import android.os.Bundle
import android.util.DisplayMetrics
import android.util.Log
import android.view.Display
import android.view.LayoutInflater
import android.view.Surface.ROTATION_0
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.camera.core.AspectRatio
import androidx.camera.core.Camera
import androidx.camera.core.CameraSelector
import androidx.camera.core.ImageAnalysis
import androidx.camera.core.ImageProxy
import androidx.camera.core.Preview
import androidx.camera.lifecycle.ProcessCameraProvider
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.navigation.Navigation
import androidx.recyclerview.widget.LinearLayoutManager
import it.thebertozz.android.housekeeping.R
import it.thebertozz.android.housekeeping.databinding.FragmentCameraBinding
import it.thebertozz.android.housekeeping.objectdetection.CategoryLabelClickListener
import it.thebertozz.android.housekeeping.objectdetection.ImageClassifierHelper
import org.tensorflow.lite.task.vision.classifier.Classifications
import java.util.Collections
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors

/**
Classe che gestisce il fragment fotocamera per l'object detection
 */

class CameraFragment : Fragment(), ImageClassifierHelper.ClassifierListener {

    companion object {
        private const val TAG = "Image Classifier"
    }

    private var currentClassifications: MutableList<String> = mutableListOf()

    private var _fragmentCameraBinding: FragmentCameraBinding? = null
    private val fragmentCameraBinding
        get() = _fragmentCameraBinding

    private lateinit var imageClassifierHelper: ImageClassifierHelper
    private lateinit var bitmapBuffer: Bitmap
    private val classificationResultsAdapter by lazy {
        ClassificationResultsAdapter(object : CategoryLabelClickListener {
            override fun onLabelSelected(label: String) {
                Log.i(TAG, "Label: $label")
                val data = Intent()
                data.putExtra("object", label)
                activity?.setResult(AppCompatActivity.RESULT_OK, data)
                activity?.finishAfterTransition()
            }
        }).apply {
            updateAdapterSize(imageClassifierHelper.maxResults)
        }
    }
    private var preview: Preview? = null
    private var imageAnalyzer: ImageAnalysis? = null
    private var camera: Camera? = null
    private var cameraProvider: ProcessCameraProvider? = null

    /** Blocking camera operations are performed using this executor */
    private lateinit var cameraExecutor: ExecutorService

    override fun onResume() {
        super.onResume()

        if (!PermissionsFragment.hasPermissions(requireContext())) {
            Navigation.findNavController(requireActivity(), R.id.fragment_container)
                .navigate(CameraFragmentDirections.actionCameraToPermissions())
        }
    }

    override fun onDestroyView() {
        _fragmentCameraBinding = null
        super.onDestroyView()
        cameraExecutor.shutdown()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _fragmentCameraBinding = FragmentCameraBinding.inflate(inflater, container, false)

        return fragmentCameraBinding!!.root
    }

    @SuppressLint("MissingPermission")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        imageClassifierHelper =
            ImageClassifierHelper(context = requireContext(), imageClassifierListener = this)

        with(fragmentCameraBinding?.recyclerviewResults) {
            this?.layoutManager = LinearLayoutManager(requireContext())
            this?.adapter = classificationResultsAdapter
        }

        cameraExecutor = Executors.newSingleThreadExecutor()

        fragmentCameraBinding?.viewFinder?.post {
            setUpCamera()
        }
    }

    private fun setUpCamera() {
        val cameraProviderFuture = ProcessCameraProvider.getInstance(requireContext())
        cameraProviderFuture.addListener(
            {
                cameraProvider = cameraProviderFuture.get()

                bindCameraUseCases()
            },
            ContextCompat.getMainExecutor(requireContext())
        )
    }

    override fun onConfigurationChanged(newConfig: Configuration) {
        super.onConfigurationChanged(newConfig)
        imageAnalyzer?.targetRotation =
            fragmentCameraBinding?.viewFinder?.display?.rotation ?: ROTATION_0
    }

    @SuppressLint("UnsafeOptInUsageError")
    private fun bindCameraUseCases() {

        val cameraProvider =
            cameraProvider ?: throw IllegalStateException("Camera initialization failed.")

        val cameraSelector =
            CameraSelector.Builder().requireLensFacing(CameraSelector.LENS_FACING_BACK).build()

        preview =
            Preview.Builder()
                .setTargetAspectRatio(AspectRatio.RATIO_4_3)
                .setTargetRotation(
                    fragmentCameraBinding?.viewFinder?.display?.rotation ?: ROTATION_0
                )
                .build()

        // RGBA 8888 to match how our models work
        imageAnalyzer =
            ImageAnalysis.Builder()
                .setTargetAspectRatio(AspectRatio.RATIO_4_3)
                .setTargetRotation(
                    fragmentCameraBinding?.viewFinder?.display?.rotation ?: ROTATION_0
                )
                .setBackpressureStrategy(ImageAnalysis.STRATEGY_KEEP_ONLY_LATEST)
                .setOutputImageFormat(ImageAnalysis.OUTPUT_IMAGE_FORMAT_RGBA_8888)
                .build()
                .also {
                    it.setAnalyzer(cameraExecutor) { image ->
                        if (!::bitmapBuffer.isInitialized) {
                            bitmapBuffer = Bitmap.createBitmap(
                                image.width,
                                image.height,
                                Bitmap.Config.ARGB_8888
                            )
                        }

                        classifyImage(image)
                    }
                }

        cameraProvider.unbindAll()

        try {
            camera = cameraProvider.bindToLifecycle(this, cameraSelector, preview, imageAnalyzer)

            preview?.setSurfaceProvider(fragmentCameraBinding?.viewFinder?.surfaceProvider)
        } catch (exc: Exception) {
            Log.e(TAG, "Use case binding failed", exc)
        }
    }

    private fun getScreenOrientation(): Int {
        val outMetrics = DisplayMetrics()

        var display: Display? = null
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.R) {
            try {
                display = requireActivity().display
                display?.getRealMetrics(outMetrics)
            } catch (e: Exception) {
                e.printStackTrace()
            }

        } else {
            @Suppress("DEPRECATION")
            display = requireActivity().windowManager.defaultDisplay
            @Suppress("DEPRECATION")
            display.getMetrics(outMetrics)
        }

        return display?.rotation ?: 0
    }

    private fun classifyImage(image: ImageProxy) {
        image.use { bitmapBuffer.copyPixelsFromBuffer(image.planes[0].buffer) }

        imageClassifierHelper.classify(bitmapBuffer, getScreenOrientation())
    }

    @SuppressLint("NotifyDataSetChanged")
    override fun onError(error: String) {
        activity?.runOnUiThread {
            Toast.makeText(requireContext(), error, Toast.LENGTH_SHORT).show()
            classificationResultsAdapter.updateResults(null)
            classificationResultsAdapter.notifyDataSetChanged()
        }
    }

    @SuppressLint("NotifyDataSetChanged")
    override fun onResults(
        results: List<Classifications>?,
        inferenceTime: Long
    ) {

        //Logica per non inviare risultati vuoti o identici alla recycler view

        if (!results.isNullOrEmpty()) {

            var newClassifications = mutableListOf<String>()

            results.forEach {
                if (it.categories.size > 0 && it.categories.firstOrNull()?.label != "") {
                    newClassifications.add(it.categories.first().label)
                }
            }

            if (newClassifications.isEmpty()) { newClassifications = currentClassifications}

            if (currentClassifications != newClassifications) {
                activity?.runOnUiThread {
                    classificationResultsAdapter.updateResults(results)
                    classificationResultsAdapter.notifyDataSetChanged()
                    fragmentCameraBinding?.bottomSheetLayout?.inferenceTimeVal?.text =
                        String.format("%d ms", inferenceTime)
                }
                currentClassifications = newClassifications
            }
        }
    }
}