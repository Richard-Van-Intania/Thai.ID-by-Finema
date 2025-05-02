package co.finema.thaidotidbyfinema.uis.screens

import androidx.compose.animation.AnimatedContentTransitionScope
import androidx.compose.animation.EnterTransition
import androidx.compose.animation.ExitTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.BottomNavigation
import androidx.compose.material.BottomNavigationItem
import androidx.compose.material.Icon
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import co.finema.thaidotidbyfinema.repositories.UserConfigRepository
import co.finema.thaidotidbyfinema.uis.Screen
import co.finema.thaidotidbyfinema.uis.primaryDarkBlue
import co.finema.thaidotidbyfinema.uis.screens.passcodes.ConfirmPasscodeFullscreen
import co.finema.thaidotidbyfinema.uis.screens.passcodes.CreatePasscodeFullscreen
import co.finema.thaidotidbyfinema.uis.screens.passcodes.EnterPasscodeLoginFullscreen
import co.finema.thaidotidbyfinema.uis.secondaryBlueGray
import co.finema.thaidotidbyfinema.uis.tabList
import co.finema.thaidotidbyfinema.uis.white
import co.finema.thaidotidbyfinema.uis.whiteBG

const val milliSeconds = 250

@Composable
fun MainScreen() {
  val navController = rememberNavController()
  val navBackStackEntry by navController.currentBackStackEntryAsState()
  val currentRoute = navBackStackEntry?.destination?.route
  val context = LocalContext.current
  val repository = remember { UserConfigRepository(context) }
  val isAcceptedAgreements by repository.isAcceptedAgreements.collectAsState(initial = null)
  Scaffold(
      bottomBar = {
        if (currentRoute == Screen.HomeTabKey.route ||
            currentRoute == Screen.HistoryTabKey.route ||
            currentRoute == Screen.ProfileTabKey.route)
            BottomNavigation(
                modifier =
                    Modifier.height(96.dp)
                        .clip(RoundedCornerShape(topStart = 16.dp, topEnd = 16.dp)),
                elevation = 1.dp,
                backgroundColor = white) {
                  tabList.forEach {
                    BottomNavigationItem(
                        modifier = Modifier.padding(top = 8.dp),
                        icon = { Icon(imageVector = it.icon!!, contentDescription = null) },
                        label = {
                          Text(
                              text = stringResource(it.name!!),
                              color =
                                  if (currentRoute == it.route) primaryDarkBlue
                                  else secondaryBlueGray,
                              fontSize = 12.sp,
                              fontWeight =
                                  if (currentRoute == it.route) FontWeight.W700
                                  else FontWeight.W400,
                          )
                        },
                        selected = currentRoute == it.route,
                        onClick = {
                          if (currentRoute != it.route) {
                            navController.navigate(it.route) {
                              popUpTo(navController.graph.startDestinationId) { saveState = true }
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
            navController = navController,
            startDestination =
                when (isAcceptedAgreements) {
                  true -> Screen.HomeTabKey.route
                  false -> Screen.WelcomeScreenKey.route
                  null -> Screen.LoadingScreenKey.route
                },
            modifier = Modifier.padding(it),
            enterTransition = {
              slideIntoContainer(
                  AnimatedContentTransitionScope.SlideDirection.Start, tween(milliSeconds))
            },
            exitTransition = {
              slideOutOfContainer(
                  AnimatedContentTransitionScope.SlideDirection.Start, tween(milliSeconds))
            },
            popEnterTransition = {
              slideIntoContainer(
                  AnimatedContentTransitionScope.SlideDirection.End, tween(milliSeconds))
            },
            popExitTransition = {
              slideOutOfContainer(
                  AnimatedContentTransitionScope.SlideDirection.End, tween(milliSeconds))
            },
        ) {
          composable(route = Screen.LoadingScreenKey.route) {
            LoadingScreen(navController = navController)
          }
          composable(route = Screen.WelcomeScreenKey.route) {
            WelcomeScreen(navController = navController)
          }
          composable(route = Screen.OnboardScreenKey.route) {
            OnboardScreen(navController = navController)
          }
          composable(
              route = "${Screen.TermsScreenKey.route}/{hasButton}",
              arguments = listOf(navArgument("hasButton") { defaultValue = false })) {
                  backStackEntry ->
                val hasButton = backStackEntry.arguments?.getBoolean("hasButton") == true
                TermsScreen(navController = navController, hasButton = hasButton)
              }
          composable(
              route = Screen.HomeTabKey.route,
              enterTransition = { EnterTransition.None },
              exitTransition = { ExitTransition.None },
              popEnterTransition = { EnterTransition.None },
              popExitTransition = { ExitTransition.None },
          ) {
            HomeTab(navController = navController, counterState)
          }
          composable(
              route = Screen.HistoryTabKey.route,
              enterTransition = { EnterTransition.None },
              exitTransition = { ExitTransition.None },
              popEnterTransition = { EnterTransition.None },
              popExitTransition = { ExitTransition.None },
          ) {
            HistoryTab(navController = navController)
          }
          composable(
              route = Screen.ProfileTabKey.route,
              enterTransition = { EnterTransition.None },
              exitTransition = { ExitTransition.None },
              popEnterTransition = { EnterTransition.None },
              popExitTransition = { ExitTransition.None },
          ) {
            ProfileTab(navController = navController, counterState)
          }
          composable(route = Screen.SelectLayoutScreenKey.route) {
            SelectLayoutScreen(navController = navController)
          }
          composable(
              route = Screen.CreatePasscodeFullscreenKey.route,
              enterTransition = { EnterTransition.None },
              exitTransition = { ExitTransition.None },
              popEnterTransition = { EnterTransition.None },
              popExitTransition = { ExitTransition.None },
          ) {
            CreatePasscodeFullscreen(navController = navController)
          }
          composable(
              route = "${Screen.ConfirmPasscodeFullscreenKey.route}/{passcode}",
              arguments = listOf(navArgument("passcode") { defaultValue = "" }),
              enterTransition = { EnterTransition.None },
              exitTransition = { ExitTransition.None },
              popEnterTransition = { EnterTransition.None },
              popExitTransition = { ExitTransition.None },
          ) { backStackEntry ->
            val passcode = backStackEntry.arguments?.getString("passcode") ?: ""
            ConfirmPasscodeFullscreen(navController = navController, passcode = passcode)
          }
          composable(
              route = Screen.EnterPasscodeLoginFullscreenKey.route,
              enterTransition = { EnterTransition.None },
              exitTransition = { ExitTransition.None },
              popEnterTransition = { EnterTransition.None },
              popExitTransition = { ExitTransition.None },
          ) {
            EnterPasscodeLoginFullscreen(navController = navController)
          }
        }
      }
}
