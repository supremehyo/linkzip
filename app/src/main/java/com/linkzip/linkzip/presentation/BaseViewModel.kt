package com.linkzip.linkzip.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.linkzip.linkzip.data.ToastKind
import com.linkzip.linkzip.data.ToastType
import com.linkzip.linkzip.data.room.GroupData
import com.linkzip.linkzip.data.room.IconData
import com.linkzip.linkzip.usecase.AddGroupUseCase
import com.linkzip.linkzip.usecase.AllViewUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

/**
 * 공통적으로 쓰이는 메소드 및 변수
 */
@HiltViewModel
class BaseViewModel @Inject constructor(
    private val allViewUseCase: AllViewUseCase,
    private val addGroupUseCase: AddGroupUseCase
): ViewModel() {

    // 전체 그룹
    private val _allGroupListFlow = MutableStateFlow<List<GroupData>>(emptyList())
    val allGroupListFlow = _allGroupListFlow.asStateFlow()

    // 전체 아이콘 리스트
    private val _iconListFlow = MutableStateFlow<List<IconData>>(emptyList())
    val iconListFlow = _iconListFlow.asStateFlow()

    // 그룹에 해당하는 아이콘 리스트
    private val _iconListByGroup = MutableStateFlow<List<IconData>>(emptyList())
    val iconListByGroup = _iconListByGroup.asStateFlow()

    // 토스트 메시지
    private val _isShowToastMessage = MutableStateFlow<ToastKind>(ToastKind.None(ToastType.SUCCESS, false))
    val isShowToastMessage = _isShowToastMessage.asStateFlow()

    // 전체 그룹 불러오기
    fun getAllGroups() {
        viewModelScope.launch(Dispatchers.IO) {
            allViewUseCase.getAllGroups().collect {
                _allGroupListFlow.emit(it)
            }
        }
    }

    // 전체 아이콘 불러오기
    fun getIconData() {
        viewModelScope.launch(Dispatchers.IO) {
            addGroupUseCase.getIconData().collect {
                _iconListFlow.emit(it)
            }
        }
    }

    // 삭제, 수정 등의 이유로 전체 그룹 리스트의 수정이 필요할 경우
    fun updateAllGroupList(list: List<GroupData>) {
        viewModelScope.launch(Dispatchers.IO) {
            _allGroupListFlow.emit(list)
        }
    }

    // 그룹에 해당하는 아이콘 불러오기
    fun getIconListById(iconIdList: List<Long>) {
        viewModelScope.launch(Dispatchers.IO) {
            allViewUseCase.getIconListById(iconIdList).collect {
                if (it != null) {
                    _iconListByGroup.emit(it)
                }
            }
        }
    }

    // 토스트 메시지 값 넣기
    fun setToastMessage(toast: ToastKind) {
        viewModelScope.launch {
            _isShowToastMessage.emit(toast)
        }
    }
}