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
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.TextButton
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.rounded.Backspace
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
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
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import co.finema.thaidotidbyfinema.R
import co.finema.thaidotidbyfinema.repositories.UserConfigRepository
import co.finema.thaidotidbyfinema.uis.Screen
import co.finema.thaidotidbyfinema.uis.components.FullScreenDialog
import co.finema.thaidotidbyfinema.uis.primaryBlack
import co.finema.thaidotidbyfinema.uis.primaryDarkBlue
import co.finema.thaidotidbyfinema.uis.white
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlin.time.Duration.Companion.seconds

@Composable
fun CreatePasscodeFullscreen(navController: NavController) {
    BackHandler(enabled = true) {}
    var showSetUpPinSuccess by remember { mutableStateOf(false) }
    if (showSetUpPinSuccess) {
        FullScreenDialog(painter = painterResource(R.drawable.create_sucess), height = 160.dp, text = stringResource(R.string.set_up_pin_success))
    }
    Scaffold(
     bottomBar = {
         Box(modifier = Modifier.fillMaxWidth().padding(all = 48.dp), contentAlignment = Alignment.Center) {
             val context = LocalContext.current
             val repository = remember { UserConfigRepository(context) }
             val scope = rememberCoroutineScope()
             TextButton(
              onClick = {
                  scope.launch { repository.updatePasscodeAsked(true) }
                  scope
                   .launch {
                       showSetUpPinSuccess = true
                       delay(2.seconds)
                       showSetUpPinSuccess = false
                   }
                   .invokeOnCompletion { navController.popBackStack() }
              }
             ) {
                 Text(text = stringResource(R.string.skip), color = primaryDarkBlue, fontSize = 20.sp, fontWeight = FontWeight.W700)
             }
         }
     }
    ) {
        Column(modifier = Modifier.fillMaxSize().padding(it), verticalArrangement = Arrangement.Center, horizontalAlignment = Alignment.CenterHorizontally) {
            var tapPasscode by remember { mutableStateOf("") }
            LaunchedEffect(tapPasscode) {
                if (tapPasscode.length == 6) {
                    navController.navigate(route = "${Screen.ConfirmPasscodeFullscreen.route}/${tapPasscode}")
                }
            }
            Text(text = stringResource(R.string.set_up_pin), color = primaryBlack, fontSize = 24.sp, fontWeight = FontWeight.W700)
            Spacer(modifier = Modifier.height(48.dp))
            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.Center, verticalAlignment = Alignment.CenterVertically) {
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
                Box(modifier = Modifier.size(80.dp).clip(CircleShape).background(white))
                Spacer(modifier = Modifier.width(32.dp))
                PasscodeButton(text = "0", onClick = { if (tapPasscode.length < 6) tapPasscode += "0" })
                Spacer(modifier = Modifier.width(32.dp))
                BottomButton(imageVector = Icons.AutoMirrored.Rounded.Backspace, onClick = { tapPasscode = tapPasscode.dropLast(1) })
            }
        }
    }
}
