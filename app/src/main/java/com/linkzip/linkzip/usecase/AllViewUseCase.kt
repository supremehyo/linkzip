package com.linkzip.linkzip.usecase

import com.linkzip.linkzip.common.UiState
import com.linkzip.linkzip.data.room.GroupData
import com.linkzip.linkzip.repository.GroupRepository
import kotlinx.coroutines.flow.flow
import javax.inject.Inject


class AllViewUseCase @Inject constructor(
 private val groupRepository: GroupRepository
) {
    fun getAllGroups() = flow {
        emit(UiState.Loding)
        runCatching {
            groupRepository.getAllGroups()
        }.onSuccess { result ->
            emit(UiState.Success(result))
        }.onFailure {
            emit(UiState.Error(it))
        }
    }

    fun insertGroup(group : GroupData)  = flow {
        emit(UiState.Loding)
        runCatching {
            groupRepository.insertGroup(group)
        }.onSuccess { result ->
            emit(UiState.Success(result))
        }.onFailure {
            emit(UiState.Error(it))
        }
    }

    fun deleteGroup(group : GroupData)  = flow {
        emit(UiState.Loding)
        runCatching {
            groupRepository.deleteGroup(group)
        }.onSuccess { result ->
            emit(UiState.Success(result))
        }.onFailure {
            emit(UiState.Error(it))
        }
    }

    fun getGroupByUid(uid : Long)  = flow {
        emit(UiState.Loding)
        runCatching {
            groupRepository.getGroupByUid(uid)
        }.onSuccess { result ->
            emit(UiState.Success(result))
        }.onFailure {
            emit(UiState.Error(it))
        }
    }
}

