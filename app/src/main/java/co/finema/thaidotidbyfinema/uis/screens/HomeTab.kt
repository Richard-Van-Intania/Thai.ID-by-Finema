package co.finema.thaidotidbyfinema.uis.screens

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.FloatingActionButton
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import co.finema.thaidotidbyfinema.repositories.UserConfigRepository
import co.finema.thaidotidbyfinema.uis.Screen
import co.finema.thaidotidbyfinema.uis.whiteBG
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

val viewModel = CounterViewModel()
val viewModel0 = CounterViewModel0()

// val viewModel1: CounterViewModel0 by viewModels()

@Composable
fun HomeTab(navController: NavController, counterState: CounterState) {
  val count by viewModel.count.collectAsState()

  //  val count0 = viewModel0.count

  val context = LocalContext.current
  val repository = remember { UserConfigRepository(context) }
  //  val scope = rememberCoroutineScope()
  //  val passcode by repository.passcode.collectAsState(initial = "")
  //  val salt by repository.salt.collectAsState(initial = "")
  //  val isSelectedNeverShowAgain by
  //      repository.isSelectedNeverShowAgain.collectAsState(initial = false)
  //  val hideInstruction by repository.hideInstruction.collectAsState(initial = false)
  //  val exportCount by repository.exportCount.collectAsState(initial = 0)
  //  val locale by repository.locale.collectAsState(initial = "th")
  //  val useBiometric by repository.useBiometric.collectAsState(initial = false)
  //  val homeViewLayout by
  //      repository.homeViewLayout.collectAsState(initial = ViewLayout.VIEW_LAYOUT_LIST)
  //  val historyViewLayout by
  //      repository.historyViewLayout.collectAsState(initial = ViewLayout.VIEW_LAYOUT_LIST)
  //  val isAcceptedAgreements by repository.isAcceptedAgreements.collectAsState(initial = null)
  //  LaunchedEffect(passcode) { println(passcode) }
  var name = remember { mutableStateOf("") }
  var mylist = remember { mutableStateListOf(1, 2, 3) }

  //  var count0 by rememberSaveable { mutableIntStateOf(0) }

  //  var cl = rememberSaveable { mutableStateListOf(0, 1, 2) }

  //  val count by viewModel.count

  val viewModel0: CounterViewModel0 = viewModel()
  val count0 = viewModel0.count

  val openDialog = remember { mutableStateOf(true) }

  Scaffold(
      backgroundColor = whiteBG,
      floatingActionButton = { FloatingActionButton(onClick = { counterState.increment() }) {} }) {
        Column(
            modifier = Modifier.fillMaxSize().padding(it),
            verticalArrangement = Arrangement.SpaceEvenly,
            horizontalAlignment = Alignment.CenterHorizontally) {
              Text(
                  "CreatePasscodeFullscreenKey",
                  modifier =
                      Modifier.clickable(
                          onClick = {
                            navController.navigate(route = Screen.CreatePasscodeFullscreenKey.route)
                          }))
              Text(
                  "EnterPasscodeLoginFullscreenKey",
                  modifier =
                      Modifier.clickable(
                          onClick = {
                            navController.navigate(
                                route = Screen.EnterPasscodeLoginFullscreenKey.route)
                          }))
              Text(
                  "biometric",
                  modifier =
                      Modifier.clickable(
                          onClick = {
                            //
                          }))
              Text(
                  "SelectLayoutScreenKey",
                  modifier =
                      Modifier.clickable(
                          onClick = {
                            navController.navigate(route = Screen.SelectLayoutScreenKey.route)
                          }))
              Text(counterState.count.toString())
            }
      }
}

// plain Kotlin class
// Plain state holder class as state owner for simple
// use plain class first
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

// mutableStateOf
class CounterViewModel0 : ViewModel() {
  var count by mutableIntStateOf(0)
    private set

  fun increment() {
    count++
  }

  fun decrement() {
    count--
  }
}

// StateFlow
class CounterViewModel : ViewModel() {
  private val _count = MutableStateFlow(0)
  val count = _count.asStateFlow()

  fun increment() {
    _count.value += 1
  }

  fun decrement() {
    _count.value -= 1
  }
}
