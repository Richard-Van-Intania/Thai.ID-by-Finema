package co.finema.thaidotidbyfinema.uis.screens.passcodes

import androidx.activity.compose.BackHandler
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
import androidx.compose.foundation.layout.width
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.TextButton
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.rounded.Backspace
import androidx.compose.material.icons.rounded.Delete
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import co.finema.thaidotidbyfinema.R
import co.finema.thaidotidbyfinema.generateSalt
import co.finema.thaidotidbyfinema.hashedPasscode
import co.finema.thaidotidbyfinema.repositories.UserConfigRepository
import co.finema.thaidotidbyfinema.uis.primaryBlack
import co.finema.thaidotidbyfinema.uis.primaryDarkBlue
import kotlinx.coroutines.launch
import kotlin.math.roundToInt

@Composable
fun ConfirmPasscodeFullscreen(navController: NavController, passcode: String) {
  BackHandler(enabled = true) {}
  Scaffold(
      bottomBar = {
        Box(
            modifier = Modifier.fillMaxWidth().padding(all = 48.dp),
            contentAlignment = Alignment.Center) {
              TextButton(
                  onClick = {
                    navController.popBackStack()
                    navController.popBackStack()
                  }) {
                    Text(
                        text = stringResource(R.string.skip),
                        color = primaryDarkBlue,
                        fontSize = 20.sp,
                        fontWeight = FontWeight.W700)
                  }
            }
      }) {
        Column(
            modifier = Modifier.fillMaxSize().padding(it),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally) {
              val context = LocalContext.current
              val repository = remember { UserConfigRepository(context) }
              val scope = rememberCoroutineScope()
              var confirmPasscode by remember { mutableStateOf("") }
              val shakeController = remember { ShakeController(scope) }
              LaunchedEffect(confirmPasscode) {
                if (confirmPasscode.length == 6) {
                  if (passcode == confirmPasscode) {
                    scope.launch {
                      val salt = generateSalt()
                      val hashedPasscode = hashedPasscode(passcode, salt)
                      repository.updateSalt(salt)
                      repository.updatePasscode(hashedPasscode)
                    }
                    navController.popBackStack()
                    navController.popBackStack()
                  } else {
                    shakeController.triggerShake()
                    confirmPasscode = ""
                  }
                }
              }
              Text(
                  text = stringResource(R.string.set_up_pin_confirm),
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
                    BottomButton(
                        imageVector = Icons.Rounded.Delete,
                        onClick = { navController.popBackStack() })
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
