@file:Suppress("UsingMaterialAndMaterial3Libraries")

package co.finema.thaidotidbyfinema.uis.screens.home

import android.graphics.Bitmap
import android.net.Uri
import androidx.activity.compose.BackHandler
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Check
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableFloatState
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.asAndroidBitmap
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import androidx.core.net.toUri
import androidx.navigation.NavController
import co.finema.thaidotidbyfinema.R
import co.finema.thaidotidbyfinema.getFileInstance
import co.finema.thaidotidbyfinema.uis.Screen
import co.finema.thaidotidbyfinema.uis.black
import co.finema.thaidotidbyfinema.uis.components.ErrorDialog
import co.finema.thaidotidbyfinema.uis.white
import io.moyuru.cropify.Cropify
import io.moyuru.cropify.CropifyOption
import io.moyuru.cropify.CropifySize
import io.moyuru.cropify.rememberCropifyState
import java.io.File
import java.io.FileOutputStream
import java.io.IOException

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CropImageScreen(
  navController: NavController,
  imageUri: MutableState<Uri?>,
  cropAspectRatio: MutableFloatState,
) {
  BackHandler(enabled = true) {}
  val context = LocalContext.current
  var showErrorsDialog by remember { mutableStateOf(false) }
  if (showErrorsDialog) {
    ErrorDialog(
      text = stringResource(R.string.wrong),
      onClick = {
        showErrorsDialog = false
        navController.navigate(route = Screen.DocumentPlaceholderScreen.route) {
          popUpTo(Screen.DocumentPlaceholderScreen.route) { inclusive = true }
        }
      },
    )
  }
  val state = rememberCropifyState()
  imageUri.value?.let { uri ->
    Scaffold(
      topBar = {
        CenterAlignedTopAppBar(
          title = {
            Text(
              text = stringResource(R.string.crop_image),
              color = white,
              fontSize = 24.sp,
              fontWeight = FontWeight.W700,
            )
          },
          actions = {
            IconButton(onClick = { state.crop() }) {
              Icon(imageVector = Icons.Rounded.Check, contentDescription = null, tint = white)
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
      backgroundColor = black,
    ) {
      Cropify(
        modifier = Modifier.fillMaxSize().padding(it),
        uri = uri,
        state = state,
        onImageCropped = { imageBitmap ->
          val photoFile = getFileInstance(context)
          if (saveImageBitmapAsJpeg(imageBitmap, photoFile)) {
            imageUri.value = photoFile.toUri()
            navController.navigate(route = Screen.DocumentPlaceholderScreen.route) {
              popUpTo(Screen.DocumentPlaceholderScreen.route) { inclusive = true }
            }
          } else {
            showErrorsDialog = true
          }
        },
        onFailedToLoadImage = { showErrorsDialog = true },
        option = CropifyOption(frameSize = CropifySize.FixedAspectRatio(cropAspectRatio.floatValue)),
      )
    }
  }
}

fun saveImageBitmapAsJpeg(imageBitmap: ImageBitmap, file: File): Boolean {
  val androidBitmap = imageBitmap.asAndroidBitmap()
  var success = false
  try {
    FileOutputStream(file).use {
      success = androidBitmap.compress(Bitmap.CompressFormat.JPEG, 100, it)
    }
  } catch (e: IOException) {
    e.printStackTrace()
  }
  return success
}
