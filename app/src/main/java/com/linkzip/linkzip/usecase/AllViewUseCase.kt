package com.linkzip.linkzip.usecase

import kotlinx.coroutines.flow.flow

/*
class AllViewUseCase {
    fun getAllGroups() = flow {
        emit(UiState.Loding)
        runCatching {
            joinRepository.signUp(joinReq)
        }.onSuccess { result ->
            emit(UiState.Success(result))
        }.onFailure {
            emit(UiState.Error(it))
        }
    }
}
}

 */