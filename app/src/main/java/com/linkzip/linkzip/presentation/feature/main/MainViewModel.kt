package com.linkzip.linkzip.presentation.feature.main

import android.content.SharedPreferences
import androidx.lifecycle.ViewModel
import com.linkzip.linkzip.data.model.IS_FRIST
import com.linkzip.linkzip.data.model.ScreenState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor() : ViewModel() {
    private val _screenState = MutableStateFlow(ScreenState.HOME)
    val screenState = _screenState.asStateFlow()

    fun updateScreenState(state: ScreenState) {
        _screenState.value = state
    }

    fun checkFirstStart(data : SharedPreferences) : Boolean{
        val editor = data.edit()
        if(!data.getBoolean(IS_FRIST,false)){
            editor.putBoolean(IS_FRIST , true).apply()
            return true
        }else{
            return false
        }
    }
}