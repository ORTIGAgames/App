package com.example.elcochedelhormiguero.Codes


import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.painter.BitmapPainter
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

@Composable
fun Barcode(
    modifier: Modifier = Modifier,
    showProgress: Boolean = true,
    resolutionFactor: Int = 10,
    value: String,
    Bar: BarCodeQr
) {
    val barcodeBitmap = remember { mutableStateOf<ImageBitmap?>(null) }
    val scope = rememberCoroutineScope()

    LaunchedEffect(value) {//siempre que los valores cambios launcheffected se ejecutara (cuando cambie el codigo para generar el bitmap nuevo)
        scope.launch {//lanza corutinas sin detener la principal
            withContext(Dispatchers.Default) {
                barcodeBitmap.value = try {
                    Bar.getImageBitmap(
                        width = 128 * resolutionFactor,
                        height = 128 * resolutionFactor,
                        value = value
                    )
                } catch (e: Exception) {
                    Log.e("ComposeBarcodes", "Invalid Barcode Format", e)
                    null
                }
            }
        }
    }

    // Box contiene al codigo ene unas dimensiones especificas
    Box(modifier = modifier) {
        //si hay codigo disponible para enseñar lo muestra si no el codigo no ha sido generado entero y muestra un circulo cargando en su lugar
        barcodeBitmap.value?.let { barcode ->
            Image(
                modifier = Modifier.fillMaxSize(),
                painter = BitmapPainter(barcode),
                contentDescription = value
            )
        } ?: run {
            if (showProgress) {//mientras no este el codigo qr en pantalla saldrá un círculo
                CircularProgressIndicator(
                    modifier = Modifier
                        .fillMaxSize(0.5f)
                        .align(Alignment.Center)
                )
            }
        }
    }
}

@Deprecated("Please set desired height and width on the modifier")//para poner el tamaño deseado de la caja
@Composable
fun Barcode(
    modifier: Modifier = Modifier,
    width: Dp = 50.dp,
    height: Dp = 50.dp,
    showProgress: Boolean = true,
    value: String
) {
    Barcode(
        modifier = modifier
            .width(width = width)
            .height(height = height),
        showProgress = showProgress,
        value = value,
        Bar = BarCodeQr()
    )
}