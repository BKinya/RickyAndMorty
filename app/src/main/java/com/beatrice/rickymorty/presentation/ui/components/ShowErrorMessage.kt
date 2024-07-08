package com.beatrice.rickymorty.presentation.ui.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun ShowErrorMessageWithRefresh(
    modifier: Modifier = Modifier,
    message: String,
    textColor: Color = Color.Red,
    onRetry: () -> Unit
) {
    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = message,
            style = TextStyle(
                textAlign = TextAlign.Center,
                fontSize = 20.sp,
                fontFamily = FontFamily.Serif,
                color = textColor,
                fontWeight = FontWeight.SemiBold
            )
        )
        Spacer(modifier = Modifier.height(14.dp))
        Button(
            shape = RoundedCornerShape(16.dp),
            onClick = onRetry
        ) {
            Text(
                text = "Retry",
                style = TextStyle(
                    fontFamily = FontFamily.SansSerif,
                    color = Color.White,
                    fontSize = 15.sp,
                    fontWeight = FontWeight.SemiBold

                )
            )
        }
    }
}

@Composable
fun ShowErrorMessage(
    modifier: Modifier = Modifier,
    message: String,
    textColor: Color = Color.Red
) {
    Box(
        modifier = modifier
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
