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
import com.kotlincocktail.pourpal.views.ResultListView
import com.kotlincocktail.pourpal.views.CameraView
import com.kotlincocktail.pourpal.views.MainView
import com.kotlincocktail.pourpal.views.LoadingView
import com.kotlincocktail.pourpal.views.ResultCardView

@Composable
fun Navigation() {
    val navController = rememberNavController()
    var imageProxy by remember { mutableStateOf<ImageProxy?>(null) }
    var resultString by remember { mutableStateOf("") }
    NavHost(
        navController = navController,
        startDestination = "main"
    ) {
        composable(route = "main") {
            MainView(navController=navController)
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
                resultString = {
                    resultString = it
                }
            )
        }
        composable(route = "result/card") {
//            ResultCardView(resultString)
            ResultCardView()
        }
        composable(route = "result/list") {
            ResultListView(navController =navController, names = arrayOf("a"))
        }
    }
}

