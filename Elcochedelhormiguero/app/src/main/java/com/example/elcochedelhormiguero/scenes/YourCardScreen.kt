package com.example.elcochedelhormiguero.scenes

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
//Aqui hay que hacer todo lo de que convierta los links en QR
@Composable
fun YourCardScreen(navController: NavController){
    Column(
        verticalArrangement= Arrangement.Center,
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 50.dp)
    ){
        Text("Aqui van cosas pero estamos trabajando en ello")
    }
}