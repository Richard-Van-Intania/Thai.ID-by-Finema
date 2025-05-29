@file:Suppress("UsingMaterialAndMaterial3Libraries")

package co.finema.thaidotidbyfinema.uis.screens.home

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Icon
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.TextButton
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Description
import androidx.compose.material.icons.rounded.Image
import androidx.compose.material.icons.rounded.PhotoCamera
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableIntState
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.core.content.ContextCompat
import androidx.navigation.NavController
import co.finema.thaidotidbyfinema.R
import co.finema.thaidotidbyfinema.cornerRadius
import co.finema.thaidotidbyfinema.enums.DocumentLayout
import co.finema.thaidotidbyfinema.enums.FileSource
import co.finema.thaidotidbyfinema.uis.Screen
import co.finema.thaidotidbyfinema.uis.components.AppBarOptBack
import co.finema.thaidotidbyfinema.uis.components.GradientButton
import co.finema.thaidotidbyfinema.uis.components.HorizontalLine
import co.finema.thaidotidbyfinema.uis.neutral02
import co.finema.thaidotidbyfinema.uis.primaryBlack
import co.finema.thaidotidbyfinema.uis.secondaryGray
import co.finema.thaidotidbyfinema.uis.white
import co.finema.thaidotidbyfinema.uis.whiteBG

@Composable
fun DocumentPlaceholderScreen(
  navController: NavController,
  layoutIndex: MutableIntState,
  placeholderFilePath0: MutableState<String>,
  placeholderFilePath1: MutableState<String>,
  placeholderFilePath2: MutableState<String>,
) {
  val context = LocalContext.current
  var startCameraLive by remember { mutableStateOf(false) }
  var hasCameraPermission by remember { mutableStateOf(false) }
  val cameraPermissionLauncher =
    rememberLauncherForActivityResult(
      contract = ActivityResultContracts.RequestPermission(),
      onResult = { isGranted -> hasCameraPermission = isGranted },
    )
  LaunchedEffect(hasCameraPermission, startCameraLive) {
    if (hasCameraPermission && startCameraLive)
      navController.navigate(route = Screen.CameraLiveScreen.route)
  }

  var pdfUri by remember { mutableStateOf<Uri?>(null) }
  val pickPdf =
    rememberLauncherForActivityResult(ActivityResultContracts.OpenDocument()) { uri ->
      if (uri != null) {
        context.contentResolver.takePersistableUriPermission(
          uri,
          Intent.FLAG_GRANT_READ_URI_PERMISSION,
        )
        pdfUri = uri
        // read content
        val inputStream = context.contentResolver.openInputStream(uri)
      }
    }
  val pickImage =
    rememberLauncherForActivityResult(ActivityResultContracts.PickVisualMedia()) { uri ->
      if (uri != null) {
        println(uri)
        // get uri here
      }
    }
  var fileSource by remember { mutableStateOf<FileSource?>(null) }
  LaunchedEffect(fileSource) {
    when (fileSource) {
      FileSource.CAMERA -> {
        hasCameraPermission =
          ContextCompat.checkSelfPermission(context, Manifest.permission.CAMERA) ==
            PackageManager.PERMISSION_GRANTED
        if (!hasCameraPermission) {
          cameraPermissionLauncher.launch(Manifest.permission.CAMERA)
        }
        startCameraLive = true
      }
      FileSource.GALLERY -> {
        pickImage.launch(PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageOnly))
      }
      FileSource.PDF -> {
        pickPdf.launch(arrayOf("application/pdf"))
      }
      null -> {}
    }
  }
  var showOptionDialog by remember { mutableStateOf(false) }
  if (showOptionDialog) {
    Dialog(onDismissRequest = {}) {
      Box(modifier = Modifier.clip(RoundedCornerShape(8.dp)).background(white).fillMaxWidth()) {
        Column(modifier = Modifier.fillMaxWidth()) {
          Text(
            modifier = Modifier.padding(all = 24.dp),
            text = stringResource(R.string.options),
            color = primaryBlack,
            fontSize = 24.sp,
            fontWeight = FontWeight.W700,
          )
          Box(modifier = Modifier.fillMaxWidth().height(2.dp).background(neutral02))
          OptionButton(
            imageVector = Icons.Rounded.PhotoCamera,
            text = R.string.scan_your_card,
            onClick = {
              fileSource = FileSource.CAMERA
              showOptionDialog = false
            },
          )
          HorizontalLine(modifier = Modifier.padding(horizontal = 16.dp))
          OptionButton(
            imageVector = Icons.Rounded.Image,
            text = R.string.import_from_album,
            onClick = {
              fileSource = FileSource.GALLERY
              showOptionDialog = false
            },
          )
          HorizontalLine(modifier = Modifier.padding(horizontal = 16.dp))
          OptionButton(
            imageVector = Icons.Rounded.Description,
            text = R.string.pick_file,
            onClick = {
              fileSource = FileSource.PDF
              showOptionDialog = false
            },
          )
          HorizontalLine(modifier = Modifier.padding(horizontal = 16.dp))
          TextButton(
            onClick = {
              fileSource = null
              showOptionDialog = false
            }
          ) {
            Row(
              verticalAlignment = Alignment.CenterVertically,
              horizontalArrangement = Arrangement.Center,
              modifier = Modifier.fillMaxWidth().padding(vertical = 24.dp),
            ) {
              Text(
                text = stringResource(R.string.cancel),
                color = primaryBlack,
                fontSize = 24.sp,
                fontWeight = FontWeight.W400,
              )
            }
          }
        }
      }
    }
  }
  val showDeleteDialog by remember { mutableStateOf(false) }
  if (showDeleteDialog) {
    Dialog(onDismissRequest = {}) {
      Box(modifier = Modifier.clip(RoundedCornerShape(8.dp)).background(white).fillMaxWidth()) {
        Column(modifier = Modifier.fillMaxWidth()) {
          //
        }
      }
    }
  }
  Scaffold(
    topBar = {
      AppBarOptBack(
        containerColor = white,
        text = stringResource(R.string.add_document),
        onClick = { navController.popBackStack() },
      )
    },
    bottomBar = {
      Box(
        modifier =
          Modifier.background(white)
            .fillMaxWidth()
            .padding(start = 16.dp, top = 16.dp, end = 16.dp, bottom = 48.dp),
        contentAlignment = Alignment.Center,
      ) {
        GradientButton(onClick = {}, text = stringResource(R.string.make_a_cert))
      }
    },
    backgroundColor = whiteBG,
  ) {
    Column(
      modifier = Modifier.fillMaxSize().padding(it).padding(horizontal = 16.dp),
      verticalArrangement = Arrangement.Center,
      horizontalAlignment = Alignment.CenterHorizontally,
    ) {
      when (layoutItems[layoutIndex.intValue].documentLayout) {
        DocumentLayout.ONE_SIDE_CARD -> {
          if (placeholderFilePath0.value.isEmpty())
            AddImageButton(
              ratio = 8.6 / 5.4,
              label = R.string.add_card,
              onClick = {
                fileSource = null
                showOptionDialog = true
              },
            )
          else Box {}
        }
        DocumentLayout.TWO_SIDE_CARD -> {
          if (placeholderFilePath1.value.isEmpty())
            AddImageButton(
              ratio = 8.6 / 5.4,
              label = R.string.add_card_1,
              onClick = {
                fileSource = null
                showOptionDialog = true
              },
            )
          else Box {}
          Spacer(modifier = Modifier.height(32.dp))
          if (placeholderFilePath2.value.isEmpty())
            AddImageButton(
              ratio = 8.6 / 5.4,
              label = R.string.add_card_2,
              onClick = {
                fileSource = null
                showOptionDialog = true
              },
            )
          else Box {}
        }
        DocumentLayout.ONE_SIDE_HALF_A4 -> {
          if (placeholderFilePath0.value.isEmpty())
            AddImageButton(
              ratio = 1.0 / 1.0,
              label = R.string.add_a_document_image,
              onClick = {
                fileSource = null
                showOptionDialog = true
              },
            )
          else Box {}
        }
        DocumentLayout.TWO_SIDE_HALF_A4 -> {
          if (placeholderFilePath1.value.isEmpty())
            AddImageButton(
              ratio = 297.0 / 210.0,
              label = R.string.add_a_document_image_1,
              onClick = {
                fileSource = null
                showOptionDialog = true
              },
            )
          else Box {}
          Spacer(modifier = Modifier.height(32.dp))
          if (placeholderFilePath2.value.isEmpty())
            AddImageButton(
              ratio = 297.0 / 210.0,
              label = R.string.add_a_document_image_2,
              onClick = {
                fileSource = null
                showOptionDialog = true
              },
            )
          else Box {}
        }
        DocumentLayout.FULL_A4 -> {
          if (placeholderFilePath0.value.isEmpty())
            AddImageButton(
              ratio = 210.0 / 297.0,
              label = R.string.add_a_document_image,
              onClick = {
                fileSource = null
                showOptionDialog = true
              },
            )
          else Box {}
        }
      }
    }
  }
}

@Composable
fun AddImageButton(ratio: Double, label: Int, onClick: () -> Unit) {
  Box(
    modifier =
      Modifier.fillMaxWidth()
        .aspectRatio(ratio.toFloat())
        .clip(RoundedCornerShape(cornerRadius))
        .border(width = 1.dp, color = secondaryGray, shape = RoundedCornerShape(cornerRadius))
        .background(white)
        .clickable(onClick = onClick),
    contentAlignment = Alignment.Center,
  ) {
    Column(horizontalAlignment = Alignment.CenterHorizontally) {
      Image(
        painter = painterResource(id = R.drawable.group_284),
        contentDescription = null,
        modifier = Modifier.height(56.dp),
      )
      Spacer(modifier = Modifier.height(24.dp))
      Text(
        text = stringResource(label),
        color = primaryBlack,
        fontSize = 24.sp,
        fontWeight = FontWeight.W400,
      )
    }
  }
}

@Composable
fun OptionButton(imageVector: ImageVector, text: Int, onClick: () -> Unit) {
  TextButton(onClick = onClick) {
    Row(
      verticalAlignment = Alignment.CenterVertically,
      modifier = Modifier.fillMaxWidth().padding(vertical = 16.dp),
    ) {
      Spacer(modifier = Modifier.width(16.dp))
      Icon(imageVector = imageVector, contentDescription = null)
      Spacer(modifier = Modifier.width(16.dp))
      Text(
        text = stringResource(text),
        color = primaryBlack,
        fontSize = 24.sp,
        fontWeight = FontWeight.W400,
      )
    }
  }
}
