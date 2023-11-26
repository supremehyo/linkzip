package com.linkzip.linkzip.data.room

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = [LinkData::class,GroupData::class], version = 1)
abstract class LinkRoomDataBase : RoomDatabase() {
    abstract fun linkDao() : LinkDao
    //group 도 추가
}