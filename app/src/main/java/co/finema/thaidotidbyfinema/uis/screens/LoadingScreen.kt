@file:Suppress("UsingMaterialAndMaterial3Libraries")

package co.finema.thaidotidbyfinema.uis.screens

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.NavController
import co.finema.thaidotidbyfinema.TH
import co.finema.thaidotidbyfinema.ViewLayout
import co.finema.thaidotidbyfinema.repositories.UserConfigRepository
import co.finema.thaidotidbyfinema.uis.Screen

@Composable
fun LoadingScreen(navController: NavController) {
  BackHandler(enabled = true) {}
  val context = LocalContext.current
  val repository = remember { UserConfigRepository(context) }
  val isAcceptedAgreements by repository.isAcceptedAgreements.collectAsState(initial = null)
  LaunchedEffect(isAcceptedAgreements) {
    when (isAcceptedAgreements) {
      true ->
        navController.navigate(route = Screen.HomeRoot.route) {
          popUpTo(Screen.LoadingScreen.route) { inclusive = true }
        }
      false -> {
        repository.updateLocale(TH)
        repository.updateHomeViewLayout(ViewLayout.VIEW_LAYOUT_LIST)
        repository.updateHistoryViewLayout(ViewLayout.VIEW_LAYOUT_LIST)
        navController.navigate(route = Screen.OnboardingRoot.route) {
          popUpTo(Screen.LoadingScreen.route) { inclusive = true }
        }
      }
      null -> {}
    }
  }
  Scaffold {
    Box(modifier = Modifier
      .fillMaxSize()
      .padding(it), contentAlignment = Alignment.Center) {
      CircularProgressIndicator()
    }
  }
}
