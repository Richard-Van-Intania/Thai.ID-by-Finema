package co.finema.thaidotidbyfinema.uis

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Description
import androidx.compose.material.icons.rounded.Home
import androidx.compose.material.icons.rounded.Person
import androidx.compose.ui.graphics.vector.ImageVector
import co.finema.thaidotidbyfinema.R

sealed class Screen(val route: String, val name: Int? = null, val icon: ImageVector? = null) {

  object LoadingScreen : Screen("LoadingScreen")

  object OnboardingRoot : Screen("OnboardingRoot")

  object WelcomeScreen : Screen("WelcomeScreen")

  object OnboardScreen : Screen("OnboardScreen")

  object TermsScreen : Screen("TermsScreen")

  object HomeRoot : Screen("HomeRoot")

  object MainScreen : Screen("MainScreen")

  object HomeTab : Screen("HomeTab", R.string.home, Icons.Rounded.Home)

  object HistoryTab : Screen("HistoryTab", R.string.history, Icons.Rounded.Description)

  object ProfileTab : Screen("ProfileTab", R.string.profile, Icons.Rounded.Person)

  object SelectLayoutScreen : Screen("SelectLayoutScreen")

  object CreatePasscodeFullscreen : Screen("CreatePasscodeFullscreen")

  object ConfirmPasscodeFullscreen : Screen("ConfirmPasscodeFullscreen")

  object EnterPasscodeLoginFullscreen : Screen("EnterPasscodeLoginFullscreen")

  object EnterPasscodeTurnOffFullscreen : Screen("EnterPasscodeTurnOffFullscreen")

  object CreateNewPasscodeFullscreen : Screen("CreateNewPasscodeFullscreen")

  object ConfirmNewPasscodeFullscreen : Screen("ConfirmNewPasscodeFullscreen")

  object EnterPasscodeChangeFullscreen : Screen("EnterPasscodeChangeFullscreen")

  object CreatePasscodeChangeFullscreen : Screen("CreatePasscodeChangeFullscreen")

  object ConfirmPasscodeChangeFullscreen : Screen("ConfirmPasscodeChangeFullscreen")

  object SupportScreen : Screen("SupportScreen")

  object SettingsScreen : Screen("SettingsScreen")

  object PolicyAndSafetyScreen : Screen("PolicyAndSafetyScreen")

  object LocalizationSettingsScreen : Screen("LocalizationSettingsScreen")

  object ProfileDetailsScreen : Screen("ProfileDetailsScreen")

  object ProfileEditScreen : Screen("ProfileEditScreen")

  object DocumentPlaceholderScreen : Screen("DocumentPlaceholderScreen")

  object SSSSSS : Screen("SSSSSS")
}

val bottomTabs = listOf(Screen.HomeTab, Screen.HistoryTab, Screen.ProfileTab)

// SupportScreen ProfileEditScreen
