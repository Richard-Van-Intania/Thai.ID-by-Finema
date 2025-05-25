@file:Suppress("UsingMaterialAndMaterial3Libraries")

package co.finema.thaidotidbyfinema.uis.screens.passcodes

import androidx.activity.compose.BackHandler
import androidx.biometric.BiometricManager
import androidx.biometric.BiometricManager.Authenticators.BIOMETRIC_STRONG
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
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
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.TextButton
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.rounded.Backspace
import androidx.compose.material.icons.rounded.Delete
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.navigation.NavController
import co.finema.thaidotidbyfinema.R
import co.finema.thaidotidbyfinema.biometricAuth
import co.finema.thaidotidbyfinema.generateSalt
import co.finema.thaidotidbyfinema.hashedPasscode
import co.finema.thaidotidbyfinema.repositories.UserConfigRepository
import co.finema.thaidotidbyfinema.uis.components.ErrorDialog
import co.finema.thaidotidbyfinema.uis.components.FullScreenDialog
import co.finema.thaidotidbyfinema.uis.gradient
import co.finema.thaidotidbyfinema.uis.lightBlue07
import co.finema.thaidotidbyfinema.uis.primaryBlack
import co.finema.thaidotidbyfinema.uis.primaryDarkBlue
import co.finema.thaidotidbyfinema.uis.white
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlin.math.roundToInt
import kotlin.time.Duration.Companion.seconds

@Composable
fun ConfirmPasscodeFullscreen(
    navController: NavController, tapPasscode: String, onBiometricAuth: () -> Unit, localAuth: MutableState<Boolean>
) {
    BackHandler(enabled = true) {}
    val context = LocalContext.current
    val biometricManager = BiometricManager.from(context)
    val repository = remember { UserConfigRepository(context) }
    val scope = rememberCoroutineScope()
    LaunchedEffect(Unit) { biometricAuth.value = null }
    var passAuth by remember { mutableStateOf(false) }
    LaunchedEffect(passAuth) {
        if (passAuth) {
            localAuth.value = true
            navController.popBackStack()
            navController.popBackStack()
        }
    }
    var showSetUpPinSuccess by remember { mutableStateOf(false) }
    if (showSetUpPinSuccess) {
        FullScreenDialog(
            painter = painterResource(R.drawable.create_sucess), height = 160.dp, text = stringResource(R.string.set_up_pin_success)
        )
    }
    var showSetUpBiometricSuccess by remember { mutableStateOf(false) }
    if (showSetUpBiometricSuccess) {
        FullScreenDialog(
            painter = painterResource(R.drawable.create_sucess), height = 160.dp, text = stringResource(R.string.enable_biometrics_success)
        )
    }
    var enableBiometric by remember { mutableStateOf(false) }
    LaunchedEffect(enableBiometric) {
        if (enableBiometric) {
            onBiometricAuth()
        }
    }
    var showBiometricAskedDialog by remember { mutableStateOf(false) }
    if (showBiometricAskedDialog) {
        Dialog(onDismissRequest = {}) {
            Box(
                modifier = Modifier
                    .clip(RoundedCornerShape(8.dp))
                    .background(white)
                    .fillMaxWidth()
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp), horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Spacer(modifier = Modifier.height(40.dp))
                    Image(
                        painter = painterResource(id = R.drawable.fingerprint), contentDescription = null, modifier = Modifier.height(96.dp)
                    )
                    Spacer(modifier = Modifier.height(24.dp))
                    Text(
                        text = stringResource(R.string.enable_biometrics),
                        color = primaryBlack,
                        fontSize = 24.sp,
                        fontWeight = FontWeight.W400,
                        textAlign = TextAlign.Center,
                    )
                    Spacer(modifier = Modifier.height(32.dp))
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Box(
                            modifier = Modifier
                                .height(56.dp)
                                .weight(1f)
                                .background(white)
                                .border(
                                    width = 2.dp, color = lightBlue07, shape = RoundedCornerShape(56.dp)
                                )
                                .clip(RoundedCornerShape(56.dp))
                                .clickable(
                                    onClick = {
                                        showBiometricAskedDialog = false
                                        passAuth = true
                                    }), contentAlignment = Alignment.Center
                        ) {
                            Text(
                                text = stringResource(R.string.not_now),
                                color = lightBlue07,
                                fontSize = 24.sp,
                                fontWeight = FontWeight.W700,
                            )
                        }
                        Spacer(modifier = Modifier.width(16.dp))
                        Box(
                            modifier = Modifier
                                .height(56.dp)
                                .weight(1f)
                                .clip(RoundedCornerShape(56.dp))
                                .background(brush = gradient)
                                .clickable(
                                    onClick = {
                                        showBiometricAskedDialog = false
                                        enableBiometric = true
                                    }), contentAlignment = Alignment.Center
                        ) {
                            Text(
                                text = stringResource(R.string.enable),
                                color = white,
                                fontSize = 24.sp,
                                fontWeight = FontWeight.W700,
                            )
                        }
                    }
                    Spacer(modifier = Modifier.height(32.dp))
                }
            }
        }
    }
    var showErrorsDialog by remember { mutableStateOf(false) }
    if (showErrorsDialog) {
        ErrorDialog(
            text = stringResource(R.string.unable_use_biometrics), onClick = {
                showErrorsDialog = false
                passAuth = true
            })
    }
    LaunchedEffect(biometricAuth.value) {
        when (biometricAuth.value) {
            true -> {
                scope.launch { repository.updateUseBiometric(true) }
                showSetUpBiometricSuccess = true
                delay(2.seconds)
                showSetUpBiometricSuccess = false
                passAuth = true
            }

            false -> {
                showErrorsDialog = true
            }

            null -> {}
        }
    }
    Scaffold(
        bottomBar = {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(all = 48.dp), contentAlignment = Alignment.Center
            ) {
                TextButton(
                    onClick = {
                        scope.launch { repository.updatePasscodeAsked(true) }
                        scope.launch {
                            showSetUpPinSuccess = true
                            delay(2.seconds)
                            showSetUpPinSuccess = false
                        }.invokeOnCompletion { passAuth = true }
                    }) {
                    Text(
                        text = stringResource(R.string.skip), color = primaryDarkBlue, fontSize = 20.sp, fontWeight = FontWeight.W700
                    )
                }
            }
        }) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(it), verticalArrangement = Arrangement.Center, horizontalAlignment = Alignment.CenterHorizontally
        ) {
            var confirmPasscode by remember { mutableStateOf("") }
            val shakeController = remember { ShakeController(scope) }
            LaunchedEffect(confirmPasscode) {
                if (confirmPasscode.length==6) {
                    if (confirmPasscode==tapPasscode) {
                        scope.launch {
                            val salt = generateSalt()
                            val hashedPasscode = hashedPasscode(tapPasscode, salt)
                            repository.updateSalt(salt)
                            repository.updatePasscode(hashedPasscode)
                            repository.updatePasscodeAsked(true)
                        }
                        showSetUpPinSuccess = true
                        delay(2.seconds)
                        showSetUpPinSuccess = false
                        if (biometricManager.canAuthenticate(BIOMETRIC_STRONG)==BiometricManager.BIOMETRIC_SUCCESS) {
                            showBiometricAskedDialog = true
                        } else {
                            passAuth = true
                        }
                    } else {
                        shakeController.triggerShake()
                        confirmPasscode = ""
                    }
                }
            }
            Text(
                text = stringResource(R.string.set_up_pin_confirm), color = primaryBlack, fontSize = 24.sp, fontWeight = FontWeight.W700
            )
            Spacer(modifier = Modifier.height(48.dp))
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .offset {
                        IntOffset(shakeController.offset.value.roundToInt(), 0)
                    }, horizontalArrangement = Arrangement.Center, verticalAlignment = Alignment.CenterVertically
            ) {
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
                modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.Center, verticalAlignment = Alignment.CenterVertically
            ) {
                PasscodeButton(
                    text = "1", onClick = { if (confirmPasscode.length < 6) confirmPasscode += "1" })
                Spacer(modifier = Modifier.width(32.dp))
                PasscodeButton(
                    text = "2", onClick = { if (confirmPasscode.length < 6) confirmPasscode += "2" })
                Spacer(modifier = Modifier.width(32.dp))
                PasscodeButton(
                    text = "3", onClick = { if (confirmPasscode.length < 6) confirmPasscode += "3" })
            }
            Spacer(modifier = Modifier.height(32.dp))
            Row(
                modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.Center, verticalAlignment = Alignment.CenterVertically
            ) {
                PasscodeButton(
                    text = "4", onClick = { if (confirmPasscode.length < 6) confirmPasscode += "4" })
                Spacer(modifier = Modifier.width(32.dp))
                PasscodeButton(
                    text = "5", onClick = { if (confirmPasscode.length < 6) confirmPasscode += "5" })
                Spacer(modifier = Modifier.width(32.dp))
                PasscodeButton(
                    text = "6", onClick = { if (confirmPasscode.length < 6) confirmPasscode += "6" })
            }
            Spacer(modifier = Modifier.height(32.dp))
            Row(
                modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.Center, verticalAlignment = Alignment.CenterVertically
            ) {
                PasscodeButton(
                    text = "7", onClick = { if (confirmPasscode.length < 6) confirmPasscode += "7" })
                Spacer(modifier = Modifier.width(32.dp))
                PasscodeButton(
                    text = "8", onClick = { if (confirmPasscode.length < 6) confirmPasscode += "8" })
                Spacer(modifier = Modifier.width(32.dp))
                PasscodeButton(
                    text = "9", onClick = { if (confirmPasscode.length < 6) confirmPasscode += "9" })
            }
            Spacer(modifier = Modifier.height(32.dp))
            Row(
                modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.Center, verticalAlignment = Alignment.CenterVertically
            ) {
                BottomButton(
                    imageVector = Icons.Rounded.Delete, onClick = { navController.popBackStack() })
                Spacer(modifier = Modifier.width(32.dp))
                PasscodeButton(
                    text = "0", onClick = { if (confirmPasscode.length < 6) confirmPasscode += "0" })
                Spacer(modifier = Modifier.width(32.dp))
                BottomButton(
                    imageVector = Icons.AutoMirrored.Rounded.Backspace, onClick = { confirmPasscode = confirmPasscode.dropLast(1) })
            }
        }
    }
}
