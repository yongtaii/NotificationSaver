package com.rnd.jyong.notificationsaver.service;

import android.app.Notification;
import android.app.Service;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.IBinder;
import android.service.notification.StatusBarNotification;
import android.util.Log;

import com.rnd.jyong.notificationsaver.data.model.NotiMessage;
import com.rnd.jyong.notificationsaver.data.repository.NotiMessageRepository;
import com.rnd.jyong.notificationsaver.utils.CommonUtil;
import com.rnd.jyong.notificationsaver.viewmodel.MainViewModel;

public class NotificationListenerService extends android.service.notification.NotificationListenerService {

    @Override
    public void onCreate() {
        super.onCreate();
        Log.i("NotificationListener", "[snowdeer] onCreate()");
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.i("NotificationListener", "[snowdeer] onStartCommand()");
        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.i("NotificationListener", "[snowdeer] onDestroy()");
    }

    @Override
    public void onNotificationPosted(StatusBarNotification sbn) {
//        Log.i("NotificationListener", "[snowdeer] onNotificationPosted() - " + sbn.toString());
//        Log.i("NotificationListener", "[snowdeer] PackageName:" + sbn.getPackageName());
//        Log.i("NotificationListener", "[snowdeer] PostTime:" + sbn.getPostTime());

        Notification notificatin = sbn.getNotification();
        Bundle extras = notificatin.extras;
        String title = extras.getString(Notification.EXTRA_TITLE);
        int smallIconRes = extras.getInt(Notification.EXTRA_SMALL_ICON);
        Bitmap largeIcon = ((Bitmap) extras.getParcelable(Notification.EXTRA_LARGE_ICON));
        CharSequence text = extras.getCharSequence(Notification.EXTRA_TEXT);
        CharSequence subText = extras.getCharSequence(Notification.EXTRA_SUB_TEXT);

        Log.i("NotificationListener", "[snowdeer] Title:" + title);
        Log.i("NotificationListener", "[snowdeer] Text:" + text);
        Log.i("NotificationListener", "[snowdeer] Sub Text:" + subText);

        String packageName = sbn.getPackageName();
        long postTime = sbn.getPostTime();
        String roomName = subText == null ? title : subText.toString();

        // ignore 광고
        if(text !=null && text.toString().contains("광고")) return;
        // ignore ignorelist
        if(CommonUtil.isExistInIgnoreList(roomName)) return;

        switch (packageName){
            case "com.kakao.talk":

                if(text != null && title != null){

                    NotiMessageRepository repository = new NotiMessageRepository(getApplication(),null);
                    repository.insert(new NotiMessage(title,text.toString(),roomName,sbn.getPostTime(),"kakao",CommonUtil.getBytes(largeIcon)));
                }

                break;
        }



    }

    @Override
    public void onNotificationRemoved(StatusBarNotification sbn) {
        Log.i("NotificationListener", "[snowdeer] onNotificationRemoved() - " + sbn.toString());
    }
}
