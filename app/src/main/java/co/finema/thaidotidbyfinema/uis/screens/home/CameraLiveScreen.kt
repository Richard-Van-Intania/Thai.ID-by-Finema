@file:Suppress("UsingMaterialAndMaterial3Libraries")

package co.finema.thaidotidbyfinema.uis.screens.home

import android.content.Context
import android.util.Log
import androidx.camera.core.CameraSelector
import androidx.camera.core.ImageCapture
import androidx.camera.core.Preview
import androidx.camera.lifecycle.ProcessCameraProvider
import androidx.camera.view.PreviewView
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.core.content.ContextCompat
import androidx.lifecycle.compose.LocalLifecycleOwner
import androidx.navigation.NavController
import co.finema.thaidotidbyfinema.R
import java.io.File
import java.text.SimpleDateFormat
import java.util.Locale
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors

@Composable
fun CameraLiveScreen(navController: NavController) {
  val context = LocalContext.current
  var capturedImageFile by remember { mutableStateOf<File?>(null) }

  Box(modifier = Modifier.fillMaxSize()) {
    CameraPreview(
      onImageCaptured = { file -> capturedImageFile = file },
      onError = { exc ->
        // Handle camera error, e.g., show a Toast
        Log.e("CameraScreen", "Camera error: ${exc.message}")
      },
    )

      Button(
              onClick = { /* Call the takePhoto lambda from CameraPreview */ },
              modifier = Modifier.align(Alignment.BottomCenter).padding(16.dp)
            ) {
          Text("Take Photo")
      }
  }
}

@Composable
fun CameraPreview(onImageCaptured: (File) -> Unit, onError: (ImageCaptureException) -> Unit) {

  val context = LocalContext.current
  val lifecycleOwner = LocalLifecycleOwner.current
  val cameraExecutor: ExecutorService = remember { Executors.newSingleThreadExecutor() }

  val imageCapture: ImageCapture = remember { ImageCapture.Builder().build() }

  val previewView: PreviewView = remember { PreviewView(context) }

  DisposableEffect(Unit) {
    val cameraProviderFuture = ProcessCameraProvider.getInstance(context)
    cameraProviderFuture.addListener(
      {
        val cameraProvider = cameraProviderFuture.get()
        val preview =
          Preview.Builder().build().also { it.surfaceProvider = previewView.surfaceProvider }

        val cameraSelector = CameraSelector.DEFAULT_BACK_CAMERA

        try {
          cameraProvider.unbindAll()
          cameraProvider.bindToLifecycle(lifecycleOwner, cameraSelector, preview, imageCapture)
        } catch (exc: Exception) {
          Log.e("CameraPreview", "Use case binding failed", exc)
          onError(ImageCaptureException(exc.message ?: "Unknown error", exc))
        }
      },
      ContextCompat.getMainExecutor(context),
    )

    onDispose { cameraExecutor.shutdown() }
  }
  val takePhoto = {
    val photoFile =
      File(
        getOutputDirectory(context),
        SimpleDateFormat(FILENAME_FORMAT, Locale.US).format(System.currentTimeMillis()) + ".jpg",
      )
    val outputOptions = ImageCapture.OutputFileOptions.Builder(photoFile).build()

    imageCapture.takePicture(
      outputOptions,
      cameraExecutor,
      object : ImageCapture.OnImageSavedCallback {
          override fun onImageSaved(outputFileResults: ImageCapture.OutputFileResults) {
              onImageCaptured(photoFile)
          }

          override fun onError(exception: androidx.camera.core.ImageCaptureException) {
              Log.e("CameraPreview", "Photo capture failed: ${exception.message}", exception)
              onError(exception)
          }

      },
    )
  }


  AndroidView(factory = { previewView }, modifier = Modifier.fillMaxSize())
}

private const val FILENAME_FORMAT = "yyyy-MM-dd-HH-mm-ss-SSS"

fun getOutputDirectory(context: Context): File {
  val mediaDir =
    context.externalMediaDirs.firstOrNull()?.let {
      File(it, context.resources.getString(R.string.app_name)).apply { mkdirs() }
    }
  return if (mediaDir != null && mediaDir.exists()) mediaDir else context.filesDir
}

// Custom exception for camera capture errors
class ImageCaptureException(message: String, cause: Throwable?) : Exception(message, cause)
