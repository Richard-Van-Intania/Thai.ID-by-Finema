@file:Suppress("UsingMaterialAndMaterial3Libraries")

package co.finema.thaidotidbyfinema.uis.screens.home

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
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import co.finema.thaidotidbyfinema.R
import co.finema.thaidotidbyfinema.uis.black
import co.finema.thaidotidbyfinema.uis.white
import io.moyuru.cropify.Cropify
import io.moyuru.cropify.CropifyOption
import io.moyuru.cropify.CropifySize.PercentageSize.Companion.FullSize
import io.moyuru.cropify.rememberCropifyState

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CropImageScreen(navController: NavController, imageUri: MutableState<Uri?>) {
  BackHandler(enabled = true) {}
  val state = rememberCropifyState()
  imageUri.value?.let { imgUri ->
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
        uri = imgUri,
        state = state,
        onImageCropped = {
          //
          println(it.width)
        },
        onFailedToLoadImage = {},
        option = CropifyOption(frameSize = FullSize),
      )
    }
  }
}
