package com.glidotalks.moneyspent.views

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.EditCalendar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MaterialTextField(
    text: String,
    label: String,
    keyboardOptions: KeyboardOptions = KeyboardOptions(
        imeAction = ImeAction.Done
    ),
    onInputChange: (String) -> Unit
) {
    OutlinedTextField(
        value = text,
        onValueChange = onInputChange,
        keyboardOptions = keyboardOptions,
        label = {
            Text(
                text = label
            )
        }
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MaterialTextFieldWithTrailingIcon(
    text: String,
    label: String,
    keyboardOptions: KeyboardOptions = KeyboardOptions.Default,
    valueChange: (String) -> Unit,
    iconClick: () -> Unit,
) {

    OutlinedTextField(
        value = text,
        onValueChange = valueChange,
        keyboardOptions = keyboardOptions,
        trailingIcon = {
            Icon(
                imageVector = Icons.Rounded.EditCalendar,
                contentDescription = null,
                modifier = Modifier
                    .width(24.dp)
                    .height(24.dp)
                    .clickable { iconClick.invoke() }
            )
        },
        label = {
            Text(
                text = label
            )
        }
    )
}
