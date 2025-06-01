package co.finema.thaidotidbyfinema

import android.content.Context
import java.io.File
import java.text.SimpleDateFormat
import java.util.Locale

fun getOutputDirectory(context: Context): File {
    val mediaDir =
        context.filesDir.let {
            File(it, context.resources.getString(R.string.app_directory)).apply { mkdirs() }
        }
    return if (mediaDir.exists()) mediaDir else context.filesDir
}

fun getFileInstance(context: Context): File {
    return File(
        getOutputDirectory(context),
        "IMG_${SimpleDateFormat(FILENAME_FORMAT, Locale.US).format(System.currentTimeMillis())}.JPEG",
    )
}
