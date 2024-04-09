package com.linkzip.linkzip.data

// 토스트 메시지 내용
object ToastMessage {

    /**
     * Success
     */
    const val ADD_GROUP_SUCCESS = "그룹 추가완료!"
    const val UPDATE_GROUP_SUCCESS = "그룹 변경완료!"

    const val DELETE_LINK_SUCCESS = "링크 삭제완료!"
    const val DELETE_GROUP_SUCCESS = "그룹 삭제완료!"

    const val SET_FAVORITE_SUCCESS = "즐겨찾기 설정완료!"
    const val SET_UNFAVORITE_SUCCESS = "즐겨찾기 해제완료!"
    /**
     * Fail
     */
    const val ENTER_WRONG_VALUE = "올바른 값을 입력해주세요."
    const val WAIT_A_MOMENT = "잠시 후 다시 시도해주세요."
    const val SELECT_LINK = "링크를 선택해주세요"
}

// 성공/실패 여부에 따른 토스트 메시지 분리
enum class ToastType {
    SUCCESS, WRONG_VALUE, WAIT, SELECT_LINK
}

// 어떤 상태에 따른 토스트를 보여줄건지
sealed class ToastKind(val isType: ToastType, var isVisible: Boolean) {
    data class None(val type: ToastType, val visible: Boolean) : ToastKind(type, visible)
    data class AddGroup(val type: ToastType, val visible: Boolean) : ToastKind(type, visible)
    data class UpdateGroup(val type: ToastType, val visible: Boolean) : ToastKind(type, visible)
    data class DeleteLink(val type: ToastType, val visible: Boolean) : ToastKind(type, visible)
    data class DeleteGroup(val type: ToastType, val visible: Boolean) : ToastKind(type, visible)
    data class FavoriteLink(val type: ToastType, val visible: Boolean) : ToastKind(type, visible)
    data class UnFavoriteLink(val type: ToastType, val visible: Boolean) : ToastKind(type, visible)
}