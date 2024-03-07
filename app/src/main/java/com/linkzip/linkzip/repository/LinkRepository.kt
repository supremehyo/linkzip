package com.linkzip.linkzip.repository

import android.util.Log
import com.linkzip.linkzip.data.room.LinkDao
import com.linkzip.linkzip.data.room.LinkData
import javax.inject.Inject

class LinkRepository @Inject constructor(
    private val linkDao: LinkDao
) {
    fun getAllLinks(): List<LinkData> {
        return linkDao.getLinkDataList()
    }

    fun getLinkListByGroup(groupId: Long): List<LinkData> {
        return linkDao.getLinkListByGroup(groupId)
    }

    fun getFavoriteLinkList(): List<LinkData> {
        return linkDao.getFavoriteLinkList()
    }

    fun insertLink(link: LinkData) {
        linkDao.insertLink(link)
    }

    fun deleteLink(uid: Long) {
        linkDao.deleteLinkByUid(uid)
    }

    fun updateFavoriteLink(favorite: Boolean, uid: Long) {
        linkDao.updateFavoriteLink(favorite, uid)
    }
}