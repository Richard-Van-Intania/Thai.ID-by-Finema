@file:Suppress("UsingMaterialAndMaterial3Libraries")

package co.finema.thaidotidbyfinema.uis.screens.home

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.Checkbox
import androidx.compose.material.CheckboxDefaults
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.rounded.List
import androidx.compose.material.icons.rounded.BorderColor
import androidx.compose.material.icons.rounded.GridView
import androidx.compose.material.icons.rounded.MoreHoriz
import androidx.compose.material.icons.rounded.Sync
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableIntState
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.drawscope.scale
import androidx.compose.ui.graphics.drawscope.translate
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.core.net.toUri
import androidx.navigation.NavController
import co.finema.thaidotidbyfinema.R
import co.finema.thaidotidbyfinema.ViewLayout
import co.finema.thaidotidbyfinema.cornerRadius
import co.finema.thaidotidbyfinema.databases.layouthistories.LayoutHistoryViewModel
import co.finema.thaidotidbyfinema.enums.DocumentLayout
import co.finema.thaidotidbyfinema.repositories.UserConfigRepository
import co.finema.thaidotidbyfinema.uis.Screen
import co.finema.thaidotidbyfinema.uis.blue05
import co.finema.thaidotidbyfinema.uis.components.GradientButton
import co.finema.thaidotidbyfinema.uis.gradient
import co.finema.thaidotidbyfinema.uis.greenDev
import co.finema.thaidotidbyfinema.uis.lightBlue09
import co.finema.thaidotidbyfinema.uis.neutral01
import co.finema.thaidotidbyfinema.uis.neutral04
import co.finema.thaidotidbyfinema.uis.neutral07
import co.finema.thaidotidbyfinema.uis.primaryBlack
import co.finema.thaidotidbyfinema.uis.primaryDarkBlue
import co.finema.thaidotidbyfinema.uis.secondaryGray
import co.finema.thaidotidbyfinema.uis.white
import co.finema.thaidotidbyfinema.uis.whiteBG
import coil.compose.rememberAsyncImagePainter
import kotlinx.coroutines.launch

@Composable
fun HomeTab(navController: NavController, layoutHistoryViewModel: LayoutHistoryViewModel, layoutHistoryId: MutableIntState) {
    val configuration = LocalConfiguration.current
    val screenWidthDp = configuration.screenWidthDp
    val context = LocalContext.current
    val repository = remember { UserConfigRepository(context) }
    val scope = rememberCoroutineScope()
    val isSelectedNeverShowAgain by repository.isSelectedNeverShowAgain.collectAsState(initial = false)
    var showAskDialog by remember { mutableStateOf(false) }
    if (showAskDialog) {
        Dialog(onDismissRequest = {}) {
            Box(
                modifier = Modifier
                    .clip(RoundedCornerShape(8.dp))
                    .background(white)
                    .fillMaxWidth()
               ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp)
                      ) {
                    Spacer(modifier = Modifier.height(32.dp))
                    Text(
                        modifier = Modifier.fillMaxWidth(),
                        text = stringResource(R.string.thai_id_not_store),
                        color = primaryBlack,
                        fontSize = 24.sp,
                        fontWeight = FontWeight.W700,
                        textAlign = TextAlign.Center,
                    )
                    Spacer(modifier = Modifier.height(24.dp))
                    Text(text = stringResource(R.string.thai_id_not_store_ext), color = neutral07, fontSize = 22.sp, fontWeight = FontWeight.W400)
                    Spacer(modifier = Modifier.height(24.dp))
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Checkbox(
                            checked = isSelectedNeverShowAgain,
                            colors = CheckboxDefaults.colors(checkedColor = primaryDarkBlue),
                            onCheckedChange = { scope.launch { repository.updateIsSelectedNeverShowAgain(it) } },
                        )
                        Text(text = stringResource(R.string.do_not_show), color = neutral07, fontSize = 22.sp, fontWeight = FontWeight.W400)
                    }
                    Spacer(modifier = Modifier.height(32.dp))
                    GradientButton(
                        onClick = {
                            showAskDialog = false
                            navController.navigate(route = Screen.SelectLayoutScreen.route)
                        },
                        text = stringResource(R.string.make_a_cert),
                    )
                    Spacer(modifier = Modifier.height(32.dp))
                }
            }
        }
    }
    Scaffold(backgroundColor = whiteBG) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(it), contentAlignment = Alignment.TopCenter
           ) {
            Canvas(modifier = Modifier.fillMaxSize()) {
                val canvasWidth = size.width
                translate(top = -(canvasWidth * linearHeight(screenWidthDp))) { scale(scaleX = 2.0f, scaleY = 0.86363636f) { drawCircle(brush = gradient, radius = canvasWidth / 2.0f) } }
            }
            Column(modifier = Modifier.fillMaxSize(), horizontalAlignment = Alignment.CenterHorizontally) {
                val homeViewLayout by repository.homeViewLayout.collectAsState(initial = null)
                val layoutHistory by layoutHistoryViewModel.layoutHistory.collectAsState()
                Spacer(modifier = Modifier.height(80.dp))
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp), verticalAlignment = Alignment.CenterVertically
                   ) {
                    Image(
                        painter = painterResource(id = R.drawable.thai_id_app_icon),
                        contentDescription = null,
                        contentScale = ContentScale.Fit,
                        modifier = Modifier
                            .size(72.dp)
                            .padding(4.dp)
                            .clip(CircleShape),
                    )
                    Spacer(modifier = Modifier.width(16.dp))
                    Column {
                        Text(text = stringResource(R.string.hello), color = white, fontSize = 20.sp, fontWeight = FontWeight.W400)
                        Text(text = stringResource(R.string.thai_dot_id), color = white, fontSize = 28.sp, fontWeight = FontWeight.W700)
                    }
                    Spacer(modifier = Modifier.weight(1f))
                    Box(
                        modifier = Modifier
                            .clip(CircleShape)
                            .background(color = blue05), contentAlignment = Alignment.Center
                       ) {
                        IconButton(onClick = { navController.navigate(route = Screen.ProfileDetailsScreen.route) }) {
                            Icon(imageVector = Icons.Rounded.BorderColor, contentDescription = null, tint = white)
                        }
                    }
                }
                Spacer(modifier = Modifier.height(32.dp))
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp), contentAlignment = Alignment.Center
                   ) {
                    Card(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(112.dp), shape = RoundedCornerShape(cornerRadius), backgroundColor = white
                        ) {
                        Box(modifier = Modifier.fillMaxWidth(), contentAlignment = Alignment.CenterEnd) {
                            Image(painter = painterResource(id = R.drawable.icon_ctc), contentDescription = null, contentScale = ContentScale.Crop)
                            Row(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(start = 32.dp, end = 24.dp), verticalAlignment = Alignment.CenterVertically
                               ) {
                                Text(text = stringResource(R.string.make_a_cert), color = lightBlue09, fontSize = 28.sp, fontWeight = FontWeight.W700)
                                Spacer(modifier = Modifier.weight(1f))
                                Image(
                                    painter = painterResource(id = R.drawable.group_40854),
                                    contentDescription = null,
                                    modifier = Modifier
                                        .size(64.dp)
                                        .clip(CircleShape)
                                        .clickable(onClick = { if (isSelectedNeverShowAgain) navController.navigate(route = Screen.SelectLayoutScreen.route) else showAskDialog = true }),
                                )
                            }
                        }
                    }
                }
                Spacer(modifier = Modifier.height(16.dp))
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp), verticalAlignment = Alignment.CenterVertically
                   ) {
                    Text(text = stringResource(R.string.my_documents_and_cards), color = primaryBlack, fontSize = 24.sp, fontWeight = FontWeight.W700)
                    Spacer(modifier = Modifier.weight(1f))
                    if (layoutHistory.isNotEmpty())
                        IconButton(
                            onClick = {
                                scope.launch {
                                    if (homeViewLayout == ViewLayout.VIEW_LAYOUT_LIST) {
                                        repository.updateHomeViewLayout(ViewLayout.VIEW_LAYOUT_THUMBNAILS)
                                    } else if (homeViewLayout == ViewLayout.VIEW_LAYOUT_THUMBNAILS) {
                                        repository.updateHomeViewLayout(ViewLayout.VIEW_LAYOUT_LIST)
                                    }
                                }
                            }
                        ) {
                            Icon(
                                imageVector =
                                    when (homeViewLayout) {
                                        ViewLayout.VIEW_LAYOUT_UNSPECIFIED -> Icons.Rounded.Sync
                                        ViewLayout.VIEW_LAYOUT_LIST -> Icons.Rounded.GridView
                                        ViewLayout.VIEW_LAYOUT_THUMBNAILS -> Icons.AutoMirrored.Rounded.List
                                        ViewLayout.UNRECOGNIZED -> Icons.Rounded.Sync
                                        null -> Icons.Rounded.Sync
                                    },
                                contentDescription = null,
                                tint = primaryDarkBlue,
                            )
                        }
                }
                Spacer(modifier = Modifier.height(12.dp))
                if (layoutHistory.isEmpty()) {
                    Spacer(modifier = Modifier.height(28.dp))
                    Image(painter = painterResource(id = R.drawable.group_40772), contentDescription = null, modifier = Modifier.height(88.dp))
                    Spacer(modifier = Modifier.height(24.dp))
                    Text(text = stringResource(R.string.there_are_no_documents_displayed_on_this_page), color = neutral04, fontSize = 20.sp, fontWeight = FontWeight.W400)
                } else {
                    LazyColumn(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(horizontal = 16.dp), verticalArrangement = Arrangement.Top
                              ) {
                        if (homeViewLayout == ViewLayout.VIEW_LAYOUT_LIST) itemsIndexed(layoutHistory) { index, item ->
                            val documentLayout = DocumentLayout.valueOf(item.documentLayout)
                            Box(modifier = Modifier.padding(bottom = 16.dp)) {
                                Box(
                                    modifier = Modifier
                                        .clip(RoundedCornerShape(cornerRadius))
                                        .background(white)
                                        .fillMaxWidth()
                                        .padding(start = 16.dp, top = 16.dp, end = 8.dp, bottom = 16.dp),
                                    contentAlignment = Alignment.Center,
                                ) {
                                    Row(modifier = Modifier.fillMaxWidth(), verticalAlignment = Alignment.CenterVertically) {
                                        Box(
                                            modifier = Modifier
                                                .size(88.dp)
                                                .clip(RoundedCornerShape(1.dp))
                                                .border(width = 1.dp, color = secondaryGray, shape = RoundedCornerShape(1.dp))
                                                .background(neutral01)
                                                .clickable(
                                                    onClick = {
                                                        //
                                                    })
                                        ) {
                                            Column(
                                                modifier = Modifier
                                                    .fillMaxSize()
                                                    .padding(all = 4.dp),
                                                verticalArrangement = Arrangement.Center,
                                                horizontalAlignment = Alignment.CenterHorizontally,
                                            ) {
                                                if (
                                                    documentLayout == DocumentLayout.ONE_SIDE_CARD ||
                                                        documentLayout == DocumentLayout.ONE_SIDE_HALF_A4 ||
                                                        documentLayout == DocumentLayout.FULL_A4
                                                )
                                                    Image(
                                                        modifier = Modifier
                                                            .fillMaxSize()
                                                            .weight(1f),
                                                        painter = rememberAsyncImagePainter(model = item.layoutRawImagefileName0?.toUri()),
                                                        contentDescription = null,
                                                        contentScale = ContentScale.Fit,
                                                    )
                                                else {
                                                    Image(
                                                        modifier = Modifier
                                                            .fillMaxSize()
                                                            .weight(1f),
                                                        painter = rememberAsyncImagePainter(model = item.layoutRawImagefileName1?.toUri()),
                                                        contentDescription = null,
                                                        contentScale = ContentScale.Fit,
                                                    )
                                                    Spacer(modifier = Modifier.height(4.dp))
                                                    Image(
                                                        modifier = Modifier
                                                            .fillMaxSize()
                                                            .weight(1f),
                                                        painter = rememberAsyncImagePainter(model = item.layoutRawImagefileName2?.toUri()),
                                                        contentDescription = null,
                                                        contentScale = ContentScale.Fit,
                                                    )
                                                }
                                            }
                                        }
                                        Spacer(modifier = Modifier.width(24.dp))
                                        Column {
                                            Text(
                                                text = item.userDefinedName ?: (stringResource(R.string.untitled_document) + " (${index + 1})"),
                                                color = primaryBlack,
                                                fontSize = 20.sp,
                                                fontWeight = FontWeight.W400,
                                            )
                                            Spacer(modifier = Modifier.height(4.dp))
                                            Text(text = item.dateCreated, color = neutral04, fontSize = 20.sp, fontWeight = FontWeight.W400)
                                        }
                                        Spacer(modifier = Modifier.weight(1f))
                                        Spacer(modifier = Modifier.width(8.dp))
                                        IconButton(
                                            onClick = {
                                                //
                                            }
                                        ) {
                                            Icon(imageVector = Icons.Rounded.MoreHoriz, contentDescription = null, tint = blue05)
                                        }
                                    }
                                }
                            }
                        }
                        if (homeViewLayout == ViewLayout.VIEW_LAYOUT_THUMBNAILS) item {
                            FlowRow(horizontalArrangement = Arrangement.spacedBy(16.dp), verticalArrangement = Arrangement.spacedBy(16.dp)) {
                                layoutHistory.forEachIndexed { index, item ->
                                    Column {
                                        Box(
                                            modifier = Modifier
                                                .width(184.dp)
                                                .height(184.dp)
                                                .background(greenDev)
                                                .clickable(
                                                    onClick = {
                                                        //
                                                    }),
                                            contentAlignment = Alignment.Center,
                                           ) { Text(text = item.id.toString()) }
                                        Box(
                                            modifier = Modifier
                                                .width(184.dp)
                                                .height(56.dp)
                                                .background(white),
                                            contentAlignment = Alignment.Center,
                                           ) {
                                            Row(
                                                modifier = Modifier
                                                    .fillMaxWidth()
                                                    .padding(horizontal = 8.dp), verticalAlignment = Alignment.CenterVertically
                                               ) {
                                                Text(
                                                    text = item.userDefinedName ?: (stringResource(R.string.untitled_document) + " (${index + 1})"),
                                                    color = primaryBlack,
                                                    fontSize = 20.sp,
                                                    fontWeight = FontWeight.W400,
                                                    )
                                                Spacer(modifier = Modifier.weight(1f))
                                                Spacer(modifier = Modifier.width(8.dp))
                                                IconButton(
                                                    onClick = {
                                                        //
                                                    }) {
                                                    Icon(imageVector = Icons.Rounded.MoreHoriz, contentDescription = null, tint = blue05)
                                                }

                                            }

                                        }
                                    }

                                }
                            }
                            Spacer(modifier = Modifier.height(16.dp))
                        }
                    }
                }
            }
        }
    }
}

fun linearHeight(screenWidthDp: Int): Float {
    return (6608 - screenWidthDp).toFloat() / 7040.0f
}
