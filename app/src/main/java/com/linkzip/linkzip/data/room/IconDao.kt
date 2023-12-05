package com.linkzip.linkzip.data.room

import androidx.room.Dao
import androidx.room.Query

@Dao
interface IconDao {
    @Query(
        "SELECT * FROM `group` JOIN IconData ON `group`.groupIconId = IconData.iconId"
    )
    fun loadGroupAndIcon(): Map<GroupData, IconData>
}