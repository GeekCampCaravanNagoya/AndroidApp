package com.kotlincocktail.pourpal.navigation

import androidx.camera.core.ImageProxy
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.kotlincocktail.pourpal.entity.Cocktail
import com.kotlincocktail.pourpal.views.CameraView
import com.kotlincocktail.pourpal.views.HomeView
import com.kotlincocktail.pourpal.views.LoadingView
import com.kotlincocktail.pourpal.views.ResultView

@Composable
fun Navigation() {
    val navController = rememberNavController()
    var imageProxy by remember { mutableStateOf<ImageProxy?>(null) }
    var resultList by remember { mutableStateOf<List<Cocktail>>(emptyList()) }

    NavHost(
        navController = navController,
        startDestination = "home"
    ) {
        composable(route = "home"){
            HomeView(navController=navController)
        }
        composable(route = "camera") {
            CameraView(
                navController=navController,
                capturedImageProxy= {
                    imageProxy = it
                }
            )
        }
        composable(route = "loading") {
            LoadingView(
                navController=navController,
                imageProxy = imageProxy,
                resultList = {
                    resultList = it
                }
            )
        }
        composable(route = "result") {
//            ResultCardView(resultString)
            ResultView(navController,resultList)
        }
        composable(route = "search") {
            HomeView(navController =navController)
        }
    }
}

