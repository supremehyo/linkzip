package com.linkzip.linkzip.presentation.feature.group

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.linkzip.linkzip.common.UiState
import com.linkzip.linkzip.data.room.LinkData
import com.linkzip.linkzip.usecase.GroupUseCase
import com.linkzip.linkzip.usecase.LinkUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class GroupViewModel @Inject constructor(
    private val groupUseCase: GroupUseCase,
    private val linkUseCase: LinkUseCase
) : ViewModel() {
    private val _linkListByGroup = MutableStateFlow<List<LinkData>>(emptyList())
    val linkListByGroup = _linkListByGroup.asStateFlow()

    private val _favoriteList = MutableSharedFlow<MutableList<LinkData>>()
    val favoriteList = _favoriteList.asSharedFlow()

    private val _unFavoriteList = MutableSharedFlow<MutableList<LinkData>>()
    val unFavoriteList = _unFavoriteList.asSharedFlow()

    private val _selectLinkList = MutableStateFlow<List<LinkData>>(emptyList())
    val selectLinkList = _selectLinkList.asStateFlow()

    fun clearSelectLinkList() {
        viewModelScope.launch(Dispatchers.IO) {
            _selectLinkList.emit(emptyList())
        }
    }

    fun addDataInSelectList(link: LinkData) {
        viewModelScope.launch(Dispatchers.IO) {
            val tempList = _selectLinkList.value.toMutableList()
            tempList.add(link)

            _selectLinkList.emit(tempList)
        }
    }

    fun deleteDataInSelectList(link: LinkData) {
        viewModelScope.launch(Dispatchers.IO) {
            val tempList = _selectLinkList.value.toMutableList()
            tempList.remove(link)

            _selectLinkList.emit(tempList)
        }
    }

    fun updateGroupId(uid: Long, newGroupId : String,success: () -> Unit, fail: () -> Unit){
        viewModelScope.launch(Dispatchers.IO) {
            groupUseCase.updateGroupId(uid, newGroupId).collect{
                when (it) {
                    is UiState.Success -> {
                            success.invoke()
                    }
                    is UiState.Error -> {
                            fail.invoke()
                    }
                    else -> {}
                }
            }
        }
    }

    fun deleteSelectListInDB(groupId: String, success: () -> Unit, fail: () -> Unit) {
        viewModelScope.launch(Dispatchers.IO) {
            _selectLinkList.value.forEach {
                linkUseCase.deleteLinkList(it.uid ?: throw NullPointerException()).collect {
                    when (it) {
                        is UiState.Success -> {
                            withContext(Dispatchers.Main) {
                                success.invoke()
                                getLinkListByGroup(groupId)
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
    }

    fun getLinkListByGroup(groupId: String) {
        viewModelScope.launch(Dispatchers.IO) {
            groupUseCase.getLinkListByGroup(groupId).collect {
                _linkListByGroup.emit(it)
                setFavoriteList(_linkListByGroup.value)
            }
        }
    }

    fun updateFavoriteLink(link: LinkData, success: () -> Unit, fail: () -> Unit) {
        viewModelScope.launch(Dispatchers.IO) {
            linkUseCase.updateFavoriteLink(
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

    fun updateLinkData(
        uid: Long,
        link: String,
        groupId: String,
        title: String,
        memo: String,
        success: () -> Unit,
        fail: () -> Unit
    ) {
        viewModelScope.launch(Dispatchers.IO) {
            linkUseCase.updateLinkData(
                uid = uid,
                link = link,
                linkGroupId = groupId,
                linkTitle = title,
                linkMemo = memo
            ).collect {
                when (it) {
                    is UiState.Success -> {
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