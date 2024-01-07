package com.linkzip.linkzip.usecase

import com.linkzip.linkzip.common.UiState
import com.linkzip.linkzip.repository.IconRepository
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class AddGroupUseCase  @Inject constructor(
    private val iconRepository: IconRepository
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
}