@file:Suppress("UsingMaterialAndMaterial3Libraries")

package co.finema.thaidotidbyfinema.uis.screens.home

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicText
import androidx.compose.foundation.text.TextAutoSize
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.TextButton
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Close
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableIntState
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import androidx.navigation.NavController
import co.finema.thaidotidbyfinema.R
import co.finema.thaidotidbyfinema.cornerRadius
import co.finema.thaidotidbyfinema.enums.DocumentLayout
import co.finema.thaidotidbyfinema.repositories.UserConfigRepository
import co.finema.thaidotidbyfinema.uis.FCIconic
import co.finema.thaidotidbyfinema.uis.Screen
import co.finema.thaidotidbyfinema.uis.components.AppBarOptBack
import co.finema.thaidotidbyfinema.uis.components.GradientButton
import co.finema.thaidotidbyfinema.uis.primaryBlack
import co.finema.thaidotidbyfinema.uis.secondaryGray
import co.finema.thaidotidbyfinema.uis.white
import co.finema.thaidotidbyfinema.uis.whiteBG
import dev.chrisbanes.haze.hazeEffect
import dev.chrisbanes.haze.hazeSource
import dev.chrisbanes.haze.rememberHazeState
import kotlinx.coroutines.launch

data class LayoutItemButtonData(
    val explanation: Int,
    val example: Int,
    val image1: Int,
    val image2: Int?,
    val bottomText: Int,
    val unselectedFilePath: Int,
    val selectedFilePath: Int,
    val documentLayout: DocumentLayout,
    val widthFactor: Double,
)

val layoutItems = listOf(
    LayoutItemButtonData(
        explanation = R.string.oneSideCard_explanation,
        example = R.string.oneSideCard_example,
        image1 = R.drawable.luna_id_card_new,
        image2 = null,
        bottomText = R.string.oneSideCard_layout,
        unselectedFilePath = R.drawable.card1,
        selectedFilePath = R.drawable.card2,
        documentLayout = DocumentLayout.ONE_SIDE_CARD,
        widthFactor = 0.25,
    ),
    LayoutItemButtonData(
        explanation = R.string.twoSideCard_explanation,
        example = R.string.twoSideCard_example,
        image1 = R.drawable.luffy_license_new,
        image2 = R.drawable.luffy_license2_new,
        bottomText = R.string.twoSideCard_layout,
        unselectedFilePath = R.drawable.card2_1,
        selectedFilePath = R.drawable.card2_2,
        documentLayout = DocumentLayout.TWO_SIDE_CARD,
        widthFactor = 0.25,
    ),
    LayoutItemButtonData(
        explanation = R.string.twoHalfA4_explanation,
        example = R.string.twoHalfA4_example,
        image1 = R.drawable.house_front_new,
        image2 = R.drawable.house_back_new,
        bottomText = R.string.twoHalfA4_layout,
        unselectedFilePath = R.drawable.house1,
        selectedFilePath = R.drawable.house2,
        documentLayout = DocumentLayout.TWO_SIDE_HALF_A4,
        widthFactor = 0.375,
    ),
    LayoutItemButtonData(
        explanation = R.string.oneHalfA4_explanation,
        example = R.string.oneHalfA4_example,
        image1 = R.drawable.bookbank_new,
        image2 = null,
        bottomText = R.string.oneHalfA4_layout,
        unselectedFilePath = R.drawable.bookbank1,
        selectedFilePath = R.drawable.bookbank2,
        documentLayout = DocumentLayout.ONE_SIDE_HALF_A4,
        widthFactor = 0.375,
    ),
    LayoutItemButtonData(
        explanation = R.string.fullA4_explanation,
        example = R.string.fullA4_example,
        image1 = R.drawable.transcript_new,
        image2 = null,
        bottomText = R.string.fullA4_layout,
        unselectedFilePath = R.drawable.doc1,
        selectedFilePath = R.drawable.doc2,
        documentLayout = DocumentLayout.FULL_A4,
        widthFactor = 0.5,
    ),
)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SelectLayoutScreen(
    navController: NavController,
    layoutIndex: MutableIntState,
    placeholderFilePath0: MutableState<String>,
    placeholderFilePath1: MutableState<String>,
    placeholderFilePath2: MutableState<String>,
) {
    val configuration = LocalConfiguration.current
    val screenWidthDp = configuration.screenWidthDp
    val context = LocalContext.current
    val repository = remember { UserConfigRepository(context) }
    val scope = rememberCoroutineScope()
    val hazeState = rememberHazeState()
    val hideInstruction by repository.hideInstruction.collectAsState(initial = true)
    var showInstructionDialog by remember { mutableStateOf(false) }
    LaunchedEffect(hideInstruction) { if (!hideInstruction) showInstructionDialog = true }
    if (showInstructionDialog) {
        Dialog(
            onDismissRequest = {}, properties = DialogProperties(
                dismissOnBackPress = false, dismissOnClickOutside = false, usePlatformDefaultWidth = false, decorFitsSystemWindows = false
            )
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .hazeEffect(state = hazeState)
                    .background(Color.Black.copy(alpha = 0.5f))
                    .padding(horizontal = 16.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Spacer(modifier = Modifier.height(48.dp))
                Box(modifier = Modifier.fillMaxWidth(), contentAlignment = Alignment.CenterEnd) {
                    IconButton(onClick = { showInstructionDialog = false }) {
                        Icon(imageVector = Icons.Rounded.Close, contentDescription = null, tint = white)
                    }
                }
                Spacer(modifier = Modifier.weight(1f))
                Image(
                    painter = painterResource(id = R.drawable.group_40912), contentDescription = null, modifier = Modifier.height(176.dp)
                )
                Spacer(modifier = Modifier.height(48.dp))
                Text(
                    text = stringResource(R.string.you_can_select_document_format), color = white, fontSize = 24.sp, fontWeight = FontWeight.W700, textAlign = TextAlign.Center
                )
                Spacer(modifier = Modifier.height(48.dp))
                Image(
                    painter = painterResource(id = R.drawable.vector_24), contentDescription = null, modifier = Modifier.height(72.dp)
                )
                Spacer(modifier = Modifier.weight(2f))
                TextButton(
                    onClick = {
                        scope.launch { repository.updateHideInstruction(true) }
                        showInstructionDialog = false
                    }) {
                    Text(
                        text = stringResource(R.string.do_not_show), style = TextStyle(
                            color = white, fontSize = 24.sp, textDecoration = TextDecoration.Underline, fontFamily = FCIconic, fontWeight = FontWeight.W400
                        )
                    )
                }
                Spacer(modifier = Modifier.height(56.dp))
            }
        }
    }
    Scaffold(
        modifier = Modifier.hazeSource(state = hazeState), topBar = {
        AppBarOptBack(
            containerColor = white, text = stringResource(R.string.select_document_format), onClick = { navController.popBackStack() })
    }, bottomBar = {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 16.dp, top = 16.dp, end = 16.dp, bottom = 48.dp), contentAlignment = Alignment.Center
        ) {
            GradientButton(
                onClick = {
                    placeholderFilePath0.value = ""
                    placeholderFilePath1.value = ""
                    placeholderFilePath2.value = ""
                    navController.navigate(route = Screen.DocumentPlaceholderScreen.route)
                }, text = stringResource(R.string.next)
            )
        }
    }, backgroundColor = white
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(it)
                .padding(horizontal = 16.dp)
        ) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(cornerRadius))
                    .border(
                        width = 1.dp, color = secondaryGray, shape = RoundedCornerShape(cornerRadius)
                    )
                    .background(whiteBG)
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp), horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Spacer(modifier = Modifier.height(24.dp))
                    Box(
                        modifier = Modifier
                            .padding(horizontal = (screenWidthDp * 0.15).dp)
                            .aspectRatio((210.0 / 297.0).toFloat())
                            .background(white)
                            .border(width = 1.dp, color = secondaryGray),
                        contentAlignment = Alignment.Center
                    ) {
                        Column(
                            verticalArrangement = Arrangement.SpaceEvenly, horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            Image(
                                painter = painterResource(
                                    id = layoutItems[layoutIndex.intValue].image1
                                ), contentDescription = null, modifier = Modifier.width(
                                    (screenWidthDp * layoutItems[layoutIndex.intValue].widthFactor).dp
                                )
                            )
                            if (layoutItems[layoutIndex.intValue].image2!=null) {
                                Spacer(modifier = Modifier.height(32.dp))
                                Image(
                                    painter = painterResource(
                                        id = layoutItems[layoutIndex.intValue].image2!!
                                    ), contentDescription = null, modifier = Modifier.width(
                                        (screenWidthDp * layoutItems[layoutIndex.intValue].widthFactor).dp
                                    )
                                )
                            }
                        }
                    }
                    Spacer(modifier = Modifier.height(16.dp))
                    Text(
                        text = stringResource(layoutItems[layoutIndex.intValue].explanation),
                        color = primaryBlack,
                        fontSize = 18.sp,
                        fontWeight = FontWeight.W700,
                        textAlign = TextAlign.Center,
                    )
                    Text(
                        text = stringResource(layoutItems[layoutIndex.intValue].example),
                        color = primaryBlack,
                        fontSize = 16.sp,
                        fontWeight = FontWeight.W400,
                        textAlign = TextAlign.Center,
                    )
                    Spacer(modifier = Modifier.height(16.dp))
                }
            }
            Spacer(modifier = Modifier.height(16.dp))
            LazyColumn(modifier = Modifier.weight(1f)) {
                item {
                    FlowRow(
                        verticalArrangement = Arrangement.spacedBy(8.dp), horizontalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        for ((idx, item) in layoutItems.withIndex()) LayoutItemButton(
                            layoutItem = item, isSelected = idx==layoutIndex.intValue, onClick = { layoutIndex.intValue = idx })
                    }
                }
            }
        }
    }
}

@Composable
fun LayoutItemButton(layoutItem: LayoutItemButtonData, isSelected: Boolean, onClick: () -> Unit) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally, modifier = Modifier
            .width(120.dp)
            .clickable(onClick = onClick)
            .padding(horizontal = 8.dp)
    ) {
        Spacer(modifier = Modifier.height(16.dp))
        Image(
            painter = painterResource(
                id = if (isSelected) layoutItem.selectedFilePath
                else layoutItem.unselectedFilePath
            ), contentDescription = null, modifier = Modifier.height(80.dp)
        )
        Spacer(modifier = Modifier.height(8.dp))
        Box(contentAlignment = Alignment.Center, modifier = Modifier.height(32.dp)) {
            BasicText(
                text = stringResource(layoutItem.bottomText),
                maxLines = 1,
                style = TextStyle(
                    fontFamily = FCIconic, color = primaryBlack, fontSize = 16.sp, fontWeight = if (isSelected) FontWeight.W700 else FontWeight.W400
                ),
                autoSize = TextAutoSize.StepBased(maxFontSize = 16.sp),
            )
        }
        Spacer(modifier = Modifier.height(8.dp))
    }
}
