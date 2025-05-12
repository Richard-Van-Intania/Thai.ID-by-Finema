@file:Suppress("UsingMaterialAndMaterial3Libraries")

package co.finema.thaidotidbyfinema.uis

import androidx.compose.material.Typography
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import co.finema.thaidotidbyfinema.R

val FCIconic =
    FontFamily(
        Font(R.font.fc_iconic_bold, FontWeight.Bold, FontStyle.Normal),
        Font(R.font.fc_iconic_bold_italic, FontWeight.Bold, FontStyle.Italic),
        Font(R.font.fc_iconic_italic, FontWeight.Normal, FontStyle.Italic),
        Font(R.font.fc_iconic_regular, FontWeight.Normal, FontStyle.Normal))

val CustomTypography = Typography(defaultFontFamily = FCIconic)
