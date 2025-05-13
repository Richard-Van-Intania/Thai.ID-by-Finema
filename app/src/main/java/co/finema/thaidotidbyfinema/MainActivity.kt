@file:Suppress("UsingMaterialAndMaterial3Libraries")

package co.finema.thaidotidbyfinema

import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.biometric.BiometricManager.Authenticators.BIOMETRIC_STRONG
import androidx.biometric.BiometricPrompt
import androidx.compose.material.MaterialTheme
import androidx.compose.material.lightColors
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.core.content.ContextCompat
import androidx.fragment.app.FragmentActivity
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.navigation
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import co.finema.thaidotidbyfinema.uis.CustomTypography
import co.finema.thaidotidbyfinema.uis.Screen
import co.finema.thaidotidbyfinema.uis.primaryDarkBlue
import co.finema.thaidotidbyfinema.uis.screens.LoadingScreen
import co.finema.thaidotidbyfinema.uis.screens.MainScreen
import co.finema.thaidotidbyfinema.uis.screens.home.SelectLayoutScreen
import co.finema.thaidotidbyfinema.uis.screens.onboarding.OnboardScreen
import co.finema.thaidotidbyfinema.uis.screens.onboarding.TermsScreen
import co.finema.thaidotidbyfinema.uis.screens.onboarding.WelcomeScreen
import co.finema.thaidotidbyfinema.uis.screens.passcodes.ConfirmPasscodeFullscreen
import co.finema.thaidotidbyfinema.uis.screens.passcodes.CreatePasscodeFullscreen
import co.finema.thaidotidbyfinema.uis.screens.passcodes.EnterPasscodeLoginFullscreen
import co.finema.thaidotidbyfinema.uis.screens.passcodes.EnterPasscodeTurnOffFullscreen
import co.finema.thaidotidbyfinema.uis.screens.profile.SettingsScreen
import java.util.concurrent.Executor

val biometricAuth: MutableState<Boolean?> = mutableStateOf(null)

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
                biometricAuth.value = false
              }

              override fun onAuthenticationSucceeded(result: BiometricPrompt.AuthenticationResult) {
                super.onAuthenticationSucceeded(result)
                biometricAuth.value = true
              }

              override fun onAuthenticationFailed() {
                super.onAuthenticationFailed()
                biometricAuth.value = false
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
            val navController = rememberNavController()
            val localAuth = remember { mutableStateOf(false) }
            NavHost(
                navController = navController, startDestination = Screen.LoadingScreenNav.route) {
                  composable(route = Screen.LoadingScreenNav.route) {
                    LoadingScreen(navController = navController)
                  }
                  navigation(
                      startDestination = Screen.WelcomeScreenNav.route,
                      route = Screen.OnboardingRootNav.route) {
                        composable(route = Screen.WelcomeScreenNav.route) {
                          WelcomeScreen(navController = navController)
                        }
                        composable(route = Screen.OnboardScreenNav.route) {
                          OnboardScreen(navController = navController)
                        }
                        composable(route = Screen.TermsScreenNav.route) {
                          TermsScreen(navController = navController)
                        }
                      }
                  navigation(
                      startDestination = Screen.MainScreenNav.route,
                      route = Screen.HomeRootNav.route) {
                        composable(route = Screen.MainScreenNav.route) {
                          MainScreen(navController = navController, localAuth = localAuth)
                        }
                        composable(route = Screen.CreatePasscodeFullscreenNav.route) {
                          CreatePasscodeFullscreen(navController = navController)
                        }
                        composable(
                            route = "${Screen.ConfirmPasscodeFullscreenNav.route}/{passcode}",
                            arguments = listOf(navArgument("passcode") { defaultValue = "" })) {
                                backStackEntry ->
                              val passcode = backStackEntry.arguments?.getString("passcode") ?: ""
                              ConfirmPasscodeFullscreen(
                                  navController = navController,
                                  passcode = passcode,
                                  onBiometricAuth = { biometricPrompt.authenticate(promptInfo) },
                                  localAuth = localAuth)
                            }
                        composable(route = Screen.EnterPasscodeLoginFullscreenNav.route) {
                          EnterPasscodeLoginFullscreen(
                              navController = navController,
                              onBiometricAuth = { biometricPrompt.authenticate(promptInfo) },
                              localAuth = localAuth)
                        }
                        composable(route = Screen.SelectLayoutScreenNav.route) {
                          SelectLayoutScreen(navController = navController)
                        }
                        composable(route = Screen.SettingsScreenNav.route) {
                          SettingsScreen(
                              navController = navController,
                              onBiometricAuth = { biometricPrompt.authenticate(promptInfo) })
                        }
                        composable(route = Screen.EnterPasscodeTurnOffFullscreenNav.route) {
                          EnterPasscodeTurnOffFullscreen(
                              navController = navController,
                              onBiometricAuth = { biometricPrompt.authenticate(promptInfo) })
                        }
                      }
                }
          }
    }
  }
}
