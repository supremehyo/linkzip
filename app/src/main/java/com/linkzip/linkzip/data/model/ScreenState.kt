package com.linkzip.linkzip.data.model

import com.linkzip.linkzip.data.room.GroupData
import com.linkzip.linkzip.data.room.IconData
import com.linkzip.linkzip.data.room.LinkData

enum class ScreenState {
    HOME, MYPAGE
}

enum class HomeScreenState {
    ALL, FAVORITE, POPUP
}

sealed class MainScreenState
    (val state: String,
     var data: Triple<GroupData?, IconData?, LinkData?>?,
     var linkData: LinkData?,
     var from : String? =null) {
    object NONE : MainScreenState("NONE", null, null)
    object ONBOARDING : MainScreenState("ONBOARDING", null, null)
    object MAIN : MainScreenState("MAIN", null, null)
    object GROUPADD : MainScreenState("GROUPADD", null, null)
    object LINKADD : MainScreenState("LINKADD", null, null)
    object GROUP : MainScreenState("GROUP", null, null)
    object MEMO : MainScreenState("MEMO", null, null)
    object WEBVIEW : MainScreenState("WEBVIEW", null, null)
    object MYWEBVIEW : MainScreenState("MYWEBVIEW",null,null)
}