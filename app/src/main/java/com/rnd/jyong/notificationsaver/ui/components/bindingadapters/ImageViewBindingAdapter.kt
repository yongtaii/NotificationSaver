package com.rnd.jyong.notificationsaver.ui.components.bindingadapters

import android.graphics.Bitmap
import android.widget.ImageView
import androidx.annotation.DrawableRes
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide

object ImageViewBindingAdapter {

    /**
     * ByteArray 이미지 셋팅
     */
    @JvmStatic
    @BindingAdapter("setImageWithByteArray")
    fun setImageWithByteArray(view: ImageView, imageByteArray: ByteArray?) {
        if(imageByteArray == null) return
        Glide.with(view.context)
            .asBitmap()
            .load(imageByteArray)
            .into(view)
    }

    @JvmStatic
    @BindingAdapter("setImageWithBitmap")
    fun setImageWithBitmap(view: ImageView, bitmap: Bitmap?) {
        if(bitmap == null) return
        Glide.with(view.context)
            .load(bitmap)
            .into(view)
    }

    /**
     * ImageResId를 입력받아 ImageView에 임포트
     */
    @JvmStatic
    @BindingAdapter("imageRes")
    fun setTextRes(view: ImageView, @DrawableRes imageRes: Int) {
        try {
            view.setImageResource(imageRes)
        } catch (e: Exception) {
//            SimpleLog.e("ImageViewBindingAdapter", "error : " + e.message)
        }
    }
}