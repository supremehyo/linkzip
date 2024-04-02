package com.linkzip.linkzip.usecase

import com.linkzip.linkzip.common.UiState
import com.linkzip.linkzip.repository.LinkRepository
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class GroupUseCase @Inject constructor(
    private val linkRepository: LinkRepository
) {
    // 그룹에 해당하는 링크 가져오기
    fun getLinkListByGroup(groupId: String) = flow {
        emit(linkRepository.getLinkListByGroup(groupId))
    }

    // 그룹 이동하기
    fun updateGroupId(
        uid: Long,
        newGroupId : String
    ) = flow {
        emit(UiState.Loding)
        runCatching {
            linkRepository.updateLinkGroupId(uid, newGroupId)
        }.onSuccess { result ->
            emit(UiState.Success(result))
        }.onFailure {
            it.printStackTrace()
            emit(UiState.Error(it))
        }
    }
}