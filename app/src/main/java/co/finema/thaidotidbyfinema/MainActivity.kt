package co.finema.thaidotidbyfinema

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.material.MaterialTheme
import androidx.compose.material.lightColors
import androidx.navigation.compose.rememberNavController
import co.finema.thaidotidbyfinema.uis.CustomTypography
import co.finema.thaidotidbyfinema.uis.primaryDarkBlue
import co.finema.thaidotidbyfinema.uis.screens.passcodes.CreatePasscodeFullscreen

class MainActivity : ComponentActivity() {
  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    enableEdgeToEdge()
    setContent {
      MaterialTheme(
          colors = lightColors(primary = primaryDarkBlue), typography = CustomTypography) {
            //            MainScreen()
            val navController = rememberNavController()
            //            DocumentPlaceholderScreen(
            //                navController = navController, documentLayout =
            // DocumentLayout.FULL_A4)

            //            SelectLayoutScreen(navController = navController)
            CreatePasscodeFullscreen(navController = navController)
          }
    }
  }
}
