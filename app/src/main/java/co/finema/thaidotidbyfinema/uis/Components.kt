@file:Suppress("UsingMaterialAndMaterial3Libraries")

package co.finema.thaidotidbyfinema.uis

import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.ArrowBackIosNew
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AppBarOptBack(containerColor: Color, text: String, onClick: (() -> Unit)? = null) {
  CenterAlignedTopAppBar(
      title = {
        Text(
            text = text,
            color = primaryBlack,
            fontSize = 24.sp,
            fontWeight = FontWeight.W700,
        )
      },
      navigationIcon = {
        if (onClick != null)
            IconButton(onClick = onClick) {
              Icon(imageVector = Icons.Rounded.ArrowBackIosNew, contentDescription = null)
            }
      },
      colors =
          TopAppBarDefaults.centerAlignedTopAppBarColors(
              containerColor = containerColor,
              navigationIconContentColor = primaryBlack,
              actionIconContentColor = primaryBlack))
}
