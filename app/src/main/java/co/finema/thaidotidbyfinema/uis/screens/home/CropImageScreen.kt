@file:Suppress("UsingMaterialAndMaterial3Libraries")

package co.finema.thaidotidbyfinema.uis.screens.home

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import androidx.activity.compose.BackHandler
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.NavController
import com.tanishranjan.cropkit.ImageCropper
import com.tanishranjan.cropkit.rememberCropController

fun getBitmapFromUri(context: Context, uri: Uri): Bitmap? {
  return try {
    val inputStream = context.contentResolver.openInputStream(uri)
    BitmapFactory.decodeStream(inputStream).also { inputStream?.close() }
  } catch (e: Exception) {
    e.printStackTrace()
    null
  }
}

@Composable
fun CropImageScreen(navController: NavController, imageUri: MutableState<Uri?>) {
  BackHandler(enabled = true) {}
  val context = LocalContext.current
  var selectedBitmap by remember { mutableStateOf<Bitmap?>(null) }
  imageUri.value?.let { selectedBitmap = getBitmapFromUri(context, it) }
  Scaffold() {
    if (selectedBitmap == null)
      Box(modifier = Modifier.fillMaxSize().padding(it), contentAlignment = Alignment.Center) {
        CircularProgressIndicator()
      }
    else
      Column(modifier = Modifier.fillMaxSize().padding(it)) {
        val cropController = rememberCropController(bitmap = selectedBitmap!!)
        ImageCropper(cropController = cropController)
      }
  }
}
