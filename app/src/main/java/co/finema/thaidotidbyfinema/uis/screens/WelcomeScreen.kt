@file:Suppress("UsingMaterialAndMaterial3Libraries")

package co.finema.thaidotidbyfinema.uis.screens

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import co.finema.thaidotidbyfinema.R
import co.finema.thaidotidbyfinema.uis.GradientButton
import co.finema.thaidotidbyfinema.uis.Screen
import co.finema.thaidotidbyfinema.uis.neutral07
import co.finema.thaidotidbyfinema.uis.primaryBlack
import co.finema.thaidotidbyfinema.uis.primaryDarkBlue

@Composable
fun WelcomeScreen(navController: NavHostController) {
  BackHandler(enabled = true) {}
  Scaffold(
      bottomBar = {
        Box(
            modifier = Modifier.fillMaxWidth().padding(all = 48.dp),
            contentAlignment = Alignment.Center) {
              GradientButton(
                  onClick = { navController.navigate(route = Screen.OnboardScreenNav.route) },
                  text = stringResource(R.string.get_started))
            }
      }) {
        it
        Column(
            modifier = Modifier.fillMaxSize().padding(horizontal = 16.dp),
            horizontalAlignment = Alignment.CenterHorizontally) {
              Spacer(modifier = Modifier.height(72.dp))
              Image(
                  painter = painterResource(id = R.drawable.thai_id_logo),
                  contentDescription = null,
                  modifier = Modifier.height(40.dp))
              Spacer(modifier = Modifier.weight(1f))
              Image(
                  painter = painterResource(id = R.drawable.welcome1),
                  contentDescription = null,
                  modifier = Modifier.height(328.dp))
              Spacer(modifier = Modifier.weight(1f))
              Text(
                  buildAnnotatedString {
                    withStyle(
                        style =
                            SpanStyle(
                                color = primaryBlack,
                                fontSize = 40.sp,
                                fontWeight = FontWeight.W700)) {
                          append(text = stringResource(R.string.welcome_to))
                        }
                    withStyle(
                        style =
                            SpanStyle(
                                color = primaryDarkBlue,
                                fontSize = 40.sp,
                                fontWeight = FontWeight.W700)) {
                          append(" Thai.ID")
                        }
                  })
              Spacer(modifier = Modifier.height(16.dp))
              Text(
                  text = stringResource(R.string.a_credible_solution),
                  color = neutral07,
                  fontSize = 24.sp,
                  fontWeight = FontWeight.W400,
                  textAlign = TextAlign.Center)
              Spacer(modifier = Modifier.weight(1f))
              Spacer(modifier = Modifier.height(104.dp))
            }
      }
}
