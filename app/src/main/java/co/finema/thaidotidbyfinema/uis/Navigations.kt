package co.finema.thaidotidbyfinema.uis

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Description
import androidx.compose.material.icons.rounded.Home
import androidx.compose.material.icons.rounded.Person
import androidx.compose.ui.graphics.vector.ImageVector
import co.finema.thaidotidbyfinema.R

sealed class Screen(val route: String, val name: Int? = null, val icon: ImageVector? = null) {

    data object LoadingScreen : Screen("LoadingScreen")

    data object OnboardingRoot : Screen("OnboardingRoot")

    data object WelcomeScreen : Screen("WelcomeScreen")

    data object OnboardScreen : Screen("OnboardScreen")

    data object TermsScreen : Screen("TermsScreen")

    data object HomeRoot : Screen("HomeRoot")

    data object MainScreen : Screen("MainScreen")

    data object HomeTab : Screen("HomeTab", R.string.home, Icons.Rounded.Home)

    data object HistoryTab : Screen("HistoryTab", R.string.history, Icons.Rounded.Description)

    data object ProfileTab : Screen("ProfileTab", R.string.profile, Icons.Rounded.Person)

    data object SelectLayoutScreen : Screen("SelectLayoutScreen")

    data object CreatePasscodeFullscreen : Screen("CreatePasscodeFullscreen")

    data object ConfirmPasscodeFullscreen : Screen("ConfirmPasscodeFullscreen")

    data object EnterPasscodeLoginFullscreen : Screen("EnterPasscodeLoginFullscreen")

    data object EnterPasscodeTurnOffFullscreen : Screen("EnterPasscodeTurnOffFullscreen")

    data object CreateNewPasscodeFullscreen : Screen("CreateNewPasscodeFullscreen")

    data object ConfirmNewPasscodeFullscreen : Screen("ConfirmNewPasscodeFullscreen")

    data object EnterPasscodeChangeFullscreen : Screen("EnterPasscodeChangeFullscreen")

    data object CreatePasscodeChangeFullscreen : Screen("CreatePasscodeChangeFullscreen")

    data object ConfirmPasscodeChangeFullscreen : Screen("ConfirmPasscodeChangeFullscreen")

    data object SupportScreen : Screen("SupportScreen")

    data object SettingsScreen : Screen("SettingsScreen")

    data object PolicyAndSafetyScreen : Screen("PolicyAndSafetyScreen")

    data object LocalizationSettingsScreen : Screen("LocalizationSettingsScreen")

    data object ProfileDetailsScreen : Screen("ProfileDetailsScreen")

    data object ProfileEditScreen : Screen("ProfileEditScreen")

    data object DocumentPlaceholderScreen : Screen("DocumentPlaceholderScreen")

    data object SignatureListScreen : Screen("SignatureListScreen")

    data object SignPadScreen : Screen("SignPadScreen")

    data object CameraScreen : Screen("CameraScreen")

    data object CropImageScreen : Screen("CropImageScreen")

    data object PdfPageSelectScreen : Screen("PdfPageSelectScreen")

    data object CreateCertifiedScreen : Screen("CreateCertifiedScreen")

    data object SSSSSS : Screen("SSSSSS")
}

val bottomTabs = listOf(Screen.HomeTab, Screen.HistoryTab, Screen.ProfileTab)

// CreateCertifiedScreen
