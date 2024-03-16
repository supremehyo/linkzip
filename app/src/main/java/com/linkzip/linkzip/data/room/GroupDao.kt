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
    fun insertGroup(group: GroupData)

    @Delete
    fun deleteGroup(group: GroupData)

    @Query("SELECT * FROM `group` ORDER BY groupId DESC") //ASC
    fun getGroups(): List<GroupData>

    @Query("SELECT * FROM `group` WHERE groupId = :uid")
    fun getGroupByUid(uid: Long): GroupData

    //그룹삭제
    @Query("DELETE FROM `group` WHERE groupId = :uid")
     fun deleteGroupByUid(uid: Long)

    //삭제하면서 그룹없음으로 이동시키는 쿼리
    @Query("UPDATE LinkData SET linkGroupId = '1' WHERE linkGroupId = :groupId")
     fun updateLinkDataGroupId(groupId: Long)

    @Query("DELETE FROM `group`")
    fun clearGroups()

    @Query("UPDATE `group` SET groupName = :name, groupIconId = :iconId, updateDate = :date WHERE groupId = :uid")
    fun updateGroupById(uid: Long, name: String, iconId: Long, date: String)

    @Transaction
    @Query("SELECT * FROM `group` ORDER BY groupId DESC") //ASC
    fun getGroupAndLinkData() : LinkWithGroupData

    //위 쿼리와 동일 room 2.4 부터는 아래와 같은 방식을 권장
    @Query(
        "SELECT * FROM `group` JOIN LinkData ON `group`.groupId = LinkData.linkGroupId"
    )
    fun loadGroupAndLink(): Map<GroupData, List<LinkData>>

    @Transaction
    fun deleteGroupAndUpdateLinks(groupId: Long) {
        updateLinkDataGroupId(groupId)
        deleteGroupByUid(groupId)
    }


}