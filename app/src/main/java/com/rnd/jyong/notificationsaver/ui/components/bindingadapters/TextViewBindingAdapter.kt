package com.rnd.jyong.notificationsaver.ui.components.bindingadapters

import android.widget.TextView
import androidx.annotation.ColorRes
import androidx.annotation.StringRes
import androidx.databinding.BindingAdapter

object TextViewBindingAdapter{
    /**
     * textColorResId를 입력받아 TextView에 색상 임포트
     */
    @JvmStatic
    @BindingAdapter("textColorRes")
    fun setTextColorRes(view: TextView, @ColorRes textColorRes: Int?) {
        if (textColorRes == 0 || textColorRes == -1 || textColorRes == null) {
            return
        }
        try {
            val context = view.context
            val color = context.getColor(textColorRes)
            view.setTextColor(color)
        } catch (e: Exception) {
//            SimpleLog.e("TextViewBindingAdapter", "error222 : " + e.message)
        }
    }

    /**
     * StringResId를 입력받아 TextView에 임포트
     */
    @JvmStatic
    @BindingAdapter("textRes")
    fun setTextRes(view: TextView, @StringRes textRes: Int?) {
        if (textRes == 0 || textRes == -1 || textRes == null) {
            return
        }
        try {
            val context = view.context
            val text = context.getString(textRes)
            view.text = text
        } catch (e: java.lang.Exception) {
//            SimpleLog.e("TextViewBindingAdapter", "error11 : " + e.message)
        }
    }

    @JvmStatic
    @BindingAdapter("app:text")
    fun setText(view: TextView, text: String?) {
        if (text.isNullOrEmpty()) {
            return
        }
        view.text = text
    }
}