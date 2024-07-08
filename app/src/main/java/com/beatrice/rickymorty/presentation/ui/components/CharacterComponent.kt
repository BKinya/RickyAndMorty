package com.beatrice.rickymorty.presentation.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.beatrice.rickymorty.R
import com.beatrice.rickymorty.domain.model.Character

@Composable
fun CharacterComponent(
    modifier: Modifier = Modifier,
    character: Character
) {
    Box(
        modifier = modifier.background(
            color = Color.LightGray,
            shape = RoundedCornerShape(20.dp)
        )
            .padding(14.dp)
            .height(200.dp)
    ) {
        Column(
            modifier = modifier,
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            AsyncImage(
                modifier = Modifier.fillMaxWidth(),
                model = character.imageUrl,
                contentDescription = stringResource(
                    R.string.character_s_image
                )
            )
            Spacer(modifier = Modifier.height(10.dp))
            Text(
                text = character.name,
                style = TextStyle(
                    fontSize = 18.sp,
                    fontWeight = FontWeight.SemiBold,
                    fontFamily = FontFamily.SansSerif,
                    color = Color.DarkGray
                )
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = character.species,
                style = TextStyle(
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Normal,
                    fontFamily = FontFamily.SansSerif,
                    color = Color.DarkGray
                )
            )
        }
    }
}
