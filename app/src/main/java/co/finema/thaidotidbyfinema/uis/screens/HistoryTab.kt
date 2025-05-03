package co.finema.thaidotidbyfinema.uis.screens

import android.content.Context
import androidx.biometric.BiometricManager
import androidx.biometric.BiometricPrompt
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.core.content.ContextCompat
import androidx.fragment.app.FragmentActivity
import androidx.navigation.NavController
import co.finema.thaidotidbyfinema.uis.whiteBG

@Composable
fun HistoryTab(navController: NavController) {
  val context = LocalContext.current
  val executor = remember { ContextCompat.getMainExecutor(context) }
  var message by remember { mutableStateOf("Not authenticated") }
  LaunchedEffect(message) { println(message) }

    val biometricManager = BiometricManager.from(context)

  Scaffold(backgroundColor = whiteBG) {
    Box(modifier = Modifier.fillMaxSize().padding(it), contentAlignment = Alignment.Center) {
      Text(
          "HistoryTab",
          modifier =
              Modifier.clickable(
                  onClick = {
                    showBiometricPrompt(
                        context = context,
                        onAuthSuccess = { message = "Authenticated successfully!" },
                        onAuthError = { error -> message = "Error: $error" })
                  }))
    }
  }
}

fun showBiometricPrompt(
    context: Context,
    onAuthSuccess: () -> Unit,
    onAuthError: (String) -> Unit
) {
  val executor = ContextCompat.getMainExecutor(context)

  val biometricPrompt =
      BiometricPrompt(
          context as FragmentActivity,
          executor,
          object : BiometricPrompt.AuthenticationCallback() {
            override fun onAuthenticationSucceeded(result: BiometricPrompt.AuthenticationResult) {
              super.onAuthenticationSucceeded(result)
              onAuthSuccess()
            }

            override fun onAuthenticationError(errorCode: Int, errString: CharSequence) {
              super.onAuthenticationError(errorCode, errString)
              onAuthError(errString.toString())
            }

            override fun onAuthenticationFailed() {
              super.onAuthenticationFailed()
              onAuthError("Authentication failed")
            }
          })

  val promptInfo =
      BiometricPrompt.PromptInfo.Builder()
          .setTitle("Biometric Authentication")
          .setSubtitle("Log in using your biometric credential")
          .setNegativeButtonText("Cancel")
          .build()

  biometricPrompt.authenticate(promptInfo)
}
