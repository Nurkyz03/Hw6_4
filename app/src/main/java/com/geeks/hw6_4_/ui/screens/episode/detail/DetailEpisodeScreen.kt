package com.example.rickandmortycompose.ui.screens.episode.detail

import android.util.Log
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
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
import com.example.rickandmortycompose.R
import com.geeks.hw6_4_.data.model.CharacterResponse
import com.geeks.hw6_4_.ui.activity.CustomCircularProgressBar
import com.geeks.hw6_4_.ui.activity.CustomLinearProgressBar
import com.geeks.hw6_4_.ui.screens.character.CharacterItem
import com.geeks.hw6_4_.ui.screens.episode.EpisodeViewModel
import com.geeks.hw6_4_.ui.screens.episode.detail.DetailEpisodeViewModel
import kotlinx.coroutines.Dispatchers
import org.koin.androidx.compose.koinViewModel
@Composable
fun EpisodeScreen(
    viewModel: EpisodeViewModel = koinViewModel(),
    toDetailEpisodeScreen: (episodeId: Int) -> Unit
) {
    val episodes = viewModel.episodePagingFlow.collectAsLazyPagingItems()
    val state = episodes.loadState

    LazyColumn {
        items(episodes.itemCount) { index ->
            episodes[index]?.let { item ->
                EpisodeItem(
                    episode = item.episode,
                    name = item.name,
                    airDate = item.airDate,
                    onItemClick = {
                        toDetailEpisodeScreen(item.id)
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
    if (state.refresh is LoadState.Loading && episodes.itemCount == 0) {
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
private fun EpisodeItem(
    episode: String,
    name: String,
    airDate: String,
    onItemClick: () -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .height(120.dp)
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
            .clickable { onItemClick() },
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            modifier = Modifier.padding(horizontal = 12.dp),
            fontSize = 32.sp,
            fontWeight = FontWeight.Bold,
            text = episode,
        )
        Column(
            modifier = Modifier.fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                modifier = Modifier.padding(top = 12.dp, bottom = 2.dp),
                text = name,
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold,
                textAlign = TextAlign.Center,
            )
            Text(
                modifier = Modifier.padding(bottom = 12.dp, top = 8.dp),
                text = airDate,
                fontSize = 16.sp,
                fontWeight = FontWeight.W300
            )
        }
    }
}