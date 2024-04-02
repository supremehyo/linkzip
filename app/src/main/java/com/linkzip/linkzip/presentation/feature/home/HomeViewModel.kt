package com.linkzip.linkzip.presentation.feature.home

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.linkzip.linkzip.common.UiState
import com.linkzip.linkzip.data.model.HomeScreenState
import com.linkzip.linkzip.data.room.GroupData
import com.linkzip.linkzip.data.room.LinkData
import com.linkzip.linkzip.usecase.AddGroupUseCase
import com.linkzip.linkzip.usecase.AllViewUseCase
import com.linkzip.linkzip.usecase.LinkUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class HomeViewModel @Inject constructor(
    private val allViewUseCase: AllViewUseCase,
    private val linkUseCase: LinkUseCase,
    private val addGroupUseCase: AddGroupUseCase
) : ViewModel() {
    private val _homeScreenState = MutableStateFlow(HomeScreenState.ALL)
    val homeScreenState = _homeScreenState.asStateFlow()

    private val _favoriteListFlow = MutableStateFlow<List<LinkData>>(emptyList())
    val favoriteListFlow = _favoriteListFlow.asStateFlow()

    private val _backDim = MutableSharedFlow<Boolean>()
    val backDim = _backDim.asSharedFlow()

    //link event
    private val _linkEventFlow = MutableSharedFlow<LinkEvent>()
    val linkEventFlow = _linkEventFlow.asSharedFlow()

    private val _countGroupLink = MutableSharedFlow<List<Pair<Long, Int>>>()
    val countGroupLink = _countGroupLink.asSharedFlow()

    fun updateHomeScreenState(state: HomeScreenState) {
        _homeScreenState.value = state
    }

    private fun postLinkEvent(event: LinkEvent) {
        viewModelScope.launch {
            _linkEventFlow.emit(event)
        }
    }

    fun setBackgroundDim(isDim: Boolean) {
        viewModelScope.launch(Dispatchers.IO) {
            _backDim.emit(isDim)
        }
    }

    fun getFavoriteLink() {
        viewModelScope.launch(Dispatchers.IO) {
            linkUseCase.getFavoriteLinkList().collect {
                _favoriteListFlow.emit(it)
            }
        }
    }

    fun deleteGroupAndUpdateLinks(groupId: Long, update: (List<GroupData>) -> Unit) {
        viewModelScope.launch(Dispatchers.IO) {
            addGroupUseCase.deleteGroupAndUpdateLinks(groupId).collect {
               update.invoke(it)
            }
        }
    }

    fun insertLink(linkData: LinkData) {
        viewModelScope.launch(Dispatchers.IO) {
            Log.v("resultText1", "${linkData}")
            linkUseCase.insertLink(linkData).collect { uiState ->
                postLinkEvent(LinkEvent.InsertLinkUiEvent(uiState))
            }
        }
    }

    fun getCountLinkInGroup(groupData: List<GroupData>) {
        viewModelScope.launch(Dispatchers.IO) {
            val list = mutableListOf<Pair<Long, Int>>()
            groupData.forEach { group ->
                allViewUseCase.getCountLinkInGroup(group.groupId).collect {
                    list.add(Pair(group.groupId, it))
                }
            }
            _countGroupLink.emit(list)
        }
    }

    sealed class LinkEvent {
        data class GetLinksUiEvent(val uiState: UiState<List<LinkData>>) : LinkEvent()
        data class InsertLinkUiEvent(val uiState: UiState<Unit>) : LinkEvent()
    }
}