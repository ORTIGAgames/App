package com.example.elcochedelhormiguero.scenes

import androidx.compose.foundation.layout.*
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.elcochedelhormiguero.Screen

@Composable
fun MainScreen(navController: NavController){
    Column(
        verticalArrangement= Arrangement.Center,
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 50.dp)
    ) {
        Button(
            onClick={
                navController.navigate(Screen.ColectionScreen.route)
            },
            modifier = Modifier.align(Alignment.CenterHorizontally)
        ){
            Text(text="You colection")
        }
        Spacer(modifier= Modifier.height(8.dp))
        Button(
            onClick={
                navController.navigate(Screen.YourCardScreen.route)
            },
            modifier = Modifier.align(Alignment.CenterHorizontally)
        ){
            Text(text="Your Card")
        }
    }
}