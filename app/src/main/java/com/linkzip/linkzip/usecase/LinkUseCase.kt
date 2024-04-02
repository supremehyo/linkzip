package com.linkzip.linkzip.usecase

import com.linkzip.linkzip.common.UiState
import com.linkzip.linkzip.data.room.LinkData
import com.linkzip.linkzip.repository.LinkRepository
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class LinkUseCase @Inject constructor(
    private val linkRepository: LinkRepository
){

    // 즐겨찾는 링크 가져오기
    fun getFavoriteLinkList() = flow {
        emit(linkRepository.getFavoriteLinkList())
    }

    // 링크 추가하기
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

    // 링크 삭제하기
    fun deleteLinkList(uid: Long) = flow {
        emit(UiState.Loding)
        runCatching {
            linkRepository.deleteLink(uid)
        }.onSuccess { result ->
            emit(UiState.Success(result))
        }.onFailure {
            it.printStackTrace()
            emit(UiState.Error(it))
        }
    }

    // 링크 수정하기
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

    // 즐겨찾는 링크 설정/해제
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
    }
}