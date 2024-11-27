package com.geeks.hw6_4_.ui.screens.character.detail

import com.geeks.hw6_4_.data.model.CharacterResponse
import com.geeks.hw6_4_.data.repository.character.CharacterRepository
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class DetailCharacterViewModel(
    private val characterRepository: CharacterRepository
) : ViewModel() {

    private val _singleCharacterStateFlow = MutableStateFlow<CharacterResponse?>(null)
    val singleCharacterStateFlow = _singleCharacterStateFlow.asStateFlow()

    fun getSingleCharacter(id: Int) {
        viewModelScope.launch {
            val character = characterRepository.getSingleCharacter(id)
            character?.let {
                _singleCharacterStateFlow.value = character
            }
        }
    }
}