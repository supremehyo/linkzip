package com.linkzip.linkzip.repository

import com.linkzip.linkzip.data.room.GroupDao
import com.linkzip.linkzip.data.room.GroupData
import kotlinx.coroutines.flow.Flow

class GroupRepository(
    private val groupDao: GroupDao
){
    fun getAllGroups() : List<GroupData> {
        return groupDao.getGroups()
    }

    fun insertGroup (group : GroupData) : Any{
        return groupDao.insertGroup(group)
    }

    fun deleteGroup (group : GroupData) : Any {
        return groupDao.deleteGroup(group)
    }

    fun getGroupByUid(uid : Long) : GroupData{
        return groupDao.getGroupByUid(uid)
    }
}