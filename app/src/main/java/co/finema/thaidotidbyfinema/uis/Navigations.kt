package co.finema.thaidotidbyfinema.uis

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.AccountCircle
import androidx.compose.material.icons.rounded.History
import androidx.compose.material.icons.rounded.Home
import androidx.compose.ui.graphics.vector.ImageVector
import co.finema.thaidotidbyfinema.R

sealed class Screen(val route: String, val name: Int? = null, val icon: ImageVector? = null) {
  object LoadingScreenKey : Screen("LoadingScreenKey")

  object WelcomeScreenKey : Screen("WelcomeScreenKey")

  object OnboardScreenKey : Screen("OnboardScreenKey")

  object TermsScreenKey : Screen("TermsScreenKey")

  object HomeTabKey : Screen("HomeTabKey", R.string.home, Icons.Rounded.Home)

  object HistoryTabKey : Screen("HistoryTabKey", R.string.history, Icons.Rounded.History)

  object ProfileTabKey : Screen("ProfileTabKey", R.string.profile, Icons.Rounded.AccountCircle)

  object SelectLayoutScreenKey : Screen("SelectLayoutScreenKey")

  object CreatePasscodeFullscreenKey : Screen("CreatePasscodeFullscreenKey")

  object ConfirmPasscodeFullscreenKey : Screen("ConfirmPasscodeFullscreenKey")

  object EnterPasscodeLoginFullscreenKey : Screen("EnterPasscodeLoginFullscreenKey")
}

val tabList = listOf(Screen.HomeTabKey, Screen.HistoryTabKey, Screen.ProfileTabKey)
