package com.rnd.jyong.notificationsaver.core.notification

import android.app.Notification
import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.drawable.BitmapDrawable
import android.os.Build
import android.os.Parcelable
import android.service.notification.StatusBarNotification
import android.util.Log
import com.rnd.jyong.notificationsaver.R
import com.rnd.jyong.notificationsaver.database.notification.entity.Message
import com.rnd.jyong.notificationsaver.utils.CommonUtil
import com.rnd.jyong.notificationsaver.utils.FileUtils

fun StatusBarNotification.toMessage(context: Context) : Message{

    val sbnExtras = this.notification.extras

    val extraTitle = sbnExtras.getString(Notification.EXTRA_TITLE) ?: ""
    val extraText = sbnExtras.getCharSequence(Notification.EXTRA_TEXT)?.toString() ?: ""
    val extraSubText = sbnExtras.getCharSequence(Notification.EXTRA_SUB_TEXT)
    val iconBase64 = getIconBase64String(this, context = context)
    val imageBase64 = getImageBase64String(this)
    val postTime = this.postTime

    return Message(name = extraTitle, message = extraText, groupName = extraSubText?.toString() ?: extraTitle, postTime = postTime, iconBase64 = iconBase64, imageBase64 = imageBase64)

}


/**
 * get Icon
 * */
private fun getIconBase64String(sbn : StatusBarNotification, context : Context) : String {

    if (Build.VERSION.SDK_INT > Build.VERSION_CODES.P && sbn.notification.getLargeIcon() != null ){
        val drawable = sbn.notification.getLargeIcon().loadDrawable(context)
        val bitmap = (drawable as BitmapDrawable).bitmap
        bitmap?.let {
            return  FileUtils.convertBitmapToBase64(it)
        } ?: return ""
    }else{
        val largeIcon = sbn.notification.extras.getParcelable<Parcelable>(Notification.EXTRA_LARGE_ICON) as Bitmap? ?: run {
            BitmapFactory.decodeResource(context.resources, R.mipmap.ic_launcher_round)
        }
        largeIcon?.let {
            return  FileUtils.convertBitmapToBase64(it)
        } ?: return ""
    }
}

/**
 * get Image
 * */
private fun getImageBase64String(sbn : StatusBarNotification) : String {
    CommonUtil.getImageFromSbnBundle(sbn.notification.extras)?.let {
        return FileUtils.convertBitmapToBase64(it)
    } ?: return ""
}


///**
// * parsing Icon
// * */
//private fun getIconFromSbn(sbn : StatusBarNotification, context : Context) : Bitmap {
//
//    if(Build.VERSION.SDK_INT > Build.VERSION_CODES.P){
//        sbn.getNotification().getLargeIcon()?.let {
//            CommonUtil.convertDrawableToBytesWithBackground(sbn.getNotification().getLargeIcon().loadDrawable(context)
//        }
//    }
//
//    return sbn.notification.extras.getParcelable<Parcelable>(Notification.EXTRA_LARGE_ICON) as Bitmap? ?: run {
//            BitmapFactory.decodeResource(context.resources, R.mipmap.ic_launcher_round)
//        }
//}
