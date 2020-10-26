package com.rnd.jyong.notificationsaver.utils;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.text.format.DateFormat;
import android.util.Log;
import android.view.View;

import androidx.annotation.ArrayRes;

import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import com.rnd.jyong.notificationsaver.R;
import com.rnd.jyong.notificationsaver.base.BaseApplication;
import com.rnd.jyong.notificationsaver.data.model.NotiMessage;
import com.rnd.jyong.notificationsaver.data.preference.JPreference;

import java.io.ByteArrayOutputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class CommonUtil {

    // convert from bitmap to byte array
    public static byte[] getBytes(Bitmap bitmap) {
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 0, stream);
        return stream.toByteArray();
    }

    // convert from Drawable to byte array
    public static byte[] getBytesFromDrawable(Drawable drawable) {

        Bitmap bitmap = ((BitmapDrawable)drawable).getBitmap();
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, stream);
        byte[] bitMapData = stream.toByteArray();

        return bitMapData;
    }




    // convert from byte array to bitmap
    public static Bitmap getImage(byte[] image) {
        return BitmapFactory.decodeByteArray(image, 0, image.length);
    }

    // convert long time to simple time
    public static String convertTimeToSimpleTime(long time) {

        Date date = new Date(time);
        // 시간을 나타냇 포맷을 정한다 ( yyyy/MM/dd 같은 형태로 변형 가능 )
        SimpleDateFormat sdfNow = new SimpleDateFormat("hh:mm a");
        // nowDate 변수에 값을 저장한다.
        String formatDate = sdfNow.format(date);

        String firstStr = formatDate.substring(6,8);
        String middleStr = formatDate.substring(0,1);
        String lastStr = formatDate.substring(1,5);

        middleStr = middleStr.equalsIgnoreCase("0") ? "" : middleStr;

        return firstStr + " " + middleStr+lastStr;
    }

    public static String convertTimeForRoomList(long time) {

        Calendar smsTime = Calendar.getInstance();
        smsTime.setTimeInMillis(time);

        Calendar now = Calendar.getInstance();

        if (now.get(Calendar.DATE) == smsTime.get(Calendar.DATE) ) {
            // today

            Date date = new Date(time);
            // 시간을 나타냇 포맷을 정한다 ( yyyy/MM/dd 같은 형태로 변형 가능 )
            SimpleDateFormat sdfNow = new SimpleDateFormat("hh:mm a");
            // nowDate 변수에 값을 저장한다.
            String formatDate = sdfNow.format(date);

            String firstStr = formatDate.substring(6,8);
            String middleStr = formatDate.substring(0,1);
            String lastStr = formatDate.substring(1,5);

            middleStr = middleStr.equalsIgnoreCase("0") ? "" : middleStr;

            return firstStr + " " + middleStr+lastStr;

        } else if (now.get(Calendar.DATE) - smsTime.get(Calendar.DATE) == 1  ){
            // yesterday
            return "Yesterday";
        } else{
            // else
            Date date = new Date(time);
            // 시간을 나타냇 포맷을 정한다 ( yyyy/MM/dd 같은 형태로 변형 가능 )
            SimpleDateFormat sdfNow = new SimpleDateFormat("MM월 dd일");
            String formatDate = sdfNow.format(date);
            return formatDate;
        }

    }

    public static void addToIgnoreList(NotiMessage notiMessage){

        List<NotiMessage> ignoreList = JPreference.getIgnoreList();
        if(ignoreList == null){
            ignoreList = new ArrayList<>();
        }
        ignoreList.add(notiMessage);
        JPreference.setIgnoreList(ignoreList);
    }

    public static void removeIgnoreListItem(NotiMessage notiMessage){

        boolean existInlist = false;
        NotiMessage toDelete = null;

        List<NotiMessage> ignoreList = JPreference.getIgnoreList();
        if(ignoreList!=null && ignoreList.size() > 0){

            for (NotiMessage notimsg : ignoreList) {
                if(notimsg.roomname.equalsIgnoreCase(notiMessage.roomname)){
                    toDelete = notimsg;
                    existInlist = true;
                }
            }
        }

        if(existInlist && toDelete!=null){
            ignoreList.remove(toDelete);
            JPreference.setIgnoreList(ignoreList);
        }

    }

    public static boolean isExistInIgnoreList(String roomname) {

        try{
            List<NotiMessage> ignoreList = JPreference.getIgnoreList();

            if(ignoreList == null) return false;

            for (NotiMessage notiMessage : ignoreList) {
                if(notiMessage.roomname.equalsIgnoreCase(roomname)) return true;
            }
        }catch (Exception e){
            return false;
        }

        return false;

    }

    public static byte[] convertDrawableToBytesWithBackground(Drawable d){
        Bitmap bitmap = ((BitmapDrawable)d).getBitmap();
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, stream);
        return stream.toByteArray();
    }

    public static boolean checkLastRoomInAdmobTime(){

        long lastRoomInAdmobTime = JPreference.getLastRoomInAdmobTime();
        long currentTime = System.currentTimeMillis();
        return currentTime -lastRoomInAdmobTime > (5 * 1000 * 60);

    }

    public static boolean checkLastUpdateDiaogTime(){

        long lastUpdateDialogTime = JPreference.getLastUpdateDialogTime();
        long currentTime = System.currentTimeMillis();
        return currentTime -lastUpdateDialogTime > (60 * 1000 * 60);

    }

    public static boolean rotateFab(final View v, boolean rotate) {
        v.animate().setDuration(200)
                .setListener(new AnimatorListenerAdapter() {
                    @Override
                    public void onAnimationEnd(Animator animation) {
                        super.onAnimationEnd(animation);
                    }
                })
                .rotation(rotate ? 135f : 0f);
        return rotate;
    }

    public static void showIn(final View v) {
        v.setVisibility(View.VISIBLE);
        v.setAlpha(0f);
        v.setTranslationY(v.getHeight());
        v.animate()
                .setDuration(200)
                .translationY(0)
                .setListener(new AnimatorListenerAdapter() {
                    @Override
                    public void onAnimationEnd(Animator animation) {
                        super.onAnimationEnd(animation);
                    }
                })
                .alpha(1f)
                .start();
    }
    public static void showOut(final View v) {
        v.setVisibility(View.VISIBLE);
        v.setAlpha(1f);
        v.setTranslationY(0);
        v.animate()
                .setDuration(200)
                .translationY(v.getHeight())
                .setListener(new AnimatorListenerAdapter() {
                    @Override
                    public void onAnimationEnd(Animator animation) {
                        v.setVisibility(View.GONE);
                        super.onAnimationEnd(animation);
                    }
                }).alpha(0f)
                .start();
    }

    public static void init(final View v) {
        v.setVisibility(View.GONE);
        v.setTranslationY(v.getHeight());
        v.setAlpha(0f);
    }

    public static String[] getStringArray(@ArrayRes int resId) {
        return BaseApplication.getInstance().getResources().getStringArray(resId);
    }

}
