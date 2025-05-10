package co.finema.thaidotidbyfinema.uis.screens

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import co.finema.thaidotidbyfinema.uis.Screen
import kotlin.time.Duration.Companion.seconds
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@Composable
fun LoadingScreen(navController: NavController) {
  BackHandler(enabled = true) {}
  val scope = rememberCoroutineScope()
  LaunchedEffect(Unit) {
    scope
        .launch { delay(5.seconds) }
        .invokeOnCompletion {
          navController.navigate(route = Screen.OnboardingRootNav.route) {
            popUpTo(Screen.LoadingScreenNav.route) { inclusive = true }
          }
        }
  }
  Scaffold {
    it
    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
      CircularProgressIndicator()
    }
  }
}
