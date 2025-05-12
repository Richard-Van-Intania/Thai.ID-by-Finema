@file:Suppress("UsingMaterialAndMaterial3Libraries")

package co.finema.thaidotidbyfinema.uis.screens

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Scaffold
import androidx.compose.material.Switch
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import co.finema.thaidotidbyfinema.R
import co.finema.thaidotidbyfinema.uis.AppBarOptBack
import co.finema.thaidotidbyfinema.uis.primaryBlack
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
        var checked by remember { mutableStateOf(false) }
        Column(modifier = Modifier.fillMaxSize().padding(it).padding(horizontal = 16.dp)) {
          Row(verticalAlignment = Alignment.CenterVertically) {
            Text(
                text = stringResource(R.string.enable_pin),
                color = primaryBlack,
                fontSize = 20.sp,
                fontWeight = FontWeight.W700,
            )
            Spacer(modifier = Modifier.weight(1f))
            Switch(checked = checked, onCheckedChange = { checked = it })
          }
        }
      }
}
