package com.android.blinkitadminjc.navigation

import com.android.blinkitadminjc.viewmodel.AuthViewModel
import android.annotation.SuppressLint
import androidx.activity.ComponentActivity
import androidx.compose.runtime.Composable
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.android.blinkitadminjc.screens.AddProduct
import com.android.blinkitadminjc.screens.PhoneAuthScreen
import com.android.blinkitadminjc.screens.bottomNav
import com.android.blinkitadminjc.screens.homeScreen
import com.android.blinkitadminjc.screens.orderScreen
import com.android.blinkitadminjc.screens.splashScreen
import com.android.blinkitadminjc.viewmodel.AdminViewModel

@SuppressLint("SuspiciousIndentation")
@Composable
fun navGraph(navController: NavHostController,activity: ComponentActivity){
    val viewModel: AuthViewModel = viewModel()
    val adminViewModel: AdminViewModel = viewModel()

        NavHost(navController = navController, startDestination = Routes.AddProduct.routes ){

            composable(Routes.Splash.routes){
                splashScreen(navController,viewModel)
            }

            composable(Routes.Otp.routes){
            PhoneAuthScreen(viewModel =viewModel , activity,navController)
            }

            composable(Routes.AddProduct.routes){
                AddProduct(navController)
            }

            composable(Routes.Order.routes){
                orderScreen(navController)
            }

            composable(Routes.HomeScreen.routes){
                homeScreen(navController)
            }

            composable(Routes.BottomNav.routes){
                bottomNav(navController)
            }

        }
    }
