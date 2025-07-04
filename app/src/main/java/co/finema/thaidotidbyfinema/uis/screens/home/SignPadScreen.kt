@file:Suppress("UsingMaterialAndMaterial3Libraries")

package co.finema.thaidotidbyfinema.uis.screens.home

import android.graphics.Matrix
import androidx.activity.compose.BackHandler
import androidx.compose.foundation.BorderStroke
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
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableIntState
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.TransformOrigin
import androidx.compose.ui.graphics.asAndroidBitmap
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.layout.layout
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.net.toUri
import androidx.navigation.NavController
import co.finema.thaidotidbyfinema.R
import co.finema.thaidotidbyfinema.databases.signatureimages.SignatureImageViewModel
import co.finema.thaidotidbyfinema.getFileInstancePNG
import co.finema.thaidotidbyfinema.saveImageBitmapAsPng
import co.finema.thaidotidbyfinema.uis.components.ErrorDialog
import co.finema.thaidotidbyfinema.uis.primaryBlack
import co.finema.thaidotidbyfinema.uis.primaryDarkBlue
import co.finema.thaidotidbyfinema.uis.secondaryGray
import co.finema.thaidotidbyfinema.uis.white
import co.finema.thaidotidbyfinema.uis.whiteBG
import io.github.joelkanyi.sain.Sain
import io.github.joelkanyi.sain.rememberSignatureState
import java.time.LocalDateTime

@Composable
fun SignPadScreen(navController: NavController, signatureImageViewModel: SignatureImageViewModel, currentSignatureImageId: MutableIntState) {
    BackHandler(enabled = true) {}
    val signatureImage by signatureImageViewModel.signatureImage.collectAsState()
    val context = LocalContext.current
    val configuration = LocalConfiguration.current
    val screenHeightDp = configuration.screenHeightDp
    var showErrorsDialog by remember { mutableStateOf(false) }
    if (showErrorsDialog) {
        ErrorDialog(
            text = stringResource(R.string.wrong),
            onClick = {
                showErrorsDialog = false
            },
                   )
    }
    val signatureState = rememberSignatureState()
    var savedSignature by remember { mutableStateOf(false) }

    var maxId by remember { mutableIntStateOf(0) }
    LaunchedEffect(Unit) {
        for (signature in signatureImage) {
            maxId = signature.id
        }
    }
    LaunchedEffect(savedSignature, signatureImage) {
        if (savedSignature) {
            for (signature in signatureImage) {
                if (signature.id > currentSignatureImageId.intValue) currentSignatureImageId.intValue = signature.id
            }
            if (currentSignatureImageId.intValue > maxId) navController.popBackStack()
            println(currentSignatureImageId.intValue)
        }
    }
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
                    .weight(1f),
                contentAlignment = Alignment.Center,
               ) {
                Sain(
                    modifier = Modifier
                        .width(424.264.dp)
                        .clip(RoundedCornerShape(1.dp))
                        .border(width = 1.dp, color = secondaryGray, shape = RoundedCornerShape(1.dp))
                        .background(white)
                        .padding(all = 8.dp),
                    state = signatureState,
                    signatureHeight = 600.dp,
                    signaturePadColor = Color.Transparent,
                    signatureThickness = 4.dp,
                    signatureBorderStroke = BorderStroke(
                        color = Color.Transparent,
                        width = 0.dp,
                                                        ),
                    onComplete = {},
                    ) {}
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically,
                   ) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically, modifier = Modifier
                            .rotate(90f)
                            .clickable(onClick = { signatureState.clearSignatureLines() })
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
            }
            Box(modifier = Modifier.padding(vertical = 24.dp)) {
                Row(
                    modifier = Modifier.fillMaxWidth()
                   ) {
                    Image(
                        painter = painterResource(id = if (signatureState.signatureLines.isEmpty()) R.drawable.buttontrue else R.drawable.buttontrueactive),
                        contentDescription = null,
                        modifier = Modifier
                            .height(72.dp)
                            .clip(RoundedCornerShape(72.dp))
                            .clickable(onClick = {
                                signatureState.signature?.let { signature ->
                                    val rotatedBitmap = rotateImageBitmap(signature, -90f)
                                    val photoFile = getFileInstancePNG(context)
                                    if (saveImageBitmapAsPng(rotatedBitmap, photoFile)) {
                                        val now = LocalDateTime.now().toString()
                                        signatureImageViewModel.addSignatureImage(photoFile.toUri().toString(), now, now)
                                        savedSignature = true
                                    } else {
                                        showErrorsDialog = true
                                    }
                                }
                            })
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

fun rotateImageBitmap(imageBitmap: ImageBitmap, degrees: Float): ImageBitmap {
    val androidBitmap = imageBitmap.asAndroidBitmap()
    val matrix = Matrix().apply { postRotate(degrees) }
    val rotatedBitmap = android.graphics.Bitmap.createBitmap(
        androidBitmap, 0, 0, androidBitmap.width, androidBitmap.height, matrix, true
                                                            )
    return rotatedBitmap.asImageBitmap()
}
