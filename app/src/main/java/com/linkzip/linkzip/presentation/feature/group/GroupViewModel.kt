package com.linkzip.linkzip.presentation.feature.group

import android.util.Log
import androidx.compose.runtime.MutableState
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.linkzip.linkzip.common.UiState
import com.linkzip.linkzip.data.room.IconData
import com.linkzip.linkzip.data.room.LinkData
import com.linkzip.linkzip.usecase.AddGroupUseCase
import com.linkzip.linkzip.usecase.GroupUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class GroupViewModel @Inject  constructor(
    private val groupUseCase: GroupUseCase
) : ViewModel() {
    private val _linkListByGroup = MutableStateFlow<List<LinkData>>(emptyList())
    val linkListByGroup = _linkListByGroup.asStateFlow()

    fun getLinkListByGroup(groupId: Long) {
        viewModelScope.launch(Dispatchers.IO) {
            groupUseCase.getLinkListByGroup(groupId).collect {
                Log.v("getLink", "${it}")
                _linkListByGroup.emit(it)
            }
        }
    }

}