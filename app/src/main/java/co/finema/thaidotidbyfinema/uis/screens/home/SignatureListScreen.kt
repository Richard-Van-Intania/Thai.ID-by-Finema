@file:Suppress("UsingMaterialAndMaterial3Libraries")

package co.finema.thaidotidbyfinema.uis.screens.home

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
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
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.core.net.toUri
import androidx.navigation.NavController
import co.finema.thaidotidbyfinema.R
import co.finema.thaidotidbyfinema.databases.signatureimages.SignatureImageViewModel
import co.finema.thaidotidbyfinema.uis.blue02
import co.finema.thaidotidbyfinema.uis.gradient
import co.finema.thaidotidbyfinema.uis.lightBlue07
import co.finema.thaidotidbyfinema.uis.neutral01
import co.finema.thaidotidbyfinema.uis.primaryBlack
import co.finema.thaidotidbyfinema.uis.white
import coil.compose.rememberAsyncImagePainter

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SignatureListScreen(navController: NavController, signatureImageViewModel: SignatureImageViewModel, currentSignatureImageId: MutableIntState) {
    val signatureImage by signatureImageViewModel.signatureImage.collectAsState()
    var deleteSignatureImageId by remember { mutableIntStateOf(0) }
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
                        text = stringResource(R.string.do_you_want_to_delete_this_photo), color = primaryBlack, fontSize = 24.sp, fontWeight = FontWeight.W700, textAlign = TextAlign.Center
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
                                        signatureImageViewModel.removeSignatureImage(deleteSignatureImageId)
                                        if (deleteSignatureImageId == currentSignatureImageId.intValue) currentSignatureImageId.intValue = 0
                                        showDeleteDialog = false
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
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(it)
                .padding(horizontal = 16.dp), horizontalAlignment = Alignment.CenterHorizontally
              ) {
            Spacer(modifier = Modifier.height(16.dp))
            Text(
                modifier = Modifier.fillMaxWidth(),
                text = stringResource(R.string.choose_your_signature),
                color = primaryBlack,
                fontSize = 22.sp,
                fontWeight = FontWeight.W700,
                textAlign = TextAlign.Left
                )
            Spacer(modifier = Modifier.height(16.dp))
            LazyColumn(
                modifier = Modifier.fillMaxSize(),
                      ) {
                items(signatureImage) { signature ->
                    Column {
                        Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(88.dp)
                            .clip(RoundedCornerShape(4.dp))
                            .border(width = 1.dp, color = blue02, shape = RoundedCornerShape(4.dp))
                            .background(neutral01)
                            .clickable(
                                onClick = {
                                    currentSignatureImageId.intValue = signature.id
                                    navController.popBackStack()
                                }), contentAlignment = Alignment.Center
                           ) {
                        Row(
                            modifier = Modifier
                                .fillMaxSize()
                                .padding(all = 16.dp), verticalAlignment = Alignment.CenterVertically
                           ) {
                            Image(
                                painter = rememberAsyncImagePainter(model = signature.fileName.toUri()), contentDescription = null,
                                 )
                            Spacer(modifier = Modifier.weight(1f))
                            Image(
                                painter = painterResource(id = R.drawable.vector), contentDescription = null, modifier = Modifier
                                    .height(24.dp)
                                    .clip(RoundedCornerShape(24.dp))
                                    .clickable {
                                        deleteSignatureImageId = signature.id
                                        showDeleteDialog = true
                                    })
                            Spacer(modifier = Modifier.width(8.dp))
                        }
                        }
                        Spacer(modifier = Modifier.height(16.dp))
                    }
                }
            }
        }
    }
}
