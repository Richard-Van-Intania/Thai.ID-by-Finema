package co.finema.thaidotidbyfinema.uis.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.ArrowBackIosNew
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import co.finema.thaidotidbyfinema.R
import co.finema.thaidotidbyfinema.repositories.UserConfigRepository
import co.finema.thaidotidbyfinema.uis.Screen
import co.finema.thaidotidbyfinema.uis.gradient
import co.finema.thaidotidbyfinema.uis.lightBlue07
import co.finema.thaidotidbyfinema.uis.neutral06
import co.finema.thaidotidbyfinema.uis.primaryBlack
import co.finema.thaidotidbyfinema.uis.white
import kotlinx.coroutines.launch

val bodyBold = SpanStyle(color = neutral06, fontSize = 18.sp, fontWeight = FontWeight.W700)
val sectionBold = SpanStyle(color = primaryBlack, fontSize = 22.sp, fontWeight = FontWeight.W700)

@Composable
fun TermsScreen(navController: NavHostController, hasButton: Boolean) {
  Scaffold(
      topBar = {
        TopAppBar(
            title = {
              Row(
                  modifier = Modifier.fillMaxSize().padding(top = 48.dp),
                  verticalAlignment = Alignment.CenterVertically) {
                    Box(modifier = Modifier.width(48.dp), contentAlignment = Alignment.Center) {
                      IconButton(onClick = { navController.popBackStack() }) {
                        Icon(imageVector = Icons.Rounded.ArrowBackIosNew, contentDescription = null)
                      }
                    }
                    Spacer(modifier = Modifier.weight(1f))
                    Text(
                        text = stringResource(R.string.terms),
                        color = primaryBlack,
                        fontSize = 24.sp,
                        fontWeight = FontWeight.W700,
                    )
                    Spacer(modifier = Modifier.weight(1f))
                    Spacer(modifier = Modifier.width(48.dp))
                  }
            },
            modifier = Modifier.height(112.dp),
            elevation = 0.dp,
            backgroundColor = white)
      },
      backgroundColor = white) {
        it
        LazyColumn(contentPadding = PaddingValues(horizontal = 16.dp)) {
          item {
            Text(
                text =
                    buildAnnotatedString {
                      append(text = paragraph1)
                      withStyle(style = bodyBold) { append(text = company) }
                      append(text = paragraph2Android)
                      withStyle(style = bodyBold) { append(text = thaidotid) }
                      append(text = paragraph3)
                      withStyle(style = bodyBold) { append(text = terms) }
                      append(text = paragraph4)
                      withStyle(style = bodyBold) { append(text = service) }
                      append(text = paragraph5)
                      withStyle(style = sectionBold) { append(text = section1) }
                      append(text = section11)
                      withStyle(style = sectionBold) { append(text = section2) }
                      append(text = section21)
                      withStyle(style = sectionBold) { append(text = section3) }
                      append(text = section31)
                      withStyle(style = sectionBold) { append(text = section4) }
                      withStyle(style = bodyBold) { append(text = section41) }
                      append(text = section411)
                      withStyle(style = bodyBold) { append(text = section42) }
                      append(text = section421)
                      withStyle(style = bodyBold) { append(text = section43) }
                      append(text = section431)
                      withStyle(style = bodyBold) { append(text = section44) }
                      append(text = section441)
                      append(text = section442)
                      withStyle(
                          style =
                              SpanStyle(
                                  color = primaryBlack,
                                  fontSize = 18.sp,
                                  fontWeight = FontWeight.W700)) {
                            append(text = agree)
                          }
                    },
                color = neutral06,
                fontSize = 18.sp,
                fontWeight = FontWeight.W400)
          }
          item {
            if (hasButton)
                Row(
                    modifier = Modifier.padding(vertical = 48.dp),
                    verticalAlignment = Alignment.CenterVertically) {
                      Box(
                          modifier =
                              Modifier.height(56.dp)
                                  .weight(1f)
                                  .background(white)
                                  .border(
                                      width = 2.dp,
                                      color = lightBlue07,
                                      shape = RoundedCornerShape(56.dp))
                                  .clip(RoundedCornerShape(56.dp))
                                  .clickable(
                                      onClick = {
                                        navController.popBackStack(
                                            route = Screen.WelcomeScreenNav.route,
                                            inclusive = false)
                                      }),
                          contentAlignment = Alignment.Center) {
                            Text(
                                text = stringResource(R.string.decline),
                                color = lightBlue07,
                                fontSize = 24.sp,
                                fontWeight = FontWeight.W700,
                            )
                          }
                      Spacer(modifier = Modifier.width(16.dp))
                      val context = LocalContext.current
                      val repository = remember { UserConfigRepository(context) }
                      val scope = rememberCoroutineScope()
                      Box(
                          modifier =
                              Modifier.height(56.dp)
                                  .weight(1f)
                                  .clip(RoundedCornerShape(56.dp))
                                  .background(brush = gradient)
                                  .clickable(
                                      onClick = {
                                        scope.launch { repository.updateIsAcceptedAgreements(true) }
                                        navController.navigate(route = Screen.HomeRootNav.route) {
                                          popUpTo(Screen.WelcomeScreenNav.route) {
                                            inclusive = true
                                          }
                                        }
                                      }),
                          contentAlignment = Alignment.Center) {
                            Text(
                                text = stringResource(R.string.accept),
                                color = white,
                                fontSize = 24.sp,
                                fontWeight = FontWeight.W700,
                            )
                          }
                    }
            else Spacer(modifier = Modifier.height(48.dp))
          }
        }
      }
}

private const val paragraph1 = "บริษัท ฟินีม่า จำกัด (ซึ่งต่อไปในประกาศนี้ เรียกว่า "
private const val company = "“บริษัท”"
private const val paragraph2Ios = ") ได้พัฒนาแอปพลิเคชัน Thai.ID (ซึ่งต่อไปนี้ในประกาศนี้ เรียกว่า "
private const val paragraph2Android =
    ") ได้พัฒนาแอปพลิเคชัน Thai.ID by Finema (ซึ่งต่อไปนี้ในประกาศนี้ เรียกว่า "
private const val thaidotid = "“Thai.ID”"

private const val paragraph3 =
    ") เพื่ออำนวยความสะดวกแก่ผู้ใช้งานในการพิสูจน์และยืนยันตัวตน ซึ่งรวมไปถึงการทำสำเนาถูกต้องของเอกสารในรูปแบบอิเล็กทรอนิกส์และลงลายมือชื่ออิเล็กทรอนิกส์\n\nบริษัทขอเรียนให้ท่านทราบว่าการที่ท่านสมัครใช้และใช้บริการ Thai.ID ถือว่าท่านได้ตกลงยอมรับข้อตกลงและเงื่อนไขการใช้งาน Thai.ID ฉบันนี้ (ซึ่งต่อไปนี้เรียกว่า "

private const val terms = "“ข้อกำหนดและเงื่อนไขฯ”"

private const val paragraph4 =
    ") ตลอดจนข้อกำหนดและเงื่อนไขฯ ที่บริษัทได้แก้ไข เพิ่มเติม หรือเปลี่ยนแปลงในอนาคต กล่าวคือ ข้อกำหนดและเงื่อนไขฯ นี้มีผลผูกพันบริษัทในฐานะผู้ให้บริการและท่านซึ่งเป็นผู้ใช้บริการ โดยระบุถึงรายละเอียดข้อกำหนดและเงื่อนไขของบริการ Thai.ID ซึ่งได้แก่ บริการพื้นฐาน และบริการเสริมอื่น ๆ ของ Thai.ID ที่ท่านได้สมัครใช้บริการในภายหลัง (ซึ่งต่อไปนี้จะเรียกว่า "
private const val service = "“บริการ”"
private const val paragraph5 =
    " หากท่านไม่ยอมรับข้อกำหนดและเงื่อนไขฯ บริษัทจะไม่สามารถให้บริการ Thai.ID กับท่านได้ เมื่อท่านได้ยอมรับข้อกำหนดและเงื่อนไขฯ นี้แล้ว แต่ต่อมาให้ภายหลังท่านประสงค์จะที่ไม่ผูกพันตามข้อกำหนดและเงื่อนไขฯ ท่านสามารถทำได้โดยวิธีการลบบัญชีผู้ใช้งาน Thai.ID เท่านั้นซึ่งจะส่งผลให้ท่านไม่สามารถใช้งาน Thai.ID ได้อีกต่อไป\n\nบริษัทขอสงวนสิทธิ์แก้ไข เพิ่มเติม หรือเปลี่ยนแปลงข้อกำหนดและ เงื่อนไขฯ ได้ตลอดเวลาตามที่บริษัทเห็นสมควร โดยจะแจ้งให้ท่านทราบผ่านทาง Thai.ID และผ่านทางเว็บไซต์ https://www.thai.id/ หรืออาจแจ้งให้ทราบผ่านทางช่องทางอื่น ๆ ที่บริษัทเห็นสมควร\n\n"

private const val section1 = "1. การใช้บริการ Thai.ID"
private const val section11 =
    "\nในการใช้บริการ Thai.ID ท่านรับรองและยืนยันว่าท่านเป็นบุคคลที่มีความสามารถหรือได้รับอนุญาตตามกฎหมายในการเข้าทำสัญญาอันก่อภาระผูกพันภายใต้กฎหมายของประเทศไทยหากท่านเป็นผู้เยาว์ที่มีอายุต่ำกว่า 20 ปี ท่านรับรองและยืนยันว่าท่านได้รับความยินยอมจากผู้ปกครองตามกฎหมายแล้ว\n\n"
private const val section2 = "2. การจัดการข้อมูลและการคุ้มครองข้อมูลส่วนบุคคล"
private const val section21 =
    "\nบริษัทไม่มีการจัดเก็บข้อมูลส่วนบุคคลของท่าน ข้อมูลส่วนบุคคลของท่านสำหรับใช้พิสูจน์และยืนยันตัวตนผ่านการใช้งาน Thai.ID จะถูกจัดเก็บไว้ในโทรศัพท์มือถือของท่าน หากบริษัทมีการปรับปรุงการให้บริการ Thai.ID ในภายหลัง ซึ่งส่งผลให้บริษัทต้องจัดเก็บข้อมูลส่วนบุคคลของท่าน บริษัทจะแจ้งให้ท่านทราบถึงรายละเอียดเกี่ยวกับฐานทางกฎหมาย วัตถุประสงค์ และระยะเวลาในการเก็บรวบรวมและใช้ข้อมูลส่วนบุคคลของท่าน ตลอดจนข้อมูลส่วนบุคคลที่เก็บรวบรวมและใช้ รวมทั้งเงื่อนไขที่บริษัทจะเปิดเผยข้อมูลส่วนบุคคลของท่าน และสิทธิของท่านในฐานะเจ้าของข้อมูลส่วนบุคคลในการดำเนินการจัดการข้อมูลส่วนบุคคลของตน พร้อมทั้งกำหนดช่องทางสำหรับการใช้สิทธิดังกล่าว ใน \"ประกาศความเป็นส่วนตัว (Privacy Notice) สำหรับการใช้งานแอปพลิเคชัน Thai.ID\" ซึ่งจะปรากฏใน Thai.ID ก่อนที่ท่านจะกรอกข้อมูลสำหรับลงทะเบียนใช้งาน Thai.ID ที่บริษัทได้ปรับปรุงใหม่ \n\nส่วนข้อมูลที่บริษัทใช้วิเคราะห์เพื่อปรับปรุงการให้บริการ Thai.ID นั้น จะมาจากข้อมูลการใช้งานหรือพฤติกรรมการใช้งาน Thai.ID ของท่านเท่านั้น ซึ่งไม่ใช่ \"ข้อมูลส่วนบุคคล\" ตามนิยามในมาตรา 6 วรรค 2 แห่งพระราชบัญญัติคุ้มครองข้อมูลส่วนบุคคล พ.ศ. 2562\n\n"
private const val section3 = "3. ข้อสงวนสิทธิ"
private const val section31 =
    "\n3.1 บริษัทขอสงวนสิทธิในการแก้ไขหรือเปลี่ยนแปลงข้อกำหนดและเงื่อนไขฯ ทั้งหมดหรือแต่บางส่วน ตามที่บริษัทเห็นสมควร ทั้งนี้ โดยไม่ต้องบอกกล่าวล่วงหน้า\n\n3.2 บริษัทขอสงวนสิทธิในการจำกัดการเข้าถึงการใช้บริการของท่านทั้งหมดหรือเฉพาะบางส่วน โดยขึ้นอยู่กับเงื่อนไข ความจำเป็น หรือเหตุปัจจัยอื่นที่บริษัทเห็นสมควร\n\n3.3 บริษัทขอสงวนสิทธิในการหยุดการให้บริการทั้งหมดหรือเฉพาะบางส่วนโดยไม่ต้องบอกกล่าวล่วงหน้าแก่ท่านในกรณีดังต่อไปนี้\n\n •เมื่อทำการบำรุงรักษาและซ่อมแซมระบบ\n\n •เมื่อไม่สามารถให้บริการได้เนื่องจากเหตุสุดวิสัยบางประการ เช่น อุบัติเหตุ ปรากฏการณ์ธรรมชาติ สงคราม การจลาจล ข้อพิพาทแรงงาน ฯลฯ\n\n •เมื่อระบบเกิดความขัดข้อง หรือมีการใช้งานระบบมากเกินกำลัง(Overload)\n\n •เมื่อระบบถูกแทรกแซงการให้บริการหรือถูกเจาะเข้าระบบ (Hack)เพื่อรักษาความปลอดภัยของท่าน หรือบุคคลอื่น หรือเพื่อดำเนินภารกิจอันเป็นประโยชน์ต่อสาธารณะในกรณีฉุกเฉิน\n\n •เมื่อบริษัทเห็นว่ามีความจำเป็นและเป็นการสมควรนอกจากกรณีที่ได้ระบุไว้ข้างต้น\n\n3.4 บริษัทขอสงวนสิทธิที่ในการลงโฆษณาของ Thai.ID หรือของบุคคลภายนอกบนระบบการให้บริการ\n\n3.5 บริษัทขอสงวนสิทธิไม่รับรองการสำรองเนื้อหาหรือข้อมูลจากท่านแต่อย่างใด ไม่ว่าทั้งหมดหรือบางส่วน\n\n3.6 บริษัทขอยืนยันว่า บริษัทได้ปฏิบัติตามมาตรการที่กฎหมายกำหนดในการให้บริการ Thai.ID อย่างไรก็ตาม บริษัทของสงวนสิทธิไม่รับประกันใด ๆ ว่า บริการนั้นปราศจากข้อบกพร่อง มีความมั่นคงปลอดภัยทางสารสนเทศ หรือมีความถูกต้องสมบูรณ์\n\n3.7 บริษัทขอสงวนสิทธิในการลบเนื้อหาหรือข้อมูลจากท่าน โดยไม่ต้องบอกกล่าวล่วงหน้า หากบริษัทเห็นว่าท่านได้ฝ่าฝืนหรืออาจฝ่าฝืนกฎหมาย กฎ ระเบียบ ประกาศ หรือคำสั่งที่ออกโดยหน่วยงานราชการ หรือข้อกำหนดและเงื่อนไขฯ ฉบับนี้\n\n"
private const val section4 = "4. ข้อตกลงอื่น ๆ"
private const val section41 = "\n4.1. ทรัพย์สินทางปัญญา"
private const val section411 =
    "\nข้อกำหนดและเงื่อนไขฉบับนี้ไม่ถือเป็นการอนุญาตให้ท่านใช้ทรัพย์สินทางปัญญาใด ๆ ของบริษัท แม้ว่าอาจมีถ้อยคำดังต่อไปนี้ปรากฏบนหน้าจอในการใช้บริการ เช่น “การซื้อ” “การขาย” และถ้อยคำอื่น ๆ ซึ่งมีความหมายคล้ายคลึงกัน ในกรณีนี้บริษัทจะยังคงเป็นเจ้าของและเป็นผู้ดำรงซึ่งสิทธิ กรรมสิทธิ์ และส่วนได้ส่วนเสียทั้งหมดที่มีอยู่ในและที่มีต่อ Thai.ID รวมถึงทรัพย์สินทางปัญญาที่เกี่ยวข้องทั้งหมด สิทธิดังกล่าวจะไม่ถูกโอนไปให้แก่ท่านซึ่งเป็นผู้ใช้บริการแต่อย่างใด ท่านจะได้รับสิทธิในการใช้งานตามที่ได้ระบุไว้ข้างต้นเท่านั้น\n\n"
private const val section42 = "4.2. ข้อจำกัดความรับผิดของ Thai.ID"
private const val section421 =
    "\nบริษัทจะไม่รับผิดชอบใด ๆ ต่อความเสียหายอันเกิดจากการกระทำของท่านอันเกี่ยวเนื่องกับการใช้บริการ Thai.ID เว้นเสียแต่ความเสียหายดังกล่าวมีส่วนเกิดจากการกระทำโดยเจตนาหรือโดยประมาทเลินเล่ออย่างร้ายแรงของบริษัท โดยบริษัทมีหน้าที่ใช้ค่าสินไหมทดแทนอันเกิดจากการละเมิดนั้น\n\n"
private const val section43 = "4.3. ความรับผิดของผู้ใช้งาน"
private const val section431 =
    "\nในการใช้งาน Thai.ID หากท่านไม่ปฏิบัติตามหรือละเมิดข้อกำหนดและเงื่อนไขฯ นี้ หรือไม่ปฏิบัติตามหรือฝ่าฝืนกฎหมายที่เกี่ยวข้อง และส่งผลให้เกิดความเสียหายต่อบริษัทไม่ว่าทางตรงหรือทางอ้อม ท่านต้องชดใช้ค่าเสียหายตามที่บริษัทเรียกร้องโดยทันที\n\n"
private const val section44 = "4.4. กฎหมายที่ใช้บังคับและเขตอำนาจศาล"
private const val section441 =
    "\n4.4.1. การตีความข้อกำหนดและเงื่อนไขฯ ฉบับนี้ให้อยู่ภายใต้บังคับของกฎหมายแห่งราชอาณาจักรไทย หากข้อความใดของข้อกำหนดและเงื่อนไขฯ ละเมิดกฎหมายดังกล่าว ให้ข้อความนั้นไม่มีผลผูกพันระหว่างบริษัทและท่าน แต่ข้อความอื่นที่เหลืออยู่ยังคงมีผลผูกพัน\n"
private const val section442 =
    "\n4.4.2. ข้อพิพาทเกิดขึ้นจากการใช้บริการ Thai.ID ระหว่างบริษัทและท่านให้อยู่ภายใต้เขตอำนาจของศาลยุติธรรมแห่งราชอาณาจักรไทย\n\n"
private const val agree =
    "ข้าพเจ้าได้อ่านและรับทราบเนื้อหาของข้อกำหนดและเงื่อนไขฯ ฉบับนี้ จึงได้กด “ยินยอม” เพื่อยอมรับข้อกำหนดและรายละเอียดข้างต้นทั้งหมด"
