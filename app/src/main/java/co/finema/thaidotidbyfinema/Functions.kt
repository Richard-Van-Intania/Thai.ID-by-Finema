package co.finema.thaidotidbyfinema

import android.content.Context
import android.graphics.Bitmap
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.asAndroidBitmap
import java.io.File
import java.io.FileOutputStream
import java.io.IOException
import java.text.SimpleDateFormat
import java.util.Locale

fun getOutputDirectory(context: Context): File {
    val mediaDir = context.filesDir.let { File(it, context.resources.getString(R.string.app_directory)).apply { mkdirs() } }
    return if (mediaDir.exists()) mediaDir else context.filesDir
}

fun getFileInstance(context: Context): File {
    return File(getOutputDirectory(context), "IMG_${SimpleDateFormat(FILENAME_FORMAT, Locale.US).format(System.currentTimeMillis())}.JPEG")
}

fun getFileInstancePNG(context: Context): File {
    return File(getOutputDirectory(context), "IMG_${SimpleDateFormat(FILENAME_FORMAT, Locale.US).format(System.currentTimeMillis())}.PNG")
}

fun saveImageBitmapAsJpeg(imageBitmap: ImageBitmap, file: File): Boolean {
    val androidBitmap = imageBitmap.asAndroidBitmap()
    var success = false
    try {
        FileOutputStream(file).use { success = androidBitmap.compress(Bitmap.CompressFormat.JPEG, 100, it) }
    } catch (e: IOException) {
        e.printStackTrace()
    }
    return success
}

fun saveImageBitmapAsPng(imageBitmap: ImageBitmap, file: File): Boolean {
    val androidBitmap = imageBitmap.asAndroidBitmap()
    var success = false
    try {
        FileOutputStream(file).use { success = androidBitmap.compress(Bitmap.CompressFormat.PNG, 100, it) }
    } catch (e: IOException) {
        e.printStackTrace()
    }
    return success
}
