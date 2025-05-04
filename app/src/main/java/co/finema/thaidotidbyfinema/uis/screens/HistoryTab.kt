package co.finema.thaidotidbyfinema.uis.screens

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import co.finema.thaidotidbyfinema.uis.whiteBG

@Composable
fun HistoryTab(navController: NavController) {
  Scaffold(backgroundColor = whiteBG) {
    Box(modifier = Modifier.fillMaxSize().padding(it), contentAlignment = Alignment.Center) {
      Text(
          "HistoryTab",
          modifier =
              Modifier.clickable(
                  onClick = {
                    //
                  }))
    }
  }
}
