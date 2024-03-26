package com.linkzip.linkzip.repository

import com.linkzip.linkzip.data.room.LinkDao
import com.linkzip.linkzip.data.room.LinkData
import javax.inject.Inject

class LinkRepository @Inject constructor(
    private val linkDao: LinkDao
) {
    fun getAllLinks(): List<LinkData> {
        return linkDao.getLinkDataList()
    }

    fun getLinkListByGroup(groupId: String): List<LinkData> {
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

    fun updateLinkGroupId(oldGroupId: String, newGroupId: String){
        linkDao.updateLinkGroupId(oldGroupId, newGroupId)
    }

    fun updateFavoriteLink(favorite: Boolean, uid: Long) {
        linkDao.updateFavoriteLink(favorite, uid)
    }

    fun updateLinkData(
        uid: Long,
        link: String,
        linkGroupId: String,
        linkTitle: String,
        linkMemo: String
    ) {
        linkDao.updateLinkData(uid, link, linkGroupId, linkTitle, linkMemo)
    }
}