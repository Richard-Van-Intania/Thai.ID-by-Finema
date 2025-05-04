package co.finema.thaidotidbyfinema

import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.biometric.BiometricManager.Authenticators.BIOMETRIC_STRONG
import androidx.biometric.BiometricPrompt
import androidx.compose.material.MaterialTheme
import androidx.compose.material.lightColors
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.core.content.ContextCompat
import androidx.fragment.app.FragmentActivity
import androidx.navigation.compose.rememberNavController
import co.finema.thaidotidbyfinema.uis.CustomTypography
import co.finema.thaidotidbyfinema.uis.primaryDarkBlue
import co.finema.thaidotidbyfinema.uis.screens.passcodes.ConfirmPasscodeFullscreen
import java.util.concurrent.Executor

val authenticate: MutableState<Boolean?> = mutableStateOf(null)

class MainActivity : FragmentActivity() {
  private lateinit var executor: Executor
  private lateinit var biometricPrompt: BiometricPrompt
  private lateinit var promptInfo: BiometricPrompt.PromptInfo

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    executor = ContextCompat.getMainExecutor(this)
    biometricPrompt =
        BiometricPrompt(
            this,
            executor,
            object : BiometricPrompt.AuthenticationCallback() {
              override fun onAuthenticationError(errorCode: Int, errString: CharSequence) {
                super.onAuthenticationError(errorCode, errString)
                authenticate.value = false
              }

              override fun onAuthenticationSucceeded(result: BiometricPrompt.AuthenticationResult) {
                super.onAuthenticationSucceeded(result)
                authenticate.value = true
              }

              override fun onAuthenticationFailed() {
                super.onAuthenticationFailed()
                authenticate.value = false
              }
            })
    promptInfo =
        BiometricPrompt.PromptInfo.Builder()
            .setTitle("Biometric login")
            .setSubtitle("Please start yours biometric")
            .setAllowedAuthenticators(BIOMETRIC_STRONG)
            .setNegativeButtonText("Cancel")
            .build()
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
            //            ProfileTab(
            //                navController = navController,
            //                onClick = { biometricPrompt.authenticate(promptInfo) })

            LaunchedEffect(authenticate.value) { println(authenticate.value) }
            ConfirmPasscodeFullscreen(
                navController = navController,
                passcode = "123456",
                onEnableBiometric = { biometricPrompt.authenticate(promptInfo) })
          }
    }
  }
}
