package com.linkzip.linkzip.usecase

import com.linkzip.linkzip.data.room.IconData
import com.linkzip.linkzip.repository.GroupRepository
import com.linkzip.linkzip.repository.IconRepository
import kotlinx.coroutines.flow.flow
import javax.inject.Inject


class AllViewUseCase @Inject constructor(
    private val groupRepository: GroupRepository,
    private val iconRepository: IconRepository) {
    // 전체 그룹 불러오기
    fun getAllGroups() = flow {
        emit(groupRepository.getAllGroups())
    }

    // 그룹에 해당하는 아이콘 불러오기
    fun getIconListById(iconIdList: List<Long>) = flow {
        val iconList = mutableListOf<IconData>()
        iconIdList.forEach { iconId ->
            iconList.add(iconRepository.getIconDataById(iconId))
        }
        emit(iconList)
    }

    // 그룹 안에 있는 링크의 수 가져오기
    fun getCountLinkInGroup(groupId: Long) = flow {
        emit(groupRepository.countLinkInGroup(groupId))
    }
}

