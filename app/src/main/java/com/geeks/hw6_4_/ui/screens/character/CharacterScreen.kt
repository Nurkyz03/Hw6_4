package com.geeks.hw6_4_.ui.screens.character

import androidx.compose.animation.*
import androidx.compose.animation.core.tween
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
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
import com.example.rickandmortycompose.R
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

        // Показываем индикатор загрузки при добавлении новых данных
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

    // Показываем прогресс-бар при первой загрузке данных
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
    // Анимация появления каждого элемента
    AnimatedVisibility(
        visible = true,
        enter = slideInHorizontally(initialOffsetX = { it }, animationSpec = tween(600)),
        exit = fadeOut(animationSpec = tween(300))
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp)
                .clip(RoundedCornerShape(8.dp))
                .background(color = colorResource(R.color.purple_200))
                .border(BorderStroke(2.dp, Color.Green), shape = RoundedCornerShape(8.dp))
                .clickable { onItemClick() }
                .padding(8.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            // Изображение персонажа
            AsyncImage(
                model = photo,
                contentDescription = "Image of character",
                modifier = Modifier
                    .size(86.dp)
                    .padding(8.dp)
                    .clip(RoundedCornerShape(50))
                    .border(2.dp, Color.White, RoundedCornerShape(50))
            )

            // Информация о персонаже
            Column(
                modifier = Modifier.fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = name,
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.Black,
                    textAlign = TextAlign.Center
                )
                Text(
                    text = gender,
                    fontSize = 12.sp,
                    fontWeight = FontWeight.SemiBold,
                    color = if (gender == "Female") Color.Magenta else Color.Blue
                )
                Text(
                    text = location,
                    fontSize = 16.sp,
                    fontStyle = FontStyle.Italic,
                    fontWeight = FontWeight.W500,
                    color = Color.DarkGray,
                    textAlign = TextAlign.Center
                )
            }
        }
    }
}
