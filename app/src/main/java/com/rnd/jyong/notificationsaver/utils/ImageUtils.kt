package com.rnd.jyong.notificationsaver.utils

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.util.Base64
import java.io.ByteArrayOutputStream

object ImageUtils {

    // convert from byte array to bitmap
    fun convertByteArrayToBitmap(image: ByteArray): Bitmap {
        return BitmapFactory.decodeByteArray(image, 0, image.size)
    }

    /**
     *
     * */
    fun convertBase64ToBitmap(base64 : String) : Bitmap {
        val byteArray =  decodeBase64ToByteArray(base64)
        return byteArrayToBitmap(byteArray)
    }
    /**
     *
     * */
    fun decodeBase64ToByteArray(base64 : String) : ByteArray {
        return Base64.decode(base64, 0)
    }
    /**
     *
     * */
    fun byteArrayToBitmap(data: ByteArray): Bitmap {
        return BitmapFactory.decodeByteArray(data, 0, data.size)
    }

    fun bitmapToByteArray(bitmap: Bitmap): ByteArray? {
        val stream = ByteArrayOutputStream()
        bitmap.compress(Bitmap.CompressFormat.PNG, 0, stream)
        return stream.toByteArray()
    }
}