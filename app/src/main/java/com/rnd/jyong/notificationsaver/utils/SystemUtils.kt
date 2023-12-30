package com.rnd.jyong.notificationsaver.utils

import android.app.Activity
import android.content.ActivityNotFoundException
import android.content.Context
import android.content.Intent
import android.net.Uri
import androidx.core.app.NotificationManagerCompat
import androidx.core.content.ContextCompat.startActivity

object SystemUtils {

    /**
     * 노티피케이션 알림 정보 허용 되었는지 확인한다
     * */
    fun isMyPackageNotificationListenerEnabled(context: Context?) : Boolean{
        context?.let {
            val listeners = NotificationManagerCompat.getEnabledListenerPackages(context)
            return listeners.contains(context.packageName)
        } ?: return false
    }

    fun goGoogleStore(appPackageName: String, activity: Activity){
        try {
            activity.startActivity(
                Intent(
                Intent.ACTION_VIEW,
                Uri.parse("market://details?id=$appPackageName")
            )
            )
        } catch (e : ActivityNotFoundException) {
            activity.startActivity(
                Intent(
                    Intent.ACTION_VIEW,
                    Uri.parse("https://play.google.com/store/apps/details?id=$appPackageName")
                )
            )
        }
    }
}