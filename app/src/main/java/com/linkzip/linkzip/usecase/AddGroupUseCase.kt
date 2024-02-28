package com.linkzip.linkzip.usecase

import com.linkzip.linkzip.common.UiState
import com.linkzip.linkzip.data.room.GroupData
import com.linkzip.linkzip.repository.GroupRepository
import com.linkzip.linkzip.repository.IconRepository
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class AddGroupUseCase @Inject constructor(
    private val iconRepository: IconRepository,
    private val groupRepository: GroupRepository
) {
    fun getIconData() = flow {
        emit(UiState.Loding)
        runCatching {
            iconRepository.getIconData()
        }.onSuccess { result ->
            emit(UiState.Success(result))
        }.onFailure {
            emit(UiState.Error(it))
        }
    }


    fun insertGroup(group: GroupData) = flow {
        emit(UiState.Loding)
        runCatching {
            groupRepository.insertGroup(group)
        }.onSuccess { result ->
            emit(UiState.Success(result))
        }.onFailure {
            emit(UiState.Error(it))
        }
    }

    fun updateGroup(uid: Long, name: String, iconId: Long, date: String) = flow {
        emit(UiState.Loding)
        runCatching {
            groupRepository.updateGroupById(uid, name, iconId, date)
        }.onSuccess { result ->
            emit(UiState.Success(result))
        }.onFailure {
            emit(UiState.Error(it))
        }
    }
}