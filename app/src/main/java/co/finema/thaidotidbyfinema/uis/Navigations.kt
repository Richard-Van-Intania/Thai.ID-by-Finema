package co.finema.thaidotidbyfinema.uis

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.AccountCircle
import androidx.compose.material.icons.rounded.History
import androidx.compose.material.icons.rounded.Home
import androidx.compose.ui.graphics.vector.ImageVector
import co.finema.thaidotidbyfinema.R

sealed class Screen(val route: String, val name: Int? = null, val icon: ImageVector? = null) {

  object LoadingScreenNav : Screen("LoadingScreenNav")

  object OnboardingRootNav : Screen("OnboardingRootNav")

  object HomeRootNav : Screen("HomeRootNav")

  object WelcomeScreenNav : Screen("WelcomeScreenNav")

  object OnboardScreenNav : Screen("OnboardScreenNav")

  object TermsScreenNav : Screen("TermsScreenNav")

  object MainScreenNav : Screen("MainScreenNav")

  object HomeTabNav : Screen("HomeTabNav", R.string.home, Icons.Rounded.Home)

  object HistoryTabNav : Screen("HistoryTabNav", R.string.history, Icons.Rounded.History)

  object ProfileTabNav : Screen("ProfileTabNav", R.string.profile, Icons.Rounded.AccountCircle)

  object SelectLayoutScreenNav : Screen("SelectLayoutScreenNav")

  object EnterPasscodeLoginFullscreenNav : Screen("EnterPasscodeLoginFullscreenNav")

  object CreatePasscodeFullscreenNav : Screen("CreatePasscodeFullscreenNav")

  object ConfirmPasscodeFullscreenNav : Screen("ConfirmPasscodeFullscreenNav")
}

val bottomTabs = listOf(Screen.HomeTabNav, Screen.HistoryTabNav, Screen.ProfileTabNav)
