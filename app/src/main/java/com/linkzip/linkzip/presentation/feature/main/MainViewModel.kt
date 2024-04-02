package com.linkzip.linkzip.presentation.feature.main

import android.content.Context
import android.content.SharedPreferences
import android.content.pm.PackageInfo
import android.content.pm.PackageManager
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.linkzip.linkzip.data.model.BottomDialogMenu
import com.linkzip.linkzip.data.model.IS_FRIST
import com.linkzip.linkzip.data.model.MainScreenState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor() : ViewModel() {
    private val _screenState = MutableStateFlow(MainScreenState.NONE.state)
    val screenState = _screenState.asStateFlow()

    private val _menuState = MutableStateFlow<BottomDialogMenu>(BottomDialogMenu.None)
    val menuState = _menuState.asStateFlow()

    var versionCode = ""

    fun updateScreenState(state: String) {
        viewModelScope.launch {
            _screenState.emit(state)
        }
    }

    fun updateMenuState(state: BottomDialogMenu) {
        viewModelScope.launch {
            _menuState.emit(state)
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

    fun getAppVersion(context: Context) {
        versionCode = try {
            val packageInfo: PackageInfo =
                context.packageManager.getPackageInfo(context.packageName, 0)
            packageInfo.versionName
        } catch (e: PackageManager.NameNotFoundException) {
            "N/A"
        }
    }
}