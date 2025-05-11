package co.finema.thaidotidbyfinema

import android.os.Bundle
import androidx.activity.compose.BackHandler
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.biometric.BiometricManager.Authenticators.BIOMETRIC_STRONG
import androidx.biometric.BiometricPrompt
import androidx.compose.animation.EnterTransition
import androidx.compose.animation.ExitTransition
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.BottomNavigation
import androidx.compose.material.BottomNavigationItem
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.lightColors
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.content.ContextCompat
import androidx.fragment.app.FragmentActivity
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.navigation
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import co.finema.thaidotidbyfinema.repositories.UserConfigRepository
import co.finema.thaidotidbyfinema.uis.CustomTypography
import co.finema.thaidotidbyfinema.uis.Screen
import co.finema.thaidotidbyfinema.uis.bottomTabs
import co.finema.thaidotidbyfinema.uis.primaryDarkBlue
import co.finema.thaidotidbyfinema.uis.screens.HistoryTab
import co.finema.thaidotidbyfinema.uis.screens.HomeTab
import co.finema.thaidotidbyfinema.uis.screens.LoadingScreen
import co.finema.thaidotidbyfinema.uis.screens.OnboardScreen
import co.finema.thaidotidbyfinema.uis.screens.ProfileTab
import co.finema.thaidotidbyfinema.uis.screens.SelectLayoutScreen
import co.finema.thaidotidbyfinema.uis.screens.TermsScreen
import co.finema.thaidotidbyfinema.uis.screens.WelcomeScreen
import co.finema.thaidotidbyfinema.uis.screens.passcodes.ConfirmPasscodeFullscreen
import co.finema.thaidotidbyfinema.uis.screens.passcodes.CreatePasscodeFullscreen
import co.finema.thaidotidbyfinema.uis.screens.passcodes.EnterPasscodeLoginFullscreen
import co.finema.thaidotidbyfinema.uis.screens.rememberCounterState
import co.finema.thaidotidbyfinema.uis.secondaryBlueGray
import co.finema.thaidotidbyfinema.uis.white
import co.finema.thaidotidbyfinema.uis.whiteBG
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
            Root(onBiometricAuth = { biometricPrompt.authenticate(promptInfo) })
          }
    }
  }
}

@Composable
fun Root(onBiometricAuth: () -> Unit) {
  val isLocalAuth = remember { mutableStateOf(false) }
  LaunchedEffect(isLocalAuth.value) { println(isLocalAuth.value) }
  val navController = rememberNavController()
  NavHost(navController = navController, startDestination = Screen.LoadingScreenNav.route) {
    composable(route = Screen.LoadingScreenNav.route) {
      LoadingScreen(navController = navController)
    }
    navigation(
        startDestination = Screen.WelcomeScreenNav.route, route = Screen.OnboardingRootNav.route) {
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
    navigation(startDestination = Screen.MainScreenNav.route, route = Screen.HomeRootNav.route) {
      composable(route = Screen.MainScreenNav.route) {
        MainScreen(navController = navController, isLocalAuth = isLocalAuth)
      }
      composable(route = Screen.CreatePasscodeFullscreenNav.route) {
        CreatePasscodeFullscreen(navController = navController)
      }
      composable(
          route = "${Screen.ConfirmPasscodeFullscreenNav.route}/{passcode}",
          arguments = listOf(navArgument("passcode") { defaultValue = "" })) { backStackEntry ->
            val passcode = backStackEntry.arguments?.getString("passcode") ?: ""
            ConfirmPasscodeFullscreen(
                navController = navController,
                passcode = passcode,
                onBiometricAuth = onBiometricAuth,
                isLocalAuth = isLocalAuth)
          }
      composable(route = Screen.EnterPasscodeLoginFullscreenNav.route) {
        EnterPasscodeLoginFullscreen(
            navController = navController,
            onBiometricAuth = onBiometricAuth,
            isLocalAuth = isLocalAuth)
      }
      composable(route = Screen.SelectLayoutScreenNav.route) {
        SelectLayoutScreen(navController = navController)
      }
    }
  }
}

@Composable
fun MainScreen(navController: NavHostController, isLocalAuth: MutableState<Boolean>) {
  BackHandler(enabled = true) {}
  val context = LocalContext.current
  val repository = remember { UserConfigRepository(context) }
  val passcodeAsked by repository.passcodeAsked.collectAsState(initial = null)
  LaunchedEffect(passcodeAsked) {
    if (passcodeAsked == false) navController.navigate(Screen.CreatePasscodeFullscreenNav.route)
  }
  val passcode by repository.passcode.collectAsState(initial = "")
  LaunchedEffect(passcode, isLocalAuth.value) {
    if (passcode.isNotEmpty() && !isLocalAuth.value)
        navController.navigate(Screen.EnterPasscodeLoginFullscreenNav.route)
  }
  val tabController = rememberNavController()
  Scaffold(
      bottomBar = {
        BottomNavigation(
            modifier =
                Modifier.height(96.dp).clip(RoundedCornerShape(topStart = 16.dp, topEnd = 16.dp)),
            elevation = 1.dp,
            backgroundColor = white) {
              bottomTabs.forEach {
                val navBackStackEntry by tabController.currentBackStackEntryAsState()
                val currentRoute = navBackStackEntry?.destination?.route
                BottomNavigationItem(
                    modifier = Modifier.padding(top = 8.dp),
                    icon = { Icon(imageVector = it.icon!!, contentDescription = null) },
                    label = {
                      Text(
                          text = stringResource(it.name!!),
                          color =
                              if (currentRoute == it.route) primaryDarkBlue else secondaryBlueGray,
                          fontSize = 12.sp,
                          fontWeight =
                              if (currentRoute == it.route) FontWeight.W700 else FontWeight.W400,
                      )
                    },
                    selected = currentRoute == it.route,
                    onClick = {
                      if (currentRoute != it.route) {
                        tabController.navigate(it.route) {
                          popUpTo(tabController.graph.startDestinationId) { saveState = true }
                          launchSingleTop = true
                          restoreState = true
                        }
                      }
                    },
                    selectedContentColor = primaryDarkBlue,
                    unselectedContentColor = secondaryBlueGray)
              }
            }
      },
      backgroundColor = whiteBG) {
        val counterState = rememberCounterState()
        NavHost(
            navController = tabController,
            startDestination = Screen.HomeTabNav.route,
            modifier = Modifier.padding(it),
        ) {
          composable(
              route = Screen.HomeTabNav.route,
              enterTransition = { EnterTransition.None },
              exitTransition = { ExitTransition.None },
              popEnterTransition = { EnterTransition.None },
              popExitTransition = { ExitTransition.None },
          ) {
            HomeTab(navController = navController, counterState)
          }
          composable(
              route = Screen.HistoryTabNav.route,
              enterTransition = { EnterTransition.None },
              exitTransition = { ExitTransition.None },
              popEnterTransition = { EnterTransition.None },
              popExitTransition = { ExitTransition.None },
          ) {
            HistoryTab(navController = navController)
          }
          composable(
              route = Screen.ProfileTabNav.route,
              enterTransition = { EnterTransition.None },
              exitTransition = { ExitTransition.None },
              popEnterTransition = { EnterTransition.None },
              popExitTransition = { ExitTransition.None },
          ) {
            ProfileTab(navController = navController, onClick = {})
          }
        }
      }
}
