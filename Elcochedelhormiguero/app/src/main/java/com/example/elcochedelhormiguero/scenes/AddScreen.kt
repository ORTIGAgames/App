package com.example.elcochedelhormiguero.scenes

import androidx.compose.foundation.layout.*
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.elcochedelhormiguero.Screen
import com.example.elcochedelhormiguero.*

@Composable
fun AddScreen(navController: NavController){
    var text by remember{
        mutableStateOf("")
    }
    var code by remember{
        mutableStateOf("")
    }

    Column(
        verticalArrangement= Arrangement.Center,
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 50.dp)
    ){

        Text("Esto es el lector QR")
        Spacer(modifier= Modifier.height(8.dp))
        TextField(
            value = text,
            onValueChange = {text=it},
            modifier= Modifier.fillMaxWidth()
        )
        Spacer(modifier= Modifier.height(8.dp))
        code = QrCodeScanner()
        Button(
            onClick={

                navController.navigate(
                    //builder.toString()
                    Screen.ColectionScreen.route+"?name=$text"+"?code=$code"
                )
            },
            modifier = Modifier.align(Alignment.End)
        ){
            Text(text="Add new one")
        }

    }
}
