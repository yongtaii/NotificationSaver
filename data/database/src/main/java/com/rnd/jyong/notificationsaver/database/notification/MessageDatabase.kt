package com.rnd.jyong.notificationsaver.database.notification

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.rnd.jyong.notificationsaver.database.notification.dao.MessageDao
import com.rnd.jyong.notificationsaver.database.notification.entity.Message

@Database(entities = [Message::class], version = 1)
abstract class MessageDatabase : RoomDatabase() {
    abstract fun messageDao(): MessageDao

//    companion object {
//        @Volatile
//        private var INSTANCE: RoomDatabase? = null
//
//        fun getDatabase(context: Context): RoomDatabase {
//            return INSTANCE ?: synchronized(this) {
//                val instance = Room.databaseBuilder(
//                    context.applicationContext,
//                    RoomDatabase::class.java,
//                    "database_message"
//                ).build()
//                INSTANCE = instance
//                instance
//            }
//        }
//
//    }

}