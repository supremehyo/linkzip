package com.linkzip.linkzip.presentation.feature.my

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.linkzip.linkzip.data.model.MainScreenState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MyPageViewModel  @Inject constructor() : ViewModel() {
    private val _reviewEvent = MutableStateFlow(false)
    val reviewEvent = _reviewEvent.asStateFlow()

    fun updateReviewEventState(state: Boolean) {
        viewModelScope.launch {
            _reviewEvent.emit(state)
        }
    }
}