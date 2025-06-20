@file:Suppress("UsingMaterialAndMaterial3Libraries")

package co.finema.thaidotidbyfinema.uis.screens.profile

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.TextButton
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.rounded.ArrowForwardIos
import androidx.compose.material.icons.rounded.BorderColor
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import co.finema.thaidotidbyfinema.R
import co.finema.thaidotidbyfinema.cornerRadius
import co.finema.thaidotidbyfinema.uis.Screen
import co.finema.thaidotidbyfinema.uis.components.AppBarOptBack
import co.finema.thaidotidbyfinema.uis.neutral05
import co.finema.thaidotidbyfinema.uis.primaryBlack
import co.finema.thaidotidbyfinema.uis.primaryDarkBlue
import co.finema.thaidotidbyfinema.uis.secondaryGray
import co.finema.thaidotidbyfinema.uis.white
import co.finema.thaidotidbyfinema.uis.whiteBG

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProfileTab(navController: NavController) {
    Scaffold(topBar = { AppBarOptBack(containerColor = whiteBG, text = stringResource(R.string.profile)) }, backgroundColor = whiteBG) {
        Column(modifier = Modifier
            .fillMaxSize()
            .padding(it)
            .padding(horizontal = 16.dp), horizontalAlignment = Alignment.CenterHorizontally) {
            Card(modifier = Modifier.fillMaxWidth(), shape = RoundedCornerShape(cornerRadius), backgroundColor = white) {
                Row(modifier = Modifier
                    .fillMaxWidth()
                    .padding(all = 16.dp), verticalAlignment = Alignment.CenterVertically) {
                    Image(
                        painter = painterResource(id = R.drawable.thai_id_app_icon),
                        contentDescription = null,
                        contentScale = ContentScale.Fit,
                        modifier = Modifier
                            .size(64.dp)
                            .border(BorderStroke(1.dp, secondaryGray), CircleShape)
                            .padding(4.dp)
                            .clip(CircleShape),
                    )
                    Spacer(modifier = Modifier.width(16.dp))
                    Text(text = stringResource(R.string.my_account), color = primaryBlack, fontSize = 24.sp, fontWeight = FontWeight.W700)
                    Spacer(modifier = Modifier.weight(1f))
                    IconButton(onClick = { navController.navigate(route = Screen.ProfileDetailsScreen.route) }) {
                        Icon(imageVector = Icons.Rounded.BorderColor, contentDescription = null, tint = neutral05)
                    }
                }
            }
            Spacer(modifier = Modifier.height(16.dp))
            Card(modifier = Modifier.fillMaxWidth(), shape = RoundedCornerShape(cornerRadius), backgroundColor = white) {
                Column(modifier = Modifier.fillMaxWidth()) {
                    Spacer(modifier = Modifier.height(32.dp))
                    Text(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 24.dp),
                        text = stringResource(R.string.settings),
                        color = primaryBlack,
                        fontSize = 20.sp,
                        fontWeight = FontWeight.W700,
                    )
                    Spacer(modifier = Modifier.height(16.dp))
                    SettingsMenu(text = stringResource(R.string.login_settings), onClick = { navController.navigate(route = Screen.SettingsScreen.route) })
                    SettingsMenu(text = stringResource(R.string.language), onClick = { navController.navigate(route = Screen.LocalizationSettingsScreen.route) })
                    SettingsMenu(text = stringResource(R.string.privacy_policy), onClick = { navController.navigate(route = Screen.PolicyAndSafetyScreen.route) })
                    SettingsMenu(text = stringResource(R.string.terms), onClick = { navController.navigate(route = Screen.TermsScreen.route) })
                    SettingsMenu(text = stringResource(R.string.help_support), onClick = { navController.navigate(route = Screen.SupportScreen.route) })
                    Spacer(modifier = Modifier.height(16.dp))
                }
            }
        }
    }
}

@Composable
fun SettingsMenu(text: String, onClick: () -> Unit) {
    TextButton(onClick = onClick) {
        Row(modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp, horizontal = 16.dp), verticalAlignment = Alignment.CenterVertically) {
            Text(text = text, color = primaryBlack, fontSize = 24.sp, fontWeight = FontWeight.W400)
            Spacer(modifier = Modifier.weight(1f))
            Icon(imageVector = Icons.AutoMirrored.Rounded.ArrowForwardIos, contentDescription = null, tint = primaryDarkBlue)
        }
    }
}
