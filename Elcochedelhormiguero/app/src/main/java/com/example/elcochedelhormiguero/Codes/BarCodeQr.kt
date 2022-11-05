package com.example.elcochedelhormiguero.Codes

import android.graphics.Bitmap
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.asImageBitmap
import com.google.zxing.BarcodeFormat
import com.google.zxing.MultiFormatWriter
import com.google.zxing.common.BitMatrix

class BarCodeQr() {

    private fun BitMatrix.toBitmap(): Bitmap {
        return Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888).apply {
            for (x in 0 until width) {
                for (y in 0 until height) {
                    val pixelColor = if (get(x, y)) {
                        android.graphics.Color.BLACK
                    } else {
                        android.graphics.Color.WHITE
                    }
                    setPixel(x, y, pixelColor)
                }
            }
        }
    }

    fun getImageBitmap(width: Int, height: Int, value: String): ImageBitmap =
        MultiFormatWriter().encode(value, BarcodeFormat.QR_CODE, width, height)
            .toBitmap()
            .asImageBitmap()

    fun isValueValid(valueToCheck: String): Boolean {
        val barcode = try {
            MultiFormatWriter().encode(valueToCheck, BarcodeFormat.QR_CODE, 25, 25)
        } catch (e: Exception) {
            null
        }

        return barcode != null
    }
}