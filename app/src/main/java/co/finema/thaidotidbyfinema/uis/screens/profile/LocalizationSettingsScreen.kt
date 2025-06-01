@file:Suppress("UsingMaterialAndMaterial3Libraries")

package co.finema.thaidotidbyfinema.uis.screens.profile

import android.app.Activity
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.TextButton
import androidx.compose.material3.HorizontalDivider
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import co.finema.thaidotidbyfinema.EN
import co.finema.thaidotidbyfinema.R
import co.finema.thaidotidbyfinema.TH
import co.finema.thaidotidbyfinema.repositories.UserConfigRepository
import co.finema.thaidotidbyfinema.uis.Screen
import co.finema.thaidotidbyfinema.uis.components.AppBarOptBack
import co.finema.thaidotidbyfinema.uis.neutral02
import co.finema.thaidotidbyfinema.uis.primaryBlack
import co.finema.thaidotidbyfinema.uis.white
import kotlinx.coroutines.launch

@Composable
fun LocalizationSettingsScreen(navController: NavController) {
    Scaffold(
        topBar = {
            AppBarOptBack(
                containerColor = white,
                text = stringResource(R.string.language),
                onClick = { navController.popBackStack() },
            )
        },
        backgroundColor = white,
    ) {
        val context = LocalContext.current
        val repository = remember { UserConfigRepository(context) }
        val locale by repository.locale.collectAsState(initial = null)
        if (locale == null)
            Box(
                modifier = Modifier.fillMaxSize().padding(it),
                contentAlignment = Alignment.Center,
            ) {
                CircularProgressIndicator()
            }
        else
            Column(
                modifier = Modifier.fillMaxSize().padding(it).padding(horizontal = 16.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
            ) {
                val scope = rememberCoroutineScope()
                TextButton(
                    onClick = {
                        if (locale != EN)
                            scope.launch {
                                repository.updateLocale(EN)
                                navController.navigate(route = Screen.LoadingScreen.route)
                                (context as? Activity)?.recreate()
                            }
                    }
                ) {
                    Row(
                        modifier = Modifier.fillMaxWidth().padding(vertical = 16.dp),
                        verticalAlignment = Alignment.CenterVertically,
                    ) {
                        Text(
                            text = "English (EN)",
                            color = primaryBlack,
                            fontSize = 20.sp,
                            fontWeight = FontWeight.W700,
                        )
                        Spacer(modifier = Modifier.weight(1f))
                        Image(
                            painter =
                                painterResource(
                                    id =
                                        if (locale == EN) R.drawable.lang_check
                                        else R.drawable.lang_uncheck
                                ),
                            contentDescription = null,
                            modifier = Modifier.height(24.dp),
                        )
                    }
                }
                HorizontalDivider(thickness = 1.dp, color = neutral02)
                TextButton(
                    onClick = {
                        if (locale != TH)
                            scope.launch {
                                repository.updateLocale(TH)
                                navController.navigate(route = Screen.LoadingScreen.route)
                                (context as? Activity)?.recreate()
                            }
                    }
                ) {
                    Row(
                        modifier = Modifier.fillMaxWidth().padding(vertical = 16.dp),
                        verticalAlignment = Alignment.CenterVertically,
                    ) {
                        Text(
                            text = "ไทย (TH)",
                            color = primaryBlack,
                            fontSize = 20.sp,
                            fontWeight = FontWeight.W700,
                        )
                        Spacer(modifier = Modifier.weight(1f))
                        Image(
                            painter =
                                painterResource(
                                    id =
                                        if (locale == TH) R.drawable.lang_check
                                        else R.drawable.lang_uncheck
                                ),
                            contentDescription = null,
                            modifier = Modifier.height(24.dp),
                        )
                    }
                }
                HorizontalDivider(thickness = 1.dp, color = neutral02)
            }
    }
}
