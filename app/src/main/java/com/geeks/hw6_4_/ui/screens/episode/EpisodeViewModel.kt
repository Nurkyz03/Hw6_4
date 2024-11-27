package com.geeks.hw6_4_.ui.screens.episode

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.cachedIn
import com.geeks.hw6_4_.data.repository.episode.EpisodeRepository

class EpisodeViewModel(
    private val episodeRepository: EpisodeRepository
) : ViewModel() {

    val episodePagingFlow = episodeRepository.getAllEpisodes().flow.cachedIn(viewModelScope)
}