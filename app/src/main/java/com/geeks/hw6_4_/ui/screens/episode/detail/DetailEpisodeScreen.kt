package com.example.rickandmortycompose.ui.screens.episode.detail

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.geeks.hw6_4_.R
import com.geeks.hw6_4_.data.model.CharacterResponse
import com.geeks.hw6_4_.ui.activity.CustomLinearProgressBar
import com.geeks.hw6_4_.ui.screens.character.CharacterItem
import com.geeks.hw6_4_.ui.screens.episode.detail.DetailEpisodeViewModel
import kotlinx.coroutines.Dispatchers
import org.koin.androidx.compose.koinViewModel

@Composable
fun DetailEpisodeScreen(
    viewModel: DetailEpisodeViewModel = koinViewModel(),
    toDetailCharacterScreen: (characterId: Int) -> Unit,
    episodeId: Int
) {
    val episode by viewModel.singleEpisodeStateFlow.collectAsState()
    val characters by viewModel.episodeCharactersStateFlow.collectAsState()

    LaunchedEffect(Dispatchers.IO) {
        viewModel.getSingleEpisode(episodeId)
    }
    Log.e("ololo", "DetailEpisodeScreen: $characters")

    if (episode == null) {
        CustomLinearProgressBar()
    } else {
        episode?.let {
            SingleEpisode(
                episode = it.episode,
                airDate = it.airDate,
                url = it.url,
                name = it.name,
                characters = characters,
                toDetailCharacterScreen = toDetailCharacterScreen
            )
        }

    }
}


@Composable
fun SingleEpisode(
    episode: String,
    name: String,
    airDate: String,
    url: String,
    characters: List<CharacterResponse>,
    toDetailCharacterScreen: (characterId: Int) -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(8.dp)
            .background(
                color = colorResource(R.color.purple_700),
                shape = RoundedCornerShape(12.dp)
            ),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Spacer(modifier = Modifier.size(20.dp))
        Text(
            modifier = Modifier,
            text = episode,
            fontSize = 24.sp,
            color = Color.Yellow
        )
        Text(
            modifier = Modifier.padding(top = 12.dp, bottom = 2.dp),
            text = name,
            textAlign = TextAlign.Center,
            fontSize = 28.sp,
            fontWeight = FontWeight.Bold,
            color = Color.White
        )
        Text(
            text = airDate,
            fontSize = 20.sp,
            fontWeight = FontWeight.SemiBold,
            color = Color.LightGray
        )
        Spacer(Modifier.size(16.dp))
        Text(
            modifier = Modifier
                .padding(horizontal = 16.dp),
            text = url,
            fontSize = 16.sp,
            fontStyle = FontStyle.Italic,
            color = Color.Cyan
        )
        Spacer(Modifier.size(16.dp))
        Text(
            text = "Characters in this Episode:",
            fontSize = 20.sp,
            fontWeight = FontWeight.Bold,
            color = Color.White,
            modifier = Modifier.padding(horizontal = 16.dp)
        )
        if (characters.isEmpty()) {
            CustomLinearProgressBar()
        } else {
            LazyColumn {
                items(characters) { character ->
                    CharacterItem(
                        photo = character.image,
                        name = character.name,
                        gender = character.gender,
                        location = character.location.name,
                        onItemClick = {
                            toDetailCharacterScreen(character.id)
                        }

                    )
                }
            }
        }
    }
}