@file:Suppress("UsingMaterialAndMaterial3Libraries")

package co.finema.thaidotidbyfinema.uis.screens.home

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.BorderColor
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.drawscope.scale
import androidx.compose.ui.graphics.drawscope.translate
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import co.finema.thaidotidbyfinema.R
import co.finema.thaidotidbyfinema.uis.Screen
import co.finema.thaidotidbyfinema.uis.blue05
import co.finema.thaidotidbyfinema.uis.gradient
import co.finema.thaidotidbyfinema.uis.white
import co.finema.thaidotidbyfinema.uis.whiteBG

@Composable
fun HomeTab(navController: NavController) {
  Scaffold(backgroundColor = whiteBG) {
    Box(modifier = Modifier.fillMaxSize().padding(it), contentAlignment = Alignment.TopCenter) {
      Canvas(modifier = Modifier.fillMaxSize()) {
        val canvasWidth = size.width
        val canvasHeight = size.height
        translate(top = -canvasHeight / 2) {
          scale(scaleX = 2f, scaleY = 0.86363636f) {
            drawCircle(brush = gradient, radius = canvasWidth / 2)
          }
        }
      }
      Column(
          modifier = Modifier.fillMaxSize(), horizontalAlignment = Alignment.CenterHorizontally) {
            Spacer(modifier = Modifier.height(64.dp))
            Row(
                modifier = Modifier.fillMaxWidth().padding(horizontal = 16.dp),
                verticalAlignment = Alignment.CenterVertically) {
                  Image(
                      painter = painterResource(id = R.drawable.thai_id_app_icon),
                      contentDescription = null,
                      contentScale = ContentScale.Fit,
                      modifier = Modifier.size(72.dp).padding(4.dp).clip(CircleShape))
                  Spacer(modifier = Modifier.width(16.dp))
                  Column {
                    Text(
                        text = stringResource(R.string.hello),
                        color = white,
                        fontSize = 20.sp,
                        fontWeight = FontWeight.W400,
                    )
                    Text(
                        text = stringResource(R.string.thai_dot_id),
                        color = white,
                        fontSize = 28.sp,
                        fontWeight = FontWeight.W700,
                    )
                  }
                  Spacer(modifier = Modifier.weight(1f))
                  Box(
                      modifier = Modifier.clip(CircleShape).background(color = blue05),
                      contentAlignment = Alignment.Center) {
                        IconButton(
                            onClick = {
                              navController.navigate(route = Screen.ProfileDetailsScreen.route)
                            }) {
                              Icon(
                                  imageVector = Icons.Rounded.BorderColor,
                                  contentDescription = null,
                                  tint = white)
                            }
                      }
                }
            Spacer(modifier = Modifier.height(16.dp))
          }
    }
  }
}
