package com.linkzip.linkzip.common

sealed class UiState<out T : Any> {
    data object Loding : UiState<Nothing>()
    data class Success<T : Any>(val data : T) : UiState<T>()
    data class Error(val error: Throwable?) : UiState<Nothing>()
}