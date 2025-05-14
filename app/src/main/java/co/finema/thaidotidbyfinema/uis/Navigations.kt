package co.finema.thaidotidbyfinema.uis

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Description
import androidx.compose.material.icons.rounded.Home
import androidx.compose.material.icons.rounded.Person
import androidx.compose.ui.graphics.vector.ImageVector
import co.finema.thaidotidbyfinema.R

sealed class Screen(val route: String, val name: Int? = null, val icon: ImageVector? = null) {

  object LoadingScreenNav : Screen("LoadingScreenNav")

  object OnboardingRootNav : Screen("OnboardingRootNav")

  object WelcomeScreenNav : Screen("WelcomeScreenNav")

  object OnboardScreenNav : Screen("OnboardScreenNav")

  object TermsScreenNav : Screen("TermsScreenNav")

  object HomeRootNav : Screen("HomeRootNav")

  object MainScreenNav : Screen("MainScreenNav")

  object HomeTabNav : Screen("HomeTabNav", R.string.home, Icons.Rounded.Home)

  object HistoryTabNav : Screen("HistoryTabNav", R.string.history, Icons.Rounded.Description)

  object ProfileTabNav : Screen("ProfileTabNav", R.string.profile, Icons.Rounded.Person)

  object SelectLayoutScreenNav : Screen("SelectLayoutScreenNav")

  object CreatePasscodeFullscreenNav : Screen("CreatePasscodeFullscreenNav")

  object ConfirmPasscodeFullscreenNav : Screen("ConfirmPasscodeFullscreenNav")

  object EnterPasscodeLoginFullscreenNav : Screen("EnterPasscodeLoginFullscreenNav")

  object EnterPasscodeTurnOffFullscreenNav : Screen("EnterPasscodeTurnOffFullscreenNav")

  object SSSSSS : Screen("SSSSSS")

  object SettingsScreenNav : Screen("SettingsScreenNav")
}

val bottomTabs = listOf(Screen.HomeTabNav, Screen.HistoryTabNav, Screen.ProfileTabNav)
