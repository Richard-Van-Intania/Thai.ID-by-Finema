@file:Suppress("UsingMaterialAndMaterial3Libraries")

package co.finema.thaidotidbyfinema.uis.screens.home

import android.content.Context
import android.widget.Toast
import androidx.camera.core.CameraSelector
import androidx.camera.core.ImageCapture
import androidx.camera.core.ImageCaptureException
import androidx.camera.core.Preview
import androidx.camera.lifecycle.ProcessCameraProvider
import androidx.camera.view.PreviewView
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
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
import java.io.File

@Composable
fun CameraLiveScreen(navController: NavController) {
  val context = LocalContext.current
  var imageCapture by remember { mutableStateOf<ImageCapture?>(null) }

  Box(modifier = Modifier.fillMaxSize()) {
    CameraPreviewView(onImageCaptureReady = { capture -> imageCapture = capture })

    Button(
      onClick = { imageCapture?.let { takePhoto(context, it) } },
      modifier = Modifier.align(Alignment.BottomCenter).padding(16.dp),
    ) {
      Text("Capture")
    }
  }
}

@Composable
fun CameraPreviewView(onImageCaptureReady: (ImageCapture) -> Unit) {

  val lifecycleOwner = LocalLifecycleOwner.current

  AndroidView(
    factory = { ctx ->
      val previewView = PreviewView(ctx)
      val cameraProviderFuture = ProcessCameraProvider.getInstance(ctx)

      cameraProviderFuture.addListener(
        {
          val cameraProvider = cameraProviderFuture.get()

          val preview =
            Preview.Builder().build().also { it.surfaceProvider = previewView.surfaceProvider }

          val imageCaptureUseCase = ImageCapture.Builder().build()
          onImageCaptureReady(imageCaptureUseCase) // Pass the instance

          val cameraSelector = CameraSelector.DEFAULT_BACK_CAMERA

          try {
            cameraProvider.unbindAll()
            cameraProvider.bindToLifecycle(
              lifecycleOwner,
              cameraSelector,
              preview,
              imageCaptureUseCase,
            )
          } catch (e: Exception) {
            e.printStackTrace()
          }
        },
        ContextCompat.getMainExecutor(ctx),
      )

      previewView
    },
    modifier = Modifier.fillMaxSize(),
  )
}

fun takePhoto(context: Context, imageCapture: ImageCapture) {
  val photoFile = File(context.externalMediaDirs.first(), "${System.currentTimeMillis()}.jpg")
  val outputOptions = ImageCapture.OutputFileOptions.Builder(photoFile).build()
  imageCapture.takePicture(
    outputOptions,
    ContextCompat.getMainExecutor(context),
    object : ImageCapture.OnImageSavedCallback {
      override fun onImageSaved(outputFileResults: ImageCapture.OutputFileResults) {
        Toast.makeText(context, "Photo saved: ${photoFile.absolutePath}", Toast.LENGTH_SHORT).show()
      }

      override fun onError(exception: ImageCaptureException) {
        Toast.makeText(context, "Capture failed: ${exception.message}", Toast.LENGTH_SHORT).show()
      }
    },
  )
}
