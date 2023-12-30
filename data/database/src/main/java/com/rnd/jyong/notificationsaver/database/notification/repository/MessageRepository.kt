package com.rnd.jyong.notificationsaver.database.notification.repository

import androidx.lifecycle.LiveData
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.rnd.jyong.notificationsaver.database.notification.dao.MessageDao
import com.rnd.jyong.notificationsaver.database.notification.entity.Message
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class MessageRepository @Inject constructor(
    private val messageDao: MessageDao
) {

    suspend fun insert(message : Message){
        messageDao.insert(message)
    }

    suspend fun deleteGroup(groupName: String){
        messageDao.deleteGroup(groupName)
    }

    suspend fun deleteMessageByTime(inputTime: Long){
        messageDao.deleteMessageByTime(inputTime)
    }

    suspend fun getLastestGroupMessages() : List<Message>{
        return messageDao.getLastestGroupMessages()
    }

    fun getAllMessages() : Flow<List<Message>> {
        return messageDao.getAllMessages()
    }

    fun getAllMessagesByPagingSource() : Flow<PagingData<Message>> {

        val pagingSourceFactory = {
            messageDao.getAllMessagesByPagingSource()
        }

        return Pager(
            config = PagingConfig(
                pageSize = DATABSE_PAGER_PAGE_SIZE,
                enablePlaceholders = false, // true 라면 전체 데이터사이즈를 미리 받아와서 RecyclerView 에 미리 홀더를 만들어 놓고 나머지를 Null 로 만든다.
                maxSize = DATABSE_PAGER_PAGE_SIZE * 3 // Pager 가 메모리에 최대로 가지고 있을 수 있는 항목의 개수
            ),
            pagingSourceFactory = pagingSourceFactory
        ).flow

    }



//    fun getGroupList() : Flow<List<Message>> {
//        return messageDao.getGroupList()
//    }

    fun getGroupListByPagingSource() : Flow<PagingData<Message>> {

        val pagingSourceFactory = {
            messageDao.getGroupListByPagingSource()
        }

        return Pager(
            config = PagingConfig(
                pageSize = DATABSE_PAGER_PAGE_SIZE,
                enablePlaceholders = false, // true 라면 전체 데이터사이즈를 미리 받아와서 RecyclerView 에 미리 홀더를 만들어 놓고 나머지를 Null 로 만든다.
                maxSize = DATABSE_PAGER_PAGE_SIZE * 3 // Pager 가 메모리에 최대로 가지고 있을 수 있는 항목의 개수
            ),
            pagingSourceFactory = pagingSourceFactory
        ).flow

    }

//    fun getGroupMessages(groupName: String) : Flow<List<Message>> {
//        return messageDao.getGroupMessages(groupName)
//    }

    fun getGroupMessagesByPagingSource(groupName: String) : Flow<PagingData<Message>> {

        val pagingSourceFactory = {
            messageDao.getGroupMessagesByPagingSource(groupName)
        }

        return Pager(
            config = PagingConfig(
                pageSize = DATABSE_PAGER_PAGE_SIZE,
                enablePlaceholders = false, // true 라면 전체 데이터사이즈를 미리 받아와서 RecyclerView 에 미리 홀더를 만들어 놓고 나머지를 Null 로 만든다.
                maxSize = DATABSE_PAGER_PAGE_SIZE * 3 // Pager 가 메모리에 최대로 가지고 있을 수 있는 항목의 개수
            ),
            pagingSourceFactory = pagingSourceFactory
        ).flow

    }

    companion object {
        const val DATABSE_PAGER_PAGE_SIZE = 20
    }

}