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
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.net.toUri
import androidx.navigation.NavController
import co.finema.thaidotidbyfinema.R
import co.finema.thaidotidbyfinema.databases.signatureimages.SignatureImageViewModel
import co.finema.thaidotidbyfinema.uis.blue02
import co.finema.thaidotidbyfinema.uis.neutral01
import co.finema.thaidotidbyfinema.uis.primaryBlack
import co.finema.thaidotidbyfinema.uis.white
import coil.compose.rememberAsyncImagePainter

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SignatureListScreen(navController: NavController, signatureImageViewModel: SignatureImageViewModel, currentSignatureImageId: MutableIntState) {
    val signatureImage by signatureImageViewModel.signatureImage.collectAsState()
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
                                    signatureImageViewModel.removeSignatureImage(signature.id)
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
                            Text(text = "Last item")
                        }
                        }
                        Spacer(modifier = Modifier.height(16.dp))
                    }

                }
            }
        }

    }
}
