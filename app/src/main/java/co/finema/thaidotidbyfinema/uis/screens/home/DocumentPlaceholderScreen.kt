@file:Suppress("UsingMaterialAndMaterial3Libraries")

package co.finema.thaidotidbyfinema.uis.screens.home

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
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.navigation.NavController
import co.finema.thaidotidbyfinema.R
import co.finema.thaidotidbyfinema.enums.DocumentLayout
import co.finema.thaidotidbyfinema.uis.components.AppBarOptBack
import co.finema.thaidotidbyfinema.uis.components.GradientButton
import co.finema.thaidotidbyfinema.uis.components.HorizontalLine
import co.finema.thaidotidbyfinema.uis.neutral02
import co.finema.thaidotidbyfinema.uis.primaryBlack
import co.finema.thaidotidbyfinema.uis.secondaryGray
import co.finema.thaidotidbyfinema.uis.white
import co.finema.thaidotidbyfinema.uis.whiteBG

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DocumentPlaceholderScreen(
    navController: NavController,
    documentLayout: DocumentLayout,
    placeholderFilePath0: MutableState<String>,
    placeholderFilePath1: MutableState<String>,
    placeholderFilePath2: MutableState<String>,
) {
  LaunchedEffect(Unit) {
    placeholderFilePath0.value = ""
    placeholderFilePath1.value = ""
    placeholderFilePath2.value = ""
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
              imageVector = Icons.Rounded.PhotoCamera, text = R.string.scan_your_card, onClick = {})
          HorizontalLine(modifier = Modifier.padding(horizontal = 16.dp))
          OptionButton(
              imageVector = Icons.Rounded.Image, text = R.string.import_from_album, onClick = {})
          HorizontalLine(modifier = Modifier.padding(horizontal = 16.dp))
          OptionButton(
              imageVector = Icons.Rounded.Description, text = R.string.pick_file, onClick = {})
          HorizontalLine(modifier = Modifier.padding(horizontal = 16.dp))
          TextButton(onClick = { showOptionDialog = false }) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Center,
                modifier = Modifier.fillMaxWidth().padding(vertical = 24.dp)) {
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
  var showDeleteDialog by remember { mutableStateOf(false) }
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
            onClick = { navController.popBackStack() })
      },
      bottomBar = {
        Box(
            modifier =
                Modifier.background(white)
                    .fillMaxWidth()
                    .padding(start = 16.dp, top = 16.dp, end = 16.dp, bottom = 48.dp),
            contentAlignment = Alignment.Center) {
              GradientButton(
                  onClick = { showOptionDialog = true },
                  text = stringResource(R.string.make_a_cert))
            }
      },
      backgroundColor = whiteBG) {
        Column(
            modifier = Modifier.fillMaxSize().padding(it).padding(horizontal = 16.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally) {
              when (documentLayout) {
                DocumentLayout.ONE_SIDE_CARD -> {
                  if (placeholderFilePath0.value.isEmpty())
                      AddImageButton(ratio = 8.6 / 5.4, label = R.string.add_card, onClick = {})
                  else Box {}
                }
                DocumentLayout.TWO_SIDE_CARD -> {
                  if (placeholderFilePath1.value.isEmpty())
                      AddImageButton(ratio = 8.6 / 5.4, label = R.string.add_card_1, onClick = {})
                  else Box {}
                  Spacer(modifier = Modifier.height(32.dp))
                  if (placeholderFilePath2.value.isEmpty())
                      AddImageButton(ratio = 8.6 / 5.4, label = R.string.add_card_2, onClick = {})
                  else Box {}
                }
                DocumentLayout.ONE_SIDE_HALF_A4 -> {
                  if (placeholderFilePath0.value.isEmpty())
                      AddImageButton(
                          ratio = 1.0 / 1.0, label = R.string.add_a_document_image, onClick = {})
                  else Box {}
                }
                DocumentLayout.TWO_SIDE_HALF_A4 -> {
                  if (placeholderFilePath1.value.isEmpty())
                      AddImageButton(
                          ratio = 297.0 / 210.0,
                          label = R.string.add_a_document_image_1,
                          onClick = {})
                  else Box {}
                  Spacer(modifier = Modifier.height(32.dp))
                  if (placeholderFilePath2.value.isEmpty())
                      AddImageButton(
                          ratio = 297.0 / 210.0,
                          label = R.string.add_a_document_image_2,
                          onClick = {})
                  else Box {}
                }
                DocumentLayout.FULL_A4 -> {
                  if (placeholderFilePath0.value.isEmpty())
                      AddImageButton(
                          ratio = 210.0 / 297.0,
                          label = R.string.add_a_document_image,
                          onClick = {})
                  else Box {}
                }
              }
              Spacer(modifier = Modifier.height(128.dp))
            }
      }
}

@Composable
fun AddImageButton(ratio: Double, label: Int, onClick: () -> Unit) {
  Box(
      modifier =
          Modifier.fillMaxWidth()
              .aspectRatio(ratio.toFloat())
              .clip(RoundedCornerShape(16.dp))
              .border(width = 1.dp, color = secondaryGray, shape = RoundedCornerShape(16.dp))
              .background(white)
              .clickable(onClick = onClick),
      contentAlignment = Alignment.Center) {
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
          Image(
              painter = painterResource(id = R.drawable.group_284),
              contentDescription = null,
              modifier = Modifier.height(56.dp))
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
        modifier = Modifier.fillMaxWidth().padding(vertical = 16.dp)) {
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

// all here

// camera
// image picker
// pdf picker
// pdf to image
// crop image
