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
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Scaffold
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Close
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import co.finema.thaidotidbyfinema.R
import co.finema.thaidotidbyfinema.databases.signatureimages.SignatureImageViewModel
import co.finema.thaidotidbyfinema.uis.primaryDarkBlue
import co.finema.thaidotidbyfinema.uis.secondaryGray
import co.finema.thaidotidbyfinema.uis.white
import co.finema.thaidotidbyfinema.uis.whiteBG

@Composable
fun SignPadScreen(navController: NavController, signatureImageViewModel: SignatureImageViewModel) {
    Scaffold(backgroundColor = whiteBG) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(it)
                .padding(horizontal = 16.dp),
              ) {
            Spacer(modifier = Modifier.height(80.dp))
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .weight(1f)
                    .clip(RoundedCornerShape(1.dp))
                    .border(width = 1.dp, color = secondaryGray, shape = RoundedCornerShape(1.dp))
                    .background(white),
                contentAlignment = Alignment.Center,
               ) {
                Row(
                    modifier = Modifier.fillMaxWidth()
                   ) {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(imageVector = Icons.Rounded.Close, contentDescription = null, tint = primaryDarkBlue)
                    }
                    Spacer(modifier = Modifier.weight(1f))
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(imageVector = Icons.Rounded.Close, contentDescription = null, tint = primaryDarkBlue)
                    }
                }
            }
            Box(modifier = Modifier.padding(vertical = 24.dp)) {
                Row(
                    modifier = Modifier.fillMaxWidth()
                   ) {
                    Image(
                        painter = painterResource(id = R.drawable.buttontrueactive),
                        contentDescription = null,
                        modifier = Modifier
                            .height(72.dp)
                            .clip(RoundedCornerShape(72.dp))
                            .clickable(onClick = {})
                         )
                    Spacer(modifier = Modifier.weight(1f))
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(imageVector = Icons.Rounded.Close, contentDescription = null, tint = primaryDarkBlue)
                    }
                }
            }

        }
    }
}
