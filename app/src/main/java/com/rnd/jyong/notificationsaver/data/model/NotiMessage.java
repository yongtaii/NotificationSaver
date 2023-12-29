package com.rnd.jyong.notificationsaver.data.model;

import android.graphics.Bitmap;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import com.rnd.jyong.notificationsaver.data.db.NotiDatabase;

import java.io.Serializable;

@Entity(tableName = NotiDatabase.TABLE_NAME_NOTI)
public class NotiMessage implements Serializable {

    @PrimaryKey(autoGenerate = true)
    public int noti_msg_id;

    public String name;

    public String msg;

    public String roomname;

    public long time;

    public byte[] icon;
    public byte[] image;

    @ColumnInfo(name = "category")
    public String category;

    public NotiMessage(String name, String msg, String roomname, long time, String category, byte[] icon, byte[] image) {
        this.name = name;
        this.msg = msg;
        this.roomname = roomname;
        this.time = time;
        this.category = category;
        this.icon = icon;
        this.image = image;
    }

    public NotiMessage(NotiMessage notiMessage) {

        this.name = notiMessage.name;
        this.msg = notiMessage.msg;
        this.roomname = notiMessage.roomname;
        this.time = notiMessage.time;
        this.category = notiMessage.category;
        this.icon = notiMessage.icon;
        this.image = notiMessage.image;

    }
}
