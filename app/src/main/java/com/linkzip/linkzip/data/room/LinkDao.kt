package com.linkzip.linkzip.data.room

import android.net.Uri
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction

@Dao
interface LinkDao {
    @Query("SELECT * FROM LinkData")
    fun getLinkDataList(): List<LinkData>

    @Query("SELECT * FROM LinkData WHERE linkGroupId = :groupId")
    fun getLinkListByGroup(groupId: Long): List<LinkData>

    @Query("SELECT * FROM LinkData WHERE uid= :id")
    fun getLinkDataByUid(id: Long): LinkData

    @Query("SELECT * FROM LinkData WHERE favorite = 1")
    fun getFavoriteLinkList(): List<LinkData>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertLink(linkData: LinkData)

    @Query("DELETE FROM LinkData WHERE uid = :uid")
    fun deleteLinkByUid(uid: Long)

    //cascade 를 하고 싶었지만 링크도 함께 지우기가 옵션으로 들어가면 따로 삭제해주는 쿼리가 필요
    @Query("DELETE FROM LinkData WHERE linkGroupId = :groupUid")
    fun deleteLinksByGroupId(groupUid: Long)

    @Query("UPDATE LinkData SET link = :link , linkGroupId = :linkGroupId,linkTitle = :linkTitle, linkMemo = :linkMemo  WHERE uid = :uid")
    fun updateLinkData(
        uid: Int,
        link: String,
        linkGroupId: String,
        linkTitle: String,
        linkMemo: String
    )
}