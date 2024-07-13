package com.android.blinkitadminjc.screens

import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Add
import androidx.compose.material.icons.rounded.Home
import androidx.compose.material.icons.rounded.Person
import androidx.compose.material.icons.rounded.Phone
import androidx.compose.material.icons.rounded.Star
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.android.blinkitadminjc.model.BottomNavItem
import com.android.blinkitadminjc.navigation.Routes

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun bottomNav(navController: NavHostController){

    val navController1 = rememberNavController()

    Scaffold(bottomBar = { BottomNavBar(
        navController1 = navController1)
    }) { innerpadding ->
        NavHost(
            navController = navController1,
            startDestination = Routes.HomeScreen.routes,
            modifier = Modifier.padding(innerpadding)
        ) {

            composable(Routes.HomeScreen.routes) {
               homeScreen(navController)
            }

            composable(route = Routes.Order.routes) {
                orderScreen(navController)
            }

            composable(route = Routes.AddProduct.routes) {
                AddProduct(navController)
            }

        }

    }

}

@Composable
fun BottomNavBar(navController1: NavHostController) {

    val backStackEntry = navController1.currentBackStackEntryAsState()

    val list = listOf(
        BottomNavItem("Home", Routes.HomeScreen.routes, Icons.Rounded.Home),
        BottomNavItem("Add", Routes.AddProduct.routes, Icons.Rounded.Add),
        BottomNavItem("Order", Routes.Order.routes, Icons.Rounded.Phone),
    )

    BottomAppBar {

        list.forEach {

            val selected = it.route == backStackEntry.value?.destination?.route

            NavigationBarItem(selected = selected,
                onClick = {
                    navController1.navigate(it.route) {
                        popUpTo(navController1.graph.findStartDestination().id) {
                            saveState = true

                        }
                        launchSingleTop = true
                    }
                },
                icon = { Icon(imageVector = it.icon, contentDescription = null) })
        }
    }

}