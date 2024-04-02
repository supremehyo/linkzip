package com.linkzip.linkzip.presentation.feature.addgroup

import android.util.Log
import androidx.compose.ui.graphics.toArgb
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.linkzip.linkzip.common.UiState
import com.linkzip.linkzip.data.room.GroupData
import com.linkzip.linkzip.data.room.IconData
import com.linkzip.linkzip.ui.theme.WG70
import com.linkzip.linkzip.ui.theme.WHITE
import com.linkzip.linkzip.usecase.AddGroupUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class AddGroupViewModel @Inject constructor(
    private val addGroupUseCase: AddGroupUseCase
) : ViewModel() {
    private val _currentAddGroupIcon = MutableStateFlow(
        IconData(
            iconId = 0,
            iconButtonColor = WG70.toArgb(),
            iconHeaderColor = WHITE.toArgb(),
            iconName = IconData.ICON_NO_GROUP
        )
    )
    val currentAddGroupIcon = _currentAddGroupIcon.asStateFlow()

    fun updateCurrentIcon(select: IconData) {
        viewModelScope.launch(Dispatchers.IO) {
            _currentAddGroupIcon.emit(select)
        }
    }

    fun insertGroup(group: GroupData, success: () -> Unit, fail: () -> Unit) {
        viewModelScope.launch(Dispatchers.IO) {
            addGroupUseCase.insertGroup(group).collect {
                Log.v("insertGroup", "${it}")

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
                    else -> {

                    }
                }
            }
        }
    }

    fun updateGroup(
        uid: Long,
        name: String,
        iconId: Long,
        date: String,
        success: () -> Unit,
        fail: () -> Unit
    ) {
        viewModelScope.launch(Dispatchers.IO) {
            addGroupUseCase.updateGroup(
                uid = uid,
                name = name,
                iconId = iconId,
                date = date
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
                    else -> {

                    }
                }
            }
        }
    }
}