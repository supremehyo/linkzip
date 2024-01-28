package com.linkzip.linkzip.data.room

import android.graphics.drawable.Icon
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface IconDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addIcon(vararg item: IconData)

    @Query("SELECT * FROM `icon`")
    fun getIconData() : List<IconData>

    @Query("SELECT * FROM `icon` WHERE `iconId`=:iconId")
    fun getIconDataById(iconId : Long) : IconData


}
