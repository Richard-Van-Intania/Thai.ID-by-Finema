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
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.TextButton
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.rounded.Backspace
import androidx.compose.material.icons.rounded.Fingerprint
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
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
import co.finema.thaidotidbyfinema.uis.Screen
import co.finema.thaidotidbyfinema.uis.components.ErrorDialog
import co.finema.thaidotidbyfinema.uis.primaryBlack
import co.finema.thaidotidbyfinema.uis.primaryDarkBlue
import co.finema.thaidotidbyfinema.uis.white
import co.finema.thaidotidbyfinema.verifyPasscode
import kotlin.math.roundToInt

@Composable
fun EnterPasscodeChangeFullscreen(navController: NavController, onBiometricAuth: () -> Unit) {
    BackHandler(enabled = true) {}
    LaunchedEffect(Unit) { biometricAuth.value = null }
    val context = LocalContext.current
    val repository = remember { UserConfigRepository(context) }
    val snackbarState = remember { SnackbarHostState() }
    val useBiometric by repository.useBiometric.collectAsState(initial = false)
    var tapPasscode by remember { mutableStateOf("") }
    var passAuth by remember { mutableStateOf(false) }
    LaunchedEffect(passAuth) {
        if (passAuth) {
            navController.navigate(Screen.CreatePasscodeChangeFullscreen.route)
        }
    }
    var showErrorsDialog by remember { mutableStateOf(false) }
    if (showErrorsDialog) {
        ErrorDialog(text = stringResource(R.string.unable_use_biometrics), onClick = { showErrorsDialog = false })
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
    Scaffold(
        snackbarHost = { SnackbarHost(hostState = snackbarState, modifier = Modifier.padding(bottom = 48.dp)) },
        bottomBar = {
            Box(modifier = Modifier
                .fillMaxWidth()
                .padding(all = 48.dp), contentAlignment = Alignment.Center) {
                TextButton(onClick = { navController.popBackStack() }) { Text(text = stringResource(R.string.cancel), color = primaryDarkBlue, fontSize = 20.sp, fontWeight = FontWeight.W700) }
            }
        },
    ) {
        Column(modifier = Modifier
            .fillMaxSize()
            .padding(it), verticalArrangement = Arrangement.Center, horizontalAlignment = Alignment.CenterHorizontally) {
            val passcode by repository.passcode.collectAsState(initial = "")
            val salt by repository.salt.collectAsState(initial = "")
            val scope = rememberCoroutineScope()
            val shakeController = remember { ShakeController(scope) }
            LaunchedEffect(tapPasscode) {
                if (tapPasscode.length == 6) {
                    if (verifyPasscode(password = tapPasscode, storedHash = passcode, salt = salt)) {
                        passAuth = true
                    } else {
                        shakeController.triggerShake()
                        tapPasscode = ""
                    }
                }
            }
            if (passcode.isEmpty() || salt.isEmpty()) Box(modifier = Modifier
                .fillMaxSize()
                .padding(it), contentAlignment = Alignment.Center) { CircularProgressIndicator() }
            else {
                Text(text = stringResource(R.string.enter_current_pin), color = primaryBlack, fontSize = 24.sp, fontWeight = FontWeight.W700)
                Spacer(modifier = Modifier.height(48.dp))
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .offset { IntOffset(shakeController.offset.value.roundToInt(), 0) },
                    horizontalArrangement = Arrangement.Center,
                    verticalAlignment = Alignment.CenterVertically,
                ) {
                    if (tapPasscode.isEmpty()) OutlinedDot() else FilledDot()
                    Spacer(modifier = Modifier.width(24.dp))
                    if (tapPasscode.length < 2) OutlinedDot() else FilledDot()
                    Spacer(modifier = Modifier.width(24.dp))
                    if (tapPasscode.length < 3) OutlinedDot() else FilledDot()
                    Spacer(modifier = Modifier.width(24.dp))
                    if (tapPasscode.length < 4) OutlinedDot() else FilledDot()
                    Spacer(modifier = Modifier.width(24.dp))
                    if (tapPasscode.length < 5) OutlinedDot() else FilledDot()
                    Spacer(modifier = Modifier.width(24.dp))
                    if (tapPasscode.length < 6) OutlinedDot() else FilledDot()
                }
                Spacer(modifier = Modifier.height(48.dp))
                Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.Center, verticalAlignment = Alignment.CenterVertically) {
                    PasscodeButton(text = "1", onClick = { if (tapPasscode.length < 6) tapPasscode += "1" })
                    Spacer(modifier = Modifier.width(32.dp))
                    PasscodeButton(text = "2", onClick = { if (tapPasscode.length < 6) tapPasscode += "2" })
                    Spacer(modifier = Modifier.width(32.dp))
                    PasscodeButton(text = "3", onClick = { if (tapPasscode.length < 6) tapPasscode += "3" })
                }
                Spacer(modifier = Modifier.height(32.dp))
                Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.Center, verticalAlignment = Alignment.CenterVertically) {
                    PasscodeButton(text = "4", onClick = { if (tapPasscode.length < 6) tapPasscode += "4" })
                    Spacer(modifier = Modifier.width(32.dp))
                    PasscodeButton(text = "5", onClick = { if (tapPasscode.length < 6) tapPasscode += "5" })
                    Spacer(modifier = Modifier.width(32.dp))
                    PasscodeButton(text = "6", onClick = { if (tapPasscode.length < 6) tapPasscode += "6" })
                }
                Spacer(modifier = Modifier.height(32.dp))
                Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.Center, verticalAlignment = Alignment.CenterVertically) {
                    PasscodeButton(text = "7", onClick = { if (tapPasscode.length < 6) tapPasscode += "7" })
                    Spacer(modifier = Modifier.width(32.dp))
                    PasscodeButton(text = "8", onClick = { if (tapPasscode.length < 6) tapPasscode += "8" })
                    Spacer(modifier = Modifier.width(32.dp))
                    PasscodeButton(text = "9", onClick = { if (tapPasscode.length < 6) tapPasscode += "9" })
                }
                Spacer(modifier = Modifier.height(32.dp))
                Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.Center, verticalAlignment = Alignment.CenterVertically) {
                    if (useBiometric) BottomButton(imageVector = Icons.Rounded.Fingerprint, onClick = { onBiometricAuth() })
                    else Box(modifier = Modifier
                        .size(80.dp)
                        .clip(CircleShape)
                        .background(white))
                    Spacer(modifier = Modifier.width(32.dp))
                    PasscodeButton(text = "0", onClick = { if (tapPasscode.length < 6) tapPasscode += "0" })
                    Spacer(modifier = Modifier.width(32.dp))
                    BottomButton(imageVector = Icons.AutoMirrored.Rounded.Backspace, onClick = { tapPasscode = tapPasscode.dropLast(1) })
                }
            }
        }
    }
}
