package com.rnd.jyong.notificationsaver.ui.components

import android.util.Log
import androidx.annotation.DrawableRes
import com.rnd.jyong.notificationsaver.R

data class HeaderViewData(
    val titleText : String? = null,
    val titleTextRes : Int? = null,
//    @DrawableRes val leftBtnImageRes : Int = R.drawable.btn_arrow_pre,
    val isVisibleLeftBtn : Boolean = true,
    val isVisibleRightBtn : Boolean = true,
    val titleTextColorRes : Int = R.color.black_00,
    val onLeftClickListener : OnLeftClickListener? = null,
    val onRightClickListener : OnRightClickListener? = null,
) {
    fun onClickLeftBtn(){
        Log.d("yong1234","111")
        onLeftClickListener?.onClick()
    }

    fun onClickRightBtn(){
        onRightClickListener?.onClick()
    }

    fun interface OnLeftClickListener{
        fun onClick()
    }

    fun interface OnRightClickListener{
        fun onClick()
    }
}
