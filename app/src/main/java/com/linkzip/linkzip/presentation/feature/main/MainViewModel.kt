package com.linkzip.linkzip.presentation.feature.main

import android.content.SharedPreferences
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.linkzip.linkzip.data.model.IS_FRIST
import com.linkzip.linkzip.data.model.MainScreenState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor() : ViewModel() {
    val _screenState = MutableStateFlow(MainScreenState.ONBOARDING)
    val screenState = _screenState.asStateFlow()


    fun updateScreenState(state: MainScreenState) {
        viewModelScope.launch {
            _screenState.emit(state)
        }
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