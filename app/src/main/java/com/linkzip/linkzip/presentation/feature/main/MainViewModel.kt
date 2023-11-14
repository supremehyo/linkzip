package com.linkzip.linkzip.presentation.feature.main

import androidx.lifecycle.ViewModel
import com.linkzip.linkzip.data.model.ScreenState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

@HiltViewModel
class MainViewModel : ViewModel() {
    private val _screenState = MutableStateFlow(ScreenState.HOME)
    val screenState = _screenState.asStateFlow()

    fun updateScreenState(state: ScreenState) {
        _screenState.value = state
    }
}