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
}