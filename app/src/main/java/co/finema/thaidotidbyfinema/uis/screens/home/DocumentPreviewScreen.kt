@file:Suppress("UsingMaterialAndMaterial3Libraries")

package co.finema.thaidotidbyfinema.uis.screens.home

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.CircularProgressIndicator
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
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableIntState
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.navigation.NavController
import co.finema.thaidotidbyfinema.R
import co.finema.thaidotidbyfinema.databases.layouthistories.LayoutHistory
import co.finema.thaidotidbyfinema.databases.layouthistories.LayoutHistoryViewModel
import co.finema.thaidotidbyfinema.enums.DocumentLayout
import co.finema.thaidotidbyfinema.uis.Screen
import co.finema.thaidotidbyfinema.uis.components.GradientButton
import co.finema.thaidotidbyfinema.uis.gradient
import co.finema.thaidotidbyfinema.uis.lightBlue07
import co.finema.thaidotidbyfinema.uis.primaryBlack
import co.finema.thaidotidbyfinema.uis.white
import co.finema.thaidotidbyfinema.uis.whiteBG
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DocumentPreviewScreen(navController: NavController, layoutHistoryViewModel: LayoutHistoryViewModel, currentLayoutHistoryId: MutableIntState) {
    val context = LocalContext.current
    val scope = rememberCoroutineScope()
    val snackbarState = remember { SnackbarHostState() }
    val deleteDocSuccessfully = stringResource(R.string.delete_doc_successfully)
    val layoutHistory by layoutHistoryViewModel.layoutHistory.collectAsState()
    var currentLayoutHistory by remember { mutableStateOf<LayoutHistory?>(null) }
    var loading by remember { mutableStateOf(false) }
    LaunchedEffect(currentLayoutHistoryId.intValue, layoutHistory) {
        for (history in layoutHistory) if (history.id == currentLayoutHistoryId.intValue) currentLayoutHistory = history
    }
    var showDeleteDialog by remember { mutableStateOf(false) }
    if (showDeleteDialog) {
        Dialog(onDismissRequest = {}) {
            Box(
                modifier = Modifier
                    .clip(RoundedCornerShape(8.dp))
                    .background(white)
                    .fillMaxWidth()
               ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp), horizontalAlignment = Alignment.CenterHorizontally
                      ) {
                    Spacer(modifier = Modifier.height(40.dp))
                    Text(
                        text = stringResource(R.string.do_you_want_to_delete_this_document), color = primaryBlack, fontSize = 24.sp, fontWeight = FontWeight.W700, textAlign = TextAlign.Center
                        )
                    Spacer(modifier = Modifier.height(32.dp))
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Box(
                            modifier = Modifier
                                .height(56.dp)
                                .weight(1f)
                                .clip(RoundedCornerShape(56.dp))
                                .background(brush = gradient)
                                .clickable(
                                    onClick = {
                                        loading = true
                                        showDeleteDialog = false
                                        layoutHistoryViewModel.removeLayoutHistory(currentLayoutHistoryId.intValue)
                                        scope.launch { snackbarState.showSnackbar(deleteDocSuccessfully) }.invokeOnCompletion { navController.popBackStack() }
                                    }),
                            contentAlignment = Alignment.Center,
                           ) {
                            Text(text = stringResource(R.string.delete), color = white, fontSize = 24.sp, fontWeight = FontWeight.W700)
                        }
                        Spacer(modifier = Modifier.width(16.dp))
                        Box(
                            modifier = Modifier
                                .height(56.dp)
                                .weight(1f)
                                .background(white)
                                .border(width = 2.dp, color = lightBlue07, shape = RoundedCornerShape(56.dp))
                                .clip(RoundedCornerShape(56.dp))
                                .clickable(onClick = {
                                    showDeleteDialog = false
                                }),
                            contentAlignment = Alignment.Center,
                           ) {
                            Text(text = stringResource(R.string.cancel), color = lightBlue07, fontSize = 24.sp, fontWeight = FontWeight.W700)
                        }
                    }
                    Spacer(modifier = Modifier.height(32.dp))
                }
            }
        }
    }
    Scaffold(
        snackbarHost = { SnackbarHost(hostState = snackbarState) },
        topBar = {
            CenterAlignedTopAppBar(
                title = {
                    Text(
                        text = currentLayoutHistory?.userDefinedName ?: stringResource(R.string.sample_document), color = primaryBlack, fontSize = 24.sp, fontWeight = FontWeight.W700
                        )
                },
                navigationIcon = { IconButton(onClick = { navController.popBackStack() }) { Icon(imageVector = Icons.Rounded.ArrowBackIosNew, contentDescription = null) } },
                actions = {
                    IconButton(onClick = {
                        if (!loading) showDeleteDialog = true
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
                    enabled = !loading,
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
              ) {
            currentLayoutHistory?.let {
                when (DocumentLayout.valueOf(it.documentLayout)) {
                    DocumentLayout.ONE_SIDE_CARD -> {}
                    DocumentLayout.TWO_SIDE_CARD -> {}
                    DocumentLayout.ONE_SIDE_HALF_A4 -> {}
                    DocumentLayout.TWO_SIDE_HALF_A4 -> {}
                    DocumentLayout.FULL_A4 -> {}
                }
            } ?: run {
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(it), contentAlignment = Alignment.Center
                   ) { CircularProgressIndicator() }
            }

        }
    }
}
