package com.rnd.jyong.notificationsaver.utils

import android.app.Activity
import android.content.DialogInterface
import android.os.Build
import androidx.appcompat.app.AlertDialog
import com.rnd.jyong.notificationsaver.R

object PopupUtils {

    fun createSimplePopup(activity : Activity,
                                  title : String? = null,
                                  message : String? = null,
                                  okListener : DialogInterface.OnClickListener? = null,) : AlertDialog {
        val builder = AlertDialog.Builder(activity)
        builder.setTitle(title)
            .setMessage(message)
            .setPositiveButton("확인",okListener)
            .setNegativeButton("취소") { dialog, _ ->
                dialog.dismiss()
            }
        return builder.create()

    }

    /**
     * 앱 업데이트 요청 팝업
     * */
    fun createAppUpdatePopup(activity : Activity,
                          okListener : DialogInterface.OnClickListener? = null,) : AlertDialog {
        val builder = AlertDialog.Builder(activity)
        builder.setMessage(R.string.popup_disable_message)
            .setCancelable(false)
            .setPositiveButton(R.string.popup_button_ok) { _, _ ->
                SystemUtils.goGoogleStore(activity.packageName , activity)
            }
        return builder.create()
    }

}