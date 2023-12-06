package com.linkzip.linkzip.data.room

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import kotlinx.coroutines.flow.Flow

@Dao
interface GroupDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertGroup(group: GroupData): Any

    @Delete
    fun deleteGroup(group: GroupData) : Any

    @Query("SELECT * FROM `group` ORDER BY groupId DESC") //ASC
    fun getGroups(): List<GroupData>

    @Query("SELECT * FROM `group` WHERE groupId = :uid")
    fun getGroupByUid(uid: Long): GroupData

    @Query("DELETE FROM `group` WHERE groupId = :uid")
    fun deleteGroupByUid(uid: Long) : Any

    @Query("DELETE FROM `group`")
    fun clearGroups()


    @Transaction
    @Query("SELECT * FROM `group` ORDER BY groupId DESC") //ASC
    fun getGroupAndLinkData() : LinkWithGroupData

    //위 쿼리와 동일 room 2.4 부터는 아래와 같은 방식을 권장
    @Query(
        "SELECT * FROM `group` JOIN LinkData ON `group`.groupId = LinkData.linkGroupId"
    )
    fun loadGroupAndLink(): Map<GroupData, List<LinkData>>

}