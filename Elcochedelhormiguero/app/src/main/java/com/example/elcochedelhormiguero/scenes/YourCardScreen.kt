package com.example.elcochedelhormiguero.scenes

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.elcochedelhormiguero.*
import com.example.elcochedelhormiguero.Codes.BarCodeQr
import com.example.elcochedelhormiguero.Codes.Barcode
import com.example.elcochedelhormiguero.ui.theme.Material3AppTheme
import com.example.elcochedelhormiguero.Codes.Qr as Qr


@ExperimentalMaterial3Api
@Composable
fun YourCardScreen(navController: NavController, name:String?, link:String?) {
    if (name != null  ) {
        cantidad++
        nom.add("$name")
        textos.add("$link")
    }

    Material3AppTheme {
        Surface(
            modifier = Modifier.fillMaxSize(),
            color = MaterialTheme.colorScheme.background
        ) {
            Scaffold(
                floatingActionButton = {
                    FloatingActionButton(
                        onClick = {
                            navController.navigate(Screen.OtherScreen.route)
                        }
                    ) {
                        Icon(
                            imageVector = Icons.Default.Add,
                            contentDescription = null,
                            tint = MaterialTheme.colorScheme.onPrimaryContainer
                        )
                    }
                },
                topBar = {
                    SmallTopAppBar(
                        title = {
                            Text(
                                "Tus enlaces",
                            )

                        },
                        navigationIcon = {
                            IconButton(onClick = { navController.navigate(Screen.MainScreen.route) }) {
                                Icon(
                                    imageVector = Icons.Default.ArrowBack,
                                    contentDescription = null,
                                    tint = MaterialTheme.colorScheme.onPrimaryContainer
                                )
                            }
                        }
                    )
                }

            ) { values ->
                LazyColumn(modifier = Modifier.padding(values)) {
                    itemsIndexed(nom){index,nombre->
                        Text(
                            text= nombre,
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(horizontal = 18.dp)
                        )

                        Text(
                            text= textos[index],
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(horizontal = 14.dp)
                        )
                        Qr(bar = BarCodeQr(), value = textos[index])
                        Button(
                            onClick = { },
                            modifier = Modifier
                                .fillMaxWidth()

                        ) {
                            Text(text = "Convertir a QR")

                        }
                    }

                }
                if (cantidad == 0) {
                    Box(
                        contentAlignment = Alignment.Center,
                        modifier = Modifier.fillMaxSize()
                    ) {
                        Text(text = "Todavía no tienes enlaces")
                    }
                }
            }

        }
    }
}
