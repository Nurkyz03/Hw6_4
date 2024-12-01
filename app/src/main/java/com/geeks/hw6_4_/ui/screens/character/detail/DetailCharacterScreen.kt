package com.geeks.hw6_4_.ui.screens.character.detail

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
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
import androidx.compose.foundation.layout.RowScopeInstance.align
import androidx.compose.ui.tooling.preview.Preview

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
            .background(
                color = colorResource(R.color.purple_700),
                shape = RoundedCornerShape(12.dp)
            ),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        AnimatedVisibility(
            enter = fadeIn(animationSpec = tween(600)),
            exit = fadeOut(animationSpec = tween(300))
        ) {
            AsyncImage(
                modifier = Modifier
                    .size(200.dp)
                    .padding(4.dp)
                    .clip(shape = RoundedCornerShape(4.dp))
                    .border(
                        border = BorderStroke(1.dp, color = Color.White),
                        shape = RoundedCornerShape(2.dp)
                    ),
                model = image,
                contentDescription = "Image of character"
            )
        }

        AnimatedVisibility(
            visible = true,
            enter = fadeIn(animationSpec = tween(600)),
            exit = fadeOut(animationSpec = tween(300))
        ) {
            Text(
                modifier = Modifier.padding(top = 12.dp, bottom = 2.dp),
                text = name,
                fontSize = 28.sp,
                color = Color.White,
                fontWeight = FontWeight.Bold
            )
        }

        AnimatedVisibility(
            visible = true,
            enter = fadeIn(animationSpec = tween(800)),
            exit = fadeOut(animationSpec = tween(300))
        ) {
            Text(
                text = species,
                fontSize = 20.sp,
                color = Color.Yellow,
                fontWeight = FontWeight.SemiBold
            )
        }

        AnimatedVisibility(
            visible = true,
            enter = fadeIn(animationSpec = tween(1000)),
            exit = fadeOut(animationSpec = tween(300))
        ) {
            Text(
                modifier = Modifier.padding(8.dp),
                text = gender,
                color = if (gender == "Female") colorResource(R.color.purple_200) else Color.Blue,
                fontSize = 16.sp,
                fontWeight = FontWeight.SemiBold
            )
        }

        AnimatedVisibility(
            visible = true,
            enter = fadeIn(animationSpec = tween(1200)),
            exit = fadeOut(animationSpec = tween(300))
        ) {
            Text(
                text = "Status: $status",
                fontSize = 16.sp,
                fontWeight = FontWeight.W400,
                color = if (status == "Alive") Color.Green else Color.Red
            )
        }

        Spacer(Modifier.size(8.dp))

        AnimatedVisibility(
            visible = true,
            enter = fadeIn(animationSpec = tween(1400)),
            exit = fadeOut(animationSpec = tween(300))
        ) {
            Text(
                modifier = Modifier
                    .padding(horizontal = 16.dp),
                text = "Location: $location",
                fontSize = 16.sp,
                fontStyle = FontStyle.Italic,
                color = Color.Magenta
            )
        }
    }
}
