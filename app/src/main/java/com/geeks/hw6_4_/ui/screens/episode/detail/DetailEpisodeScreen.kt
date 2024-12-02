package com.geeks.hw6_4_.ui.screens.episode.detail

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
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.paging.LoadState
import androidx.paging.compose.collectAsLazyPagingItems
import com.example.rickandmortycompose.R
import com.geeks.hw6_4_.ui.activity.CustomCircularProgressBar
import com.geeks.hw6_4_.ui.activity.CustomLinearProgressBar
import com.geeks.hw6_4_.ui.screens.episode.EpisodeViewModel
import org.koin.androidx.compose.koinViewModel

fun Modifier.episodeItemContainerStyle(): Modifier = this
    .fillMaxWidth()
    .height(120.dp)
    .clip(RoundedCornerShape(4.dp))
    .padding(top = 12.dp, start = 8.dp, end = 8.dp)
    .background(
        color = colorResource(R.color.purple_200),
        shape = RoundedCornerShape(4.dp)
    )
    .border(
        border = BorderStroke(4.dp, color = Color.Green),
        shape = RoundedCornerShape(4.dp)
    )

fun Modifier.episodeTextStyle(fontSize: Int, weight: FontWeight = FontWeight.Normal): Modifier = this
    .fontSize(fontSize.sp)
    .fontWeight(weight)
    .padding(horizontal = 12.dp)

@Composable
fun AnimatedText(text: String, fontSize: Int, weight: FontWeight = FontWeight.Normal, color: Color = Color.Black) {
    AnimatedVisibility(
        visible = true,
        enter = fadeIn(animationSpec = tween(600)),
        exit = fadeOut(animationSpec = tween(300))
    ) {
        Text(
            text = text,
            fontSize = fontSize.sp,
            color = color,
            fontWeight = weight,
            textAlign = TextAlign.Center
        )
    }
}

@Composable
fun EpisodeScreen(
    viewModel: EpisodeViewModel = koinViewModel(),
    toDetailEpisodeScreen: (episodeId: Int) -> Unit
) {
    val episodes = viewModel.episodePagingFlow.collectAsLazyPagingItems()
    val state = episodes.loadState

    LazyColumn(
        modifier = Modifier.padding(8.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
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
    AnimatedVisibility(
        visible = true,
        enter = slideInVertically(initialOffsetY = { it }, animationSpec = tween(600)),
        exit = fadeOut(animationSpec = tween(300))
    ) {
        Row(
            modifier = Modifier
                .episodeItemContainerStyle()
                .clickable { onItemClick() },
            verticalAlignment = Alignment.CenterVertically
        ) {
            AnimatedText(
                text = episode,
                fontSize = 32,
                weight = FontWeight.Bold
            )

            Column(
                modifier = Modifier.fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                AnimatedText(
                    text = name,
                    fontSize = 20,
                    weight = FontWeight.Bold
                )
                AnimatedText(
                    text = airDate,
                    fontSize = 16,
                    weight = FontWeight.W300
                )
            }
        }
    }
}
