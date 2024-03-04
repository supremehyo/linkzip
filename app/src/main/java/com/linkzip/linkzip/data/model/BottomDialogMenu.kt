package com.linkzip.linkzip.data.model

import com.linkzip.linkzip.R

sealed class BottomDialogMenu(
    val title : Int,
    val iconImg: Int
) {
    data object LinkAdd : BottomDialogMenu(R.string.home_bottom_dialog_menu_linkadd, R.drawable.icon_link)
    data object GroupAdd : BottomDialogMenu(R.string.home_bottom_dialog_menu_groupadd, R.drawable.icon_circle_plus_white)
    data object ShareLink : BottomDialogMenu(R.string.link_bottom_dialog_menu_share, R.drawable.icon_share)
    data object ModifyLink : BottomDialogMenu(R.string.link_bottom_dialog_menu_modify, R.drawable.icon_edit)
    data object FavoriteLink : BottomDialogMenu(R.string.link_bottom_dialog_menu_favorite, R.drawable.icon_pin)
    data object None : BottomDialogMenu(R.string.none, 0)
}
