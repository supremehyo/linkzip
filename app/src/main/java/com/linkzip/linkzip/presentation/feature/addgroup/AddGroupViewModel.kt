package com.linkzip.linkzip.presentation.feature.addgroup

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.linkzip.linkzip.common.UiState
import com.linkzip.linkzip.data.room.IconData
import com.linkzip.linkzip.usecase.AddGroupUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AddGroupViewModel @Inject constructor(
    private val addGroupUseCase: AddGroupUseCase
) : ViewModel() {
    val _iconListFlow = MutableSharedFlow<UiState<List<IconData>>>()
    val iconListFlow = _iconListFlow.asSharedFlow()

    val _currentAddGroupIcon = MutableStateFlow(IconData.ICON_NO_GROUP)
    val currentAddGroupIcon = _currentAddGroupIcon.asStateFlow()

    fun getIconData(){
        viewModelScope.launch(Dispatchers.IO) {
            addGroupUseCase.getIconData().collect{
                _iconListFlow.emit(it)
            }
        }
    }

    fun updateCurrentIcon(select: String) {
        viewModelScope.launch(Dispatchers.IO) {
            _currentAddGroupIcon.emit(select)
        }

    }
}