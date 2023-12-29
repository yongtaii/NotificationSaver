package com.rnd.jyong.notificationsaver.data.repository;

import android.app.Application;
import android.os.AsyncTask;

import androidx.lifecycle.LiveData;

import com.rnd.jyong.notificationsaver.base.App;
import com.rnd.jyong.notificationsaver.data.dao.NotiMessageDao;
import com.rnd.jyong.notificationsaver.data.db.NotiDatabase;
import com.rnd.jyong.notificationsaver.data.model.NotiMessage;

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

            allNotiMessages = notiMessageDao.getAllNotiMessages();
            allRoomListNotiMessages = notiMessageDao.getRoomListNotiMessages();

        // roomname 있을 때 : room (MainActivity)입장 시
        if(roomname != null){
                msgLiveDataByRoomname = notiMessageDao.getNotiMessagesByRoomname(roomname);
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
//                NotiDatabase db = NotiDatabase.getDatabase(App.getInstance().getApplicationContext());
//                notiMessageDao = db.notiMessageDao();
            }
            @Override
            protected Long doInBackground(NotiMessage... notiMessages) {
                if (notiMessageDao == null)
                    return -1L;

                try{
                    return notiMessageDao.insert(notiMessages[0]);
                }catch (Exception e){
                    e.printStackTrace();
                    return -999l;
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
//                NotiDatabase db = NotiDatabase.getDatabase(App.getInstance().getApplicationContext());
//                notiMessageDao = db.notiMessageDao();
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

    public static void deleteMessageWithTime(long time){
        new AsyncTask<Long, Void, Long>() {

            private NotiMessageDao notiMessageDao;

            @Override
            protected void onPreExecute() {
                super.onPreExecute();
//                NotiDatabase db = NotiDatabase.getDatabase(App.getInstance().getApplicationContext());
//                notiMessageDao = db.notiMessageDao();
            }
            @Override
            protected Long doInBackground(Long... time) {
                if (notiMessageDao == null)
                    return -1L;

                try{
                    notiMessageDao.deleteMessageWithTime(time[0]);
                }catch (Exception e){
                    e.printStackTrace();
                }

                return null;
            }

            @Override
            protected void onPostExecute(Long aLong) {
                super.onPostExecute(aLong);
            }
        }.execute(time);
    }

    public static void deleteRoomMessage(String roomname){
        new AsyncTask<String, Void, Long>() {

            private NotiMessageDao notiMessageDao;

            @Override
            protected void onPreExecute() {
                super.onPreExecute();
//                NotiDatabase db = NotiDatabase.getDatabase(App.getInstance().getApplicationContext());
//                notiMessageDao = db.notiMessageDao();
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
