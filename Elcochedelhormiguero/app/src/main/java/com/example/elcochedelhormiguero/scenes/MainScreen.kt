package com.example.elcochedelhormiguero.scenes

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.elcochedelhormiguero.Screen
import com.example.elcochedelhormiguero.ui.theme.Material3AppTheme
@ExperimentalMaterial3Api
@Composable
fun MainScreen(navController: NavController){
    Material3AppTheme {

        Surface(
            modifier = Modifier.fillMaxSize(),
            color = MaterialTheme.colorScheme.background
        ) {
            Column(
                verticalArrangement = Arrangement.Center,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 50.dp)

            ) {
                Button(
                    onClick = {
                        navController.navigate(Screen.ColectionScreen.route)
                    },
                    modifier = Modifier.align(Alignment.CenterHorizontally).size(250.dp)
                ) {
                    Text(text = "Colección de QR")
                }
                Spacer(modifier = Modifier.height(8.dp))
                Button(
                    onClick = {
                        navController.navigate(Screen.YourCardScreen.route)
                    },
                    modifier = Modifier.align(Alignment.CenterHorizontally).size(250.dp)
                ) {
                    Text(text = "Tus QRs")
                }
            }
        }
    }
}