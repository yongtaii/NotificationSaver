package com.rnd.jyong.notificationsaver.database.notification.di

import android.content.Context
import androidx.room.Room
import com.rnd.jyong.notificationsaver.database.notification.MessageDatabase
import com.rnd.jyong.notificationsaver.database.notification.dao.MessageDao
import com.rnd.jyong.notificationsaver.database.notification.repository.MessageRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
object MessageDBModule {

    @Singleton
    @Provides
    fun provideMessageDatabase(
        @ApplicationContext context: Context
    ): MessageDatabase = Room
        .databaseBuilder(context, MessageDatabase::class.java, "database_message.db")
        .build()

    @Singleton
    @Provides
    fun provideMessageDao(messageDatabase: MessageDatabase): MessageDao = messageDatabase.messageDao()

    @Singleton
    @Provides
    fun provideMessageRepository(
        messageDao: MessageDao
    ) : MessageRepository{
        return MessageRepository(messageDao)
    }
}