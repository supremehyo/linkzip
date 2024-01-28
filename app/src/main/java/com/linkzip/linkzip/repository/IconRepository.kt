package com.linkzip.linkzip.repository

import com.linkzip.linkzip.data.room.IconDao
import com.linkzip.linkzip.data.room.IconData
import javax.inject.Inject

class IconRepository @Inject constructor(private val iconDao: IconDao) {
    fun getIconData(): List<IconData> {
        return iconDao.getIconData()
    }
    fun getIconDataById(iconId : Long) : IconData{
        return iconDao.getIconDataById(iconId)
    }
}