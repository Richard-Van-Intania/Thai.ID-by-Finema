@file:Suppress("UsingMaterialAndMaterial3Libraries")

package co.finema.thaidotidbyfinema

import android.content.Context
import android.content.res.Configuration
import android.net.Uri
import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.biometric.BiometricManager.Authenticators.BIOMETRIC_STRONG
import androidx.biometric.BiometricPrompt
import androidx.compose.material.MaterialTheme
import androidx.compose.material.lightColors
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.core.content.ContextCompat
import androidx.fragment.app.FragmentActivity
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.navigation
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import co.finema.thaidotidbyfinema.databases.signatureimages.SignatureImageDatabase
import co.finema.thaidotidbyfinema.databases.signatureimages.SignatureImageViewModel
import co.finema.thaidotidbyfinema.repositories.UserConfigRepository
import co.finema.thaidotidbyfinema.uis.CustomTypography
import co.finema.thaidotidbyfinema.uis.Screen
import co.finema.thaidotidbyfinema.uis.primaryDarkBlue
import co.finema.thaidotidbyfinema.uis.screens.LoadingScreen
import co.finema.thaidotidbyfinema.uis.screens.MainScreen
import co.finema.thaidotidbyfinema.uis.screens.home.CameraScreen
import co.finema.thaidotidbyfinema.uis.screens.home.CropImageScreen
import co.finema.thaidotidbyfinema.uis.screens.home.DocumentPlaceholderScreen
import co.finema.thaidotidbyfinema.uis.screens.home.SelectLayoutScreen
import co.finema.thaidotidbyfinema.uis.screens.home.SignatureListScreen
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
import co.finema.thaidotidbyfinema.uis.screens.profile.LocalizationSettingsScreen
import co.finema.thaidotidbyfinema.uis.screens.profile.PolicyAndSafetyScreen
import co.finema.thaidotidbyfinema.uis.screens.profile.ProfileDetailsScreen
import co.finema.thaidotidbyfinema.uis.screens.profile.ProfileEditScreen
import co.finema.thaidotidbyfinema.uis.screens.profile.SettingsScreen
import co.finema.thaidotidbyfinema.uis.screens.profile.SupportScreen
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
import java.util.Locale
import java.util.concurrent.Executor

val biometricAuth: MutableState<Boolean?> = mutableStateOf(null)

fun readUserConfigBlocking(context: Context): UserConfig {
  val repository = UserConfigRepository(context)
  return runBlocking { repository.userConfigFlow.first() }
}

object LocaleHelper {
  fun updateLocale(context: Context, locale: Locale): Context {
    Locale.setDefault(locale)
    val config = Configuration(context.resources.configuration)
    config.setLocale(locale)
    return context.createConfigurationContext(config)
  }
}

class MainActivity : FragmentActivity() {
  override fun attachBaseContext(newBase: Context) {
    val localeString = readUserConfigBlocking(newBase).locale
    val locale = Locale(localeString.ifEmpty { TH })
    val context = LocaleHelper.updateLocale(newBase, locale)
    super.attachBaseContext(context)
  }

  private lateinit var executor: Executor
  private lateinit var biometricPrompt: BiometricPrompt
  private lateinit var promptInfo: BiometricPrompt.PromptInfo

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    val signatureImageDatabase = SignatureImageDatabase.getDatabase(this)
    val signatureImageDao = signatureImageDatabase.signatureImageDao()
    val signatureImageViewModel = SignatureImageViewModel(signatureImageDao)
    // more db below

    // biometric below
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
        },
      )
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
        colors = lightColors(primary = primaryDarkBlue),
        typography = CustomTypography,
      ) {
        val navController = rememberNavController()
        val localAuth = remember { mutableStateOf(false) }
        val layoutIndex = remember { mutableIntStateOf(0) }
        val placeholderFilePath0 = remember { mutableStateOf("") }
        val placeholderFilePath1 = remember { mutableStateOf("") }
        val placeholderFilePath2 = remember { mutableStateOf("") }
        val imageIndex = remember { mutableIntStateOf(0) }
        val imageUri = remember { mutableStateOf<Uri?>(null) }
        NavHost(navController = navController, startDestination = Screen.LoadingScreen.route) {
          composable(route = Screen.LoadingScreen.route) {
            LoadingScreen(navController = navController)
          }
          navigation(
            startDestination = Screen.WelcomeScreen.route,
            route = Screen.OnboardingRoot.route,
          ) {
            composable(route = Screen.WelcomeScreen.route) {
              WelcomeScreen(navController = navController)
            }
            composable(route = Screen.OnboardScreen.route) {
              OnboardScreen(navController = navController)
            }
            composable(route = Screen.TermsScreen.route) {
              TermsScreen(navController = navController)
            }
          }
          navigation(startDestination = Screen.MainScreen.route, route = Screen.HomeRoot.route) {
            composable(route = Screen.MainScreen.route) {
              MainScreen(navController = navController, localAuth = localAuth)
            }
            composable(route = Screen.CreatePasscodeFullscreen.route) {
              CreatePasscodeFullscreen(navController = navController)
            }
            composable(
              route = "${Screen.ConfirmPasscodeFullscreen.route}/{tapPasscode}",
              arguments = listOf(navArgument("tapPasscode") { defaultValue = "" }),
            ) { backStackEntry ->
              val tapPasscode = backStackEntry.arguments?.getString("tapPasscode") ?: ""
              ConfirmPasscodeFullscreen(
                navController = navController,
                tapPasscode = tapPasscode,
                onBiometricAuth = startBiometricAuth,
                localAuth = localAuth,
              )
            }
            composable(route = Screen.EnterPasscodeLoginFullscreen.route) {
              EnterPasscodeLoginFullscreen(
                navController = navController,
                onBiometricAuth = startBiometricAuth,
                localAuth = localAuth,
              )
            }
            composable(route = Screen.SelectLayoutScreen.route) {
              SelectLayoutScreen(
                navController = navController,
                layoutIndex = layoutIndex,
                placeholderFilePath0 = placeholderFilePath0,
                placeholderFilePath1 = placeholderFilePath1,
                placeholderFilePath2 = placeholderFilePath2,
              )
            }
            composable(route = Screen.DocumentPlaceholderScreen.route) {
              DocumentPlaceholderScreen(
                navController = navController,
                layoutIndex = layoutIndex,
                placeholderFilePath0 = placeholderFilePath0,
                placeholderFilePath1 = placeholderFilePath1,
                placeholderFilePath2 = placeholderFilePath2,
                imageUri = imageUri,
                imageIndex = imageIndex,
              )
            }
            composable(route = Screen.CameraScreen.route) {
              CameraScreen(navController = navController, imageUri = imageUri)
            }
            composable(route = Screen.CropImageScreen.route) {
              CropImageScreen(
                navController = navController,
                imageUri = imageUri,
                layoutIndex = layoutIndex,
                imageIndex = imageIndex,
                placeholderFilePath0 = placeholderFilePath0,
                placeholderFilePath1 = placeholderFilePath1,
                placeholderFilePath2 = placeholderFilePath2,
              )
            }
            composable(route = Screen.ProfileDetailsScreen.route) {
              ProfileDetailsScreen(navController = navController)
            }
            composable(route = Screen.ProfileEditScreen.route) {
              ProfileEditScreen(navController = navController)
            }
            composable(route = Screen.SettingsScreen.route) {
              SettingsScreen(navController = navController, onBiometricAuth = startBiometricAuth)
            }
            composable(route = Screen.EnterPasscodeTurnOffFullscreen.route) {
              EnterPasscodeTurnOffFullscreen(
                navController = navController,
                onBiometricAuth = startBiometricAuth,
              )
            }
            composable(route = Screen.CreateNewPasscodeFullscreen.route) {
              CreateNewPasscodeFullscreen(navController = navController)
            }
            composable(
              route = "${Screen.ConfirmNewPasscodeFullscreen.route}/{tapPasscode}",
              arguments = listOf(navArgument("tapPasscode") { defaultValue = "" }),
            ) { backStackEntry ->
              val tapPasscode = backStackEntry.arguments?.getString("tapPasscode") ?: ""
              ConfirmNewPasscodeFullscreen(navController = navController, tapPasscode = tapPasscode)
            }
            composable(route = Screen.EnterPasscodeChangeFullscreen.route) {
              EnterPasscodeChangeFullscreen(
                navController = navController,
                onBiometricAuth = startBiometricAuth,
              )
            }
            composable(route = Screen.CreatePasscodeChangeFullscreen.route) {
              CreatePasscodeChangeFullscreen(navController = navController)
            }
            composable(
              route = "${Screen.ConfirmPasscodeChangeFullscreen.route}/{tapPasscode}",
              arguments = listOf(navArgument("tapPasscode") { defaultValue = "" }),
            ) { backStackEntry ->
              val tapPasscode = backStackEntry.arguments?.getString("tapPasscode") ?: ""
              ConfirmPasscodeChangeFullscreen(
                navController = navController,
                tapPasscode = tapPasscode,
              )
            }
            composable(route = Screen.SupportScreen.route) {
              SupportScreen(navController = navController)
            }
            composable(route = Screen.PolicyAndSafetyScreen.route) {
              PolicyAndSafetyScreen(navController = navController)
            }
            composable(route = Screen.LocalizationSettingsScreen.route) {
              LocalizationSettingsScreen(navController = navController)
            }
            composable(route = Screen.SignatureListScreen.route) {
              SignatureListScreen(
                navController = navController,
                signatureImageViewModel = signatureImageViewModel,
              )
            }
          }
        }
      }
    }
  }
}
