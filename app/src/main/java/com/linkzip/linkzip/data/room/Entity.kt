package com.linkzip.linkzip.data.room

import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.Relation

@Entity
data class LinkData(
    @PrimaryKey
    val uid : Long = 0,
    val link : String,
    val linkGroupId : String,
    val linkTitle : String,
    val linkMemo : String,
    val createDate : String,
    val updateDate : String,
    val favorite: Boolean
)

@Entity(tableName = "group")
data class GroupData(
    @PrimaryKey
    val groupId : Long = 0,
    val groupName : String,
    val groupIconId : Long,
    val createDate : String,
    val updateDate : String
)

@Entity(tableName = "icon")
data class IconData(
    @PrimaryKey(autoGenerate = true) val iconId : Long = 0,
    val iconName : String,
    val iconButtonColor : String,
    val iconHeaderColor : String
) {
    companion object {
        const val ICON_NO_GROUP = "icon_nogroup"
        const val ICON_RICE = "icon_rice"
        const val ICON_COFFEE = "icon_coffee"
        const val ICON_WINE = "icon_wine"
        const val ICON_GAME = "icon_game"
        const val ICON_COMPUTER = "icon_computer"
        const val ICON_CAMERA = "icon_camera"
        const val ICON_MONEY = "icon_money"
        const val ICON_PALETTE = "icon_palette"
        const val ICON_GIFT = "icon_gift"
        const val ICON_MEMO = "icon_memo"
        const val ICON_BOOK = "icon_book"
        const val ICON_HOME = "icon_home"
        const val ICON_CAR = "icon_car"
        const val ICON_AIRPLANE = "icon_airplane"
        const val ICON_HEART = "icon_heart"

        val NO_GROUP = IconData(0, ICON_NO_GROUP, "wg70", "white")
        val RICE = IconData(1, ICON_RICE, "orangeFFAA2C", "orangeFFE6C1")
        val COFFEE = IconData(2, ICON_COFFEE, "brownBC783C", "brownFADECA")
        val WINE = IconData(3, ICON_WINE, "redFB5B63", "pinkF9D9E2")
        val GAME = IconData(4, ICON_GAME, "gray353E45", "grayE0E6EB")
        val COMPUTER = IconData(5, ICON_COMPUTER, "gray353E45", "grayE2E6E9")
        val CAMERA = IconData(6, ICON_CAMERA, "blue294459", "blueD6EAF2")
        val MONEY = IconData(7, ICON_MONEY, "green2FCE7B", "greenBDF3C2")
        val PALETTE = IconData(8, ICON_PALETTE, "orangeFFAA2C", "orangeFFE6C1")
        val GIFT = IconData(9, ICON_GIFT, "orangeFFC737", "orangeFFEEB1")
        val MEMO = IconData(10, ICON_MEMO, "green719525", "greenF3F4C2")
        val BOOK = IconData(11, ICON_BOOK, "blue40C3EC", "blueC0F0FF")
        val HOME = IconData(12, ICON_HOME, "green23A79F", "greenD4F0EB")
        val CAR = IconData(13, ICON_CAR, "blue4088F4", "blueC8DDFD")
        val AIRPLANE = IconData(14, ICON_AIRPLANE, "purple8E56FF", "purpleEFE7FF")
        val HEART = IconData(15, ICON_HEART, "pinkFF70CE", "pinkFFE8F7")
    }
}

data class LinkWithGroupData(
    @Embedded val groupData: GroupData,
    @Relation(
        parentColumn = "groupId",
        entityColumn = "linkGroupId"
    )
    val linkList: List<LinkData>
)
