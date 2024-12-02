package com.geeks.hw6_4_.ui.screens.character.detail

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.example.rickandmortycompose.R
import kotlinx.coroutines.Dispatchers
import org.koin.androidx.compose.koinViewModel
import androidx.compose.animation.*
import androidx.compose.animation.core.tween
import androidx.compose.foundation.layout.FlowColumnScopeInstance.align
import androidx.compose.ui.tooling.preview.Preview

fun Modifier.characterImageStyle(): Modifier = this
    .size(200.dp)
    .padding(4.dp)
    .clip(RoundedCornerShape(4.dp))
    .border(BorderStroke(1.dp, Color.White), RoundedCornerShape(2.dp))

fun Modifier.characterTextStyle(): Modifier = this
    .padding(top = 12.dp, bottom = 2.dp)

@Composable
fun AnimatedText(text: String, fontSize: Int, color: Color, modifier: Modifier = Modifier) {
    AnimatedVisibility(
        visible = true,
        enter = fadeIn(animationSpec = tween(600)),
        exit = fadeOut(animationSpec = tween(300))
    ) {
        Text(
            text = text,
            fontSize = fontSize.sp,
            color = color,
            modifier = modifier,
            fontWeight = FontWeight.Bold
        )
    }
}

@Composable
fun DetailCharacterScreen(
    viewModel: DetailCharacterViewModel = koinViewModel(),
    id: Int
) {
    val character by viewModel.singleCharacterStateFlow.collectAsState()

    LaunchedEffect(Dispatchers.IO) {
        viewModel.getSingleCharacter(id)
    }

    if (character == null) {
        CircularProgressIndicator(
            modifier = Modifier.align(Alignment.Center)
        )
    } else {
        character?.let {
            SingleCharacter(
                gender = it.gender,
                name = it.name,
                image = it.image,
                status = it.status,
                species = it.species,
                location = it.location.name
            )
        }
    }
}

@Composable
fun SingleCharacter(
    gender: String,
    name: String,
    species: String,
    image: String,
    status: String,
    location: String
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(8.dp)
            .background(color = colorResource(R.color.purple_700), shape = RoundedCornerShape(12.dp)),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        AnimatedVisibility(
            enter = fadeIn(animationSpec = tween(600)),
            exit = fadeOut(animationSpec = tween(300))
        ) {
            AsyncImage(
                modifier = Modifier.characterImageStyle(),
                model = image,
                contentDescription = "Image of character"
            )
        }

        AnimatedText(
            text = name,
            fontSize = 28,
            color = Color.White,
            modifier = Modifier.characterTextStyle()
        )

        AnimatedText(
            text = species,
            fontSize = 20,
            color = Color.Yellow,
            modifier = Modifier.characterTextStyle()
        )

        AnimatedText(
            text = gender,
            fontSize = 16,
            color = if (gender == "Female") colorResource(R.color.purple_200) else Color.Blue,
            modifier = Modifier.characterTextStyle()
        )

        AnimatedText(
            text = "Status: $status",
            fontSize = 16,
            color = if (status == "Alive") Color.Green else Color.Red,
            modifier = Modifier.characterTextStyle()
        )

        Spacer(Modifier.size(8.dp))

        AnimatedText(
            text = "Location: $location",
            fontSize = 16,
            color = Color.Magenta,
            modifier = Modifier.characterTextStyle().padding(horizontal = 16.dp),
            fontStyle = FontStyle.Italic
        )
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewDetailCharacterScreen() {
    SingleCharacter(
        gender = "Male",
        name = "Rick Sanchez",
        species = "Human",
        image = "https://rickandmortyapi.com/api/character/avatar/1.jpeg",
        status = "Alive",
        location = "Earth"
    )
}
