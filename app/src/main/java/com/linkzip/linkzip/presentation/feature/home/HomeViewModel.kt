package com.linkzip.linkzip.presentation.feature.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.linkzip.linkzip.common.UiState
import com.linkzip.linkzip.data.model.HomeScreenState
import com.linkzip.linkzip.data.room.GroupData
import com.linkzip.linkzip.data.room.LinkData
import com.linkzip.linkzip.usecase.AllViewUseCase
import com.linkzip.linkzip.usecase.FavoriteUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class HomeViewModel @Inject constructor(
    private val allViewUseCase: AllViewUseCase,
    private val favoriteUseCase: FavoriteUseCase
) : ViewModel() {
    val _homeScreenState = MutableStateFlow(HomeScreenState.ALL)
    val homeScreenState = _homeScreenState.asStateFlow()

    val _allGroupListFlow = MutableSharedFlow<UiState<List<GroupData>>>()
    val allGroupListFlow = _allGroupListFlow.asSharedFlow()

    val _favoriteListFlow = MutableSharedFlow<UiState<List<LinkData>>>()
    val favoriteListFlow = _favoriteListFlow.asSharedFlow()


    fun updateHomeScreenState(state: HomeScreenState) {
        _homeScreenState.value = state
    }


    fun getAllGroups(){
        viewModelScope.launch {
            allViewUseCase.getAllGroups().collect{ it->
                _allGroupListFlow.emit(it)
            }
        }
    }

    fun getFavoriteLink(){
        viewModelScope.launch {
            favoriteUseCase.getFavoriteLinkList().collect{
                _favoriteListFlow.emit(it)
            }
        }
    }

    fun deleteGroup(group : GroupData){
        viewModelScope.launch {
            allViewUseCase.getAllGroups().collect{ uiState->

            }
        }
    }

    fun getGroupByUid(uid : Long){
        viewModelScope.launch {
            allViewUseCase.getAllGroups().collect{ uiState->

            }
        }
    }


}