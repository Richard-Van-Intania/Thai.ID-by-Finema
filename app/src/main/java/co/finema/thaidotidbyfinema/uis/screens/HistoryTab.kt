@file:Suppress("UsingMaterialAndMaterial3Libraries")

package co.finema.thaidotidbyfinema.uis.screens

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import co.finema.thaidotidbyfinema.R
import co.finema.thaidotidbyfinema.uis.whiteBG
import kotlinx.coroutines.launch

@Composable
fun HistoryTab(navController: NavController) {
  val counterState = rememberCounterState()
  LaunchedEffect(counterState.count) { println(counterState.count) }
  val count = remember { mutableIntStateOf(0) }
  LaunchedEffect(count.intValue) { println(count.intValue) }
  val snackbarHostState = remember { SnackbarHostState() }
  val scope = rememberCoroutineScope()
  val disableBiometricsSuccess = stringResource(R.string.disable_biometrics_success)
  Scaffold(
      snackbarHost = {
        SnackbarHost(hostState = snackbarHostState, modifier = Modifier.padding(bottom = 8.dp))
      },
      backgroundColor = whiteBG) {
        Box(modifier = Modifier.fillMaxSize().padding(it), contentAlignment = Alignment.Center) {
          Text(
              "HistoryTab",
              modifier =
                  Modifier.clickable(
                      onClick = {
                        scope.launch { snackbarHostState.showSnackbar(disableBiometricsSuccess) }
                      }))
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
