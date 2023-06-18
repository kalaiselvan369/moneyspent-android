package com.glidotalks.moneyspent.views

import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

@Composable
fun MaterialButton(
    buttonText: String,
    enabled: Boolean = false,
    onClick: () -> Unit
) {
    Button(
        onClick = onClick,
        modifier = Modifier
            .width(200.dp)
            .height(40.dp),
        enabled = enabled,
        colors = ButtonDefaults.buttonColors()
    ) {
        Text(
            modifier = Modifier.padding(start = 24.dp, end = 24.dp),
            text = buttonText,
            style = MaterialTheme.typography.labelLarge
        )
    }
}

@Preview
@Composable
fun MaterialButtonPreview() {
    MaterialButton(
        buttonText = "Next"
    ) {

    }
}