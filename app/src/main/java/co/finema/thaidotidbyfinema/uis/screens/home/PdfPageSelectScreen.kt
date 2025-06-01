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
import androidx.compose.foundation.gestures.detectHorizontalDragGestures
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.core.graphics.createBitmap
import androidx.navigation.NavController
import co.finema.thaidotidbyfinema.R
import co.finema.thaidotidbyfinema.uis.components.AppBarOptBack
import co.finema.thaidotidbyfinema.uis.components.GradientButton
import co.finema.thaidotidbyfinema.uis.white
import co.finema.thaidotidbyfinema.uis.whiteBG
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.io.File
import java.io.FileOutputStream

@Composable
fun PdfPageSelectScreen(navController: NavController, contentUri: MutableState<Uri?>) {
    BackHandler(enabled = true) {}
    val context = LocalContext.current
    var pageIndex by remember { mutableIntStateOf(0) }
    var imageBitmap by remember { mutableStateOf<ImageBitmap?>(null) }
    var isLoading by remember { mutableStateOf(true) }
    LaunchedEffect(contentUri.value, pageIndex) {
        contentUri.value?.let { uri ->
            isLoading = true
            imageBitmap =
                withContext(Dispatchers.IO) {
                    val file = context.getFileFromContentUri(uri)
                    file?.let { renderPdfPage(it, pageIndex)?.asImageBitmap() }
                }
            isLoading = false
        }
    }
    Scaffold(
        topBar = {
            AppBarOptBack(
                containerColor = white,
                text = stringResource(R.string.select_one_page),
                onClick = {
                    contentUri.value = null
                    navController.popBackStack()
                },
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
                GradientButton(onClick = {}, text = stringResource(R.string.next))
            }
        },
        backgroundColor = whiteBG,
    ) {
        Box(modifier = Modifier.fillMaxSize().padding(it), contentAlignment = Alignment.Center) {
            when {
                isLoading -> CircularProgressIndicator()
                imageBitmap != null ->
                    Box(
                        modifier =
                            Modifier.fillMaxSize().pointerInput(pageIndex) {
                                detectHorizontalDragGestures { change, dragAmount ->
                                    if (dragAmount > 0) {
                                        if (pageIndex > 0) pageIndex--
                                    } else {
                                        contentUri.value?.let { uri ->
                                            val file = context.getFileFromContentUri(uri)
                                            file?.let {
                                                val totalPages = getPdfPageCount(it)
                                                if (pageIndex < totalPages - 1) pageIndex++
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
                else -> {}
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
    val fileDescriptor = ParcelFileDescriptor.open(file, ParcelFileDescriptor.MODE_READ_ONLY)
    val renderer = PdfRenderer(fileDescriptor)
    val count = renderer.pageCount
    renderer.close()
    fileDescriptor.close()
    return count
}
