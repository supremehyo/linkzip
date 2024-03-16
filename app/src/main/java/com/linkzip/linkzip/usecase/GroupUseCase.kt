package com.linkzip.linkzip.usecase

import com.linkzip.linkzip.common.UiState
import com.linkzip.linkzip.repository.LinkRepository
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class GroupUseCase @Inject constructor(
    private val linkRepository: LinkRepository
) {
    fun getLinkListByGroup(groupId: Long) = flow {
        emit(linkRepository.getLinkListByGroup(groupId))
    }

    fun updateLinkData(
        uid: Long,
        link: String,
        linkGroupId: String,
        linkTitle: String,
        linkMemo: String
    ) = flow {
        emit(UiState.Loding)
        runCatching {
            linkRepository.updateLinkData(uid, link, linkGroupId, linkTitle, linkMemo)
        }.onSuccess { result ->
            emit(UiState.Success(result))
        }.onFailure {
            it.printStackTrace()
            emit(UiState.Error(it))
        }
    }
}