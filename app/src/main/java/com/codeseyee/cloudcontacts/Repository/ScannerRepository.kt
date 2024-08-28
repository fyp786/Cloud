package com.codeseyee.cloudcontacts.Repository


import android.graphics.Bitmap
import com.codeseye.cloudcontacts.models.User
import com.codeseyee.cloudcontacts.Models.UserQR
import com.google.gson.Gson
import com.google.zxing.BinaryBitmap
import com.google.zxing.LuminanceSource
import com.google.zxing.MultiFormatReader
import com.google.zxing.RGBLuminanceSource
import com.google.zxing.Reader
import com.google.zxing.Result
import com.google.zxing.common.HybridBinarizer

class ScannerRepository {

    fun scanQRImage(bitmap: Bitmap): String? {
        val intArray = IntArray(bitmap.width * bitmap.height)
        bitmap.getPixels(intArray, 0, bitmap.width, 0, 0, bitmap.width, bitmap.height)

        val source: LuminanceSource = RGBLuminanceSource(bitmap.width, bitmap.height, intArray)
        val binaryBitmap = BinaryBitmap(HybridBinarizer(source))

        val reader: Reader = MultiFormatReader()
        return try {
            val result: Result = reader.decode(binaryBitmap)
            result.text
        } catch (e: Exception) {
            e.printStackTrace()
            null
        }
    }

    fun handleQRCodeResult(result: String): User? {
        return try {
            val userQR = Gson().fromJson(result, UserQR::class.java)
            userQR.toUser()
        } catch (e: Exception) {
            null
        }
    }
}
