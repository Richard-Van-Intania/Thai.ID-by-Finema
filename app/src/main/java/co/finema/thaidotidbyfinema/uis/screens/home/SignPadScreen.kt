@file:Suppress("UsingMaterialAndMaterial3Libraries")

package co.finema.thaidotidbyfinema.uis.screens.home

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Close
import androidx.compose.material.icons.rounded.Replay
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.TransformOrigin
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.layout.layout
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import co.finema.thaidotidbyfinema.R
import co.finema.thaidotidbyfinema.databases.signatureimages.SignatureImageViewModel
import co.finema.thaidotidbyfinema.uis.primaryBlack
import co.finema.thaidotidbyfinema.uis.primaryDarkBlue
import co.finema.thaidotidbyfinema.uis.secondaryGray
import co.finema.thaidotidbyfinema.uis.white
import co.finema.thaidotidbyfinema.uis.whiteBG

@Composable
fun SignPadScreen(navController: NavController, signatureImageViewModel: SignatureImageViewModel) {
    Scaffold(backgroundColor = whiteBG) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(it)
                .padding(horizontal = 16.dp),
              ) {
            Spacer(modifier = Modifier.height(80.dp))
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .weight(1f)
                    .clip(RoundedCornerShape(1.dp))
                    .border(width = 1.dp, color = secondaryGray, shape = RoundedCornerShape(1.dp))
                    .background(white),
                contentAlignment = Alignment.Center,
               ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically,
                   ) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically, modifier = Modifier
                            .rotate(90f)
                            .clickable(onClick = { })
                            .padding(8.dp)
                       ) {
                        Icon(
                            imageVector = Icons.Rounded.Replay, contentDescription = null, tint = primaryDarkBlue
                            )
                        Spacer(modifier = Modifier.width(8.dp))
                        Text(
                            text = stringResource(R.string.clear), color = primaryBlack, fontSize = 20.sp, fontWeight = FontWeight.W400
                            )
                    }
                    Spacer(modifier = Modifier.weight(1f))
                    Box(modifier = Modifier
                        .layout { measurable, constraints ->
                            val placeable = measurable.measure(
                                constraints.copy(
                                    minWidth = constraints.minHeight,
                                    maxWidth = constraints.maxHeight,
                                    minHeight = constraints.minWidth,
                                    maxHeight = constraints.maxWidth,
                                                )
                                                              )
                            layout(placeable.height, placeable.width) {
                                placeable.placeRelative(0, 0)
                            }
                        }
                        .graphicsLayer {
                            rotationZ = 90f
                            transformOrigin = TransformOrigin(0f, 0f)
                        }) {
                        Text(
                            text = stringResource(R.string.sign_your_name), color = primaryBlack, fontSize = 20.sp, fontWeight = FontWeight.W400
                            )
                    }
                }
                // here
            }
            Box(modifier = Modifier.padding(vertical = 24.dp)) {
                Row(
                    modifier = Modifier.fillMaxWidth()
                   ) {
                    Image(
                        painter = painterResource(id = R.drawable.buttontrueactive),
                        contentDescription = null,
                        modifier = Modifier
                            .height(72.dp)
                            .clip(RoundedCornerShape(72.dp))
                            .clickable(onClick = {})
                         )
                    Spacer(modifier = Modifier.weight(1f))
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(imageVector = Icons.Rounded.Close, contentDescription = null, tint = primaryDarkBlue)
                    }
                }
            }

        }
    }
}
