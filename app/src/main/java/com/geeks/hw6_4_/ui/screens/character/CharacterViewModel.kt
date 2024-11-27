package com.geeks.hw6_4_.ui.screens.character

import com.geeks.hw6_4_.data.repository.character.CharacterRepository
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.cachedIn

class CharacterViewModel(
    private val characterRepository: CharacterRepository
) : ViewModel() {

    val charactersPagingFlow = characterRepository.getAllCharacter().flow.cachedIn(viewModelScope)
}