package com.beatrice.rickymorty.presentation.ui.components

import androidx.compose.foundation.layout.Box
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

@Composable
fun ShowLoadingIndicator(
    modifier: Modifier = Modifier
){
    Box(modifier = modifier){
        CircularProgressIndicator()
    }
}
