@file:Suppress("UsingMaterialAndMaterial3Libraries")

package co.finema.thaidotidbyfinema.uis.screens.passcodes

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.rounded.Backspace
import androidx.compose.material.icons.rounded.Fingerprint
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import co.finema.thaidotidbyfinema.R
import co.finema.thaidotidbyfinema.biometricAuth
import co.finema.thaidotidbyfinema.repositories.UserConfigRepository
import co.finema.thaidotidbyfinema.uis.components.ErrorDialog
import co.finema.thaidotidbyfinema.uis.primaryBlack
import co.finema.thaidotidbyfinema.uis.white
import co.finema.thaidotidbyfinema.verifyPasscode
import kotlin.math.roundToInt

@Composable
fun EnterPasscodeLoginFullscreen(
    navController: NavController,
    onBiometricAuth: () -> Unit,
    isLocalAuth: MutableState<Boolean>
) {
  BackHandler(enabled = true) {}
  LaunchedEffect(Unit) { biometricAuth.value = null }
  var passAuth by remember { mutableStateOf(false) }
  LaunchedEffect(passAuth) {
    if (passAuth) {
      isLocalAuth.value = true
      navController.popBackStack()
    }
  }
  var showErrorsDialog by remember { mutableStateOf(false) }
  if (showErrorsDialog) {
    ErrorDialog(
        text = stringResource(R.string.unable_use_biometrics),
        onClick = { showErrorsDialog = false })
  }
  LaunchedEffect(biometricAuth.value) {
    when (biometricAuth.value) {
      true -> {
        passAuth = true
      }
      false -> {
        showErrorsDialog = true
        biometricAuth.value = null
      }
      null -> {}
    }
  }
  Scaffold {
    Column(
        modifier = Modifier.fillMaxSize().padding(it),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally) {
          val context = LocalContext.current
          val repository = remember { UserConfigRepository(context) }
          val scope = rememberCoroutineScope()
          var confirmPasscode by remember { mutableStateOf("") }
          val shakeController = remember { ShakeController(scope) }
          val passcode by repository.passcode.collectAsState(initial = "")
          val salt by repository.salt.collectAsState(initial = "")
          val useBiometric by repository.useBiometric.collectAsState(initial = false)
          LaunchedEffect(confirmPasscode) {
            if (confirmPasscode.length == 6) {
              if (verifyPasscode(password = confirmPasscode, storedHash = passcode, salt = salt)) {
                passAuth = true
              } else {
                shakeController.triggerShake()
                confirmPasscode = ""
              }
            }
          }
          Text(
              text = stringResource(R.string.enter_pin),
              color = primaryBlack,
              fontSize = 24.sp,
              fontWeight = FontWeight.W700)
          Spacer(modifier = Modifier.height(48.dp))
          Row(
              modifier =
                  Modifier.fillMaxWidth().offset {
                    IntOffset(shakeController.offset.value.roundToInt(), 0)
                  },
              horizontalArrangement = Arrangement.Center,
              verticalAlignment = Alignment.CenterVertically) {
                if (confirmPasscode.isEmpty()) OutlinedDot() else FilledDot()
                Spacer(modifier = Modifier.width(24.dp))
                if (confirmPasscode.length < 2) OutlinedDot() else FilledDot()
                Spacer(modifier = Modifier.width(24.dp))
                if (confirmPasscode.length < 3) OutlinedDot() else FilledDot()
                Spacer(modifier = Modifier.width(24.dp))
                if (confirmPasscode.length < 4) OutlinedDot() else FilledDot()
                Spacer(modifier = Modifier.width(24.dp))
                if (confirmPasscode.length < 5) OutlinedDot() else FilledDot()
                Spacer(modifier = Modifier.width(24.dp))
                if (confirmPasscode.length < 6) OutlinedDot() else FilledDot()
              }
          Spacer(modifier = Modifier.height(48.dp))
          Row(
              modifier = Modifier.fillMaxWidth(),
              horizontalArrangement = Arrangement.Center,
              verticalAlignment = Alignment.CenterVertically) {
                PasscodeButton(
                    text = "1",
                    onClick = { if (confirmPasscode.length < 6) confirmPasscode += "1" })
                Spacer(modifier = Modifier.width(32.dp))
                PasscodeButton(
                    text = "2",
                    onClick = { if (confirmPasscode.length < 6) confirmPasscode += "2" })
                Spacer(modifier = Modifier.width(32.dp))
                PasscodeButton(
                    text = "3",
                    onClick = { if (confirmPasscode.length < 6) confirmPasscode += "3" })
              }
          Spacer(modifier = Modifier.height(32.dp))
          Row(
              modifier = Modifier.fillMaxWidth(),
              horizontalArrangement = Arrangement.Center,
              verticalAlignment = Alignment.CenterVertically) {
                PasscodeButton(
                    text = "4",
                    onClick = { if (confirmPasscode.length < 6) confirmPasscode += "4" })
                Spacer(modifier = Modifier.width(32.dp))
                PasscodeButton(
                    text = "5",
                    onClick = { if (confirmPasscode.length < 6) confirmPasscode += "5" })
                Spacer(modifier = Modifier.width(32.dp))
                PasscodeButton(
                    text = "6",
                    onClick = { if (confirmPasscode.length < 6) confirmPasscode += "6" })
              }
          Spacer(modifier = Modifier.height(32.dp))
          Row(
              modifier = Modifier.fillMaxWidth(),
              horizontalArrangement = Arrangement.Center,
              verticalAlignment = Alignment.CenterVertically) {
                PasscodeButton(
                    text = "7",
                    onClick = { if (confirmPasscode.length < 6) confirmPasscode += "7" })
                Spacer(modifier = Modifier.width(32.dp))
                PasscodeButton(
                    text = "8",
                    onClick = { if (confirmPasscode.length < 6) confirmPasscode += "8" })
                Spacer(modifier = Modifier.width(32.dp))
                PasscodeButton(
                    text = "9",
                    onClick = { if (confirmPasscode.length < 6) confirmPasscode += "9" })
              }
          Spacer(modifier = Modifier.height(32.dp))
          Row(
              modifier = Modifier.fillMaxWidth(),
              horizontalArrangement = Arrangement.Center,
              verticalAlignment = Alignment.CenterVertically) {
                if (useBiometric)
                    BottomButton(
                        imageVector = Icons.Rounded.Fingerprint, onClick = { onBiometricAuth() })
                else Box(modifier = Modifier.size(80.dp).clip(CircleShape).background(white))
                Spacer(modifier = Modifier.width(32.dp))
                PasscodeButton(
                    text = "0",
                    onClick = { if (confirmPasscode.length < 6) confirmPasscode += "0" })
                Spacer(modifier = Modifier.width(32.dp))
                BottomButton(
                    imageVector = Icons.AutoMirrored.Rounded.Backspace,
                    onClick = { confirmPasscode = confirmPasscode.dropLast(1) })
              }
        }
  }
}
