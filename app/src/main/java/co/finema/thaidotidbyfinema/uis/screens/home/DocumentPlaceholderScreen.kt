@file:Suppress("UsingMaterialAndMaterial3Libraries")

package co.finema.thaidotidbyfinema.uis.screens.home

import android.Manifest
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
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableIntState
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.core.content.ContextCompat
import androidx.navigation.NavController
import co.finema.thaidotidbyfinema.R
import co.finema.thaidotidbyfinema.cornerRadius
import co.finema.thaidotidbyfinema.databases.layouthistories.LayoutHistoryViewModel
import co.finema.thaidotidbyfinema.enums.DocumentLayout
import co.finema.thaidotidbyfinema.enums.FileSource
import co.finema.thaidotidbyfinema.uis.Screen
import co.finema.thaidotidbyfinema.uis.black
import co.finema.thaidotidbyfinema.uis.components.AppBarOptBack
import co.finema.thaidotidbyfinema.uis.components.GradientButton
import co.finema.thaidotidbyfinema.uis.components.HorizontalLine
import co.finema.thaidotidbyfinema.uis.gradient
import co.finema.thaidotidbyfinema.uis.lightBlue07
import co.finema.thaidotidbyfinema.uis.neutral02
import co.finema.thaidotidbyfinema.uis.primaryBlack
import co.finema.thaidotidbyfinema.uis.secondaryGray
import co.finema.thaidotidbyfinema.uis.white
import co.finema.thaidotidbyfinema.uis.whiteBG
import coil.compose.rememberAsyncImagePainter
import kotlinx.coroutines.launch
import java.time.LocalDateTime

@Composable
fun DocumentPlaceholderScreen(
    navController: NavController,
    layoutIndex: MutableIntState,
    placeholderFilePath0: MutableState<Uri?>,
    placeholderFilePath1: MutableState<Uri?>,
    placeholderFilePath2: MutableState<Uri?>,
    imageUri: MutableState<Uri?>,
    imageIndex: MutableIntState,
    pdfUrl: MutableState<Uri?>,
    layoutHistoryViewModel: LayoutHistoryViewModel,
    savedLayoutHistory: MutableState<Boolean>,
) {
    val context = LocalContext.current
    val snackbarState = remember { SnackbarHostState() }
    val enableBiometricsSuccess = stringResource(R.string.delete_photo_successfully)
    val scope = rememberCoroutineScope()
    var startCameraLive by remember { mutableStateOf(false) }
    var hasCameraPermission by remember { mutableStateOf(false) }
    val cameraPermissionLauncher = rememberLauncherForActivityResult(contract = ActivityResultContracts.RequestPermission(), onResult = { isGranted -> hasCameraPermission = isGranted })
    LaunchedEffect(hasCameraPermission, startCameraLive) {
        if (hasCameraPermission && startCameraLive) {
            imageUri.value = null
            navController.navigate(route = Screen.CameraScreen.route)
        }
    }
    val pickPdf =
        rememberLauncherForActivityResult(ActivityResultContracts.OpenDocument()) { uri ->
            if (uri != null) {
                imageUri.value = null
                pdfUrl.value = uri
                navController.navigate(route = Screen.PdfPageSelectScreen.route)
            }
        }
    val pickImage =
        rememberLauncherForActivityResult(ActivityResultContracts.PickVisualMedia()) { uri ->
            if (uri != null) {
                imageUri.value = uri
                navController.navigate(route = Screen.CropImageScreen.route)
            }
        }
    var fileSource by remember { mutableStateOf<FileSource?>(null) }
    LaunchedEffect(fileSource) {
        when (fileSource) {
            FileSource.CAMERA -> {
                hasCameraPermission = ContextCompat.checkSelfPermission(context, Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED
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
            Box(modifier = Modifier
                .clip(RoundedCornerShape(8.dp))
                .background(white)
                .fillMaxWidth()) {
                Column(modifier = Modifier.fillMaxWidth()) {
                    Text(modifier = Modifier.padding(all = 24.dp), text = stringResource(R.string.options), color = primaryBlack, fontSize = 24.sp, fontWeight = FontWeight.W700)
                    Box(modifier = Modifier
                        .fillMaxWidth()
                        .height(2.dp)
                        .background(neutral02))
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
                        Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.Center, modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 24.dp)) {
                            Text(text = stringResource(R.string.cancel), color = primaryBlack, fontSize = 24.sp, fontWeight = FontWeight.W400)
                        }
                    }
                }
            }
        }
    }
    var showDeleteDialog by remember { mutableStateOf(false) }
    if (showDeleteDialog) {
        Dialog(onDismissRequest = {}) {
            Box(modifier = Modifier
                .clip(RoundedCornerShape(8.dp))
                .background(white)
                .fillMaxWidth()) {
                Column(modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp), horizontalAlignment = Alignment.CenterHorizontally) {
                    Spacer(modifier = Modifier.height(40.dp))
                    Text(text = stringResource(R.string.do_you_want_to_delete_this_photo), color = primaryBlack, fontSize = 24.sp, fontWeight = FontWeight.W700, textAlign = TextAlign.Center)
                    Spacer(modifier = Modifier.height(32.dp))
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Box(
                            modifier = Modifier
                                .height(56.dp)
                                .weight(1f)
                                .clip(RoundedCornerShape(56.dp))
                                .background(brush = gradient)
                                .clickable(
                                    onClick = {
                                        when (imageIndex.intValue) {
                                            0 -> placeholderFilePath0.value = null
                                            1 -> placeholderFilePath1.value = null
                                            2 -> placeholderFilePath2.value = null
                                        }
                                        savedLayoutHistory.value = false
                                        showDeleteDialog = false
                                        scope.launch { snackbarState.showSnackbar(enableBiometricsSuccess) }
                                    }),
                            contentAlignment = Alignment.Center,
                        ) {
                            Text(text = stringResource(R.string.delete), color = white, fontSize = 24.sp, fontWeight = FontWeight.W700)
                        }
                        Spacer(modifier = Modifier.width(16.dp))
                        Box(
                            modifier = Modifier
                                .height(56.dp)
                                .weight(1f)
                                .background(white)
                                .border(width = 2.dp, color = lightBlue07, shape = RoundedCornerShape(56.dp))
                                .clip(RoundedCornerShape(56.dp))
                                .clickable(onClick = { showDeleteDialog = false }),
                            contentAlignment = Alignment.Center,
                        ) {
                            Text(text = stringResource(R.string.cancel), color = lightBlue07, fontSize = 24.sp, fontWeight = FontWeight.W700)
                        }
                    }
                    Spacer(modifier = Modifier.height(32.dp))
                }
            }
        }
    }
    Scaffold(
        snackbarHost = { SnackbarHost(hostState = snackbarState) },
        topBar = { AppBarOptBack(containerColor = white, text = stringResource(R.string.add_document), onClick = { navController.popBackStack() }) },
        bottomBar = {
            Box(modifier = Modifier
                .background(white)
                .fillMaxWidth()
                .padding(start = 16.dp, top = 16.dp, end = 16.dp, bottom = 48.dp), contentAlignment = Alignment.Center) {
                GradientButton(
                    enabled = when (layoutItems[layoutIndex.intValue].documentLayout) {
                        DocumentLayout.ONE_SIDE_CARD -> placeholderFilePath0.value != null
                        DocumentLayout.TWO_SIDE_CARD -> placeholderFilePath1.value != null && placeholderFilePath2.value != null
                        DocumentLayout.ONE_SIDE_HALF_A4 -> placeholderFilePath0.value != null
                        DocumentLayout.TWO_SIDE_HALF_A4 -> placeholderFilePath1.value != null && placeholderFilePath2.value != null
                        DocumentLayout.FULL_A4 -> placeholderFilePath0.value != null
                    },
                    onClick = {
                        if (!savedLayoutHistory.value) {
                            val now = LocalDateTime.now().toString()
                            layoutHistoryViewModel.addLayoutHistory(
                                documentLayout = layoutItems[layoutIndex.intValue].documentLayout.name,
                                dateCreated = now,
                                dateLastUsed = now,
                                layoutRawImagefileName0 = placeholderFilePath0.value?.toString(),
                                layoutRawImagefileName1 = placeholderFilePath1.value?.toString(),
                                layoutRawImagefileName2 = placeholderFilePath2.value?.toString(),
                                userDefinedName = null,
                                                                   )
                            savedLayoutHistory.value = true
                        }
                        navController.navigate(route = Screen.CreateCertifiedScreen.route)
                    },
                    text = stringResource(R.string.make_a_cert),
                )
            }
        },
        backgroundColor = whiteBG,
    ) {
        Column(modifier = Modifier
            .fillMaxSize()
            .padding(it)
            .padding(horizontal = 16.dp), verticalArrangement = Arrangement.Center, horizontalAlignment = Alignment.CenterHorizontally) {
            when (layoutItems[layoutIndex.intValue].documentLayout) {
                DocumentLayout.ONE_SIDE_CARD -> {
                    if (placeholderFilePath0.value == null)
                        AddImageButton(
                            ratio = layoutItems[layoutIndex.intValue].aspectRatio,
                            label = R.string.add_a_document_image,
                            onClick = {
                                imageIndex.intValue = 0
                                fileSource = null
                                showOptionDialog = true
                            },
                                      )
                    else
                        PlaceholderImagePreview(
                            aspectRatio = layoutItems[layoutIndex.intValue].aspectRatio,
                            placeholderFilePath = placeholderFilePath0,
                            onClick = {
                                imageIndex.intValue = 0
                                showDeleteDialog = true
                            },
                                               )
                }
                DocumentLayout.TWO_SIDE_CARD -> {
                    if (placeholderFilePath1.value == null)
                        AddImageButton(
                            ratio = layoutItems[layoutIndex.intValue].aspectRatio,
                            label = R.string.add_card_1,
                            onClick = {
                                imageIndex.intValue = 1
                                fileSource = null
                                showOptionDialog = true
                            },
                        )
                    else
                        PlaceholderImagePreview(
                            aspectRatio = layoutItems[layoutIndex.intValue].aspectRatio,
                            placeholderFilePath = placeholderFilePath1,
                            onClick = {
                                imageIndex.intValue = 1
                                showDeleteDialog = true
                            },
                        )
                    Spacer(modifier = Modifier.height(32.dp))
                    if (placeholderFilePath2.value == null)
                        AddImageButton(
                            ratio = layoutItems[layoutIndex.intValue].aspectRatio,
                            label = R.string.add_card_2,
                            onClick = {
                                imageIndex.intValue = 2
                                fileSource = null
                                showOptionDialog = true
                            },
                        )
                    else
                        PlaceholderImagePreview(
                            aspectRatio = layoutItems[layoutIndex.intValue].aspectRatio,
                            placeholderFilePath = placeholderFilePath2,
                            onClick = {
                                imageIndex.intValue = 2
                                showDeleteDialog = true
                            },
                        )
                }
                DocumentLayout.ONE_SIDE_HALF_A4 -> {
                    if (placeholderFilePath0.value == null)
                        AddImageButton(
                            ratio = layoutItems[layoutIndex.intValue].aspectRatio,
                            label = R.string.add_a_document_image,
                            onClick = {
                                imageIndex.intValue = 0
                                fileSource = null
                                showOptionDialog = true
                            },
                        )
                    else
                        PlaceholderImagePreview(
                            aspectRatio = layoutItems[layoutIndex.intValue].aspectRatio,
                            placeholderFilePath = placeholderFilePath0,
                            onClick = {
                                imageIndex.intValue = 0
                                showDeleteDialog = true
                            },
                        )
                }
                DocumentLayout.TWO_SIDE_HALF_A4 -> {
                    if (placeholderFilePath1.value == null)
                        AddImageButton(
                            ratio = layoutItems[layoutIndex.intValue].aspectRatio,
                            label = R.string.add_a_document_image_1,
                            onClick = {
                                imageIndex.intValue = 1
                                fileSource = null
                                showOptionDialog = true
                            },
                        )
                    else
                        PlaceholderImagePreview(
                            aspectRatio = layoutItems[layoutIndex.intValue].aspectRatio,
                            placeholderFilePath = placeholderFilePath1,
                            onClick = {
                                imageIndex.intValue = 1
                                showDeleteDialog = true
                            },
                        )
                    Spacer(modifier = Modifier.height(32.dp))
                    if (placeholderFilePath2.value == null)
                        AddImageButton(
                            ratio = layoutItems[layoutIndex.intValue].aspectRatio,
                            label = R.string.add_a_document_image_2,
                            onClick = {
                                imageIndex.intValue = 2
                                fileSource = null
                                showOptionDialog = true
                            },
                        )
                    else
                        PlaceholderImagePreview(
                            aspectRatio = layoutItems[layoutIndex.intValue].aspectRatio,
                            placeholderFilePath = placeholderFilePath2,
                            onClick = {
                                imageIndex.intValue = 2
                                showDeleteDialog = true
                            },
                        )
                }
                DocumentLayout.FULL_A4 -> {
                    if (placeholderFilePath0.value == null)
                        AddImageButton(
                            ratio = layoutItems[layoutIndex.intValue].aspectRatio,
                            label = R.string.add_a_document_image,
                            onClick = {
                                imageIndex.intValue = 0
                                fileSource = null
                                showOptionDialog = true
                            },
                        )
                    else
                        PlaceholderImagePreview(
                            aspectRatio = layoutItems[layoutIndex.intValue].aspectRatio,
                            placeholderFilePath = placeholderFilePath0,
                            onClick = {
                                imageIndex.intValue = 0
                                showDeleteDialog = true
                            },
                        )
                }
            }
        }
    }
}

@Composable
fun AddImageButton(ratio: Float, label: Int, onClick: () -> Unit) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .aspectRatio(ratio)
            .clip(RoundedCornerShape(cornerRadius))
            .border(width = 1.dp, color = secondaryGray, shape = RoundedCornerShape(cornerRadius))
            .background(white)
            .clickable(onClick = onClick),
        contentAlignment = Alignment.Center,
    ) {
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Image(painter = painterResource(id = R.drawable.group_284), contentDescription = null, modifier = Modifier.height(56.dp))
            Spacer(modifier = Modifier.height(24.dp))
            Text(text = stringResource(label), color = primaryBlack, fontSize = 24.sp, fontWeight = FontWeight.W400)
        }
    }
}

@Composable
fun OptionButton(imageVector: ImageVector, text: Int, onClick: () -> Unit) {
    TextButton(onClick = onClick) {
        Row(verticalAlignment = Alignment.CenterVertically, modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 16.dp)) {
            Spacer(modifier = Modifier.width(16.dp))
            Icon(imageVector = imageVector, contentDescription = null)
            Spacer(modifier = Modifier.width(16.dp))
            Text(text = stringResource(text), color = primaryBlack, fontSize = 24.sp, fontWeight = FontWeight.W400)
        }
    }
}

@Composable
fun PlaceholderImagePreview(aspectRatio: Float, placeholderFilePath: MutableState<Uri?>, onClick: () -> Unit) {
    Box(modifier = Modifier
        .fillMaxWidth()
        .aspectRatio(aspectRatio)
        .clip(RoundedCornerShape(cornerRadius)), contentAlignment = Alignment.TopEnd) {
        Image(modifier = Modifier.fillMaxSize(), painter = rememberAsyncImagePainter(model = placeholderFilePath.value), contentDescription = null, contentScale = ContentScale.Fit)
        Box(modifier = Modifier
            .padding(all = 16.dp)
            .clip(RoundedCornerShape(32.dp))
            .background(black.copy(alpha = 0.55f))
            .clickable(onClick = onClick), contentAlignment = Alignment.Center) {
            Text(modifier = Modifier.padding(vertical = 8.dp, horizontal = 24.dp), text = stringResource(R.string.delete), color = white, fontSize = 20.sp, fontWeight = FontWeight.W400)
        }
    }
}
