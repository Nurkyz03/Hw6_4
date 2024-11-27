package com.geeks.hw6_4_.ui.screens.character.detail

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import kotlinx.coroutines.Dispatchers
import org.koin.androidx.compose.koinViewModel

@Composable
fun DetailCharacterScreen(
    viewModel: DetailCharacterViewModel = koinViewModel(),
    id: Int
) {
    val character by viewModel.singleCharacterStateFlow.collectAsState()
    LaunchedEffect(Dispatchers.IO) {
        viewModel.getSingleCharacter(id)
    }
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
            .padding(8.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        AsyncImage(
            modifier = Modifier
                .size(200.dp)
                .padding(4.dp),
            model = image,
            contentDescription = "image of character"
        )
        Text(
            modifier = Modifier.padding(top = 12.dp, bottom = 2.dp),
            text = name,
            fontSize = 28.sp,
            fontWeight = FontWeight.Bold
        )
        Text(
            text = species,
            fontSize = 20.sp,
            fontWeight = FontWeight.SemiBold
        )
        Text(
            modifier = Modifier.padding(8.dp),
            text = gender,
            color = if (gender == "Female") Color.Red else Color.Blue,
            fontSize = 16.sp,
            fontWeight = FontWeight.SemiBold
        )
        Text(
            text = "Status: $status",
            fontSize = 16.sp,
            fontWeight = FontWeight.W400
        )
        Spacer(Modifier.size(8.dp))
        Text(
            modifier = Modifier
                .padding(horizontal = 16.dp),
            text = "Location: $location",
            fontSize = 16.sp,
            fontStyle = FontStyle.Italic
        )
    }
}
