package com.rnd.jyong.notificationsaver.service;

import android.app.Notification;
import android.app.Service;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.Icon;
import android.os.Build;
import android.os.Bundle;
import android.os.IBinder;
import android.service.notification.StatusBarNotification;
import android.util.Log;

import com.rnd.jyong.notificationsaver.BuildConfig;
import com.rnd.jyong.notificationsaver.R;
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


        String pkgName = sbn.getPackageName();
        Bundle extras = sbn.getNotification().extras;
        Drawable smallIcon = null;
        Bitmap extraPicture;

        int iconId = extras.getInt(Notification.EXTRA_SMALL_ICON);

//        try {
//            PackageManager manager = getPackageManager();
//            Resources resources = manager.getResourcesForApplication(pkgName);
//            smallIcon = resources.getDrawable(iconId);
//
//        } catch (PackageManager.NameNotFoundException e) {
//            e.printStackTrace();
//        }
//
//        if (extras.containsKey(Notification.EXTRA_PICTURE)) {
//            // this bitmap contain the picture attachment
//            extraPicture = (Bitmap) extras.get(Notification.EXTRA_PICTURE);
//        }



        Notification notificatin = sbn.getNotification();
//        int smallIconRes = extras.getInt(Notification.EXTRA_SMALL_ICON);
//        Bitmap largeIcon = ((Bitmap) extras.getParcelable(Notification.EXTRA_LARGE_ICON));
//        Icon icon = extras.getParcelable(Notification.EXTRA_LARGE_ICON);
//        Icon icon = notificatin.getLargeIcon();
//        Drawable largeIcon = icon.loadDrawable(getApplicationContext());

        Bitmap largeIcon;
        try{
            largeIcon = ((Bitmap) extras.getParcelable(Notification.EXTRA_LARGE_ICON));
            if(largeIcon == null){
                largeIcon = BitmapFactory.decodeResource(getResources(), R.mipmap.ic_launcher_round);
            }
        }catch (Exception e){
            largeIcon = BitmapFactory.decodeResource(getResources(), R.mipmap.ic_launcher_round);
        }

//        if(Build.VERSION.SDK_INT > Build.VERSION_CODES.P){
//            largeIcon = BitmapFactory.decodeResource(getResources(), R.mipmap.ic_launcher_round);
//        }else{
//            largeIcon = ((Bitmap) extras.getParcelable(Notification.EXTRA_LARGE_ICON));
//        }

        String title = extras.getString(Notification.EXTRA_TITLE);
        CharSequence text = extras.getCharSequence(Notification.EXTRA_TEXT);
        CharSequence subText = extras.getCharSequence(Notification.EXTRA_SUB_TEXT);

        Log.i("NotificationListener", "[snowdeer] Title:" + title);
        Log.i("NotificationListener", "[snowdeer] Text:" + text);
        Log.i("NotificationListener", "[snowdeer] Sub Text:" + subText);

//        String packageName = sbn.getPackageName();
//        long postTime = sbn.getPostTime();

        String roomName = subText == null ? title : subText.toString();

        // ignore 광고
        if(text !=null && text.toString().contains("광고")) return;
        // ignore ignorelist
        if(CommonUtil.isExistInIgnoreList(roomName)) return;

        switch (pkgName){
            case "com.kakao.talk":

                if(text != null && title != null){

                    NotiMessageRepository repository = new NotiMessageRepository(getApplication(),null);
//                    repository.insert(new NotiMessage(title,text.toString(),roomName,sbn.getPostTime(),"kakao",CommonUtil.getBytesFromDrawable(smallIcon)));
//                    repository.insert(new NotiMessage(title,text.toString(),roomName,sbn.getPostTime(),"kakao",CommonUtil.getBytesFromDrawable(largeIcon)));
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
