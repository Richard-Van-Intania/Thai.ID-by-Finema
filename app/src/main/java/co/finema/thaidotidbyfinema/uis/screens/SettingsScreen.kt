@file:Suppress("UsingMaterialAndMaterial3Libraries")

package co.finema.thaidotidbyfinema.uis.screens

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Icon
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.TextButton
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.rounded.ArrowForwardIos
import androidx.compose.material3.Switch
import androidx.compose.material3.SwitchDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import co.finema.thaidotidbyfinema.R
import co.finema.thaidotidbyfinema.uis.AppBarOptBack
import co.finema.thaidotidbyfinema.uis.HorizontalLine
import co.finema.thaidotidbyfinema.uis.neutral04
import co.finema.thaidotidbyfinema.uis.primaryBlack
import co.finema.thaidotidbyfinema.uis.primaryDarkBlue
import co.finema.thaidotidbyfinema.uis.secondaryGray
import co.finema.thaidotidbyfinema.uis.success06
import co.finema.thaidotidbyfinema.uis.white

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
        var pass by remember { mutableStateOf(false) }
        var bio by remember { mutableStateOf(false) }
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
                checked = pass,
                onCheckedChange = { pass = it },
                colors =
                    SwitchDefaults.colors(
                        checkedThumbColor = white,
                        checkedTrackColor = success06,
                        uncheckedThumbColor = white,
                        uncheckedTrackColor = secondaryGray,
                        uncheckedBorderColor = Color.Transparent))
          }
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
                checked = bio,
                onCheckedChange = { bio = it },
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
              modifier = Modifier.padding(end = 88.dp),
              text = stringResource(R.string.once_you_enable),
              color = neutral04,
              fontSize = 16.sp,
              fontWeight = FontWeight.W400,
          )
          Spacer(modifier = Modifier.height(16.dp))
          HorizontalLine()
          TextButton(onClick = {}) {
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
