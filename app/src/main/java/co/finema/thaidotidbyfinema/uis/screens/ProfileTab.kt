@file:Suppress("UsingMaterialAndMaterial3Libraries")

package co.finema.thaidotidbyfinema.uis.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import co.finema.thaidotidbyfinema.R
import co.finema.thaidotidbyfinema.uis.devGreen
import co.finema.thaidotidbyfinema.uis.primaryBlack
import co.finema.thaidotidbyfinema.uis.whiteBG

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
                    containerColor = whiteBG,
                    navigationIconContentColor = primaryBlack,
                    actionIconContentColor = primaryBlack))
      },
      backgroundColor = whiteBG) {
        Column(
            modifier =
                Modifier.fillMaxSize().padding(it).padding(horizontal = 16.dp).background(devGreen),
        ) {}
      }
}
