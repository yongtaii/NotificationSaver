package com.rnd.jyong.notificationsaver.database.notification.dao

import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.rnd.jyong.notificationsaver.database.notification.entity.Message
import kotlinx.coroutines.flow.Flow

/**
 * Dao(Data Access Object) : 앱이 데이터베이스의 데이터를 쿼리를 제공한다
 * */
@Dao
interface MessageDao {

    /**
     * insert 메시지
     * */
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(message: Message) : Long

    /**
     * delete Group
     * */
    @Query("DELETE FROM table_message WHERE group_name=:groupName ")
    suspend fun deleteGroup(groupName: String)

    /**
     * 입력시간 기반 메시지를 삭제한다
     * */
    @Query("DELETE FROM table_message WHERE post_time < :inputTime")
    suspend fun deleteMessageByTime(inputTime: Long)

    /**
     * 전체 메시지
     * @return Flow
     * */
    @Query("SELECT * FROM table_message ORDER BY post_time ASC")
    fun getAllMessages(): Flow<List<Message>>

    /**
     * 전체 메시지
     * @return PagingSource
     * */
    @Query("SELECT * FROM table_message ORDER BY post_time ASC")
    fun getAllMessagesByPagingSource(): PagingSource<Int, Message>

    /**
     * 메인화면 그룹 리스트
     * group_name 으로 그룹화 한후 최근 메시지를 가져온다
     * */
    @Query("SELECT * FROM ( SELECT * FROM table_message GROUP BY group_name HAVING max(post_time))  ORDER BY post_time DESC")
    suspend fun getLastestGroupMessages(): List<Message>

    /**
     * 메인화면 그룹 리스트
     * group_name 으로 그룹화 한후 최근 메시지를 가져온다
     * */
    @Query("SELECT * FROM ( SELECT * FROM table_message GROUP BY group_name HAVING max(post_time))  ORDER BY post_time DESC")
    fun getGroupListByPagingSource(): PagingSource<Int, Message>

    /**
     * 그룹의 메시지 전부를 가져온다
     * */
    @Query("SELECT * FROM table_message WHERE group_name=:groupName ORDER BY post_time DESC")
    fun getGroupMessages(groupName: String): Flow<List<Message>>

    /**
     * 그룹의 메시지 전부를 가져온다
     * */
    @Query("SELECT * FROM table_message WHERE group_name=:groupName ORDER BY post_time DESC")
    fun getGroupMessagesByPagingSource(groupName: String): PagingSource<Int, Message>


//    @Query("SELECT * FROM table_message ORDER BY saved_time ASC")
//    fun getAllNotiMessages(): LiveData<List<Message?>?>?
//

//
//    @Query("SELECT DISTINCT name from table_message ORDER BY saved_time ASC")
//    fun getAllRoomList(): List<String?>?
//
//    @Query("DELETE FROM table_message")
//    fun deleteAll()
//

}