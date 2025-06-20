@file:Suppress("UsingMaterialAndMaterial3Libraries")

package co.finema.thaidotidbyfinema.uis.screens.profile

import android.content.Intent
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.net.toUri
import androidx.navigation.NavController
import co.finema.thaidotidbyfinema.R
import co.finema.thaidotidbyfinema.uis.blue05
import co.finema.thaidotidbyfinema.uis.components.AppBarOptBack
import co.finema.thaidotidbyfinema.uis.neutral04
import co.finema.thaidotidbyfinema.uis.primaryBlack
import co.finema.thaidotidbyfinema.uis.white

const val email = "contact@thai.id"

@Composable
fun SupportScreen(navController: NavController) {
    Scaffold(topBar = { AppBarOptBack(containerColor = white, text = stringResource(R.string.help_support), onClick = { navController.popBackStack() }) }, backgroundColor = white) {
        Column(modifier = Modifier
            .fillMaxSize()
            .padding(it)
            .padding(horizontal = 16.dp), horizontalAlignment = Alignment.CenterHorizontally) {
            val context = LocalContext.current
            val subject = stringResource(R.string.subject)
            val body = stringResource(R.string.body)
            val packageInfo = context.packageManager.getPackageInfo(context.packageName, 0)
            val versionName = packageInfo.versionName
            val versionCode = packageInfo.longVersionCode
            Spacer(modifier = Modifier.height(32.dp))
            Text(text = stringResource(R.string.if_you_encounter), color = primaryBlack, fontSize = 20.sp, fontWeight = FontWeight.W400, textAlign = TextAlign.Center, lineHeight = 32.sp)
            Spacer(modifier = Modifier.height(8.dp))
            TextButton(
                onClick = {
                    val uri = "mailto:$email".toUri().buildUpon().appendQueryParameter("subject", subject).appendQueryParameter("body", body).build()
                    val intent = Intent(Intent.ACTION_SENDTO).apply { data = uri }
                    if (intent.resolveActivity(context.packageManager) != null) context.startActivity(intent)
                }
            ) {
                Text(text = email, color = blue05, fontSize = 20.sp, fontWeight = FontWeight.W700, textDecoration = TextDecoration.Underline)
            }
            Spacer(modifier = Modifier.weight(1f))
            Text(text = "${stringResource(R.string.version)} $versionName build $versionCode", color = neutral04, fontSize = 20.sp, fontWeight = FontWeight.W400)
            Spacer(modifier = Modifier.height(48.dp))
        }
    }
}
