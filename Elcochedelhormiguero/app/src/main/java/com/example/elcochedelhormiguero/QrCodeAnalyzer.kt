package com.example.elcochedelhormiguero

import android.graphics.ImageFormat
import androidx.camera.core.ImageAnalysis
import androidx.camera.core.ImageProxy
import com.google.zxing.*
import com.google.zxing.common.HybridBinarizer
import java.nio.ByteBuffer

class QrCodeAnalyzer
    (private val onQrCodeScanned: (String) -> Unit//the string qr gives
): ImageAnalysis.Analyzer {

    private val supportedImageFormats = listOf(//lista de formatos de imagen que soporta para leer codigos qr
        ImageFormat.YUV_420_888,
        ImageFormat.YUV_422_888,
        ImageFormat.YUV_444_888,
    )

    override fun analyze(image: ImageProxy) {
        if(image.format in supportedImageFormats){//si el formato de la imagen es valido
            val bytes = image.planes.first().buffer.toByteArray()//queremos raw data, raw bytes, en un array como no hay funcion la creamos, la imagen en array de bytes

            val source = PlanarYUVLuminanceSource(//para quitar los pixeles innecesarios
                bytes,//imagen
                image.width,//anchura de la imagen
                image.height,//altura de la imagen
                0,//empezamos en la izquierda
                0,//empezamos arriba
                image.width,//acabamos en la anchura maxima de la imagen
                image.height,//acabamos en la altura maxima de la imagen
                false//no se hace un flip horizontal
            )
            //ahora creamos un bitmap que contiene la informacion de nuestro qrCode
            val binaryBmp = BinaryBitmap(HybridBinarizer(source))//hybridBinarizer esta diseñado para imagenes con datos en negro y fondo blanco (perfecto para qrcodes)
            //ahora hay que decodificarlo para sacar la información
            try{//try para cuando no haya qrcode salte excepcion diciendo que no lo ha encontrado
                val result = MultiFormatReader().apply {//MultiFormtarReader sirve para decodificar codigos de barra lo que es perfecto la vd
                    setHints(//con esta funcion definimos un mapa con los tipos de codigos que queremos analizar
                        mapOf(//aqui definimod que tipo de formato de imagen queremos
                            DecodeHintType.POSSIBLE_FORMATS to arrayListOf(
                                BarcodeFormat.QR_CODE//elegimos qrcode
                            )
                        )
                    )
                }.decode(binaryBmp)
                onQrCodeScanned(result.text)//podemos ya pasar la informacion de la imagen decodificada en texto al escaner
            }catch(e: Exception){//pillara la excepcion
                e.printStackTrace()
            } finally {
                image.close()
            }
        }
    }


    private fun ByteBuffer.toByteArray(): ByteArray{
        rewind()//asegurarse de mepezar desde el principio
        return ByteArray(remaining()).also{
            get(it)//asegurarse de coger todos los bytes en el array
        }
    }
}