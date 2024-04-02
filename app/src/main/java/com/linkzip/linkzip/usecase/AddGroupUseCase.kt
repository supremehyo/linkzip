package com.linkzip.linkzip.usecase

import com.linkzip.linkzip.common.UiState
import com.linkzip.linkzip.data.room.GroupData
import com.linkzip.linkzip.repository.GroupRepository
import com.linkzip.linkzip.repository.IconRepository
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class AddGroupUseCase @Inject constructor(
    private val groupRepository: GroupRepository,
    private val iconRepository: IconRepository
) {
    // 그룹 추가하기
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

    // 그룹 수정하기
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

    // 그룹 삭제하기
    fun deleteGroupAndUpdateLinks(groupId: Long) = flow {
        emit(groupRepository.deleteGroupAndUpdateLinks(groupId))
    }

    // 전체 아이콘 불러오기
    fun getIconData() = flow {
        emit(iconRepository.getIconData())
    }
}