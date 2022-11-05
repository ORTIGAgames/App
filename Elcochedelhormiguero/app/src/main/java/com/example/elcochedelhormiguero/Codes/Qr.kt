package com.example.elcochedelhormiguero.Codes

import androidx.compose.foundation.layout.*
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp

@Composable
fun Qr(
    bar: BarCodeQr,
    value: String,
) {
    Surface() {
        Column(modifier = Modifier.fillMaxSize()) {
            if (bar.isValueValid(value)) {//generador del codigo Qr
                Barcode(//llamada a la funcion que crea el codigo
                    modifier = Modifier
                        .align(Alignment.CenterHorizontally)
                        .width(300.dp)
                        .height(300.dp),
                    Bar = bar,
                    value = value
                )
                Text(//texto de debajo
                    modifier = Modifier.fillMaxWidth(),
                    textAlign = TextAlign.Center,
                    text = "Enlace checkeable"
                )
            }
        }
    }
}