package com.linkzip.linkzip.data.room

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import kotlinx.coroutines.CoroutineScope

@Database(entities = [LinkData::class,GroupData::class,IconData::class], version = 1)
abstract class LinkRoomDataBase : RoomDatabase() {
    abstract fun linkDao() : LinkDao
    abstract fun groupDao() : GroupDao
    abstract fun iconDao() : IconDao

    companion object {
        @Volatile
        private var INSTANCE: LinkRoomDataBase? = null

        fun getDatabase(
            context: Context,
            scope: CoroutineScope
        ): LinkRoomDataBase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    LinkRoomDataBase::class.java,
                    "linkzip-database"
                )
                    .build()
                INSTANCE = instance
                instance
            }
        }
    }
}