package co.finema.thaidotidbyfinema.uis.screens

import androidx.compose.material.Scaffold
import androidx.compose.runtime.Composable
import androidx.navigation.NavController
import co.finema.thaidotidbyfinema.uis.whiteBG

@Composable
fun HomeTab(navController: NavController, counterState: CounterState) {
  Scaffold(backgroundColor = whiteBG) { it }
}
