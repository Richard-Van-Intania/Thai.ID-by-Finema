@file:Suppress("UsingMaterialAndMaterial3Libraries")

package co.finema.thaidotidbyfinema.uis.screens.profile

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.ArrowBackIosNew
import androidx.compose.material.icons.rounded.BorderColor
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import co.finema.thaidotidbyfinema.EN
import co.finema.thaidotidbyfinema.R
import co.finema.thaidotidbyfinema.TH
import co.finema.thaidotidbyfinema.formatterEN
import co.finema.thaidotidbyfinema.formatterTH
import co.finema.thaidotidbyfinema.repositories.UserCardRepository
import co.finema.thaidotidbyfinema.repositories.UserConfigRepository
import co.finema.thaidotidbyfinema.uis.Screen
import co.finema.thaidotidbyfinema.uis.components.ProfileDetailsHr
import co.finema.thaidotidbyfinema.uis.neutral02
import co.finema.thaidotidbyfinema.uis.neutral04
import co.finema.thaidotidbyfinema.uis.primaryBlack
import co.finema.thaidotidbyfinema.uis.primaryDarkBlue
import co.finema.thaidotidbyfinema.uis.white
import java.time.LocalDateTime
import java.time.chrono.ThaiBuddhistDate

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProfileDetailsScreen(navController: NavController) {
  Scaffold(
      topBar = {
        CenterAlignedTopAppBar(
            title = {
              Text(
                  text = stringResource(R.string.my_profile),
                  color = primaryBlack,
                  fontSize = 24.sp,
                  fontWeight = FontWeight.W700,
              )
            },
            navigationIcon = {
              IconButton(onClick = { navController.popBackStack() }) {
                Icon(imageVector = Icons.Rounded.ArrowBackIosNew, contentDescription = null)
              }
            },
            actions = {
              IconButton(
                  onClick = { navController.navigate(route = Screen.ProfileEditScreen.route) }) {
                    Icon(
                        imageVector = Icons.Rounded.BorderColor,
                        contentDescription = null,
                        tint = primaryBlack)
                  }
            },
            colors =
                TopAppBarDefaults.centerAlignedTopAppBarColors(
                    containerColor = white,
                    navigationIconContentColor = primaryBlack,
                    actionIconContentColor = primaryBlack))
      },
      backgroundColor = white) {
        val context = LocalContext.current
        val userConfigRepository = remember { UserConfigRepository(context) }
        val locale by userConfigRepository.locale.collectAsState(initial = null)
        val userCardRepository = remember { UserCardRepository(context) }
        val idString by userCardRepository.idString.collectAsState(initial = "")
        val thaiPrefix by userCardRepository.thaiPrefix.collectAsState(initial = "")
        val thaiName by userCardRepository.thaiName.collectAsState(initial = "")
        val thaiMiddleName by userCardRepository.thaiMiddleName.collectAsState(initial = "")
        val thaiSurname by userCardRepository.thaiSurname.collectAsState(initial = "")
        val engPrefix by userCardRepository.engPrefix.collectAsState(initial = "")
        val engName by userCardRepository.engName.collectAsState(initial = "")
        val engMiddleName by userCardRepository.engMiddleName.collectAsState(initial = "")
        val engSurname by userCardRepository.engSurname.collectAsState(initial = "")
        val birthDate by
            userCardRepository.birthDate.collectAsState(initial = LocalDateTime.now().toString())
        if (locale == null)
            Box(
                modifier = Modifier.fillMaxSize().padding(it),
                contentAlignment = Alignment.Center) {
                  CircularProgressIndicator()
                }
        else
            LazyColumn(
                modifier = Modifier.fillMaxSize().padding(it).padding(horizontal = 16.dp),
                horizontalAlignment = Alignment.CenterHorizontally) {
                  item {
                    ProfileDetailsItem(
                        text = stringResource(R.string.id_number),
                        value =
                            if (idString.isEmpty()) ""
                            else
                                "${idString.substring(0, 1)} ${idString.substring(1, 5)} ${idString.substring(5, 10)} ${idString.substring(10, 12)} ${idString.substring(12)}",
                    )
                    Row(
                        modifier = Modifier.padding(vertical = 16.dp),
                        verticalAlignment = Alignment.CenterVertically) {
                          Text(
                              text = stringResource(R.string.date_of_birth),
                              color = primaryBlack,
                              fontSize = 20.sp,
                              fontWeight = FontWeight.W700,
                          )
                          Spacer(modifier = Modifier.weight(1f))
                          Text(
                              text =
                                  if (birthDate.isEmpty()) stringResource(R.string.date_format)
                                  else
                                      when (locale) {
                                        EN -> {
                                          val localDateTime = LocalDateTime.parse(birthDate)
                                          formatterEN.format(localDateTime)
                                        }
                                        TH -> {
                                          val localDateTime = LocalDateTime.parse(birthDate)
                                          val thaiBuddhistDate =
                                              ThaiBuddhistDate.from(localDateTime)
                                          formatterTH.format(thaiBuddhistDate)
                                        }
                                        else -> {
                                          val localDateTime = LocalDateTime.parse(birthDate)
                                          formatterEN.format(localDateTime)
                                        }
                                      },
                              color = if (birthDate.isEmpty()) neutral04 else primaryDarkBlue,
                              fontSize = 20.sp,
                              fontWeight = FontWeight.W400,
                          )
                        }
                    HorizontalDivider(thickness = 1.dp, color = neutral02)
                    ProfileDetailsHr(text = stringResource(R.string.personal_info_thai))
                    ProfileDetailsItem(text = stringResource(R.string.title), value = thaiPrefix)
                    ProfileDetailsItem(text = stringResource(R.string.first_name), value = thaiName)
                    ProfileDetailsItem(
                        text = stringResource(R.string.middle_name), value = thaiMiddleName)
                    ProfileDetailsItem(
                        text = stringResource(R.string.last_name), value = thaiSurname)
                    ProfileDetailsHr(text = stringResource(R.string.personal_info_eng))
                    ProfileDetailsItem(text = stringResource(R.string.title), value = engPrefix)
                    ProfileDetailsItem(text = stringResource(R.string.first_name), value = engName)
                    ProfileDetailsItem(
                        text = stringResource(R.string.middle_name), value = engMiddleName)
                    ProfileDetailsItem(
                        text = stringResource(R.string.last_name), value = engSurname)
                    Spacer(modifier = Modifier.height(48.dp))
                  }
                }
      }
}

@Composable
fun ProfileDetailsItem(text: String, value: String) {
  Row(
      modifier = Modifier.padding(vertical = 16.dp),
      verticalAlignment = Alignment.CenterVertically) {
        Text(
            text = text,
            color = primaryBlack,
            fontSize = 20.sp,
            fontWeight = FontWeight.W700,
        )
        Spacer(modifier = Modifier.weight(1f))
        Text(
            text = if (value.isEmpty()) text else value,
            color = if (value.isEmpty()) neutral04 else primaryDarkBlue,
            fontSize = 20.sp,
            fontWeight = FontWeight.W400,
        )
      }
  HorizontalDivider(thickness = 1.dp, color = neutral02)
}
