package com.rnd.jyong.notificationsaver.utils

import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Date

object TimeUtils {

    /*
    * 채팅방 표시 시각
    * */
    fun convertTimeToSimpleTime(time: Long): String {
        val date = Date(time)
        // 시간을 나타냇 포맷을 정한다 ( yyyy/MM/dd 같은 형태로 변형 가능 )
        val sdfNow = SimpleDateFormat("hh:mm a")
        // nowDate 변수에 값을 저장한다.
        val formatDate = sdfNow.format(date)
        val firstStr = formatDate.substring(6, 8)
        var middleStr = formatDate.substring(0, 1)
        val lastStr = formatDate.substring(1, 5)
        middleStr = if (middleStr.equals("0", ignoreCase = true)) "" else middleStr
        return "$firstStr $middleStr$lastStr"
    }

    /**
     * 메인 화면 표출용 시간 변환
     * */
    fun convertTimeForMainList(time: Long): String {
        val smsTime = Calendar.getInstance()
        smsTime.timeInMillis = time
        val now = Calendar.getInstance()
        return if (now[Calendar.DATE] == smsTime[Calendar.DATE]) {
            // today
            val date = Date(time)
            // 시간을 나타냇 포맷을 정한다 ( yyyy/MM/dd 같은 형태로 변형 가능 )
            val sdfNow = SimpleDateFormat("hh:mm a")
            // nowDate 변수에 값을 저장한다.
            val formatDate = sdfNow.format(date)
            val firstStr = formatDate.substring(6, 8)
            var middleStr = formatDate.substring(0, 1)
            val lastStr = formatDate.substring(1, 5)
            middleStr = if (middleStr.equals("0", ignoreCase = true)) "" else middleStr
            "$firstStr $middleStr$lastStr"
        } else if (now[Calendar.DATE] - smsTime[Calendar.DATE] == 1) {
            // yesterday
            "Yesterday"
        } else {
            // else
            val date = Date(time)
            // 시간을 나타냇 포맷을 정한다 ( yyyy/MM/dd 같은 형태로 변형 가능 )
            val sdfNow = SimpleDateFormat("MM월 dd일")
            sdfNow.format(date)
        }
    }

    /**
     * Detail 화면 표출용 시간 변환
     * */
    fun convertTimeForDetailList(time: Long): String {
        val date = Date(time)
        // 시간을 나타냇 포맷을 정한다 ( yyyy/MM/dd 같은 형태로 변형 가능 )
        val sdfNow = SimpleDateFormat("hh:mm a")
        // nowDate 변수에 값을 저장한다.
        val formatDate = sdfNow.format(date)
        val firstStr = formatDate.substring(6, 8)
        var middleStr = formatDate.substring(0, 1)
        val lastStr = formatDate.substring(1, 5)
        middleStr = if (middleStr.equals("0", ignoreCase = true)) "" else middleStr
        return "$firstStr $middleStr$lastStr"
    }
}