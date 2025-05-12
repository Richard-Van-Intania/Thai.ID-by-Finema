@file:Suppress("UsingMaterialAndMaterial3Libraries")

package co.finema.thaidotidbyfinema.uis.screens

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Icon
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.TextButton
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.rounded.ArrowForwardIos
import androidx.compose.material3.Switch
import androidx.compose.material3.SwitchDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import co.finema.thaidotidbyfinema.R
import co.finema.thaidotidbyfinema.repositories.UserConfigRepository
import co.finema.thaidotidbyfinema.uis.AppBarOptBack
import co.finema.thaidotidbyfinema.uis.HorizontalLine
import co.finema.thaidotidbyfinema.uis.neutral04
import co.finema.thaidotidbyfinema.uis.primaryBlack
import co.finema.thaidotidbyfinema.uis.primaryDarkBlue
import co.finema.thaidotidbyfinema.uis.secondaryGray
import co.finema.thaidotidbyfinema.uis.success06
import co.finema.thaidotidbyfinema.uis.white
import kotlinx.coroutines.launch

@Composable
fun SettingsScreen(navController: NavController) {
  Scaffold(
      topBar = {
        AppBarOptBack(
            containerColor = white,
            text = stringResource(R.string.login_settings),
            onClick = { navController.popBackStack() })
      },
      backgroundColor = white) {
        val context = LocalContext.current
        val repository = remember { UserConfigRepository(context) }
        val passcode by repository.passcode.collectAsState(initial = null)
        val useBiometric by repository.useBiometric.collectAsState(initial = null)
        val scope = rememberCoroutineScope()
        if (passcode == null || useBiometric == null)
            Box(
                modifier = Modifier.fillMaxSize().padding(it),
                contentAlignment = Alignment.Center) {
                  CircularProgressIndicator()
                }
        else
            Column(modifier = Modifier.fillMaxSize().padding(it).padding(horizontal = 16.dp)) {
              Row(verticalAlignment = Alignment.CenterVertically) {
                Text(
                    text = stringResource(R.string.enable_pin),
                    color = primaryBlack,
                    fontSize = 20.sp,
                    fontWeight = FontWeight.W700,
                )
                Spacer(modifier = Modifier.weight(1f))
                Switch(
                    checked = passcode!!.isNotEmpty(),
                    onCheckedChange = {
                      if (it) {
                        // navController.navigate(Screen.CreatePasscode.route)
                      } else {
                        scope.launch {
                          repository.updateSalt("")
                          repository.updatePasscode("")
                          repository.updateUseBiometric(false)
                          // snack
                        }
                      }
                    },
                    colors =
                        SwitchDefaults.colors(
                            checkedThumbColor = white,
                            checkedTrackColor = success06,
                            uncheckedThumbColor = white,
                            uncheckedTrackColor = secondaryGray,
                            uncheckedBorderColor = Color.Transparent))
              }
              if (passcode!!.isNotEmpty()) {
                Spacer(modifier = Modifier.height(16.dp))
                Row(verticalAlignment = Alignment.CenterVertically) {
                  Column {
                    Text(
                        text = stringResource(R.string.biometrics),
                        color = primaryBlack,
                        fontSize = 20.sp,
                        fontWeight = FontWeight.W700,
                    )
                    Text(
                        text = stringResource(R.string.use_biometrics),
                        color = neutral04,
                        fontSize = 20.sp,
                        fontWeight = FontWeight.W400,
                    )
                  }
                  Spacer(modifier = Modifier.weight(1f))
                  Switch(
                      checked = useBiometric!!,
                      onCheckedChange = {
                        if (it) {
                          // onbio
                        } else {
                          scope.launch {
                            repository.updateUseBiometric(false)
                            // snack
                          }
                        }
                      },
                      colors =
                          SwitchDefaults.colors(
                              checkedThumbColor = white,
                              checkedTrackColor = success06,
                              uncheckedThumbColor = white,
                              uncheckedTrackColor = secondaryGray,
                              uncheckedBorderColor = Color.Transparent))
                }
                Spacer(modifier = Modifier.height(16.dp))
                Text(
                    modifier = Modifier.padding(end = 96.dp),
                    text = stringResource(R.string.once_you_enable),
                    color = neutral04,
                    fontSize = 16.sp,
                    fontWeight = FontWeight.W400,
                )
                Spacer(modifier = Modifier.height(16.dp))
                HorizontalLine()
                TextButton(
                    onClick = {
                      // nav here
                    }) {
                      Row(
                          modifier = Modifier.fillMaxWidth().padding(vertical = 16.dp),
                          verticalAlignment = Alignment.CenterVertically) {
                            Text(
                                text = stringResource(R.string.change_pin),
                                color = primaryBlack,
                                fontSize = 20.sp,
                                fontWeight = FontWeight.W700,
                            )
                            Spacer(modifier = Modifier.weight(1f))
                            Icon(
                                imageVector = Icons.AutoMirrored.Rounded.ArrowForwardIos,
                                contentDescription = null,
                                tint = primaryDarkBlue)
                          }
                    }
                HorizontalLine()
              }
            }
      }
}
