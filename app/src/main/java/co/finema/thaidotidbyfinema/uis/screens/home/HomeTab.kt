@file:Suppress("UsingMaterialAndMaterial3Libraries")

package co.finema.thaidotidbyfinema.uis.screens.home

import androidx.compose.material.FloatingActionButton
import androidx.compose.material.Icon
import androidx.compose.material.Scaffold
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Add
import androidx.compose.runtime.Composable
import androidx.navigation.NavController
import co.finema.thaidotidbyfinema.uis.Screen
import co.finema.thaidotidbyfinema.uis.whiteBG

@Composable
fun HomeTab(navController: NavController) {
  Scaffold(
      backgroundColor = whiteBG,
      floatingActionButton = {
        FloatingActionButton(onClick = {
            navController.navigate(route = Screen.SelectLayoutScreen.route)
        }) {
          Icon(imageVector = Icons.Rounded.Add, contentDescription = null)
        }
      }) {
        it
      }
}
