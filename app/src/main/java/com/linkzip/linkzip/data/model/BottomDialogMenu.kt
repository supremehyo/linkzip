package com.linkzip.linkzip.data.model

import com.linkzip.linkzip.R

sealed class HomeBottomDialogMenu(
    val title : Int
) {
    data object LinkAdd : HomeBottomDialogMenu(R.string.home_bottom_dialog_menu_linkadd)
    data object GroupAdd : HomeBottomDialogMenu(R.string.home_bottom_dialog_menu_groupadd)
    data object None : HomeBottomDialogMenu(R.string.none)
}