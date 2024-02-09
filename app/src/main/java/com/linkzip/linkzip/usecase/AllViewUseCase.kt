package com.linkzip.linkzip.usecase

import com.linkzip.linkzip.common.UiState
import com.linkzip.linkzip.data.room.GroupData
import com.linkzip.linkzip.data.room.IconData
import com.linkzip.linkzip.repository.GroupRepository
import com.linkzip.linkzip.repository.IconRepository
import kotlinx.coroutines.flow.flow
import javax.inject.Inject


class AllViewUseCase @Inject constructor(
    private val iconRepository: IconRepository,
    private val groupRepository: GroupRepository
) {
    fun getAllGroups() = flow {
        emit(groupRepository.getAllGroups())
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

    fun getIconDataById(iconIdList : List<Long>) = flow{
        var iconList = mutableListOf<IconData>()
        iconIdList.forEach { iconId ->
            iconList.add(iconRepository.getIconDataById(iconId))
        }
        emit(iconList)
    }
}

