@file:Suppress("UsingMaterialAndMaterial3Libraries")

package co.finema.thaidotidbyfinema.uis.screens.home

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Close
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableIntState
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import co.finema.thaidotidbyfinema.R
import co.finema.thaidotidbyfinema.databases.signatureimages.SignatureImageViewModel
import co.finema.thaidotidbyfinema.uis.primaryBlack
import co.finema.thaidotidbyfinema.uis.white

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SignatureListScreen(navController: NavController, signatureImageViewModel: SignatureImageViewModel, currentSignatureImageId: MutableIntState) {
    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = { Text(text = stringResource(R.string.your_saved_signature), color = primaryBlack, fontSize = 24.sp, fontWeight = FontWeight.W700) },
                navigationIcon = {
                    IconButton(onClick = {
                        navController.popBackStack()
                    }) { Icon(imageVector = Icons.Rounded.Close, contentDescription = null) }
                },
                colors = TopAppBarDefaults.centerAlignedTopAppBarColors(containerColor = white, navigationIconContentColor = primaryBlack, actionIconContentColor = primaryBlack),
                                  )
        },
        backgroundColor = white,
            ) {
        LazyColumn(modifier = Modifier
            .fillMaxSize()
            .padding(it)
            .padding(horizontal = 16.dp)
                  ) {}
    }
}
