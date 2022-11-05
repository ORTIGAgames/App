package com.example.elcochedelhormiguero

import androidx.compose.runtime.Composable
import com.example.elcochedelhormiguero.Codes.BarCodeQr
import com.example.elcochedelhormiguero.Codes.Qr

@Composable
fun Generator(){
    Qr(BarCodeQr(), "https://www.youtube.com/watch?v=TCoZoL2RFOU")
}