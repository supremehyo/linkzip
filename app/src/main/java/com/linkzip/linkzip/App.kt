package com.linkzip.linkzip

import android.app.Application
import android.net.Uri
import androidx.room.Room
import com.linkzip.linkzip.data.room.LinkRoomDataBase
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class App : Application()  {
    companion object{
        var db : LinkRoomDataBase? = null
        var EMPTY_THUMBNAIL = ""
    }

    override fun onCreate() {
        super.onCreate()
       // db = initRoomDataBase()
        EMPTY_THUMBNAIL = Uri.parse("android.resource://${applicationContext.packageName}/${R.drawable.linkzip_logo}").toString()
    }

    //room db 생성
    private fun initRoomDataBase() : LinkRoomDataBase{
       return Room.databaseBuilder(
            applicationContext,
           LinkRoomDataBase::class.java ,"linkzip-database"
        ).build()
    }
}