@file:Suppress("UsingMaterialAndMaterial3Libraries")

package co.finema.thaidotidbyfinema.uis.screens.home

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.ArrowBackIosNew
import androidx.compose.material.icons.rounded.Delete
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableIntState
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import co.finema.thaidotidbyfinema.R
import co.finema.thaidotidbyfinema.databases.layouthistories.LayoutHistoryViewModel
import co.finema.thaidotidbyfinema.uis.Screen
import co.finema.thaidotidbyfinema.uis.components.GradientButton
import co.finema.thaidotidbyfinema.uis.primaryBlack
import co.finema.thaidotidbyfinema.uis.white
import co.finema.thaidotidbyfinema.uis.whiteBG

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DocumentPreviewScreen(navController: NavController, layoutHistoryViewModel: LayoutHistoryViewModel, currentLayoutHistoryId: MutableIntState) {
    val context = LocalContext.current
    val scope = rememberCoroutineScope()
    val snackbarState = remember { SnackbarHostState() }
    val deleteDocSuccessfully = stringResource(R.string.delete_doc_successfully)
    val layoutHistory by layoutHistoryViewModel.layoutHistory.collectAsState()
    Scaffold(
        snackbarHost = { SnackbarHost(hostState = snackbarState) },
        topBar = {
            CenterAlignedTopAppBar(
                title = { Text(text = stringResource(R.string.my_profile), color = primaryBlack, fontSize = 24.sp, fontWeight = FontWeight.W700) },
                navigationIcon = { IconButton(onClick = { navController.popBackStack() }) { Icon(imageVector = Icons.Rounded.ArrowBackIosNew, contentDescription = null) } },
                actions = {
                    IconButton(onClick = {
                        //
                    }) {
                        Icon(imageVector = Icons.Rounded.Delete, contentDescription = null, tint = primaryBlack)
                    }
                },
                colors = TopAppBarDefaults.centerAlignedTopAppBarColors(containerColor = white, navigationIconContentColor = primaryBlack, actionIconContentColor = primaryBlack),
                                  )
        },
        bottomBar = {
            Box(
                modifier = Modifier
                    .background(white)
                    .fillMaxWidth()
                    .padding(start = 16.dp, top = 16.dp, end = 16.dp, bottom = 48.dp), contentAlignment = Alignment.Center
               ) {
                GradientButton(
                    onClick = {
                        navController.navigate(route = Screen.CreateCertifiedScreen.route)
                    },
                    text = stringResource(R.string.make_a_cert),
                              )
            }
        },
        backgroundColor = whiteBG,
            ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(it)
                .padding(horizontal = 16.dp), verticalArrangement = Arrangement.Center, horizontalAlignment = Alignment.CenterHorizontally
              ) {}
    }
}
