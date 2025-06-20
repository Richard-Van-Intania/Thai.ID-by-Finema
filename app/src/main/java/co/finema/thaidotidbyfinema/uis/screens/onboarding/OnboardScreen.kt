@file:Suppress("UsingMaterialAndMaterial3Libraries")

package co.finema.thaidotidbyfinema.uis.screens.onboarding

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import co.finema.thaidotidbyfinema.R
import co.finema.thaidotidbyfinema.uis.Screen
import co.finema.thaidotidbyfinema.uis.components.GradientButton
import co.finema.thaidotidbyfinema.uis.neutral07
import co.finema.thaidotidbyfinema.uis.primaryBlack
import co.finema.thaidotidbyfinema.uis.primaryRed
import co.finema.thaidotidbyfinema.uis.secondaryBlueGray
import co.finema.thaidotidbyfinema.uis.secondaryGray

data class TipsData(val imageResource: Int, val head: Int, val body: Int)

val tipsList =
    listOf(
        TipsData(imageResource = R.drawable.welcome3, head = R.string.certified_true_copy, body = R.string.easily_make),
        TipsData(imageResource = R.drawable.welcome2, head = R.string.identity_wallet, body = R.string.securely_store),
        TipsData(imageResource = R.drawable.welcome4, head = R.string.conveniently_create, body = R.string.create_and_verify),
        TipsData(imageResource = R.drawable.welcome5, head = R.string.save_and_share, body = R.string.save_and_share_documents),
    )

@Composable
fun OnboardScreen(navController: NavHostController) {
    Scaffold(
        bottomBar = {
            Box(modifier = Modifier
                .fillMaxWidth()
                .padding(all = 48.dp), contentAlignment = Alignment.Center) {
                GradientButton(onClick = { navController.navigate(route = Screen.TermsScreen.route) }, text = stringResource(R.string.log_in))
            }
        }
    ) {
        Column(modifier = Modifier
            .fillMaxSize()
            .padding(it)
            .padding(horizontal = 16.dp), horizontalAlignment = Alignment.CenterHorizontally) {
            Spacer(modifier = Modifier.height(48.dp))
            Box(modifier = Modifier.fillMaxWidth(), contentAlignment = Alignment.CenterEnd) {
                TextButton(onClick = { navController.navigate(route = Screen.TermsScreen.route) }) {
                    Text(text = stringResource(R.string.skip), color = secondaryBlueGray, fontSize = 20.sp, fontWeight = FontWeight.W400)
                }
            }
            Spacer(modifier = Modifier.weight(2f))
            val pagerState = rememberPagerState(pageCount = { tipsList.size })
            Box(modifier = Modifier.height(560.dp), contentAlignment = Alignment.Center) {
                HorizontalPager(state = pagerState, modifier = Modifier.fillMaxWidth()) { page ->
                    Column(modifier = Modifier.fillMaxWidth(), horizontalAlignment = Alignment.CenterHorizontally) {
                        Image(painter = painterResource(id = tipsList[page].imageResource), contentDescription = null, modifier = Modifier.height(328.dp))
                        Spacer(modifier = Modifier.height(56.dp))
                        Text(text = stringResource(tipsList[page].head), color = primaryBlack, fontSize = 32.sp, fontWeight = FontWeight.W700, textAlign = TextAlign.Center)
                        Spacer(modifier = Modifier.height(16.dp))
                        Text(text = stringResource(tipsList[page].body), color = neutral07, fontSize = 24.sp, fontWeight = FontWeight.W400, textAlign = TextAlign.Center)
                    }
                }
            }
            Spacer(modifier = Modifier.weight(1f))
            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.Center) {
                repeat(pagerState.pageCount) { iteration ->
                    val color = if (pagerState.currentPage == iteration) primaryRed else secondaryGray
                    Box(modifier = Modifier
                        .padding(horizontal = 4.dp)
                        .clip(CircleShape)
                        .background(color)
                        .size(12.dp))
                }
            }
            Spacer(modifier = Modifier.weight(2f))
            Spacer(modifier = Modifier.height(104.dp))
        }
    }
}
