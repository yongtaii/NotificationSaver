package com.rnd.jyong.notificationsaver.utils;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.text.format.DateFormat;
import android.util.Log;

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

        List<NotiMessage> ignoreList = JPreference.getIgnoreList();
        if(ignoreList.size() > 0){

            for (NotiMessage notimsg : ignoreList) {
                if(notimsg.roomname.equalsIgnoreCase(notiMessage.roomname)){
                    ignoreList.remove(notimsg);
                    JPreference.setIgnoreList(ignoreList);
                }
            }
        }
    }

    public static boolean isExistInIgnoreList(String roomname) {

        List<NotiMessage> ignoreList = JPreference.getIgnoreList();

        if(ignoreList == null) return false;

        for (NotiMessage notiMessage : ignoreList) {
            if(notiMessage.roomname.equalsIgnoreCase(roomname)) return true;
        }

        return false;

    }

}
