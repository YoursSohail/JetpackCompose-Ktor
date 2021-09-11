package com.sohail.jetktor.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sohail.jetktor.model.FreeGames
import com.sohail.jetktor.repo.FreeGameRepo
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch

class GameViewModel() : ViewModel() {

    val freeGamesFlow = MutableStateFlow<FreeGames?>(null)
    init {
        viewModelScope.launch {
            kotlin.runCatching {
                FreeGameRepo.getFreeGames()
            }.onSuccess {
                freeGamesFlow.value = it
            }.onFailure {
                freeGamesFlow.value = null
            }
        }
    }
}