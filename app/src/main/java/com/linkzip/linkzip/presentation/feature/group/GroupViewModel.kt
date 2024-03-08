package com.linkzip.linkzip.presentation.feature.group

import android.util.Log
import androidx.compose.runtime.MutableState
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.linkzip.linkzip.common.UiState
import com.linkzip.linkzip.data.room.IconData
import com.linkzip.linkzip.data.room.LinkData
import com.linkzip.linkzip.usecase.AddGroupUseCase
import com.linkzip.linkzip.usecase.FavoriteUseCase
import com.linkzip.linkzip.usecase.GroupUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class GroupViewModel @Inject constructor(
    private val groupUseCase: GroupUseCase,
    private val favoriteUseCase: FavoriteUseCase
) : ViewModel() {
    private val _linkListByGroup = MutableStateFlow<List<LinkData>>(emptyList())
    val linkListByGroup = _linkListByGroup.asStateFlow()

    private val _favoriteList = MutableSharedFlow<MutableList<LinkData>>()
    val favoriteList = _favoriteList.asSharedFlow()

    private val _unFavoriteList = MutableSharedFlow<MutableList<LinkData>>()
    val unFavoriteList = _unFavoriteList.asSharedFlow()
    fun getLinkListByGroup(groupId: Long) {
        viewModelScope.launch(Dispatchers.IO) {
            groupUseCase.getLinkListByGroup(groupId).collect {
                _linkListByGroup.emit(it)
                setFavoriteList(_linkListByGroup.value)
            }
        }
    }

    fun updateFavoriteLink(link: LinkData, success: () -> Unit, fail: () -> Unit) {
        viewModelScope.launch(Dispatchers.IO) {
            favoriteUseCase.updateFavoriteLink(
                !link.favorite,
                link.uid ?: throw NullPointerException()
            ).collect {
                when (it) {
                    is UiState.Success -> {
                        modifyFavoriteLink(link)
                        withContext(Dispatchers.Main) {
                            success.invoke()
                        }
                    }

                    is UiState.Error -> {
                        withContext(Dispatchers.Main) {
                            fail.invoke()
                        }
                    }

                    else -> {}
                }
            }
        }
    }

    private fun setFavoriteList(list: List<LinkData>) {
        viewModelScope.launch(Dispatchers.IO) {
            _favoriteList.emit(list.filter { it.favorite }.toMutableList())
            _unFavoriteList.emit(list.filter { !it.favorite }.toMutableList())
        }
    }

    private fun modifyFavoriteLink(link: LinkData) {
        viewModelScope.launch(Dispatchers.IO) {
            _linkListByGroup.value.find { it.uid == link.uid }?.favorite = !link.favorite
            setFavoriteList(_linkListByGroup.value)
        }
    }
}