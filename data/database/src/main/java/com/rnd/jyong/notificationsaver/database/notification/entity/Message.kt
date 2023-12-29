package com.rnd.jyong.notificationsaver.database.notification.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

/**
 * Entity : 데이터베이스의 테이블을 나타낸다
 * */
@Entity(tableName = "table_message")
data class Message(
    /**
     * 보낸사람
     * */
    @ColumnInfo(name = "name") val name: String,
    /**
     * message
     * */
    @ColumnInfo(name = "message") val message: String,
    /**
     * 그룹 이름
     * */
    @ColumnInfo(name = "group_name") val groupName: String,
    /**
     * 시각
     * */
    @ColumnInfo(name = "post_time") val postTime: Long,
    /**
     * 아이콘
     * */
    @ColumnInfo(name = "icon_base64") val iconBase64: String,
    /**
     * 이미지
     * */
    @ColumnInfo(name = "image_base64") val imageBase64: String
) {
    /**
     * id
     */
    @PrimaryKey(autoGenerate = true) var id: Int = 0
}

