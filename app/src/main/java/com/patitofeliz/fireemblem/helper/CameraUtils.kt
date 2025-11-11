package com.patitofeliz.fireemblem.helper

import android.graphics.Bitmap
import android.util.Base64
import java.io.ByteArrayOutputStream

object CamaraUtils
{
    fun convertirDeBitMapABase64(bitmap: Bitmap): String
    {
        val outputStream = ByteArrayOutputStream()

        bitmap.compress(Bitmap.CompressFormat.JPEG, 90, outputStream)

        val byteArray = outputStream.toByteArray()

        return Base64.encodeToString(byteArray, Base64.NO_WRAP)
    }
}