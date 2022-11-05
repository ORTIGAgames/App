package com.example.elcochedelhormiguero
//El navArguments no se como funciona y todo da error me quiero morir :D
import androidx.compose.runtime.Composable
import androidx.navigation.NavType
import androidx.navigation.compose.rememberNavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.navArgument
import androidx.compose.material3.*
import com.example.elcochedelhormiguero.scenes.*

var items =0

@Composable
@ExperimentalMaterial3Api
fun Navigation(){

    val navController = rememberNavController()
    NavHost(navController=navController,startDestination=Screen.MainScreen.route){
        composable(route=Screen.MainScreen.route){
            MainScreen(navController = navController)
        }
        composable(route=Screen.YourCardScreen.route){
            YourCardScreen(navController = navController)
        }
        composable(route=Screen.AddScreen.route){
            AddScreen(navController = navController)
        }
        composable(
            route = Screen.ColectionScreen.route + "?name={name}" + "?code={code}",
            arguments= listOf(
                navArgument("name"){
                    type= NavType.StringType
                    nullable=true
                }
            )
        ){entry->
            ColectionScreen(navController = navController,name = entry.arguments?.getString("name"),code=entry.arguments?.getString("data"))
        }
    }
}








