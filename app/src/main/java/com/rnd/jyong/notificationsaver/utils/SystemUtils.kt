package com.rnd.jyong.notificationsaver.utils

import android.content.Context
import androidx.core.app.NotificationManagerCompat

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
}