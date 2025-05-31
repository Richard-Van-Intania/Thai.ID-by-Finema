@file:Suppress("UsingMaterialAndMaterial3Libraries")

package co.finema.thaidotidbyfinema.uis.screens.home

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
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
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import co.finema.thaidotidbyfinema.R
import co.finema.thaidotidbyfinema.uis.black
import co.finema.thaidotidbyfinema.uis.white
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

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CropImageScreen(navController: NavController, imageUri: MutableState<Uri?>) {
  BackHandler(enabled = true) {}
  val context = LocalContext.current
  var selectedBitmap by remember { mutableStateOf<Bitmap?>(null) }
  LaunchedEffect(imageUri.value) {
    imageUri.value?.let { selectedBitmap = getBitmapFromUri(context, it) }
  }
  selectedBitmap?.let { bitmap ->
    val cropController = rememberCropController(bitmap = bitmap)
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
          actions = {
            IconButton(onClick = {}) {
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
      ImageCropper(modifier = Modifier.fillMaxSize().padding(it), cropController = cropController)
    }
  }
}
