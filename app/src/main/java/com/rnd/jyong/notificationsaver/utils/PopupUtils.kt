package com.rnd.jyong.notificationsaver.utils

import android.app.Activity
import android.content.DialogInterface
import android.os.Build
import androidx.appcompat.app.AlertDialog

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

}