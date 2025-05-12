@file:Suppress("UsingMaterialAndMaterial3Libraries")

package co.finema.thaidotidbyfinema.uis.screens

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import co.finema.thaidotidbyfinema.uis.Screen
import co.finema.thaidotidbyfinema.uis.whiteBG

@Composable
fun HistoryTab(navController: NavController) {
  val counterState = rememberCounterState()
  LaunchedEffect(counterState.count) { println(counterState.count) }
  val count = remember { mutableIntStateOf(0) }
  LaunchedEffect(count.intValue) { println(count.intValue) }
  Scaffold(backgroundColor = whiteBG) {
    Box(modifier = Modifier.fillMaxSize().padding(it), contentAlignment = Alignment.Center) {
      Text(
          "ProfileTab",
          modifier =
              Modifier.clickable(
                  onClick = { navController.navigate(route = Screen.SelectLayoutScreenNav.route) }))
    }
  }
}

class CounterState {
  var count by mutableIntStateOf(0)
    private set

  fun increment() {
    count++
  }
}

@Composable
fun rememberCounterState(): CounterState {
  return remember { CounterState() }
}
