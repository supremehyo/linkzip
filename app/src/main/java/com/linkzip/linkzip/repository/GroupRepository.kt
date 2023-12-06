package com.linkzip.linkzip.repository

import com.linkzip.linkzip.data.room.GroupDao
import com.linkzip.linkzip.data.room.GroupData
import kotlinx.coroutines.flow.Flow

class GroupRepository(
    private val groupDao: GroupDao
){
    fun getAllGroups() : Flow<List<GroupData>> {
       return groupDao.getGroups()
    }

    fun insertGroup (group : GroupData) : Flow<Any>{
        return groupDao.insertGroup(group)
    }

    fun deleteGroup (group : GroupData) : Flow<Any> {
        return groupDao.deleteGroup(group)
    }

    fun getGroupByUid(uid : Long) : Flow<GroupData>{
        return groupDao.getGroupByUid(uid)
    }
}