package com.linkzip.linkzip.presentation.feature.home

import androidx.lifecycle.ViewModel
import com.linkzip.linkzip.data.model.HomeScreenState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow


@HiltViewModel
class HomeViewModel : ViewModel() {
    val _homeScreenState = MutableStateFlow(HomeScreenState.ALL)
    val homeScreenState = _homeScreenState.asStateFlow()

    fun updateHomeScreenState(state: HomeScreenState) {
        _homeScreenState.value = state
    }
}