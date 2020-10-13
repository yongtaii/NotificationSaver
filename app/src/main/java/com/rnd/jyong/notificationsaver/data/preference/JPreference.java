package com.rnd.jyong.notificationsaver.data.preference;

import android.app.Activity;
import android.content.SharedPreferences;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.rnd.jyong.notificationsaver.base.BaseApplication;
import com.rnd.jyong.notificationsaver.data.model.NotiMessage;

import org.json.JSONArray;
import org.json.JSONException;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class JPreference {

    private static final String PREF_NAME = "rms_pref";

    private static final String IGNORE_LIST  = "GPS_RECEIVED";
    private static final String GUIDE_TALK_MESSAGE  = "GUIDE_TALK_MESSAGE";
    private static final String ADMOB_ROOM_IN  = "ADMOB_ROOM_IN";

    public static List<NotiMessage> getIgnoreList() {
        return getIgnoreList(IGNORE_LIST);
    }

    public static boolean getIsFirst() {
        return get(GUIDE_TALK_MESSAGE, true);
    }
    public static void setIsFirst(boolean isfirst) {
        put(GUIDE_TALK_MESSAGE, isfirst);
    }

    public static long getLastRoomInAdmobTime() {
        return get(ADMOB_ROOM_IN, -9999l);
    }
    public static void setShowLastRoomInAdmobTime(long adTime) {
        put(ADMOB_ROOM_IN, adTime);
    }

    public static void setIgnoreList(List<NotiMessage> list) {
        put(IGNORE_LIST,list);
    }

    public static List<NotiMessage> getIgnoreList(String key){
        String serializedObject = get(key, "");

        if (serializedObject != null){
            Gson gson = new Gson();
            Type type = new TypeToken<List<NotiMessage>>(){}.getType();
            return gson.fromJson(serializedObject, type);
        }

        return new ArrayList<>();
    }

    public static void put(String key, List<NotiMessage> list) {
        Gson gson = new Gson();
        String json = gson.toJson(list);
        put(key, json);
    }

    public static void put(String key, String value) {
        SharedPreferences pref = BaseApplication.getInstance().getSharedPreferences(PREF_NAME, Activity.MODE_PRIVATE);
        SharedPreferences.Editor editor = pref.edit();

        editor.putString(key, value);
        editor.apply();
    }

    public static void put(String key, boolean value) {
        SharedPreferences pref = BaseApplication.getInstance().getSharedPreferences(PREF_NAME, Activity.MODE_PRIVATE);
        SharedPreferences.Editor editor = pref.edit();

        editor.putBoolean(key, value);
        editor.apply();
    }

    public static void put(String key, int value) {
        SharedPreferences pref = BaseApplication.getInstance().getSharedPreferences(PREF_NAME, Activity.MODE_PRIVATE);
        SharedPreferences.Editor editor = pref.edit();

        editor.putInt(key, value);
        editor.apply();
    }

    public static void put(String key, long value) {
        SharedPreferences pref = BaseApplication.getInstance().getSharedPreferences(PREF_NAME, Activity.MODE_PRIVATE);
        SharedPreferences.Editor editor = pref.edit();

        editor.putLong(key, value);
        editor.apply();
    }

    public static String get(String key, String dftValue) {
        SharedPreferences pref = BaseApplication.getInstance().getSharedPreferences(PREF_NAME, Activity.MODE_PRIVATE);
        try {
            return pref.getString(key, dftValue);
        } catch (Exception e) {
            return dftValue;
        }
    }

    public static int get(String key, int dftValue) {
        SharedPreferences pref = BaseApplication.getInstance().getSharedPreferences(PREF_NAME, Activity.MODE_PRIVATE);
        try {
            return pref.getInt(key, dftValue);
        } catch (Exception e) {
            return dftValue;
        }
    }

    public static long get(String key, long dftValue) {
        SharedPreferences pref = BaseApplication.getInstance().getSharedPreferences(PREF_NAME, Activity.MODE_PRIVATE);
        try {
            return pref.getLong(key, dftValue);
        } catch (Exception e) {
            return dftValue;
        }
    }

    public static boolean get(String key, boolean dftValue) {

        SharedPreferences pref = BaseApplication.getInstance().getSharedPreferences(PREF_NAME, Activity.MODE_PRIVATE);
        try {
            return pref.getBoolean(key, dftValue);
        } catch (Exception e) {
            return dftValue;
        }
    }

}
