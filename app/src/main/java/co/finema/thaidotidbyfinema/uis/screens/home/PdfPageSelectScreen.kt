package co.finema.thaidotidbyfinema.uis.screens.home

import android.net.Uri
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.navigation.NavController

@Composable fun PdfPageSelectScreen(navController: NavController, imageUri: MutableState<Uri?>) {}



//context.contentResolver.takePersistableUriPermission(
//uri,
//Intent.FLAG_GRANT_READ_URI_PERMISSION,
//)
//imageUri.value = uri
//// read content
//val inputStream = context.contentResolver.openInputStream(uri)