@file:Suppress("UsingMaterialAndMaterial3Libraries")

package co.finema.thaidotidbyfinema.uis.screens.profile

import android.app.Activity
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import co.finema.thaidotidbyfinema.R
import co.finema.thaidotidbyfinema.repositories.UserConfigRepository
import co.finema.thaidotidbyfinema.uis.Screen
import co.finema.thaidotidbyfinema.uis.components.AppBarOptBack
import co.finema.thaidotidbyfinema.uis.white
import kotlinx.coroutines.launch

@Composable
fun LocalizationSettingsScreen(navController: NavController) {
  Scaffold(
      topBar = {
        AppBarOptBack(
            containerColor = white,
            text = stringResource(R.string.language),
            onClick = { navController.popBackStack() })
      },
      backgroundColor = white) {
        val context = LocalContext.current
        val repository = remember { UserConfigRepository(context) }
        val scope = rememberCoroutineScope()
        Column(
            modifier = Modifier.fillMaxSize().padding(it).padding(horizontal = 16.dp),
            horizontalAlignment = Alignment.CenterHorizontally) {
              Spacer(modifier = Modifier.height(32.dp))
              Text(
                  "en",
                  Modifier.clickable(
                      onClick = {
                        scope.launch {
                          repository.updateLocale("en")
                          navController.navigate(route = Screen.LoadingScreen.route)
                          (context as? Activity)?.recreate()
                        }
                      }))
              Spacer(modifier = Modifier.height(32.dp))
              Text(
                  "th",
                  Modifier.clickable(
                      onClick = {
                        scope.launch {
                          repository.updateLocale("th")
                          navController.navigate(route = Screen.LoadingScreen.route)
                          (context as? Activity)?.recreate()
                        }
                      }))
            }
      }
}
