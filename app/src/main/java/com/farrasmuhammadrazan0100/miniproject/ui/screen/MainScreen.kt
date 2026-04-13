package com.farrasmuhammadrazan0100.miniproject.ui.screen

import android.content.Context
import android.content.Intent
import android.content.res.Configuration
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.selection.selectable
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Share
import androidx.compose.material.icons.filled.Warning
import androidx.compose.material.icons.outlined.Info
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.farrasmuhammadrazan0100.miniproject.R
import com.farrasmuhammadrazan0100.miniproject.ui.theme.MiniProjectTheme

@Preview(showBackground = true)
@Preview(uiMode = Configuration.UI_MODE_NIGHT_YES, showBackground = true)
@Composable
fun MainScreenPreview() {
    MiniProjectTheme {
        MainScreen(rememberNavController())
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainScreen(navController: NavHostController) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(text = stringResource(id = R.string.app_name)) },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primaryContainer,
                    titleContentColor = MaterialTheme.colorScheme.primary,
                ),
                actions = {
                    IconButton(onClick = { navController.navigate("aboutScreen") }) {
                        Icon(
                            imageVector = Icons.Outlined.Info,
                            contentDescription = "Tentang Aplikasi",
                            tint = MaterialTheme.colorScheme.primary
                        )
                    }
                }
            )
        }
    ) { innerPadding ->
        ScreenContent(Modifier.padding(innerPadding))
    }
}

@Composable
fun ScreenContent(modifier: Modifier) {
    var berat by rememberSaveable { mutableStateOf("") }
    var beratError by rememberSaveable { mutableStateOf(false) }
    var tinggi by rememberSaveable { mutableStateOf("") }
    var tinggiError by rememberSaveable { mutableStateOf(false) }
    var umur by rememberSaveable { mutableStateOf("") }
    var umurError by rememberSaveable { mutableStateOf(false) }

    val radioOptions = listOf(
        stringResource(R.string.pria),
        stringResource(R.string.wanita)
    )
    var gender by rememberSaveable { mutableStateOf(radioOptions[0]) }
    var bmr by rememberSaveable { mutableFloatStateOf(0f) }

    val context = LocalContext.current

    Column(
        modifier = modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = stringResource(R.string.bmr_intro),
            style = MaterialTheme.typography.bodyLarge,
            modifier = Modifier.fillMaxWidth()
        )

        OutlinedTextField(
            value = berat,
            onValueChange = { berat = it; beratError = false },
            label = { Text(text = stringResource(R.string.berat_badan)) },
            trailingIcon = { IconPicker(beratError, "kg") },
            supportingText = { ErrorHint(beratError) },
            isError = beratError,
            singleLine = true,
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number, imeAction = ImeAction.Next),
            modifier = Modifier.fillMaxWidth()
        )

        OutlinedTextField(
            value = tinggi,
            onValueChange = { tinggi = it; tinggiError = false },
            label = { Text(text = stringResource(R.string.tinggi_badan)) },
            trailingIcon = { IconPicker(tinggiError, "cm") },
            supportingText = { ErrorHint(tinggiError) },
            isError = tinggiError,
            singleLine = true,
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number, imeAction = ImeAction.Next),
            modifier = Modifier.fillMaxWidth()
        )

        OutlinedTextField(
            value = umur,
            onValueChange = { umur = it; umurError = false },
            label = { Text(text = stringResource(R.string.usia)) },
            trailingIcon = { IconPicker(umurError, "thn") },
            supportingText = { ErrorHint(umurError) },
            isError = umurError,
            singleLine = true,
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number, imeAction = ImeAction.Done),
            modifier = Modifier.fillMaxWidth()
        )

        Row(
            modifier = Modifier
                .padding(top = 6.dp)
                .border(1.dp, Color.Gray, RoundedCornerShape(4.dp))
        ) {
            radioOptions.forEach { text ->
                GenderOption(
                    label = text,
                    isSelected = gender == text,
                    modifier = Modifier
                        .selectable(
                            selected = gender == text,
                            onClick = { gender = text },
                            role = Role.RadioButton
                        )
                        .weight(1f)
                        .padding(16.dp)
                )
            }
        }

        Row(
            modifier = Modifier.fillMaxWidth().padding(top = 8.dp),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Button(
                onClick = {
                    beratError = (berat == "" || berat == "0")
                    tinggiError = (tinggi == "" || tinggi == "0")
                    umurError = (umur == "" || umur == "0")

                    if (beratError || tinggiError || umurError) return@Button

                    bmr = hitungBmr(
                        berat.toFloat(),
                        tinggi.toFloat(),
                        umur.toInt(),
                        gender == radioOptions[0]
                    )
                },
                modifier = Modifier.weight(1f),
                contentPadding = PaddingValues(vertical = 16.dp)
            ) {
                Text(text = stringResource(R.string.hitung))
            }

            OutlinedButton(
                onClick = {
                    berat = ""; tinggi = ""; umur = ""; bmr = 0f
                    beratError = false; tinggiError = false; umurError = false
                },
                modifier = Modifier.weight(1f),
                contentPadding = PaddingValues(vertical = 16.dp)
            ) {
                Text(text = "Reset")
            }
        }

        if (bmr != 0f) {
            val message = stringResource(R.string.bagikan_template, berat, tinggi, umur, gender, bmr)

            HorizontalDivider(modifier = Modifier.padding(vertical = 16.dp))

            Card(
                colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.primaryContainer),
                modifier = Modifier.fillMaxWidth()
            ) {
                Column(modifier = Modifier.padding(16.dp), horizontalAlignment = Alignment.CenterHorizontally) {
                    Text(text = stringResource(R.string.hasil_bmr), style = MaterialTheme.typography.titleMedium)
                    Text(
                        text = stringResource(R.string.bmr_x, bmr),
                        style = MaterialTheme.typography.headlineLarge,
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.primary
                    )
                    Text(
                        text = stringResource(R.string.kkal),
                        style = MaterialTheme.typography.bodyMedium
                    )
                }
            }

            Button(
                onClick = { shareData(context, message) },
                modifier = Modifier.fillMaxWidth().padding(top = 16.dp),
                contentPadding = PaddingValues(16.dp)
            ) {
                Icon(Icons.Default.Share, contentDescription = null)
                Spacer(Modifier.width(8.dp))
                Text(text = stringResource(R.string.bagikan))
            }
        }
    }
}


@Composable
fun GenderOption(label: String, isSelected: Boolean, modifier: Modifier) {
    Row(modifier = modifier, verticalAlignment = Alignment.CenterVertically) {
        RadioButton(selected = isSelected, onClick = null)
        Text(text = label, modifier = Modifier.padding(start = 8.dp))
    }
}

private fun hitungBmr(berat: Float, tinggi: Float, usia: Int, isMale: Boolean): Float {
    return if (isMale) {
        (10f * berat) + (6.25f * tinggi) - (5f * usia) + 5f
    } else {
        (10f * berat) + (6.25f * tinggi) - (5f * usia) - 161f
    }
}

private fun shareData(context: Context, message: String) {
    val shareIntent = Intent(Intent.ACTION_SEND).apply {
        type = "text/plain"
        putExtra(Intent.EXTRA_TEXT, message)
    }
    context.startActivity(Intent.createChooser(shareIntent, "Bagikan hasil BMR"))
}

@Composable
fun IconPicker(isError: Boolean, unit: String) {
    if (isError) Icon(Icons.Filled.Warning, null, tint = Color.Red) else Text(unit)
}

@Composable
fun ErrorHint(isError: Boolean) {
    if (isError) Text(stringResource(R.string.input_invalid), color = Color.Red)
}