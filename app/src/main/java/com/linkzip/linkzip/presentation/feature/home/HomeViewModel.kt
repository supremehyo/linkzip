package com.linkzip.linkzip.presentation.feature.home

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.linkzip.linkzip.common.UiState
import com.linkzip.linkzip.data.model.HomeScreenState
import com.linkzip.linkzip.data.room.GroupData
import com.linkzip.linkzip.data.room.IconData
import com.linkzip.linkzip.data.room.LinkData
import com.linkzip.linkzip.usecase.AddGroupUseCase
import com.linkzip.linkzip.usecase.AllViewUseCase
import com.linkzip.linkzip.usecase.FavoriteUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class HomeViewModel @Inject constructor(
    private val allViewUseCase: AllViewUseCase,
    private val favoriteUseCase: FavoriteUseCase,
    private val addGroupUseCase: AddGroupUseCase
) : ViewModel() {
    private val _homeScreenState = MutableStateFlow(HomeScreenState.ALL)
    val homeScreenState = _homeScreenState.asStateFlow()

    private val _allGroupListFlow = MutableStateFlow<List<GroupData>>(emptyList())
    val allGroupListFlow = _allGroupListFlow.asStateFlow()

    private val _favoriteListFlow = MutableStateFlow<List<LinkData>>(emptyList())
    val favoriteListFlow = _favoriteListFlow.asStateFlow()

    private val _pasteClipBoardEvent = MutableSharedFlow<Boolean>()
    val pasteClipBoardEvent = _pasteClipBoardEvent.asSharedFlow()

    private val _backDim = MutableSharedFlow<Boolean>()
    val backDim = _backDim.asSharedFlow()

    private val _iconListFlow = MutableStateFlow<List<IconData>>(emptyList())
    val iconListFlow = _iconListFlow.asStateFlow()

    //link event
    private val _linkEventFlow = MutableSharedFlow<LinkEvent>()
    val linkEventFlow = _linkEventFlow.asSharedFlow()

    fun getIconListById(iconIdList : List<Long>) {
        viewModelScope.launch(Dispatchers.IO) {
            allViewUseCase.getIconListById(iconIdList).collect {
                if(it != null){
                    _iconListFlow.emit(it)
                }
            }
        }
    }

    private fun postLinkEvent(event : LinkEvent){
        viewModelScope.launch {
            _linkEventFlow.emit(event)
        }
    }

    fun setBackgroundDim(isDim : Boolean){
        viewModelScope.launch(Dispatchers.IO) {
            _backDim.emit(isDim)
        }
    }

    //link event

    fun updateHomeScreenState(state: HomeScreenState) {
        _homeScreenState.value = state
    }


    fun getAllGroups(){
        viewModelScope.launch(Dispatchers.IO) {
            allViewUseCase.getAllGroups().collect{ it->
                _allGroupListFlow.emit(it)
            }
        }
    }

    fun getFavoriteLink(){
        viewModelScope.launch(Dispatchers.IO) {
            favoriteUseCase.getFavoriteLinkList().collect{
                _favoriteListFlow.emit(it)
            }
        }
    }

    fun pasteClipBoardEvent(boolean: Boolean){
        viewModelScope.launch {
            _pasteClipBoardEvent.emit(boolean)
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

    fun deleteGroupAndUpdateLinks(groupId : Long){
        viewModelScope.launch(Dispatchers.IO) {
            addGroupUseCase.deleteGroupAndUpdateLinks(groupId).collect{it->
                _allGroupListFlow.emit(it)
            }
        }
    }

    fun insertLink(linkData: LinkData){
        viewModelScope.launch(Dispatchers.IO) {
            Log.v("resultText1", "${linkData}")
            favoriteUseCase.insertLink(linkData).collect{ uiState ->
                postLinkEvent(LinkEvent.InsertLinkUiEvent(uiState))
            }
        }
    }

    fun getAllLinks(){
        viewModelScope.launch(Dispatchers.IO) {
            favoriteUseCase.getAllLinks().collect{ uiState ->
                postLinkEvent(LinkEvent.GetLinksUiEvent(uiState))
            }
        }
    }



    sealed class LinkEvent {
        data class GetLinksUiEvent(val uiState: UiState<List<LinkData>>) : LinkEvent()
        data class InsertLinkUiEvent(val uiState: UiState<Unit>) : LinkEvent()
    }
}