package com.rnd.jyong.notificationsaver.ui.detail.data

import android.graphics.Bitmap

data class DetailItemViewData(
    /**
     * 이름
     * */
    val name : String,
    /**
     * 시각
     * */
    val time : String,
    /**
     * 아이콘
     * */
    var icon : Bitmap,
    /**
     * 메시지
     * */
    val message : String? = null,
    /**
     * 이미지
     * */
    var image : Bitmap? = null,
    /**
     * 아마자 타입 메시지 여부
     * */
    var isImageType : Boolean = image != null,

)

