@file:Suppress("UsingMaterialAndMaterial3Libraries")

package co.finema.thaidotidbyfinema.uis.screens.home

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.FloatingActionButton
import androidx.compose.material.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import co.finema.thaidotidbyfinema.databases.signatureimages.SignatureImageViewModel
import java.time.LocalDateTime

@Composable
fun SignatureListScreen(
    navController: NavController,
    signatureImageViewModel: SignatureImageViewModel,
) {
    val scope = rememberCoroutineScope()
    val signatureImage by signatureImageViewModel.signatureImage.collectAsState()
    LaunchedEffect(signatureImage) { println(signatureImage.size) }
    Scaffold(
        floatingActionButton = {
            FloatingActionButton(
                onClick = {
                    signatureImageViewModel.newDateLastUsed(
                        id = 2,
                        dateLastUsed = LocalDateTime.now().toString(),
                    )
                }
            ) {}
        }
    ) {
        LazyColumn(
            modifier = Modifier.fillMaxSize().padding(it).padding(horizontal = 16.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center,
        ) {
            items(signatureImage) {
                Text(
                    "id:${it.id} fileName:${it.fileName} dateCreated:${it.dateCreated} dateLastUsed:${it.dateLastUsed}",
                    fontSize = 16.sp,
                )
            }
        }
    }
}
