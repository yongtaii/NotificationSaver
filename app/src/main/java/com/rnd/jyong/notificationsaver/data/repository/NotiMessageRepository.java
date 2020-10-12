package com.rnd.jyong.notificationsaver.data.repository;

import android.app.Application;
import android.os.AsyncTask;
import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.LiveData;

import com.rnd.jyong.notificationsaver.base.BaseApplication;
import com.rnd.jyong.notificationsaver.data.dao.NotiMessageDao;
import com.rnd.jyong.notificationsaver.data.db.NotiDatabase;
import com.rnd.jyong.notificationsaver.data.model.NotiMessage;

import java.lang.ref.WeakReference;
import java.util.List;

public class NotiMessageRepository {

    private static final String TAG = NotiMessageRepository.class.getSimpleName();


    private final LiveData<List<NotiMessage>> msgLiveDataByRoomname;
    private final LiveData<List<NotiMessage>> allNotiMessages;
    private final LiveData<List<NotiMessage>> allRoomListNotiMessages;
//    private final List<String> allRommList;
    private final NotiMessageDao notiMessageDao;

    public NotiMessageRepository(Application application, String roomname) {
        NotiDatabase db = NotiDatabase.getDatabase(application);
        notiMessageDao = db.notiMessageDao();

        try{
            allNotiMessages = notiMessageDao.getAllNotiMessages();
        }catch (Exception e){
        }

        try{
            allRoomListNotiMessages = notiMessageDao.getRoomListNotiMessages();
        }catch (Exception e){
        }

        // roomname 있을 때 : room (MainActivity)입장 시
        if(roomname != null){
            try{
                msgLiveDataByRoomname = notiMessageDao.getNotiMessagesByRoomname(roomname);
            }catch (Exception e){
            }
        }else{
            msgLiveDataByRoomname = null;
        }

    }

    public static void insert(NotiMessage notiMessage) {
        new AsyncTask<NotiMessage, Void, Long>() {

            private NotiMessageDao notiMessageDao;

            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                NotiDatabase db = NotiDatabase.getDatabase(BaseApplication.getInstance().getApplicationContext());
                notiMessageDao = db.notiMessageDao();
            }
            @Override
            protected Long doInBackground(NotiMessage... notiMessages) {
                if (notiMessageDao == null)
                    return -1L;

                try{
                    return notiMessageDao.insert(notiMessages[0]);
                }catch (Exception e){
                    return -999l;
                    e.printStackTrace();
                }

            }

            @Override
            protected void onPostExecute(Long aLong) {
                super.onPostExecute(aLong);
            }
        }.execute(notiMessage);
    }

    public LiveData<List<NotiMessage>> getAllNotiMessages() {
        return allNotiMessages;
    }

    public LiveData<List<NotiMessage>> getNotiMessagesByRoomname(String roomname) {
        return msgLiveDataByRoomname;
    }

//    public LiveData<List<String>> getAllRoomList() {
//        return allRommList;
//    }
    public LiveData<List<NotiMessage>> getAllRoomList() {
        return allRoomListNotiMessages;
    }

    public static void deleteAll(){
        new AsyncTask<Void, Void, Long>() {

            private NotiMessageDao notiMessageDao;

            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                NotiDatabase db = NotiDatabase.getDatabase(BaseApplication.getInstance().getApplicationContext());
                notiMessageDao = db.notiMessageDao();
            }
            @Override
            protected Long doInBackground(Void... voids) {
                if (notiMessageDao == null)
                    return -1L;

                try{
                    notiMessageDao.deleteAll();
                }catch (Exception e){
                    e.printStackTrace();
                }

                return null;
            }

            @Override
            protected void onPostExecute(Long aLong) {
                super.onPostExecute(aLong);
            }
        }.execute();
    }

    public static void deleteRoomMessage(String roomname){
        new AsyncTask<String, Void, Long>() {

            private NotiMessageDao notiMessageDao;

            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                NotiDatabase db = NotiDatabase.getDatabase(BaseApplication.getInstance().getApplicationContext());
                notiMessageDao = db.notiMessageDao();
            }
            @Override
            protected Long doInBackground(String... strings) {
                if (notiMessageDao == null)
                    return -1L;

                try{
                    notiMessageDao.deleteRoomMessage(strings[0]);
                }catch (Exception e){
                    e.printStackTrace();
                }

                return null;
            }

            @Override
            protected void onPostExecute(Long aLong) {
                super.onPostExecute(aLong);
            }
        }.execute(roomname);
    }

}
