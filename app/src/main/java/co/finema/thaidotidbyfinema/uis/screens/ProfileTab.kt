package co.finema.thaidotidbyfinema.uis.screens

import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import co.finema.thaidotidbyfinema.R
import co.finema.thaidotidbyfinema.uis.primaryBlack
import co.finema.thaidotidbyfinema.uis.white

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProfileTab(navController: NavController) {
  Scaffold(
      topBar = {
        CenterAlignedTopAppBar(
            title = {
              Text(
                  text = stringResource(R.string.profile),
                  color = primaryBlack,
                  fontSize = 24.sp,
                  fontWeight = FontWeight.W700,
              )
            },
            colors =
                TopAppBarDefaults.centerAlignedTopAppBarColors(
                    containerColor = white,
                    navigationIconContentColor = primaryBlack,
                    actionIconContentColor = primaryBlack))
      },
      backgroundColor = white) {
        it
      }
}
