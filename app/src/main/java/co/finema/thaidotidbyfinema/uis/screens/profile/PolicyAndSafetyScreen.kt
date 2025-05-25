@file:Suppress("UsingMaterialAndMaterial3Libraries")

package co.finema.thaidotidbyfinema.uis.screens.profile

import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.viewinterop.AndroidView
import androidx.navigation.NavController
import co.finema.thaidotidbyfinema.R
import co.finema.thaidotidbyfinema.uis.components.AppBarOptBack
import co.finema.thaidotidbyfinema.uis.white

@Composable
fun PolicyAndSafetyScreen(navController: NavController) {
    Scaffold(
        topBar = {
            AppBarOptBack(
                containerColor = white, text = stringResource(R.string.privacy_policy), onClick = { navController.popBackStack() })
        }, backgroundColor = white
    ) {
        AndroidView(
            modifier = Modifier.padding(it), factory = {
                WebView(it).apply {
                    webViewClient = WebViewClient()
                    loadUrl("https://thai.id/privacy")
                }
            })
    }
}
