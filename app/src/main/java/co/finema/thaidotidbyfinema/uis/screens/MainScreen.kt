@file:Suppress("UsingMaterialAndMaterial3Libraries")

package co.finema.thaidotidbyfinema.uis.screens

import androidx.activity.compose.BackHandler
import androidx.compose.animation.EnterTransition
import androidx.compose.animation.ExitTransition
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Icon
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material3.NavigationBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableIntState
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import co.finema.thaidotidbyfinema.cornerRadius
import co.finema.thaidotidbyfinema.databases.layouthistories.LayoutHistoryViewModel
import co.finema.thaidotidbyfinema.repositories.UserConfigRepository
import co.finema.thaidotidbyfinema.uis.Screen
import co.finema.thaidotidbyfinema.uis.bottomTabs
import co.finema.thaidotidbyfinema.uis.primaryDarkBlue
import co.finema.thaidotidbyfinema.uis.screens.history.HistoryTab
import co.finema.thaidotidbyfinema.uis.screens.history.rememberCounterState
import co.finema.thaidotidbyfinema.uis.screens.home.HomeTab
import co.finema.thaidotidbyfinema.uis.screens.profile.ProfileTab
import co.finema.thaidotidbyfinema.uis.secondaryBlueGray
import co.finema.thaidotidbyfinema.uis.white
import co.finema.thaidotidbyfinema.uis.whiteBG

// @Composable
// fun MainScreen() {
//  val navController = rememberNavController()
//  val navBackStackEntry by navController.currentBackStackEntryAsState()
//  val currentRoute = navBackStackEntry?.destination?.route
//  val context = LocalContext.current
//  val repository = remember { UserConfigRepository(context) }
//  val isAcceptedAgreements by repository.isAcceptedAgreements.collectAsState(initial = null)
//  Scaffold(
//      bottomBar = {
//        if (currentRoute == Screen.HomeTabKey.route ||
//            currentRoute == Screen.HistoryTabKey.route ||
//            currentRoute == Screen.ProfileTabKey.route)
//            BottomNavigation(
//                modifier =
//                    Modifier.height(96.dp)
//                        .clip(RoundedCornerShape(topStart = 16.dp, topEnd = 16.dp)),
//                elevation = 1.dp,
//                backgroundColor = white) {
//                  bottomTabs.forEach {
//                    BottomNavigationItem(
//                        modifier = Modifier.padding(top = 8.dp),
//                        icon = { Icon(imageVector = it.icon!!, contentDescription = null) },
//                        label = {
//                          Text(
//                              text = stringResource(it.name!!),
//                              color =
//                                  if (currentRoute == it.route) primaryDarkBlue
//                                  else secondaryBlueGray,
//                              fontSize = 12.sp,
//                              fontWeight =
//                                  if (currentRoute == it.route) FontWeight.W700
//                                  else FontWeight.W400,
//                          )
//                        },
//                        selected = currentRoute == it.route,
//                        onClick = {
//                          if (currentRoute != it.route) {
//                            navController.navigate(it.route) {
//                              popUpTo(navController.graph.startDestinationId) { saveState = true }
//                              launchSingleTop = true
//                              restoreState = true
//                            }
//                          }
//                        },
//                        selectedContentColor = primaryDarkBlue,
//                        unselectedContentColor = secondaryBlueGray)
//                  }
//                }
//      },
//      backgroundColor = whiteBG) {
//        val counterState = rememberCounterState()
//        NavHost(
//            navController = navController,
//            startDestination =
//                when (isAcceptedAgreements) {
//                  true -> Screen.HomeTabKey.route
//                  false -> Screen.WelcomeScreenKey.route
//                  null -> Screen.LoadingScreenKey.route
//                },
//            modifier = Modifier.padding(it),
//            enterTransition = {
//              slideIntoContainer(
//                  AnimatedContentTransitionScope.SlideDirection.Start, tween(milliSeconds))
//            },
//            exitTransition = {
//              slideOutOfContainer(
//                  AnimatedContentTransitionScope.SlideDirection.Start, tween(milliSeconds))
//            },
//            popEnterTransition = {
//              slideIntoContainer(
//                  AnimatedContentTransitionScope.SlideDirection.End, tween(milliSeconds))
//            },
//            popExitTransition = {
//              slideOutOfContainer(
//                  AnimatedContentTransitionScope.SlideDirection.End, tween(milliSeconds))
//            },
//        ) {
//          composable(route = Screen.LoadingScreenKey.route) {
//            LoadingScreen(navController = navController)
//          }
//          composable(route = Screen.WelcomeScreenKey.route) {
//            WelcomeScreen(navController = navController)
//          }
//          composable(route = Screen.OnboardScreenKey.route) {
//            OnboardScreen(navController = navController)
//          }
//          composable(
//              route = "${Screen.TermsScreenKey.route}/{hasButton}",
//              arguments = listOf(navArgument("hasButton") { defaultValue = false })) {
//                  backStackEntry ->
//                val hasButton = backStackEntry.arguments?.getBoolean("hasButton") == true
//                TermsScreen(navController = navController, hasButton = hasButton)
//              }
//          composable(
//              route = Screen.HomeTabKey.route,
//              enterTransition = { EnterTransition.None },
//              exitTransition = { ExitTransition.None },
//              popEnterTransition = { EnterTransition.None },
//              popExitTransition = { ExitTransition.None },
//          ) {
//            HomeTab(navController = navController, counterState)
//          }
//          composable(
//              route = Screen.HistoryTabKey.route,
//              enterTransition = { EnterTransition.None },
//              exitTransition = { ExitTransition.None },
//              popEnterTransition = { EnterTransition.None },
//              popExitTransition = { ExitTransition.None },
//          ) {
//            //            HistoryTab(navController = navController)
//          }
//          composable(
//              route = Screen.ProfileTabKey.route,
//              enterTransition = { EnterTransition.None },
//              exitTransition = { ExitTransition.None },
//              popEnterTransition = { EnterTransition.None },
//              popExitTransition = { ExitTransition.None },
//          ) {
//            //            ProfileTab(navController = navController, counterState)
//            ProfileTab(navController = navController, onClick = {})
//          }
//          composable(route = Screen.SelectLayoutScreenKey.route) {
//            SelectLayoutScreen(navController = navController)
//          }
//          composable(
//              route = Screen.CreatePasscodeFullscreenKey.route,
//              enterTransition = { EnterTransition.None },
//              exitTransition = { ExitTransition.None },
//              popEnterTransition = { EnterTransition.None },
//              popExitTransition = { ExitTransition.None },
//          ) {
//            CreatePasscodeFullscreen(navController = navController)
//          }
//          composable(
//              route = "${Screen.ConfirmPasscodeFullscreenKey.route}/{passcode}",
//              arguments = listOf(navArgument("passcode") { defaultValue = "" }),
//              enterTransition = { EnterTransition.None },
//              exitTransition = { ExitTransition.None },
//              popEnterTransition = { EnterTransition.None },
//              popExitTransition = { ExitTransition.None },
//          ) { backStackEntry ->
//            val passcode = backStackEntry.arguments?.getString("passcode") ?: ""
//            ConfirmPasscodeFullscreen(
//                navController = navController, passcode = passcode, onEnableBiometric = {})
//          }
//          composable(
//              route = Screen.EnterPasscodeLoginFullscreenKey.route,
//              enterTransition = { EnterTransition.None },
//              exitTransition = { ExitTransition.None },
//              popEnterTransition = { EnterTransition.None },
//              popExitTransition = { ExitTransition.None },
//          ) {
//            EnterPasscodeLoginFullscreen(navController = navController, onAuthBiometric = {})
//          }
//        }
//      }
// }

@Composable
fun MainScreen(navController: NavHostController, localAuth: MutableState<Boolean>, layoutHistoryViewModel: LayoutHistoryViewModel, layoutHistoryId: MutableIntState) {
    BackHandler(enabled = true) {}
    val context = LocalContext.current
    val repository = remember { UserConfigRepository(context) }
    val passcodeAsked by repository.passcodeAsked.collectAsState(initial = null)
    LaunchedEffect(passcodeAsked) { if (passcodeAsked == false) navController.navigate(Screen.CreatePasscodeFullscreen.route) }
    val passcode by repository.passcode.collectAsState(initial = "")
    LaunchedEffect(passcode, localAuth.value) {
        if (passcode.isNotEmpty() && !localAuth.value) {
            navController.navigate(Screen.EnterPasscodeLoginFullscreen.route)
            //                  navController.navigate(Screen.ProfileEditScreen.route)
        }
    }
    val tabController = rememberNavController()
    Scaffold(
        bottomBar = {
            NavigationBar(modifier = Modifier.clip(RoundedCornerShape(topStart = cornerRadius, topEnd = cornerRadius)), containerColor = white) {
                val navBackStackEntry by tabController.currentBackStackEntryAsState()
                val currentTab = navBackStackEntry?.destination?.route
                bottomTabs.forEach {
                    Column(
                        modifier =
                            Modifier
                                .weight(1f)
                                .clickable(
                                    onClick = {
                                        if (currentTab != it.route) {
                                            tabController.navigate(it.route) {
                                                popUpTo(tabController.graph.startDestinationId) { saveState = true }
                                                launchSingleTop = true
                                                restoreState = true
                                            }
                                        }
                                    },
                                    indication = null,
                                    interactionSource = remember { MutableInteractionSource() },
                                          ),
                        horizontalAlignment = Alignment.CenterHorizontally,
                    ) {
                        Icon(imageVector = it.icon!!, contentDescription = null, tint = if (currentTab == it.route) primaryDarkBlue else secondaryBlueGray)
                        Box(modifier = Modifier.height(24.dp), contentAlignment = Alignment.Center) {
                            Text(
                                text = stringResource(it.name!!),
                                color = if (currentTab == it.route) primaryDarkBlue else secondaryBlueGray,
                                fontSize = 12.sp,
                                fontWeight = if (currentTab == it.route) FontWeight.W700 else FontWeight.W400,
                            )
                        }
                    }
                }
            }
        },
        backgroundColor = whiteBG,
    ) {
        val counterState = rememberCounterState()
        NavHost(modifier = Modifier.padding(it), navController = tabController, startDestination = Screen.HomeTab.route) {
            composable(
                route = Screen.HomeTab.route,
                enterTransition = { EnterTransition.None },
                exitTransition = { ExitTransition.None },
                popEnterTransition = { EnterTransition.None },
                popExitTransition = { ExitTransition.None },
            ) {
                HomeTab(navController = navController, layoutHistoryId = layoutHistoryId, layoutHistoryViewModel = layoutHistoryViewModel)
            }
            composable(
                route = Screen.HistoryTab.route,
                enterTransition = { EnterTransition.None },
                exitTransition = { ExitTransition.None },
                popEnterTransition = { EnterTransition.None },
                popExitTransition = { ExitTransition.None },
            ) {
                HistoryTab(navController = navController, counterState = counterState)
            }
            composable(
                route = Screen.ProfileTab.route,
                enterTransition = { EnterTransition.None },
                exitTransition = { ExitTransition.None },
                popEnterTransition = { EnterTransition.None },
                popExitTransition = { ExitTransition.None },
            ) {
                ProfileTab(navController = navController)
            }
        }
    }
}
