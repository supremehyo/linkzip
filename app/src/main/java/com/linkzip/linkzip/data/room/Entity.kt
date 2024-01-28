package com.linkzip.linkzip.data.room

import androidx.compose.ui.graphics.toArgb
import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.Relation
import com.linkzip.linkzip.ui.theme.BLUE_294459
import com.linkzip.linkzip.ui.theme.BLUE_4088F4
import com.linkzip.linkzip.ui.theme.BLUE_40C3EC
import com.linkzip.linkzip.ui.theme.BLUE_C0F0FF
import com.linkzip.linkzip.ui.theme.BLUE_C8DDFD
import com.linkzip.linkzip.ui.theme.BLUE_D6EAF2
import com.linkzip.linkzip.ui.theme.BROWN_BC783C
import com.linkzip.linkzip.ui.theme.BROWN_FADECA
import com.linkzip.linkzip.ui.theme.GRAY_353E45
import com.linkzip.linkzip.ui.theme.GRAY_E0E6EB
import com.linkzip.linkzip.ui.theme.GRAY_E2E6E9
import com.linkzip.linkzip.ui.theme.GREEN_23A79F
import com.linkzip.linkzip.ui.theme.GREEN_2FCE7B
import com.linkzip.linkzip.ui.theme.GREEN_719525
import com.linkzip.linkzip.ui.theme.GREEN_BDF3C2
import com.linkzip.linkzip.ui.theme.GREEN_D4F0EB
import com.linkzip.linkzip.ui.theme.GREEN_F3F4C2
import com.linkzip.linkzip.ui.theme.ORANGE_FFAA2C
import com.linkzip.linkzip.ui.theme.ORANGE_FFC737
import com.linkzip.linkzip.ui.theme.ORANGE_FFE6C1
import com.linkzip.linkzip.ui.theme.ORANGE_FFEEB1
import com.linkzip.linkzip.ui.theme.PINK_F9D9E2
import com.linkzip.linkzip.ui.theme.PINK_FF70CE
import com.linkzip.linkzip.ui.theme.PINK_FFE8F7
import com.linkzip.linkzip.ui.theme.PURPLE_8E56FF
import com.linkzip.linkzip.ui.theme.PURPLE_EFE7FF
import com.linkzip.linkzip.ui.theme.RED_FB5B63
import com.linkzip.linkzip.ui.theme.WG70
import com.linkzip.linkzip.ui.theme.WHITE

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
    @PrimaryKey(autoGenerate = true)
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
    val iconButtonColor : Int,
    val iconHeaderColor : Int
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

        val NO_GROUP = IconData(0, ICON_NO_GROUP, WG70.toArgb(), WHITE.toArgb())
        val RICE = IconData(1, ICON_RICE, ORANGE_FFAA2C.toArgb(), ORANGE_FFE6C1.toArgb())
        val COFFEE = IconData(2, ICON_COFFEE, BROWN_BC783C.toArgb(), BROWN_FADECA.toArgb())
        val WINE = IconData(3, ICON_WINE, RED_FB5B63.toArgb(), PINK_F9D9E2.toArgb())
        val GAME = IconData(4, ICON_GAME, GRAY_353E45.toArgb(), GRAY_E0E6EB.toArgb())
        val COMPUTER = IconData(5, ICON_COMPUTER, GRAY_353E45.toArgb(), GRAY_E2E6E9.toArgb())
        val CAMERA = IconData(6, ICON_CAMERA, BLUE_294459.toArgb(), BLUE_D6EAF2.toArgb())
        val MONEY = IconData(7, ICON_MONEY, GREEN_2FCE7B.toArgb(), GREEN_BDF3C2.toArgb())
        val PALETTE = IconData(8, ICON_PALETTE, ORANGE_FFAA2C.toArgb(), ORANGE_FFE6C1.toArgb())
        val GIFT = IconData(9, ICON_GIFT, ORANGE_FFC737.toArgb(), ORANGE_FFEEB1.toArgb())
        val MEMO = IconData(10, ICON_MEMO, GREEN_719525.toArgb(), GREEN_F3F4C2.toArgb())
        val BOOK = IconData(11, ICON_BOOK, BLUE_40C3EC.toArgb(), BLUE_C0F0FF.toArgb())
        val HOME = IconData(12, ICON_HOME, GREEN_23A79F.toArgb(), GREEN_D4F0EB.toArgb())
        val CAR = IconData(13, ICON_CAR, BLUE_4088F4.toArgb(), BLUE_C8DDFD.toArgb())
        val AIRPLANE = IconData(14, ICON_AIRPLANE, PURPLE_8E56FF.toArgb(), PURPLE_EFE7FF.toArgb())
        val HEART = IconData(15, ICON_HEART, PINK_FF70CE.toArgb(), PINK_FFE8F7.toArgb())
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
