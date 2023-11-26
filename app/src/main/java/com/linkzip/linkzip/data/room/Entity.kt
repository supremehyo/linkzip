package com.linkzip.linkzip.data.room

import android.net.Uri
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
    val createDate : String
)

@Entity
data class GroupData(
    @PrimaryKey
    val groupId : Long = 0,
    val groupName : String,
    val groupIcon : String
)


//https://nuritech.tistory.com/39 참고
data class LinkWithGroupData(
    @Embedded val groupData: GroupData,
    @Relation(
        parentColumn = "groupId",
        entityColumn = "linkGroupId"
    )
    val linkList: List<LinkData>
)
