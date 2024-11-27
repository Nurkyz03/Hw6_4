package com.geeks.hw6_4_.ui.screens.character

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.paging.LoadState
import androidx.paging.compose.collectAsLazyPagingItems
import coil.compose.AsyncImage
import com.geeks.hw6_4_.R
import com.geeks.hw6_4_.ui.activity.CustomCircularProgressBar
import com.geeks.hw6_4_.ui.activity.CustomLinearProgressBar
import org.koin.androidx.compose.koinViewModel

@Composable
fun CharacterScreen(
    viewModel: CharacterViewModel = koinViewModel(),
    toDetailCharacterScreen: (characterId: Int) -> Unit
) {
    val characters = viewModel.charactersPagingFlow.collectAsLazyPagingItems()
    val state = characters.loadState

    LazyColumn {
        items(characters.itemCount) { index ->
            characters[index]?.let { character ->
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
        if (state.append is LoadState.Loading) {
            item {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp),
                    contentAlignment = Alignment.Center
                ) {
                    CustomCircularProgressBar()
                }
            }
        }
    }
    if (state.refresh is LoadState.Loading && characters.itemCount == 0) {
        Box(
            modifier = Modifier
                .fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            CustomLinearProgressBar()
        }
    }
}


@Composable
fun CharacterItem(
    photo: String,
    name: String,
    gender: String,
    location: String,
    onItemClick: () -> Unit
) {
    Row(modifier = Modifier
        .fillMaxWidth()
        .clip(
            shape = RoundedCornerShape(4.dp)
        )
        .padding(top = 12.dp, start = 8.dp, end = 8.dp)
        .background(
            color = colorResource(R.color.purple_200),
            shape = RoundedCornerShape(4.dp)
        )
        .border(
            border = BorderStroke(
                4.dp,
                color = Color.Green
            ),
            shape = RoundedCornerShape(4.dp)
        )
        .clickable { onItemClick() }
    ) {
        AsyncImage(
            modifier = Modifier
                .size(86.dp)
                .padding(top = 8.dp, start = 12.dp)
                .clip(shape = RoundedCornerShape(4.dp))
                .border(
                    border = BorderStroke(1.dp, color = Color.White),
                    shape = RoundedCornerShape(2.dp)
                ),
            model = photo,
            contentDescription = "image of character"
        )
        Column(
            modifier = Modifier.fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                modifier = Modifier.padding(top = 12.dp, bottom = 2.dp),
                text = name,
                textAlign = TextAlign.Center,
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold,
                color = Color.Black

            )
            Text(
                text = gender,
                fontSize = 12.sp,
                fontWeight = FontWeight.SemiBold,
                color = if (gender == "Female") Color.Magenta else Color.Blue
            )
            Text(
                modifier = Modifier.padding(bottom = 8.dp),
                text = location,
                textAlign = TextAlign.Center,
                fontSize = 16.sp,
                fontStyle = FontStyle.Italic,
                fontWeight = FontWeight.W500,
                color = Color.DarkGray
            )
        }
    }
}