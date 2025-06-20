@file:Suppress("UsingMaterialAndMaterial3Libraries")

package co.finema.thaidotidbyfinema.uis.screens.profile

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.CalendarMonth
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import co.finema.thaidotidbyfinema.EN
import co.finema.thaidotidbyfinema.R
import co.finema.thaidotidbyfinema.TH
import co.finema.thaidotidbyfinema.formatterEN
import co.finema.thaidotidbyfinema.formatterTH
import co.finema.thaidotidbyfinema.regexNumber
import co.finema.thaidotidbyfinema.repositories.UserCardRepository
import co.finema.thaidotidbyfinema.repositories.UserConfigRepository
import co.finema.thaidotidbyfinema.uis.FCIconic
import co.finema.thaidotidbyfinema.uis.components.AppBarOptBack
import co.finema.thaidotidbyfinema.uis.components.ProfileDetailsHr
import co.finema.thaidotidbyfinema.uis.neutral05
import co.finema.thaidotidbyfinema.uis.primaryBlack
import co.finema.thaidotidbyfinema.uis.primaryRed
import co.finema.thaidotidbyfinema.uis.white
import co.finema.thaidotidbyfinema.uis.whiteBG
import com.vanpra.composematerialdialogs.MaterialDialog
import com.vanpra.composematerialdialogs.datetime.date.datepicker
import com.vanpra.composematerialdialogs.rememberMaterialDialogState
import kotlinx.coroutines.launch
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.chrono.ThaiBuddhistDate

val filledStyle = TextStyle(fontFamily = FCIconic, color = primaryBlack, fontSize = 20.sp, fontWeight = FontWeight.W400)

val thaiItems = listOf("นาย", "นางสาว", "นาง", "เด็กชาย", "เด็กหญิง")
val engItems = listOf("Mr.", "Miss", "Mrs.", "Master")

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProfileEditScreen(navController: NavController) {
    BackHandler(enabled = true) {}
    val context = LocalContext.current
    val focusManager = LocalFocusManager.current
    val scope = rememberCoroutineScope()
    val userConfigRepository = remember { UserConfigRepository(context) }
    val locale by userConfigRepository.locale.collectAsState(initial = "")
    val userCardRepository = remember { UserCardRepository(context) }
    val idString by userCardRepository.idString.collectAsState(initial = "")
    var idStringText by remember { mutableStateOf("") }
    var idStringError by remember { mutableStateOf(false) }
    val birthDate by userCardRepository.birthDate.collectAsState(initial = "")
    var birthDateText by remember { mutableStateOf("") }
    LaunchedEffect(idString) { idStringText = idString }
    LaunchedEffect(locale, birthDate) {
        if (locale.isNotEmpty() && birthDate.isNotEmpty())
            when (locale) {
                EN -> birthDateText = formatterEN.format(LocalDateTime.parse(birthDate))
                TH -> birthDateText = formatterTH.format(ThaiBuddhistDate.from(LocalDateTime.parse(birthDate)))

                else -> birthDateText = formatterEN.format(LocalDateTime.parse(birthDate))
            }
    }
    val dialogState = rememberMaterialDialogState()
    MaterialDialog(
        dialogState = dialogState,
        onCloseRequest = {},
        buttons = {
            positiveButton(stringResource(R.string.ok))
            negativeButton(stringResource(R.string.cancel))
        },
    ) {
        datepicker(title = stringResource(R.string.date_of_birth), allowedDateValidator = { !it.isAfter(LocalDate.now()) }) {
            val localDateTime: LocalDateTime = it.atStartOfDay()
            scope.launch { userCardRepository.updateBirthDate(localDateTime.toString()) }
        }
    }
    val thaiPrefix by userCardRepository.thaiPrefix.collectAsState(initial = "")
    var thaiExpanded by remember { mutableStateOf(false) }
    var thaiSelectedValue by remember { mutableStateOf("") }
    LaunchedEffect(thaiPrefix) { thaiSelectedValue = thaiPrefix }
    val thaiName by userCardRepository.thaiName.collectAsState(initial = "")
    var thaiNameText by remember { mutableStateOf("") }
    LaunchedEffect(thaiName) { thaiNameText = thaiName }
    val thaiMiddleName by userCardRepository.thaiMiddleName.collectAsState(initial = "")
    var thaiMiddleNameText by remember { mutableStateOf("") }
    LaunchedEffect(thaiMiddleName) { thaiMiddleNameText = thaiMiddleName }
    val thaiSurname by userCardRepository.thaiSurname.collectAsState(initial = "")
    var thaiSurnameText by remember { mutableStateOf("") }
    LaunchedEffect(thaiSurname) { thaiSurnameText = thaiSurname }
    val engPrefix by userCardRepository.engPrefix.collectAsState(initial = "")
    var engExpanded by remember { mutableStateOf(false) }
    var engSelectedValue by remember { mutableStateOf("") }
    LaunchedEffect(engPrefix) { engSelectedValue = engPrefix }
    val engName by userCardRepository.engName.collectAsState(initial = "")
    var engNameText by remember { mutableStateOf("") }
    LaunchedEffect(engName) { engNameText = engName }
    val engMiddleName by userCardRepository.engMiddleName.collectAsState(initial = "")
    var engMiddleNameText by remember { mutableStateOf("") }
    LaunchedEffect(engMiddleName) { engMiddleNameText = engMiddleName }
    val engSurname by userCardRepository.engSurname.collectAsState(initial = "")
    var engSurnameText by remember { mutableStateOf("") }
    LaunchedEffect(engSurname) { engSurnameText = engSurname }
    Scaffold(
        topBar = {
            AppBarOptBack(
                containerColor = white,
                text = stringResource(R.string.id_card_info),
                onClick = {
                    focusManager.clearFocus()
                    if (idStringText.isEmpty() || idStringText.length == 13) {
                        scope
                            .launch {
                                userCardRepository.updateIdString(idStringText)
                                userCardRepository.updateThaiPrefix(thaiSelectedValue.trim())
                                userCardRepository.updateThaiName(thaiNameText.trim())
                                userCardRepository.updateThaiMiddleName(thaiMiddleNameText.trim())
                                userCardRepository.updateThaiSurname(thaiSurnameText.trim())
                                userCardRepository.updateEngPrefix(engSelectedValue.trim())
                                userCardRepository.updateEngName(engNameText.trim())
                                userCardRepository.updateEngMiddleName(engMiddleNameText.trim())
                                userCardRepository.updateEngSurname(engSurnameText.trim())
                            }
                            .invokeOnCompletion { navController.popBackStack() }
                    } else {
                        idStringError = true
                    }
                },
            )
        },
        backgroundColor = white,
    ) {
        LazyColumn(modifier = Modifier
            .fillMaxSize()
            .padding(it)
            .padding(horizontal = 16.dp)) {
            item {
                Text(text = stringResource(R.string.id_number), color = primaryBlack, fontSize = 20.sp, fontWeight = FontWeight.W700)
                OutlinedTextField(
                    value = idStringText,
                    onValueChange = {
                        val value = it.trim()
                        if ((regexNumber.matches(value) && value.length < 14) || value.isEmpty()) {
                            idStringText = value
                            idStringError = false
                        }
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 8.dp),
                    textStyle = filledStyle,
                    placeholder = { Text(text = stringResource(R.string.id_number), color = neutral05, fontSize = 20.sp, fontWeight = FontWeight.W400) },
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number, showKeyboardOnFocus = true),
                    singleLine = true,
                    isError = idStringError,
                )
                Row(modifier = Modifier.padding(horizontal = 8.dp)) {
                    if (idStringError) Text(text = stringResource(R.string.required), color = primaryRed, fontSize = 16.sp, fontWeight = FontWeight.W400)
                    Spacer(modifier = Modifier.weight(1f))
                    Text(text = "${idStringText.length}/13", color = neutral05, fontSize = 16.sp, fontWeight = FontWeight.W400)
                }
                Spacer(modifier = Modifier.height(8.dp))
                Text(text = stringResource(R.string.date_of_birth), color = primaryBlack, fontSize = 20.sp, fontWeight = FontWeight.W700)
                OutlinedTextField(
                    value = birthDateText,
                    onValueChange = {},
                    readOnly = true,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 8.dp),
                    textStyle = filledStyle,
                    placeholder = { Text(text = stringResource(R.string.date_format), color = neutral05, fontSize = 20.sp, fontWeight = FontWeight.W400) },
                    trailingIcon = { IconButton(onClick = { dialogState.show() }) { Icon(imageVector = Icons.Rounded.CalendarMonth, contentDescription = null, tint = primaryBlack) } },
                    singleLine = true,
                )
                ProfileDetailsHr(text = stringResource(R.string.personal_info_thai))
                Text(text = stringResource(R.string.title), color = primaryBlack, fontSize = 20.sp, fontWeight = FontWeight.W700)
                ExposedDropdownMenuBox(expanded = thaiExpanded, onExpandedChange = { thaiExpanded = it }) {
                    OutlinedTextField(
                        value = thaiSelectedValue,
                        onValueChange = { thaiSelectedValue = it },
                        modifier = Modifier
                            .menuAnchor()
                            .fillMaxWidth()
                            .padding(top = 8.dp),
                        textStyle = filledStyle,
                        placeholder = { Text(text = stringResource(R.string.title), color = neutral05, fontSize = 20.sp, fontWeight = FontWeight.W400) },
                        trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = thaiExpanded) },
                        singleLine = true,
                    )
                    ExposedDropdownMenu(containerColor = whiteBG, expanded = thaiExpanded, onDismissRequest = { thaiExpanded = false }) {
                        thaiItems.forEach {
                            DropdownMenuItem(
                                text = { Text(text = it, color = primaryBlack, fontSize = 20.sp, fontWeight = FontWeight.W400) },
                                onClick = {
                                    thaiSelectedValue = it
                                    thaiExpanded = false
                                    focusManager.clearFocus()
                                },
                            )
                        }
                    }
                }
                NameTextField(text = stringResource(R.string.first_name), value = thaiNameText, onValueChange = { thaiNameText = it })
                NameTextField(text = stringResource(R.string.middle_name), value = thaiMiddleNameText, onValueChange = { thaiMiddleNameText = it })
                NameTextField(text = stringResource(R.string.last_name), value = thaiSurnameText, onValueChange = { thaiSurnameText = it })
                ProfileDetailsHr(text = stringResource(R.string.personal_info_eng))
                Text(text = stringResource(R.string.title), color = primaryBlack, fontSize = 20.sp, fontWeight = FontWeight.W700)
                ExposedDropdownMenuBox(expanded = engExpanded, onExpandedChange = { engExpanded = it }) {
                    OutlinedTextField(
                        value = engSelectedValue,
                        onValueChange = { engSelectedValue = it },
                        modifier = Modifier
                            .menuAnchor()
                            .fillMaxWidth()
                            .padding(top = 8.dp),
                        textStyle = filledStyle,
                        placeholder = { Text(text = stringResource(R.string.title), color = neutral05, fontSize = 20.sp, fontWeight = FontWeight.W400) },
                        trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = engExpanded) },
                        singleLine = true,
                    )
                    ExposedDropdownMenu(containerColor = whiteBG, expanded = engExpanded, onDismissRequest = { engExpanded = false }) {
                        engItems.forEach {
                            DropdownMenuItem(
                                text = { Text(text = it, color = primaryBlack, fontSize = 20.sp, fontWeight = FontWeight.W400) },
                                onClick = {
                                    engSelectedValue = it
                                    engExpanded = false
                                    focusManager.clearFocus()
                                },
                            )
                        }
                    }
                }
                NameTextField(text = stringResource(R.string.first_name), value = engNameText, onValueChange = { engNameText = it })
                NameTextField(text = stringResource(R.string.middle_name), value = engMiddleNameText, onValueChange = { engMiddleNameText = it })
                NameTextField(text = stringResource(R.string.last_name), value = engSurnameText, onValueChange = { engSurnameText = it })
                Spacer(modifier = Modifier.height(48.dp))
            }
        }
    }
}

@Composable
fun NameTextField(text: String, value: String, onValueChange: (String) -> Unit) {
    Spacer(modifier = Modifier.height(32.dp))
    Text(text = text, color = primaryBlack, fontSize = 20.sp, fontWeight = FontWeight.W700)
    OutlinedTextField(
        value = value,
        onValueChange = onValueChange,
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 8.dp),
        textStyle = filledStyle,
        placeholder = { Text(text = text, color = neutral05, fontSize = 20.sp, fontWeight = FontWeight.W400) },
        singleLine = true,
    )
}
