package co.finema.thaidotidbyfinema.uis.screens.home

import android.net.Uri
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Text
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableIntState
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import co.finema.thaidotidbyfinema.uis.Screen

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
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(it), contentAlignment = Alignment.Center
           ) {
            Text("SignatureListScreen", modifier = Modifier.clickable(onClick = {
                navController.navigate(route = Screen.SignatureListScreen.route)
            }))
        }

    }
}
