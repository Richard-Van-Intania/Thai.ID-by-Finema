@file:Suppress("UsingMaterialAndMaterial3Libraries")

package co.finema.thaidotidbyfinema.uis.screens.profile

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import co.finema.thaidotidbyfinema.R
import co.finema.thaidotidbyfinema.uis.blue05
import co.finema.thaidotidbyfinema.uis.components.AppBarOptBack
import co.finema.thaidotidbyfinema.uis.neutral04
import co.finema.thaidotidbyfinema.uis.primaryBlack
import co.finema.thaidotidbyfinema.uis.white

@Composable
fun SupportScreen(navController: NavController) {
  Scaffold(
      topBar = {
        AppBarOptBack(
            containerColor = white,
            text = stringResource(R.string.help_support),
            onClick = { navController.popBackStack() })
      },
      backgroundColor = white) {
        Column(
            modifier = Modifier.fillMaxSize().padding(it).padding(horizontal = 16.dp),
            horizontalAlignment = Alignment.CenterHorizontally) {
              Spacer(modifier = Modifier.height(32.dp))
              Text(
                  text = stringResource(R.string.if_you_encounter),
                  color = primaryBlack,
                  fontSize = 20.sp,
                  fontWeight = FontWeight.W400,
                  textAlign = TextAlign.Center,
                  lineHeight = 32.sp)
              Spacer(modifier = Modifier.height(8.dp))
              TextButton(onClick = {}) {
                Text(
                    text = "contact@thai.id",
                    color = blue05,
                    fontSize = 20.sp,
                    fontWeight = FontWeight.W700,
                    textDecoration = TextDecoration.Underline)
              }
              Spacer(modifier = Modifier.weight(1f))
              Text(
                  text = "contact@thai.id",
                  color = neutral04,
                  fontSize = 20.sp,
                  fontWeight = FontWeight.W400)
              Spacer(modifier = Modifier.height(48.dp))
            }
      }
}
