package com.rnd.jyong.notificationsaver.core.notification

import android.app.Notification
import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.drawable.BitmapDrawable
import android.os.Build
import android.os.Parcelable
import android.service.notification.NotificationListenerService
import android.service.notification.StatusBarNotification
import android.util.Log
import com.rnd.jyong.notificationsaver.R
import com.rnd.jyong.notificationsaver.data.model.NotiMessage
import com.rnd.jyong.notificationsaver.data.repository.NotiMessageRepository
import com.rnd.jyong.notificationsaver.database.notification.repository.MessageRepository
import com.rnd.jyong.notificationsaver.utils.CommonUtil
import com.rnd.jyong.notificationsaver.utils.FileUtils
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class NotificationListenerService : NotificationListenerService() {

    @Inject
    lateinit var messageRepository: MessageRepository

    override fun onNotificationPosted(sbn: StatusBarNotification) {

        // kakao 메시지가 아닐 경우 return
        if (sbn.packageName != "com.kakao.talk") return
        // 메시지 변환
        val message = sbn.toMessage(applicationContext)
        // 빈값일 경우 return
        if(message.name.isEmpty() && message.message.isEmpty()) return
        // 광고일 경우 return
        if(message.message.contains("(광고)")) return


        Log.d("yong1234","1name : ${message.name}")
        Log.d("yong1234","1message : ${message.message}")
        Log.d("yong1234","1groupName : ${message.groupName}")
//        Log.d("yong1234","1iconBase64 : ${message.iconBase64 == ""}")
//        Log.d("yong1234","11imageBase64 : ${message.imageBase64 == ""}")
        Log.d("yong1234","postTime : ${message.postTime}")

        CoroutineScope(Dispatchers.IO).launch {
            messageRepository.insert(message)
        }

    }

}