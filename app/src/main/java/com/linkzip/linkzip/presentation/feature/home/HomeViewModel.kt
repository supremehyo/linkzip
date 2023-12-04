package com.linkzip.linkzip.presentation.feature.home

import androidx.lifecycle.ViewModel
import com.linkzip.linkzip.data.model.HomeScreenState
import com.linkzip.linkzip.data.room.GroupData
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject


@HiltViewModel
class HomeViewModel @Inject constructor(

) : ViewModel() {
    val _homeScreenState = MutableStateFlow(HomeScreenState.ALL)
    val homeScreenState = _homeScreenState.asStateFlow()

    val _allGroupLink = MutableStateFlow<List<GroupData>>(mutableListOf())
    val allGroupLink = _allGroupLink.asStateFlow()


    fun updateHomeScreenState(state: HomeScreenState) {
        _homeScreenState.value = state
    }

    fun getAllGroupLinks(){

    }
}