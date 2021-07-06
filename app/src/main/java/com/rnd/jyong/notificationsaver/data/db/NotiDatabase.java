package com.rnd.jyong.notificationsaver.data.db;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.rnd.jyong.notificationsaver.data.dao.NotiMessageDao;
import com.rnd.jyong.notificationsaver.data.model.NotiMessage;

@Database(entities = {NotiMessage.class}, version = 2,exportSchema = false)
public abstract class NotiDatabase extends RoomDatabase {
    public abstract NotiMessageDao notiMessageDao();

    private static NotiDatabase INSTANCE;
    public static final String DB_NAME = "notidatabase";
    public static final String TABLE_NAME_NOTI = "noti_table";

    public static NotiDatabase getDatabase(Context context) {
        if (INSTANCE == null) {
            INSTANCE =
                    Room.databaseBuilder(context.getApplicationContext(), NotiDatabase.class, NotiDatabase.DB_NAME)
                            .fallbackToDestructiveMigration()
                            .build();
        }
        return INSTANCE;
    }


    public static void destroyInstance() {
        INSTANCE = null;
    }

}
