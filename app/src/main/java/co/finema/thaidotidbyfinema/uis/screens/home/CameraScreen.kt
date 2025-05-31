@file:Suppress("UsingMaterialAndMaterial3Libraries")

package co.finema.thaidotidbyfinema.uis.screens.home

import android.content.Context
import android.net.Uri
import androidx.camera.core.CameraSelector
import androidx.camera.core.ImageCapture
import androidx.camera.core.ImageCaptureException
import androidx.camera.core.Preview
import androidx.camera.lifecycle.ProcessCameraProvider
import androidx.camera.view.PreviewView
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Camera
import androidx.compose.material.icons.rounded.Close
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.core.content.ContextCompat
import androidx.lifecycle.compose.LocalLifecycleOwner
import androidx.navigation.NavController
import co.finema.thaidotidbyfinema.R
import co.finema.thaidotidbyfinema.uis.black
import co.finema.thaidotidbyfinema.uis.white
import java.io.File
import java.text.SimpleDateFormat
import java.util.Locale

fun getOutputDirectory(context: Context): File {
  val mediaDir =
    context.filesDir.let {
      File(it, context.resources.getString(R.string.app_directory)).apply { mkdirs() }
    }
  return if (mediaDir.exists()) mediaDir else context.filesDir
}

private const val FILENAME_FORMAT = "yyyy-MM-dd-HH-mm-ss-SSS"

fun takePhoto(context: Context, imageCapture: ImageCapture, onImageSaved: (Uri?) -> Unit) {
  val photoFile =
    File(
      getOutputDirectory(context),
      "IMG_${SimpleDateFormat(FILENAME_FORMAT, Locale.US).format(System.currentTimeMillis())}.jpg",
    )
  val outputOptions = ImageCapture.OutputFileOptions.Builder(photoFile).build()
  imageCapture.takePicture(
    outputOptions,
    ContextCompat.getMainExecutor(context),
    object : ImageCapture.OnImageSavedCallback {
      override fun onImageSaved(outputFileResults: ImageCapture.OutputFileResults) {
        onImageSaved(outputFileResults.savedUri)
      }

      override fun onError(exception: ImageCaptureException) {
        onImageSaved(null)
      }
    },
  )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CameraScreen(navController: NavController, imageUri: MutableState<Uri?>) {
  val context = LocalContext.current
  var imageCapture by remember { mutableStateOf<ImageCapture?>(null) }
  LaunchedEffect(imageUri.value) { if (imageUri.value != null) navController.popBackStack() }
  Scaffold(
    topBar = {
      CenterAlignedTopAppBar(
        title = {
          Text(
            text = stringResource(R.string.scan_your_card),
            color = white,
            fontSize = 24.sp,
            fontWeight = FontWeight.W700,
          )
        },
        navigationIcon = {
          IconButton(onClick = { navController.popBackStack() }) {
            Icon(imageVector = Icons.Rounded.Close, contentDescription = null, tint = white)
          }
        },
        colors =
          TopAppBarDefaults.centerAlignedTopAppBarColors(
            containerColor = black,
            navigationIconContentColor = white,
            actionIconContentColor = white,
          ),
      )
    },
    bottomBar = {
      Box(
        modifier =
          Modifier.fillMaxWidth().padding(start = 16.dp, top = 16.dp, end = 16.dp, bottom = 48.dp),
        contentAlignment = Alignment.Center,
      ) {
        IconButton(
          onClick = { imageCapture?.let { takePhoto(context, it) { uri -> imageUri.value = uri } } }
        ) {
          Icon(
            imageVector = Icons.Rounded.Camera,
            contentDescription = null,
            tint = white,
            modifier = Modifier.size(64.dp),
          )
        }
      }
    },
    backgroundColor = black,
  ) {
    CameraPreviewView(
      modifier = Modifier.fillMaxSize().padding(it),
      onImageCaptureReady = { capture -> imageCapture = capture },
    )
  }
}

@Composable
fun CameraPreviewView(modifier: Modifier = Modifier, onImageCaptureReady: (ImageCapture) -> Unit) {
  val lifecycleOwner = LocalLifecycleOwner.current
  AndroidView(
    factory = { context ->
      val previewView = PreviewView(context)
      val cameraProviderFuture = ProcessCameraProvider.getInstance(context)
      cameraProviderFuture.addListener(
        {
          val cameraProvider = cameraProviderFuture.get()
          val preview =
            Preview.Builder().build().also { it.surfaceProvider = previewView.surfaceProvider }
          val imageCaptureUseCase = ImageCapture.Builder().build()
          onImageCaptureReady(imageCaptureUseCase)
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
        ContextCompat.getMainExecutor(context),
      )
      previewView
    },
    modifier = modifier.fillMaxSize(),
  )
}
