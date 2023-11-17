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
<<<<<<< HEAD
import com.kotlincocktail.pourpal.views.MainView
=======
>>>>>>> main
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
<<<<<<< HEAD

//        composable(route = "main") {
//            CameraView(
//                navController=navController,
//                applicationContext = applicationContext
//            )
//        }
//メモ
        composable(route = "home"){
            HomeView(navController=navController)
        }

        composable(route = "main") {
            MainView(navController=navController)
=======
        composable(route = "home"){
            HomeView(navController=navController)
>>>>>>> main
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
        
//メモ
//        composable(route = "page2") {
//            page2(navController)
//        }
//        composable(
//            route = "page3/{id}/{title}",
//            arguments = listOf(
//                navArgument("id") { type = NavType.IntType },
//                navArgument("title") { type = NavType.StringType }
//            )
//        ){arguments ->
//            val id = arguments.arguments?.getInt("id") ?: ""
//            val title = arguments.arguments?.getString("title") ?: ""
//            page3(id,title)
//        }
    }
}

