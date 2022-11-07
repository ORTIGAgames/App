package com.example.elcochedelhormiguero

import androidx.compose.runtime.Composable
import androidx.navigation.NavType
import androidx.navigation.compose.rememberNavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.navArgument
import androidx.compose.material3.*
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import com.example.elcochedelhormiguero.scenes.*

var items =0 //Qr guardades
var nombres= mutableListOf<String>() //Nombres de los QR
var enlaces= mutableListOf<String>() //Enlaces escaneados por los QR

var cantidad=0 //cantidad de enlaces pripios guardados
var nom= mutableListOf<String>() //nombre de tus enlaces
var textos= mutableListOf<String>() //Enlaces propis guardados

@Composable
@ExperimentalMaterial3Api
fun Navigation(){

    val navController = rememberNavController()
    NavHost(navController=navController,startDestination=Screen.MainScreen.route){
        composable(route=Screen.MainScreen.route){
            MainScreen(navController = navController)
        }
        composable(route=Screen.AddScreen.route){
            AddScreen(navController = navController)
        }
        composable(route=Screen.OtherScreen.route){
            OtherScreen(navController = navController)
        }
        composable(
            route = Screen.ColectionScreen.route + "?name={name}" + "?code={code}",
            arguments= listOf(
                navArgument("name"){
                    type= NavType.StringType
                    nullable=true
                },
                navArgument("code"){
                    type= NavType.StringType
                    nullable=true
                }
            )
        ){entry->
            ColectionScreen(navController = navController,name = entry.arguments?.getString("name"),code=entry.arguments?.getString("data"))
        }
        composable(
            route = Screen.YourCardScreen.route + "?name={name}" + "?link={link}",
            arguments= listOf(
                navArgument("name"){
                    type= NavType.StringType
                    nullable=true
                },
                navArgument("link"){
                    type= NavType.StringType
                    nullable=true
                }
            )
        ){ entry->
                YourCardScreen(navController = navController,name = entry.arguments?.getString("name"),link=entry.arguments?.getString("link"))
        }
    }
}








