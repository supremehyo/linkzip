package com.linkzip.linkzip.usecase

import com.linkzip.linkzip.common.UiState
import com.linkzip.linkzip.data.room.LinkData
import com.linkzip.linkzip.repository.LinkRepository
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class FavoriteUseCase @Inject constructor(
    private val linkRepository: LinkRepository
){
    fun getAllLinks() = flow {
        emit(UiState.Loding)
        runCatching {
            linkRepository.getAllLinks()
        }.onSuccess { result ->
            emit(UiState.Success(result))
        }.onFailure {
            emit(UiState.Error(it))
        }
    }

    fun getFavoriteLinkList() = flow {
        emit(UiState.Loding)
        runCatching {
            linkRepository.getFavoriteLinkList()
        }.onSuccess { result ->
            emit(UiState.Success(result))
        }.onFailure {
            emit(UiState.Error(it))
        }
    }

    fun insertLink(group : LinkData) = flow {
        emit(UiState.Loding)
        runCatching {
            linkRepository.insertLink(group)
        }.onSuccess { result ->
            emit(UiState.Success(result))
        }.onFailure {
            emit(UiState.Error(it))
        }
    }

    fun deleteLink(uid: Long) = flow {
        emit(UiState.Loding)
        runCatching {
            linkRepository.deleteLink(uid)
        }.onSuccess { result ->
            emit(UiState.Success(result))
        }.onFailure {
            emit(UiState.Error(it))
        }
    }

    fun updateFavoriteLink(favorite: Boolean, uid: Long) = flow {
        emit(UiState.Loding)
        runCatching {
            linkRepository.updateFavoriteLink(favorite, uid)
        }.onSuccess { result ->
            emit(UiState.Success(result))
        }.onFailure {
            it.printStackTrace()
            emit(UiState.Error(it))
        }
     //   emit(linkRepository.updateFavoriteLink(favorite, uid))
    }
}