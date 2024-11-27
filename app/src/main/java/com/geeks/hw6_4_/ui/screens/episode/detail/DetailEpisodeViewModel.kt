package com.geeks.hw6_4_.ui.screens.episode.detail

import androidx.lifecycle.ViewModel
import com.geeks.hw6_4_.data.model.CharacterResponse
import com.geeks.hw6_4_.data.model.EpisodeResponse
import com.geeks.hw6_4_.data.repository.episode.EpisodeRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

class DetailEpisodeViewModel(
    private val episodeRepository: EpisodeRepository
) : ViewModel() {

    private val _singleEpisodeStateFlow = MutableStateFlow<EpisodeResponse?>(null)
    val singleEpisodeStateFlow = _singleEpisodeStateFlow.asStateFlow()

    private val _episodeCharactersStateFlow = MutableStateFlow<List<CharacterResponse>>(emptyList())
    val episodeCharactersStateFlow = _episodeCharactersStateFlow.asStateFlow()

    suspend fun getSingleEpisode(id: Int) {
        val episode = episodeRepository.getSingleEpisode(id)
        episode?.let {
            _singleEpisodeStateFlow.value = episode
            getEpisodeCharacters(it.characters)
        }
    }

    suspend fun getEpisodeCharacters(characterUrl: List<String>) {
        val charactersIds = characterUrl.map { url ->
            url.split("/").last().toInt()
        }
        val characters = episodeRepository.getEpisodeCharacters(charactersIds)
        _episodeCharactersStateFlow.value = characters
    }
}