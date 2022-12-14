package com.example.elcochedelhormiguero.scenes

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.elcochedelhormiguero.ImageCard
import com.example.elcochedelhormiguero.Screen
import com.example.elcochedelhormiguero.items
import com.example.elcochedelhormiguero.nombres
import com.example.elcochedelhormiguero.enlaces
import com.example.elcochedelhormiguero.ui.theme.Material3AppTheme


@ExperimentalMaterial3Api
@Composable
fun ColectionScreen(navController: NavController, name:String?, code:String?) {
    if (name != null  && code!=null) {
        items++
        nombres.add("$name")
        enlaces.add("$code")
    }

    Material3AppTheme {

        Surface(
            modifier = Modifier.fillMaxSize(),
            color = MaterialTheme.colorScheme.background
        ) {
            Scaffold(
                floatingActionButton = {
                    FloatingActionButton(
                        onClick = { navController.navigate(Screen.AddScreen.route) }
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
                                "Tu colecci??n",
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
                LazyColumn(contentPadding = values) {
                    itemsIndexed(nombres) {index, nombre->
                        ImageCard(
                            title = nombre,
                            description = "Pulsa el bot??n para abrir la pagina web ",
                            code=enlaces[index],
                            context=  LocalContext.current,
                            modifier = Modifier.padding(16.dp)
                        )

                    }

                }

                if (items == 0) {
                    Box(
                        contentAlignment = Alignment.Center,
                        modifier = Modifier.fillMaxSize()
                    ) {
                        Text(text = "Todav??a no tienes tarjetas")
                    }
                }
            }
        }
    }
}