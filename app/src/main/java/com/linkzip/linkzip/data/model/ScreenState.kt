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

sealed class MainScreenState(val state: String, var data: Triple<GroupData?, IconData?, LinkData?>?) {
    object NONE : MainScreenState("NONE", null)
    object ONBOARDING : MainScreenState("ONBOARDING", null)
    object MAIN : MainScreenState("MAIN", null)
    object GROUPADD : MainScreenState("GROUPADD", null)
    object LINKADD : MainScreenState("LINKADD", null)
    object GROUP : MainScreenState("GROUP", null)
    object MEMO : MainScreenState("MEMO", null)
}