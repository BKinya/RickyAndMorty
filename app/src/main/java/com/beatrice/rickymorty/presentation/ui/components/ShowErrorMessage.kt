package com.beatrice.rickymorty.presentation.ui.components

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.sp

@Composable
fun ShowErrorMessage(
    modifier: Modifier = Modifier,
    message: String,
    textColor: Color = Color.Red
) {
    Box(
        modifier = modifier.fillMaxSize()
    ) {
        Text(
            text = message,
            style = TextStyle(
                textAlign = TextAlign.Center,
                fontSize = 20.sp,
                fontFamily = FontFamily.Serif,
                color = textColor,
                fontWeight = FontWeight.SemiBold
            ),
            modifier = Modifier.align(alignment = Alignment.Center)
        )
    }
}
