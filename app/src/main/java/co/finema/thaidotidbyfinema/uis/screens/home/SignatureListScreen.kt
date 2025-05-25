@file:Suppress("UsingMaterialAndMaterial3Libraries")

package co.finema.thaidotidbyfinema.uis.screens.home

import androidx.compose.material.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.navigation.NavController
import co.finema.thaidotidbyfinema.databases.signatureimage.SignatureImageViewModel

@Composable
fun SignatureListScreen(
    navController: NavController,
    signatureImageViewModel: SignatureImageViewModel
) {
  val signatureImage by signatureImageViewModel.signatureImage.collectAsState()
  Scaffold { it }
}
