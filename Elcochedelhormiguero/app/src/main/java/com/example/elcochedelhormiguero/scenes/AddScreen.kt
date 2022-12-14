package com.example.elcochedelhormiguero.scenes

import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.elcochedelhormiguero.Screen
import com.example.elcochedelhormiguero.*
import com.example.elcochedelhormiguero.ui.theme.Material3AppTheme


@Composable
fun AddScreen(navController: NavController) {
    var text by remember {
        mutableStateOf("")
    }
    var code by remember {
        mutableStateOf("")
    }
    Material3AppTheme {

        Surface(
            modifier = Modifier.fillMaxSize(),
            color = MaterialTheme.colorScheme.background
        ) {
            Column(
                verticalArrangement = Arrangement.Center,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 15.dp)
            ) {

                SmallTopAppBar(
                    title = {
                        Text(
                            "Añade un nuevo QR",
                        )

                    },
                    //modifier = Modifier.fillMaxWidth(),
                    navigationIcon = {
                        IconButton(onClick = { navController.navigate(Screen.ColectionScreen.route) }) {
                            Icon(
                                imageVector = Icons.Default.ArrowBack,
                                contentDescription = null,
                                tint = MaterialTheme.colorScheme.onPrimaryContainer
                            )
                        }
                    }
                )
                Spacer(modifier = Modifier.height(8.dp))
                Text(text = "Nombre del QR")
                Spacer(modifier = Modifier.height(8.dp))
                TextField(
                    value = text,
                    onValueChange = { text = it },
                    modifier = Modifier.fillMaxWidth()
                )

                Spacer(modifier = Modifier.height(10.dp))
                code = QrCodeScanner()
                Spacer(modifier = Modifier.height(10.dp))
                Button(
                    onClick = {
                        navController.navigate(Screen.ColectionScreen.route + "?name=$text" + "?code=$code")
                    },
                    modifier = Modifier.align(Alignment.End)
                ) {
                    Text(text = "Guardar")
                }

            }
        }
    }
}
