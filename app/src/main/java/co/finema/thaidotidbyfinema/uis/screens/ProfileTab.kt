package co.finema.thaidotidbyfinema.uis.screens

import androidx.biometric.BiometricManager
import androidx.biometric.BiometricManager.Authenticators.BIOMETRIC_STRONG
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.NavController
import co.finema.thaidotidbyfinema.authenticate
import co.finema.thaidotidbyfinema.uis.whiteBG

@Composable
fun ProfileTab(navController: NavController, onClick: () -> Unit) {
  val context = LocalContext.current
  val biometricManager = BiometricManager.from(context)
  LaunchedEffect(authenticate.value) { println(authenticate.value) }
  Scaffold(backgroundColor = whiteBG) {
    Box(modifier = Modifier.fillMaxSize().padding(it), contentAlignment = Alignment.Center) {
      Text(
          "ProfileTab",
          modifier =
              Modifier.clickable(
                  onClick = {
                    //                    delay(1.seconds)

                    when (biometricManager.canAuthenticate(BIOMETRIC_STRONG)) {
                      BiometricManager.BIOMETRIC_SUCCESS -> {
                        //                        Log.d("MY_APP_TAG", "App can authenticate using
                        // biometrics.")
                      }
                      BiometricManager.BIOMETRIC_ERROR_NO_HARDWARE -> {
                        //                        Log.e("MY_APP_TAG", "No biometric features
                        // available on this device.")
                      }
                      BiometricManager.BIOMETRIC_ERROR_HW_UNAVAILABLE -> {
                        //                        Log.e("MY_APP_TAG", "Biometric features are
                        // currently unavailable.")
                      }
                      BiometricManager.BIOMETRIC_ERROR_NONE_ENROLLED -> {
                        //                        Log.e("MY_APP_TAG", "Biometric features are
                        // currently unavailable.")
                      }

                      BiometricManager.BIOMETRIC_ERROR_SECURITY_UPDATE_REQUIRED -> {
                        TODO()
                      }

                      BiometricManager.BIOMETRIC_ERROR_UNSUPPORTED -> {
                        TODO()
                      }

                      BiometricManager.BIOMETRIC_STATUS_UNKNOWN -> {
                        TODO()
                      }
                    }
                    //
                  }))
    }
  }
}
