@file:Suppress("UsingMaterialAndMaterial3Libraries")

package co.finema.thaidotidbyfinema.uis.screens.profile

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import co.finema.thaidotidbyfinema.R
import co.finema.thaidotidbyfinema.repositories.UserCardRepository
import co.finema.thaidotidbyfinema.uis.components.AppBarOptBack
import co.finema.thaidotidbyfinema.uis.white

@Composable
fun ProfileDetailsScreen(navController: NavController) {
  Scaffold(
      topBar = {
        AppBarOptBack(
            containerColor = white,
            text = stringResource(R.string.my_profile),
            onClick = { navController.popBackStack() })
      },
      backgroundColor = white) {
        val context = LocalContext.current
        val repository = remember { UserCardRepository(context) }
        val idString by repository.idString.collectAsState(initial = "")
        val thaiPrefix by repository.thaiPrefix.collectAsState(initial = "")
        val thaiName by repository.thaiName.collectAsState(initial = "")
        val thaiMiddleName by repository.thaiMiddleName.collectAsState(initial = "")
        val thaiSurname by repository.thaiSurname.collectAsState(initial = "")
        val engPrefix by repository.engPrefix.collectAsState(initial = "")
        val engName by repository.engName.collectAsState(initial = "")
        val engMiddleName by repository.engMiddleName.collectAsState(initial = "")
        val engSurname by repository.engSurname.collectAsState(initial = "")
        val birthDate by repository.birthDate.collectAsState(initial = "")
        Column(
            modifier = Modifier.fillMaxSize().padding(it).padding(horizontal = 16.dp),
            horizontalAlignment = Alignment.CenterHorizontally) {}
      }
}
