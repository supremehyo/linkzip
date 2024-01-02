package com.linkzip.linkzip.data.room

import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.Relation

@Entity
data class LinkData(
    @PrimaryKey(autoGenerate = true)
    var uid : Long? = 0,
    var link : String,
    var linkGroupId : String,
    var linkTitle : String,
    var linkMemo : String,
    var createDate : String,
    var updateDate : String,
    var favorite: Boolean
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
    @PrimaryKey
    val iconId : Long = 0,
    val iconName : String,
    val iconButtonColor : String,
    val iconHeaderColor : String
)

data class LinkWithGroupData(
    @Embedded val groupData: GroupData,
    @Relation(
        parentColumn = "groupId",
        entityColumn = "linkGroupId"
    )
    val linkList: List<LinkData>
)
