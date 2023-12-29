package com.rnd.jyong.notificationsaver.utils

import android.R.attr.bitmap
import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.util.Base64
import java.io.ByteArrayOutputStream
import java.io.File
import java.io.FileOutputStream
import java.io.IOException


object FileUtils {

    /**
     * Bitmap 저장 유틸
     * */
    fun saveToInternalStorage(context: Context, bitmapImage: Bitmap, fileName: String): String? {
        val mypath = File(getInternalStorageDir(context), fileName)
        var fos: FileOutputStream? = null
        try {
            fos = FileOutputStream(mypath)
            // Use the compress method on the BitMap object to write image to the OutputStream
            bitmapImage.compress(Bitmap.CompressFormat.PNG, 100, fos)
        } catch (e: java.lang.Exception) {
            e.printStackTrace()
        } finally {
            try {
                fos!!.close()
            } catch (e: IOException) {
                e.printStackTrace()
            }
        }
        return mypath.absolutePath
    }


    fun getInternalStorageDir(context: Context) : File{
        val dir = File(context.filesDir, "images")
        if (!dir.exists()) {
            dir.mkdir()
        }
        return dir
    }

    fun convertBitmapToBase64(bitmap : Bitmap) : String{
        val byteArray = convertBitmapToByteArray(bitmap)
        return encodeByteArrayToBase64(byteArray)
    }

    fun convertBitmapToByteArray(bitmap : Bitmap) : ByteArray{
        val byteArrayOutputStream = ByteArrayOutputStream()
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, byteArrayOutputStream)
        return byteArrayOutputStream.toByteArray()
    }

    fun encodeByteArrayToBase64(byteArray : ByteArray) : String{
        return Base64.encodeToString(byteArray, Base64.DEFAULT);
    }



}