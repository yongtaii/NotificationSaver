package com.rnd.jyong.notificationsaver.service;

import android.app.Notification;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Build;
import android.os.Bundle;
import android.service.notification.StatusBarNotification;

import com.rnd.jyong.notificationsaver.R;
import com.rnd.jyong.notificationsaver.data.model.NotiMessage;
import com.rnd.jyong.notificationsaver.data.repository.NotiMessageRepository;
import com.rnd.jyong.notificationsaver.utils.CommonUtil;

public class NotificationListenerService extends android.service.notification.NotificationListenerService {

    @Override
    public void onCreate() {
        super.onCreate();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    @Override
    public void onNotificationPosted(StatusBarNotification sbn) {
        String pkgName = sbn.getPackageName();
        Bundle extras = sbn.getNotification().extras;
        Bitmap largeIcon;
        try{
            largeIcon = ((Bitmap) extras.getParcelable(Notification.EXTRA_LARGE_ICON));
            if(largeIcon == null){
                largeIcon = BitmapFactory.decodeResource(getResources(), R.mipmap.ic_launcher_round);
            }
        }catch (Exception e){
            largeIcon = BitmapFactory.decodeResource(getResources(), R.mipmap.ic_launcher_round);
        }
        Bitmap image = CommonUtil.getImageFromSbnBundle(extras);
        byte[] imageByteArray = new byte[0];
        if(image != null)  imageByteArray = CommonUtil.getBytes(image);
        String title = extras.getString(Notification.EXTRA_TITLE);
        CharSequence text = extras.getCharSequence(Notification.EXTRA_TEXT);
        CharSequence subText = extras.getCharSequence(Notification.EXTRA_SUB_TEXT);


        String roomName = subText == null ? title : subText.toString();
        // ignore 광고
        if(text !=null && text.toString().contains("광고")) return;
        // ignore ignorelist
        if(CommonUtil.isExistInIgnoreList(roomName)) return;


        switch (pkgName){
            case "com.kakao.talk":

                if(text != null && title != null){
                    inesrtDB(title,text,roomName,sbn,largeIcon,imageByteArray);
                }

                break;
        }

    }

    private void inesrtDB(String title,CharSequence text,String roomName,StatusBarNotification sbn,Bitmap largeIcon,byte[] imageByteArray ){

        NotiMessageRepository repository = new NotiMessageRepository(getApplication(),null);

        try{

            if( Build.VERSION.SDK_INT > Build.VERSION_CODES.P && sbn.getNotification().getLargeIcon() != null){
                repository.insert(new NotiMessage(title,text.toString(),roomName,sbn.getPostTime(),
                        "kakao",CommonUtil.convertDrawableToBytesWithBackground(sbn.getNotification().getLargeIcon().loadDrawable(getApplicationContext())),imageByteArray));
            }else{
                repository.insert(new NotiMessage(title,text.toString(),roomName,sbn.getPostTime(),"kakao",CommonUtil.getBytes(largeIcon),imageByteArray));
            }

        }catch (Exception e){
            e.printStackTrace();
            repository.insert(new NotiMessage(title,text.toString(),roomName,sbn.getPostTime(),"kakao",CommonUtil.getBytes(largeIcon),imageByteArray));
        }

    }

    @Override
    public void onNotificationRemoved(StatusBarNotification sbn) {
    }
}
