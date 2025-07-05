@file:Suppress("UsingMaterialAndMaterial3Libraries")

package co.finema.thaidotidbyfinema.uis.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import co.finema.thaidotidbyfinema.R
import co.finema.thaidotidbyfinema.uis.primaryBlack
import co.finema.thaidotidbyfinema.uis.white

@Composable
fun FullScreenDialog(painter: Painter, height: Dp, text: String) {
    Dialog(onDismissRequest = {}, properties = DialogProperties(usePlatformDefaultWidth = false, decorFitsSystemWindows = false)) {
        Column(Modifier.fillMaxSize().background(white), verticalArrangement = Arrangement.Center, horizontalAlignment = Alignment.CenterHorizontally) {
            Image(painter = painter, contentDescription = null, modifier = Modifier.height(height))
            Spacer(modifier = Modifier.height(24.dp))
            Text(text = text, color = primaryBlack, fontSize = 24.sp, fontWeight = FontWeight.W700, textAlign = TextAlign.Center)
        }
    }
}

@Composable
fun ErrorDialog(modifier: Modifier = Modifier, text: String, onClick: () -> Unit) {
    Dialog(onDismissRequest = {}) {
        Box(modifier = modifier.clip(RoundedCornerShape(8.dp)).background(white).fillMaxWidth()) {
            Column(modifier = Modifier.fillMaxWidth().padding(horizontal = 16.dp), horizontalAlignment = Alignment.CenterHorizontally) {
                Spacer(modifier = Modifier.height(40.dp))
                Image(painter = painterResource(id = R.drawable.group_40126), contentDescription = null, modifier = Modifier.height(96.dp))
                Spacer(modifier = Modifier.height(24.dp))
                Text(text = text, color = primaryBlack, fontSize = 24.sp, fontWeight = FontWeight.W400, textAlign = TextAlign.Center)
                Spacer(modifier = Modifier.height(32.dp))
                GradientButton(onClick = onClick, text = stringResource(R.string.ok))
                Spacer(modifier = Modifier.height(32.dp))
            }
        }
    }
}
