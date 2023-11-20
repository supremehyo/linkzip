package com.linkzip.linkzip

import android.app.Application
import androidx.room.Room
import com.linkzip.linkzip.data.room.LinkRoomDataBase
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class App : Application()  {
    companion object{
        var db : LinkRoomDataBase? = null
    }

    override fun onCreate() {
        super.onCreate()
        db = initRoomDataBase()
    }

    //room db 생성
    private fun initRoomDataBase() : LinkRoomDataBase{
       return Room.databaseBuilder(
            applicationContext,
           LinkRoomDataBase::class.java ,"linkzip-database"
        ).build()
    }
}