package com.rnd.jyong.notificationsaver.data.dao;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import com.rnd.jyong.notificationsaver.data.model.NotiMessage;

import java.util.List;

@Dao
public interface NotiMessageDao {
    @Insert
    long insert(NotiMessage notiMessage);

    @Query("SELECT * FROM noti_table ORDER BY time ASC")
    LiveData<List<NotiMessage>> getAllNotiMessages();

    @Query("SELECT * FROM noti_table WHERE roomname=:rname ORDER BY time DESC")
    LiveData<List<NotiMessage>> getNotiMessagesByRoomname(String rname);

    @Query("SELECT DISTINCT name from noti_table ORDER BY time ASC")
    List<String> getAllRoomList();

    @Query("DELETE FROM noti_table")
    void deleteAll();

    @Query("DELETE FROM noti_table WHERE time < :inputTime")
    void deleteMessageWithTime(long inputTime);

    @Query("DELETE FROM noti_table WHERE roomname=:rname ")
    void deleteRoomMessage(String rname);

    @Query("SELECT * FROM ( SELECT * FROM noti_table ORDER BY time ASC) AS A GROUP BY roomname ORDER BY time DESC")
    LiveData<List<NotiMessage>> getRoomListNotiMessages();

//    SELECT TOP 1 * FROM Table ORDER BY ID DESC GROUP BY

}
