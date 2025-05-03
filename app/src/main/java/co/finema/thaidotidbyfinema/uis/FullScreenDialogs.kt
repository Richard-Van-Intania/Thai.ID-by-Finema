package co.finema.thaidotidbyfinema.uis

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties

@Composable
fun FullScreenDialog(image: Int, height: Int, text: Int) {
  Dialog(
      onDismissRequest = {},
      properties =
          DialogProperties(usePlatformDefaultWidth = false, decorFitsSystemWindows = false)) {
        Column(
            Modifier.fillMaxSize().background(white),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally) {
              Image(
                  painter = painterResource(id = image),
                  contentDescription = null,
                  modifier = Modifier.height(height.dp))
              Spacer(modifier = Modifier.height(24.dp))
              Text(
                  text = stringResource(text),
                  color = primaryBlack,
                  fontSize = 24.sp,
                  fontWeight = FontWeight.W700,
                  textAlign = TextAlign.Center,
              )
            }
      }
}
