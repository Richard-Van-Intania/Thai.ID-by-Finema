@file:Suppress("UsingMaterialAndMaterial3Libraries")

package co.finema.thaidotidbyfinema.uis.screens.home

import android.content.Context
import android.graphics.Bitmap
import android.graphics.pdf.PdfRenderer
import android.net.Uri
import android.os.ParcelFileDescriptor
import androidx.activity.compose.BackHandler
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.awaitHorizontalDragOrCancellation
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.DropdownMenu
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.ArrowBackIosNew
import androidx.compose.material.icons.rounded.FormatListNumberedRtl
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberUpdatedState
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.input.pointer.positionChange
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.graphics.createBitmap
import androidx.navigation.NavController
import co.finema.thaidotidbyfinema.R
import co.finema.thaidotidbyfinema.uis.components.GradientButton
import co.finema.thaidotidbyfinema.uis.primaryBlack
import co.finema.thaidotidbyfinema.uis.white
import co.finema.thaidotidbyfinema.uis.whiteBG
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.io.File
import java.io.FileOutputStream

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PdfPageSelectScreen(navController: NavController, contentUri: MutableState<Uri?>) {
    BackHandler(enabled = true) {}
    val context = LocalContext.current
    var pageIndex by remember { mutableIntStateOf(0) }
    var totalPages by remember { mutableIntStateOf(0) }
    var expanded by remember { mutableStateOf(false) }
    var imageBitmap by remember { mutableStateOf<ImageBitmap?>(null) }
    var isLoading by remember { mutableStateOf(true) }
    val currentPageIndex = rememberUpdatedState(pageIndex)
    LaunchedEffect(contentUri.value, pageIndex) {
        contentUri.value?.let { uri ->
            isLoading = true
            imageBitmap =
                withContext(Dispatchers.IO) {
                    val file = context.getFileFromContentUri(uri)
                    file?.let {
                        totalPages = getPdfPageCount(it)
                        renderPdfPage(it, pageIndex)?.asImageBitmap()
                    }
                }
            isLoading = false
        }
    }
    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = {
                    Text(
                        text = stringResource(R.string.select_one_page),
                        color = primaryBlack,
                        fontSize = 24.sp,
                        fontWeight = FontWeight.W700,
                    )
                },
                navigationIcon = {
                    IconButton(
                        onClick = {
                            contentUri.value = null
                            navController.popBackStack()
                        }
                    ) {
                        Icon(imageVector = Icons.Rounded.ArrowBackIosNew, contentDescription = null)
                    }
                },
                actions = {
                    IconButton(onClick = { expanded = !expanded }) {
                        Icon(
                            imageVector = Icons.Rounded.FormatListNumberedRtl,
                            contentDescription = null,
                        )
                    }
                    DropdownMenu(expanded = expanded, onDismissRequest = { expanded = false }) {
                        for (page in 1..totalPages) {
                            DropdownMenuItem(
                                text = {
                                    Text(
                                        modifier = Modifier.fillMaxWidth(),
                                        text = page.toString(),
                                        color = primaryBlack,
                                        fontSize = 24.sp,
                                        fontWeight =
                                            if (currentPageIndex.value == page - 1) FontWeight.W700
                                            else FontWeight.W400,
                                        textAlign = TextAlign.Center,
                                    )
                                },
                                onClick = { pageIndex = page - 1 },
                            )
                        }
                    }
                },
                colors =
                    TopAppBarDefaults.centerAlignedTopAppBarColors(
                        containerColor = white,
                        navigationIconContentColor = primaryBlack,
                        actionIconContentColor = primaryBlack,
                    ),
            )
        },
        bottomBar = {
            Box(
                modifier =
                    Modifier.background(white)
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp, vertical = 24.dp),
                contentAlignment = Alignment.Center,
            ) {
                GradientButton(onClick = {}, text = stringResource(R.string.next))
            }
        },
        containerColor = whiteBG,
    ) { padding ->
        Box(
            modifier = Modifier.fillMaxSize().padding(padding),
            contentAlignment = Alignment.Center,
        ) {
            when {
                isLoading -> CircularProgressIndicator()
                imageBitmap != null -> {
                    Box(
                        modifier =
                            Modifier.fillMaxSize().pointerInput(
                                totalPages,
                                currentPageIndex.value,
                            ) {
                                awaitPointerEventScope {
                                    while (true) {
                                        val down =
                                            awaitPointerEvent().changes.firstOrNull() ?: continue
                                        if (down.pressed) {
                                            val drag = awaitHorizontalDragOrCancellation(down.id)
                                            drag?.let {
                                                val dragThreshold = 100
                                                when {
                                                    it.positionChange().x > dragThreshold &&
                                                        currentPageIndex.value > 0 -> {
                                                        pageIndex--
                                                    }
                                                    it.positionChange().x < -dragThreshold &&
                                                        currentPageIndex.value < totalPages - 1 -> {
                                                        pageIndex++
                                                    }
                                                }
                                            }
                                        }
                                    }
                                }
                            },
                        contentAlignment = Alignment.Center,
                    ) {
                        Image(
                            bitmap = imageBitmap!!,
                            contentDescription = null,
                            modifier = Modifier.fillMaxSize(),
                            contentScale = ContentScale.Fit,
                        )
                    }
                }
            }
        }
    }
}

fun Context.getFileFromContentUri(uri: Uri): File? {
    return try {
        contentResolver.openInputStream(uri)?.use { inputStream ->
            val tempFile = File.createTempFile("temp_pdf_", ".pdf", cacheDir)
            FileOutputStream(tempFile).use { outputStream -> inputStream.copyTo(outputStream) }
            tempFile
        }
    } catch (e: Exception) {
        e.printStackTrace()
        null
    }
}

fun renderPdfPage(file: File, pageIndex: Int): Bitmap? {
    var renderer: PdfRenderer? = null
    var fileDescriptor: ParcelFileDescriptor? = null
    return try {
        fileDescriptor = ParcelFileDescriptor.open(file, ParcelFileDescriptor.MODE_READ_ONLY)
        renderer = PdfRenderer(fileDescriptor)
        if (pageIndex >= renderer.pageCount) return null
        renderer.openPage(pageIndex).use { page ->
            val bitmap = createBitmap(page.width, page.height)
            page.render(bitmap, null, null, PdfRenderer.Page.RENDER_MODE_FOR_DISPLAY)
            bitmap
        }
    } catch (e: Exception) {
        e.printStackTrace()
        null
    } finally {
        renderer?.close()
        fileDescriptor?.close()
    }
}

fun getPdfPageCount(file: File): Int {
    ParcelFileDescriptor.open(file, ParcelFileDescriptor.MODE_READ_ONLY).use { fileDescriptor ->
        PdfRenderer(fileDescriptor).use { renderer ->
            return renderer.pageCount
        }
    }
}
