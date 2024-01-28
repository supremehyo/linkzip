package com.linkzip.linkzip.data.room

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import kotlinx.coroutines.runBlocking
import java.util.concurrent.Executors

@Database(
    entities = [LinkData::class, GroupData::class, IconData::class],
    version = 1,
    exportSchema = false
)
abstract class LinkRoomDataBase : RoomDatabase() {
    abstract fun linkDao(): LinkDao
    abstract fun groupDao(): GroupDao
    abstract fun iconDao(): IconDao

    companion object {
        fun getDatabase(context: Context): LinkRoomDataBase = Room
            .databaseBuilder(
                context,
                LinkRoomDataBase::class.java,
                "linkzip-database.db"
            )
            .addCallback(object : Callback() {
                override fun onCreate(db: SupportSQLiteDatabase) {
                    super.onCreate(db)
                    Executors.newSingleThreadExecutor().execute {
                        runBlocking {
                            getDatabase(context).iconDao()
                                .addIcon(
                                    IconData.NO_GROUP,
                                    IconData.RICE,
                                    IconData.COFFEE,
                                    IconData.WINE,
                                    IconData.GAME,
                                    IconData.COMPUTER,
                                    IconData.CAMERA,
                                    IconData.MONEY,
                                    IconData.PALETTE,
                                    IconData.GIFT,
                                    IconData.MEMO,
                                    IconData.BOOK,
                                    IconData.HOME,
                                    IconData.CAR,
                                    IconData.AIRPLANE,
                                    IconData.HEART
                                )
                        }
                    }
                }
            })
            .build()
    }
}