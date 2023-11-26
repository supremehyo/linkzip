package com.linkzip.linkzip.data.room

import android.net.Uri
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Transaction

@Dao
interface LinkDao {
    @Query("SELECT * FROM LinkData")
    fun getLinkDataList() : List<LinkData>

    @Query("SELECT * FROM LinkData WHERE uid= :uid")
    fun getLinkDataByUid(uid : Long) : LinkData

    @Insert
    fun insertTempVideo(linkData: LinkData)

    @Query("DELETE FROM LinkData WHERE uid = :uid")
    fun delete(uid: Long)

    @Query("UPDATE LinkData SET link = :link , linkGroupId = :linkGroupId,linkTitle = :linkTitle, linkMemo = :linkMemo  WHERE uid = :uid")
    fun updateLinkData(uid : Int, link: String, linkGroupId : String, linkTitle: String, linkMemo:String)
}