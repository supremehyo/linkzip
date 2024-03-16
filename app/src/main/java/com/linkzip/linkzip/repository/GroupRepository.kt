package com.linkzip.linkzip.repository

import com.linkzip.linkzip.data.room.GroupDao
import com.linkzip.linkzip.data.room.GroupData
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GroupRepository @Inject constructor(
    private val groupDao: GroupDao
) {
    fun getAllGroups(): List<GroupData> {
        return groupDao.getGroups()
    }

    fun insertGroup(group: GroupData) {
        groupDao.insertGroup(group)
    }

    fun deleteGroup(group: GroupData) {
        groupDao.deleteGroup(group)
    }

    fun getGroupByUid(uid: Long): GroupData {
        return groupDao.getGroupByUid(uid)
    }

    fun updateGroupById(uid: Long, name: String, iconId: Long, date: String) {
        groupDao.updateGroupById(uid, name, iconId, date)
    }

    fun deleteGroupAndUpdateLinks(groupId : Long){
        groupDao.deleteGroupAndUpdateLinks(groupId)
    }
}