package com.geeks.hw6_4_.ui

import com.geeks.hw6_4_.ui.screens.character.CharacterViewModel
import com.geeks.hw6_4_.ui.screens.character.detail.DetailCharacterViewModel
import com.geeks.hw6_4_.ui.screens.episode.EpisodeViewModel
import com.geeks.hw6_4_.ui.screens.episode.detail.DetailEpisodeViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.module.Module
import org.koin.dsl.module

val uiModule: Module = module {

    viewModel {
        CharacterViewModel(get())
    }

    viewModel {
        EpisodeViewModel(get())
    }

    viewModel {
        DetailCharacterViewModel(get())
    }

    viewModel {
        DetailEpisodeViewModel(get())
    }
}