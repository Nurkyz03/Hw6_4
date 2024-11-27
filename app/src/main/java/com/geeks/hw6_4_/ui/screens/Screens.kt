package com.geeks.hw6_4_.ui.screens

sealed class Screens(val route: String) {

    data object CharacterScreen : Screens("CharacterScreen")
    data object DetailCharacterScreen : Screens("DetailCharacterScreen/{characterId}")
    data object EpisodeScreen : Screens("EpisodeScreen")
    data object DetailEpisodeScreen : Screens("DetailEpisodeScreen/{episodeId}")
}