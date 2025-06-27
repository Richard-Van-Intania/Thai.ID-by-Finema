package co.finema.thaidotidbyfinema.uis.screens.home

import android.net.Uri
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Text
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableIntState
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import co.finema.thaidotidbyfinema.uis.Screen
import coil.compose.rememberAsyncImagePainter

@Composable
fun CreateCertifiedScreen(
    navController: NavController,
    layoutIndex: MutableIntState,
    placeholderFilePath0: MutableState<Uri?>,
    placeholderFilePath1: MutableState<Uri?>,
    placeholderFilePath2: MutableState<Uri?>,
) {
    Scaffold {
        it
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(it),
           ) {
            Text("SignatureListScreen", modifier = Modifier.clickable(onClick = {
                navController.navigate(route = Screen.SignatureListScreen.route)
            }))
            Spacer(modifier = Modifier.height(40.dp))
            Text("SignPadScreen", modifier = Modifier.clickable(onClick = {
                navController.navigate(route = Screen.SignPadScreen.route)
            }))
            Image(painter = rememberAsyncImagePainter(model = Uri.parse("file:///data/user/0/co.finema.thaidotidbyfinema/files/thaidotidbyfinema/IMG_2025-06-27-22-46-55-890.PNG")), contentDescription = null, )
        }

    }
}
