package co.finema.thaidotidbyfinema.uis.screens

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
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.ArrowBackIosNew
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import co.finema.thaidotidbyfinema.DocumentLayout
import co.finema.thaidotidbyfinema.R
import co.finema.thaidotidbyfinema.uis.GradientButton
import co.finema.thaidotidbyfinema.uis.primaryBlack
import co.finema.thaidotidbyfinema.uis.secondaryGray
import co.finema.thaidotidbyfinema.uis.white
import co.finema.thaidotidbyfinema.uis.whiteBG

data class LayoutItemButtonData(
    val explanation: Int,
    val example: Int,
    val image1: Int,
    val image2: Int?,
    val bottomText: Int,
    val unselectedFilePath: Int,
    val selectedFilePath: Int,
    val documentLayout: DocumentLayout,
)

val layoutItems =
    listOf(
        LayoutItemButtonData(
            explanation = R.string.oneSideCard_explanation,
            example = R.string.oneSideCard_example,
            image1 = R.drawable.luna_id_card_new,
            image2 = null,
            bottomText = R.string.oneSideCard_layout,
            unselectedFilePath = R.drawable.card1,
            selectedFilePath = R.drawable.card2,
            documentLayout = DocumentLayout.ONE_SIDE_CARD,
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
        ),
    )

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SelectLayoutScreen(navController: NavController) {
  val configuration = LocalConfiguration.current
  val screenWidthDp = configuration.screenWidthDp
  var index by remember { mutableIntStateOf(0) }
  Scaffold(
      topBar = {
        CenterAlignedTopAppBar(
            title = {
              Text(
                  text = stringResource(R.string.select_document_format),
                  color = primaryBlack,
                  fontSize = 24.sp,
                  fontWeight = FontWeight.W700,
              )
            },
            navigationIcon = {
              IconButton(onClick = { navController.popBackStack() }) {
                Icon(imageVector = Icons.Rounded.ArrowBackIosNew, contentDescription = null)
              }
            },
            colors =
                TopAppBarDefaults.centerAlignedTopAppBarColors(
                    containerColor = white,
                    navigationIconContentColor = primaryBlack,
                    actionIconContentColor = primaryBlack))
      },
      bottomBar = {
        Box(
            modifier =
                Modifier.fillMaxWidth()
                    .padding(start = 16.dp, top = 24.dp, end = 16.dp, bottom = 48.dp),
            contentAlignment = Alignment.Center) {
              GradientButton(onClick = {}, text = stringResource(R.string.next))
            }
      },
      backgroundColor = white) {
        it
        Column(modifier = Modifier.fillMaxSize().padding(horizontal = 16.dp)) {
          Box(
              modifier =
                  Modifier.fillMaxWidth()
                      .clip(RoundedCornerShape(16.dp))
                      .border(
                          width = 1.dp, color = secondaryGray, shape = RoundedCornerShape(16.dp))
                      .background(whiteBG)) {
                Column(
                    modifier = Modifier.fillMaxWidth().padding(horizontal = 16.dp),
                    horizontalAlignment = Alignment.CenterHorizontally) {
                      Spacer(modifier = Modifier.height(24.dp))
                      Box(
                          modifier =
                              Modifier.padding(horizontal = (screenWidthDp * 0.15).dp)
                                  .aspectRatio((210.0 / 297.0).toFloat())
                                  .background(white)
                                  .border(width = 1.dp, color = secondaryGray),
                          contentAlignment = Alignment.Center) {
                            Column(
                                verticalArrangement = Arrangement.SpaceEvenly,
                                horizontalAlignment = Alignment.CenterHorizontally) {
                                  val widthFactor =
                                      when (index) {
                                        0,
                                        1 -> 0.25
                                        2,
                                        3 -> 0.375
                                        else -> 0.5
                                      }
                                  Image(
                                      painter = painterResource(id = layoutItems[index].image1),
                                      contentDescription = null,
                                      modifier = Modifier.width((screenWidthDp * widthFactor).dp))
                                  if (layoutItems[index].image2 != null) {
                                    Spacer(modifier = Modifier.height(32.dp))
                                    Image(
                                        painter = painterResource(id = layoutItems[index].image2!!),
                                        contentDescription = null,
                                        modifier = Modifier.width((screenWidthDp * widthFactor).dp))
                                  }
                                }
                          }
                      Spacer(modifier = Modifier.height(16.dp))
                      Text(
                          text = stringResource(layoutItems[index].explanation),
                          color = primaryBlack,
                          fontSize = 18.sp,
                          fontWeight = FontWeight.W700,
                          textAlign = TextAlign.Center,
                      )
                      Text(
                          text = stringResource(layoutItems[index].example),
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
                  verticalArrangement = Arrangement.spacedBy(8.dp),
                  horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                    for ((idx, item) in layoutItems.withIndex()) LayoutItemButton(
                        layoutItem = item, isSelected = idx == index, onClick = { index = idx })
                  }
            }
          }
          Spacer(modifier = Modifier.height(128.dp))
        }
      }
}

@Composable
fun LayoutItemButton(layoutItem: LayoutItemButtonData, isSelected: Boolean, onClick: () -> Unit) {
  Column(
      horizontalAlignment = Alignment.CenterHorizontally,
      modifier = Modifier.width(120.dp).clickable(onClick = onClick).padding(horizontal = 8.dp)) {
        Spacer(modifier = Modifier.height(16.dp))
        Image(
            painter =
                painterResource(
                    id =
                        if (isSelected) layoutItem.selectedFilePath
                        else layoutItem.unselectedFilePath),
            contentDescription = null,
            modifier = Modifier.height(80.dp))
        Spacer(modifier = Modifier.height(8.dp))
        Box(contentAlignment = Alignment.Center, modifier = Modifier.height(32.dp)) {
          BasicText(
              text = stringResource(layoutItem.bottomText),
              maxLines = 1,
              style =
                  TextStyle(
                      color = primaryBlack,
                      fontSize = 16.sp,
                      fontWeight = if (isSelected) FontWeight.W700 else FontWeight.W400),
              autoSize = TextAutoSize.StepBased(maxFontSize = 16.sp),
          )
        }
        Spacer(modifier = Modifier.height(8.dp))
      }
}
