package co.finema.thaidotidbyfinema

import androidx.compose.ui.unit.dp
import java.time.format.DateTimeFormatter
import java.util.Locale

val compactWidth = 600.dp

val cornerRadius = 16.dp

const val milliSeconds = 250

const val EN = "en"
const val TH = "th"

val formatterTH: DateTimeFormatter = DateTimeFormatter.ofPattern("d MMMM yyyy", Locale("th", "TH"))
val formatterEN: DateTimeFormatter = DateTimeFormatter.ofPattern("d MMMM yyyy", Locale("en", "EN"))

val formatterShort: DateTimeFormatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss")

val regexNumber = Regex("^[0-9]+$")

const val FILENAME_FORMAT = "yyyy-MM-dd-HH-mm-ss-SSS"
