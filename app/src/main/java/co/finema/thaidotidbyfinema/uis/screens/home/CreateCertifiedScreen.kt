@file:Suppress("UsingMaterialAndMaterial3Libraries")

package co.finema.thaidotidbyfinema.uis.screens.home

import android.net.Uri
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.ArrowBackIosNew
import androidx.compose.material.icons.rounded.IosShare
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableIntState
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.net.toUri
import androidx.navigation.NavController
import co.finema.thaidotidbyfinema.R
import co.finema.thaidotidbyfinema.databases.signatureimages.SignatureImageViewModel
import co.finema.thaidotidbyfinema.uis.Screen
import co.finema.thaidotidbyfinema.uis.primaryBlack
import co.finema.thaidotidbyfinema.uis.white
import co.finema.thaidotidbyfinema.uis.whiteBG
import coil.compose.rememberAsyncImagePainter

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CreateCertifiedScreen(
 navController: NavController,
 layoutIndex: MutableIntState,
 placeholderFilePath0: MutableState<Uri?>,
 placeholderFilePath1: MutableState<Uri?>,
 placeholderFilePath2: MutableState<Uri?>,
 signatureImageViewModel: SignatureImageViewModel,
 currentSignatureImageId: MutableIntState,
) {
    val signatureImage by signatureImageViewModel.signatureImage.collectAsState()
    var signatureUri by remember { mutableStateOf<Uri?>(null) }
    LaunchedEffect(currentSignatureImageId) {
        if (currentSignatureImageId.intValue == 0) {
            signatureUri = null
        } else {
            for (signature in signatureImage) {
                if (signature.id == currentSignatureImageId.intValue) signatureUri = signature.fileName.toUri()
            }
        }
    }
    Scaffold(
     topBar = {
         CenterAlignedTopAppBar(
          title = { Text(text = stringResource(R.string.make_document), color = primaryBlack, fontSize = 24.sp, fontWeight = FontWeight.W700) },
          navigationIcon = { IconButton(onClick = { navController.popBackStack() }) { Icon(imageVector = Icons.Rounded.ArrowBackIosNew, contentDescription = null) } },
          actions = {
              IconButton(
               onClick = {
                   //
               }
              ) {
                  Icon(imageVector = Icons.Rounded.IosShare, contentDescription = null, tint = primaryBlack)
              }
          },
          colors = TopAppBarDefaults.centerAlignedTopAppBarColors(containerColor = white, navigationIconContentColor = primaryBlack, actionIconContentColor = primaryBlack),
         )
     },
     backgroundColor = whiteBG,
    ) {
        Column(modifier = Modifier.fillMaxSize().padding(it).padding(horizontal = 16.dp), horizontalAlignment = Alignment.CenterHorizontally) {
            Text("SignatureListScreen", modifier = Modifier.clickable(onClick = { navController.navigate(route = Screen.SignatureListScreen.route) }))
            Spacer(modifier = Modifier.height(40.dp))
            Text("SignPadScreen", modifier = Modifier.clickable(onClick = { navController.navigate(route = Screen.SignPadScreen.route) }))
            Spacer(modifier = Modifier.height(40.dp))
            if (signatureUri != null)
             Image(modifier = Modifier.fillMaxWidth(), painter = rememberAsyncImagePainter(model = signatureUri), contentDescription = null, contentScale = ContentScale.Fit)
        }
    }
}
