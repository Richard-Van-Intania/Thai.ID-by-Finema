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
import co.finema.thaidotidbyfinema.uis.screens.passcodes.ConfirmNewPasscodeFullscreen
import co.finema.thaidotidbyfinema.uis.screens.passcodes.ConfirmPasscodeChangeFullscreen
import co.finema.thaidotidbyfinema.uis.screens.passcodes.ConfirmPasscodeFullscreen
import co.finema.thaidotidbyfinema.uis.screens.passcodes.CreateNewPasscodeFullscreen
import co.finema.thaidotidbyfinema.uis.screens.passcodes.CreatePasscodeChangeFullscreen
import co.finema.thaidotidbyfinema.uis.screens.passcodes.CreatePasscodeFullscreen
import co.finema.thaidotidbyfinema.uis.screens.passcodes.EnterPasscodeChangeFullscreen
import co.finema.thaidotidbyfinema.uis.screens.passcodes.EnterPasscodeLoginFullscreen
import co.finema.thaidotidbyfinema.uis.screens.passcodes.EnterPasscodeTurnOffFullscreen
import co.finema.thaidotidbyfinema.uis.screens.profile.SettingsScreen
import co.finema.thaidotidbyfinema.uis.screens.profile.SupportScreen
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
    val startBiometricAuth = { biometricPrompt.authenticate(promptInfo) }
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
                            route = "${Screen.ConfirmPasscodeFullscreenNav.route}/{tapPasscode}",
                            arguments = listOf(navArgument("tapPasscode") { defaultValue = "" })) {
                                backStackEntry ->
                              val tapPasscode =
                                  backStackEntry.arguments?.getString("tapPasscode") ?: ""
                              ConfirmPasscodeFullscreen(
                                  navController = navController,
                                  tapPasscode = tapPasscode,
                                  onBiometricAuth = startBiometricAuth,
                                  localAuth = localAuth)
                            }
                        composable(route = Screen.EnterPasscodeLoginFullscreenNav.route) {
                          EnterPasscodeLoginFullscreen(
                              navController = navController,
                              onBiometricAuth = startBiometricAuth,
                              localAuth = localAuth)
                        }
                        composable(route = Screen.SelectLayoutScreenNav.route) {
                          SelectLayoutScreen(navController = navController)
                        }
                        composable(route = Screen.SettingsScreenNav.route) {
                          SettingsScreen(
                              navController = navController, onBiometricAuth = startBiometricAuth)
                        }
                        composable(route = Screen.EnterPasscodeTurnOffFullscreenNav.route) {
                          EnterPasscodeTurnOffFullscreen(
                              navController = navController, onBiometricAuth = startBiometricAuth)
                        }
                        composable(route = Screen.CreateNewPasscodeFullscreenNav.route) {
                          CreateNewPasscodeFullscreen(navController = navController)
                        }
                        composable(
                            route = "${Screen.ConfirmNewPasscodeFullscreenNav.route}/{tapPasscode}",
                            arguments = listOf(navArgument("tapPasscode") { defaultValue = "" })) {
                                backStackEntry ->
                              val tapPasscode =
                                  backStackEntry.arguments?.getString("tapPasscode") ?: ""
                              ConfirmNewPasscodeFullscreen(
                                  navController = navController, tapPasscode = tapPasscode)
                            }
                        composable(route = Screen.EnterPasscodeChangeFullscreenNav.route) {
                          EnterPasscodeChangeFullscreen(
                              navController = navController, onBiometricAuth = startBiometricAuth)
                        }
                        composable(route = Screen.CreatePasscodeChangeFullscreenNav.route) {
                          CreatePasscodeChangeFullscreen(navController = navController)
                        }
                        composable(
                            route =
                                "${Screen.ConfirmPasscodeChangeFullscreenNav.route}/{tapPasscode}",
                            arguments = listOf(navArgument("tapPasscode") { defaultValue = "" })) {
                                backStackEntry ->
                              val tapPasscode =
                                  backStackEntry.arguments?.getString("tapPasscode") ?: ""
                              ConfirmPasscodeChangeFullscreen(
                                  navController = navController, tapPasscode = tapPasscode)
                            }
                        composable(route = Screen.SupportScreenNav.route) {
                          SupportScreen(navController = navController)
                        }
                      }
                }
          }
    }
  }
}
