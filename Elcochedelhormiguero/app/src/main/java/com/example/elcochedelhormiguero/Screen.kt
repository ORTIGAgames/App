package com.example.elcochedelhormiguero

sealed class Screen(val route:String) {
    object MainScreen : Screen("main_screen")
    object YourCardScreen: Screen("your_card_screen")
    object ColectionScreen: Screen("colection_screen")
    object AddScreen: Screen("add_screen")
    object OtherScreen: Screen("other_screen")

    fun withArgs(vararg args:String):String{
        return buildString {
            append(route)
            args.forEach { arg->
                append("/$arg")
            }
        }
    }
}