package com.rnd.jyong.notificationsaver.ui.main.data

import android.graphics.Bitmap

data class MainItemViewData(
    /**
     * 채팅 룸 타이틀
     * */
    val groupName : String,
    /**
     * 채팅 룸 최근 메시지
     * */
    val message : String,
    /**
     * 채팅 룸 메시지 시각
     * */
    val time : String,
    /**
     * 그룹 아이콘
     * */
    var icon : Bitmap,
    /**
     * Click Listener
     * */
    var listener : OnClickListener? = null,
) {

    /**
     * Item CLick
     * */
    fun onClickItem(){
        listener?.onClick(groupName)
    }
    /**
     * ClickListener
     * */
    fun interface OnClickListener {
        fun onClick(groupName : String)
    }
}
