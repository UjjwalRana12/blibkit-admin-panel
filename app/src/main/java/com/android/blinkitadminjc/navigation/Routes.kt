package com.android.blinkitadminjc.navigation

sealed class Routes(val routes: String){

    object Splash:Routes("splash")
    object Login:Routes("login")

    object Otp:Routes("otp")
    object HomeScreen:Routes("home")
    object Order:Routes("order")
    object AddProduct:Routes("add")
    object BottomNav : Routes("bottom_nav")

    object UserActivity:Routes("user_activity")



}
