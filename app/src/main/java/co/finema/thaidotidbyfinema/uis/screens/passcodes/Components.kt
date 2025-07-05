@file:Suppress("UsingMaterialAndMaterial3Libraries")

package co.finema.thaidotidbyfinema.uis.screens.passcodes

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import co.finema.thaidotidbyfinema.uis.primaryDarkBlue
import co.finema.thaidotidbyfinema.uis.white

@Composable
fun PasscodeButton(text: String, onClick: () -> Unit) {
    Box(
     modifier = Modifier.size(80.dp).clip(CircleShape).background(white).border(width = 1.dp, color = primaryDarkBlue, shape = CircleShape).clickable(onClick = onClick),
     contentAlignment = Alignment.Center,
    ) {
        Text(text = text, color = primaryDarkBlue, fontSize = 24.sp, fontWeight = FontWeight.W700)
    }
}

@Composable
fun BottomButton(imageVector: ImageVector, onClick: () -> Unit) {
    Box(modifier = Modifier.size(80.dp).clip(CircleShape).background(white).clickable(onClick = onClick), contentAlignment = Alignment.Center) {
        Icon(imageVector = imageVector, tint = primaryDarkBlue, contentDescription = null)
    }
}

@Composable fun FilledDot() = Box(modifier = Modifier.size(16.dp).clip(CircleShape).background(primaryDarkBlue))

@Composable fun OutlinedDot() = Box(modifier = Modifier.size(16.dp).clip(CircleShape).background(white).border(width = 1.dp, color = primaryDarkBlue, shape = CircleShape))
